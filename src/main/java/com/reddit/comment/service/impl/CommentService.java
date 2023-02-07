package com.reddit.comment.service.impl;

import com.reddit.comment.exception.BadRequestException;
import com.reddit.comment.exception.NotFoundException;
import com.reddit.comment.feign.post.controller.PostClient;
import com.reddit.comment.feign.user.controller.UserClient;
import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.payload.comment.CommentRequest;
import com.reddit.comment.repository.CommentRepository;
import com.reddit.comment.repository.ReplyRepository;
import com.reddit.comment.security.JwtTokenUtil;
import com.reddit.comment.service.IComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService implements IComment {

    private final CommentRepository commentRepository;

    private final UserClient userClient;

    private final PostClient postClient;

    private final ReplyRepository replyRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public Comment saveComment(CommentRequest request, String jwt) {

        var fetchUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        var fetchPost = postClient.getPostById(request.getPostId());

        Comment comment = new Comment();

        if (fetchPost == null || fetchUser == null)
            throw new NotFoundException("User or post are null");

        if (request.getComment() == null || request.getComment().isEmpty())
            throw new BadRequestException("The comment is null or empty");

        if (request.getParentId() == null) {
            comment.setComment(request.getComment());
            comment.setPostDto(fetchPost);
            comment.setUserDto(fetchUser);

            return commentRepository.save(comment);
        }

        //find the parent comment by id
        comment = commentRepository.findById(request.getParentId()).orElseThrow(() -> new NotFoundException("The comment does not exist"));

        Comment reply = new Comment();
        reply.setUserDto(fetchUser);
        reply.setPostDto(fetchPost);
        reply.getParentIds().addAll(comment.getParentIds());
        reply.getParentIds().add(request.getParentId());
        reply.setComment(request.getComment());

        //persist new comment/reply in db
        var saveReply = commentRepository.save(reply);

        var mainObject = addReplyToObjectAndReturnIt(reply);

        return commentRepository.save(mainObject);
   }


    public Comment addReplyToObjectAndReturnIt(Comment reply) {
        //nadji glavni
        var mainParent = commentRepository.findById(reply.getParentIds().stream().findFirst().get());

        //u reply imam koji su leveli u pitanju za potraznju svog komentara kojeg trebam update-at
        List<String> levels = reply.getParentIds().stream().collect(Collectors.toList());

        if (mainParent.get().getReplies().isEmpty()) {
            mainParent.get().getReplies().add(reply);
            return mainParent.get();
        }

        if (levels.size() == 1 && mainParent.get().getId().matches(levels.get(0))) {
            mainParent.get().getReplies().add(reply);
            return mainParent.get();
        }

        String lastlevel = levels.get(levels.size() - 1);

        addReply(mainParent.get(), reply, lastlevel);

        return mainParent.get();
    }

    private void addReply(Comment comment, Comment newComment, String level) {

        if (comment.getId().matches(level)) {
            comment.getReplies().add(newComment);
            return;
        }

        for (Comment reply : comment.getReplies()) {
            addReply(reply, newComment, level);
        }

    }


    @Override
    public void deleteCommentById(String id) {

    }

    @Override
    public Set<Comment> getCommentsByPostId(Long postId) {
        return null;
    }


    @Override
    public List<Comment> getLatestCommentsFromPost(Long postId) {
        return commentRepository.getLatestCommentFromPost(postId);
    }

    @Override
    public Long numberOfCommentsInPost(Long postId) {
        var fetchNumbers = commentRepository.numberOfCommentsInPost(postId);

        return fetchNumbers;
    }


}
