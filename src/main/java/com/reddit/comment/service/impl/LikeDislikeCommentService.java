package com.reddit.comment.service.impl;

import com.reddit.comment.exception.NotFoundException;
import com.reddit.comment.feign.user.controller.UserClient;
import com.reddit.comment.feign.user.dto.UserDto;
import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
import com.reddit.comment.payload.comment.LikeDislikeCommentRequest;
import com.reddit.comment.repository.CommentRepository;
import com.reddit.comment.security.JwtTokenUtil;
import com.reddit.comment.service.ILikeDislike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDislikeCommentService implements ILikeDislike {

    private final CommentRepository commentRepository;

    private final UserClient userClient;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Comment likeDislikeComment(LikeDislikeCommentRequest request, String jwt) {

        var findComment = commentRepository.likeDislikeComment(request.getId());

        var getCurrentUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        if (getCurrentUser == null)
            throw new NotFoundException("User was not found");

        if (findComment.isEmpty())
            throw new NotFoundException("The comment does not exist");

        if (!findComment.get().getId().matches(request.getId())) {
            //means it is not the main comment, its nested reply
            var reply = findComment.get().getReplies()
                    .stream().filter(item -> item.getId().matches(request.getId())).findAny();

            if (reply.isEmpty())
                throw new NotFoundException("Does not exist");

            findAction(getCurrentUser, request.isLike(), reply.get().getLikeDislikeComments(), request.getId());


            return commentRepository.save(findComment.get());
        }

        findAction(getCurrentUser, request.isLike(), findComment.get().getLikeDislikeComments(), request.getId());

        return commentRepository.save(findComment.get());

    }

    //helper methods for like/dislike
    private void findAction(UserDto userDto, boolean isLike, List<LikeDislikeComment> likeDislikeComment, String commentId) {

        var findObject = likeDislikeComment.stream().filter(filt -> filt.getUserDto().getId() == userDto.getId()).findAny();

        if (findObject.isEmpty()) {
            likeDislikeComment.add(new LikeDislikeComment(commentId, userDto, isLike));
        } else if (findObject.get().isLike() == isLike && findObject.get().getUserDto().getId() == userDto.getId()) {
            likeDislikeComment.remove(findObject.get());
        } else if (findObject.get().isLike() != isLike && findObject.get().getUserDto().getId() == userDto.getId()) {
            findObject.get().setLike(isLike);
        } else {
            throw new NotFoundException("Exception");
        }
    }
}