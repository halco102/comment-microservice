package com.reddit.comment.service.impl;

import com.reddit.comment.exception.NotFoundException;
import com.reddit.comment.feign.post.controller.PostClient;
import com.reddit.comment.feign.user.controller.UserClient;
import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.model.comment.Reply;
import com.reddit.comment.payload.comment.CommentRequest;
import com.reddit.comment.repository.CommentRepository;
import com.reddit.comment.repository.ReplyRepository;
import com.reddit.comment.security.JwtTokenUtil;
import com.reddit.comment.service.IComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public Comment saveComment(CommentRequest request, String jwt) {

        var fetchUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        var fetchPost = postClient.getPostById(request.getPostId());

        if (fetchPost == null || fetchUser == null)
            throw new NotFoundException("User or post are null");

        if (request.getParentId() != null){
            // find the parent comment
            var fetchParentComment = commentRepository.findById(request.getParentId());

            if (fetchParentComment.isEmpty())
                throw new NotFoundException("The parent comment does not exist");

            //save reply to db
            var saveReplyToDb = replyRepository.save(new Reply(null,
                    fetchPost, fetchUser,
                    request.getComment(), LocalDateTime.now(),
                    new ArrayList<>(),
                    request.getParentId()));

            //add reply to parent comment
            fetchParentComment.get().getReplies().add(saveReplyToDb);

            return commentRepository.save(fetchParentComment.get());
        }
        return commentRepository.save(new Comment(null, fetchPost, fetchUser, request.getComment(),LocalDateTime.now(), new ArrayList<>(),null));
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
