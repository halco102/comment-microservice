package com.reddit.comment.service.impl;

import com.reddit.comment.exception.BadRequestException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDislikeCommentService implements ILikeDislike {

    private final CommentRepository commentRepository;

    private final UserClient userClient;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public Comment likeDislikeComment(LikeDislikeCommentRequest request, String jwt) {

        if (request == null || request.getId() == null || Boolean.valueOf(request.isLike()) == null)
            throw new BadRequestException("The request is null or the id is null");

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

        var saveObjectToDb = commentRepository.save(findComment.get());

        return saveObjectToDb;
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

    @Override
    public List<LikeDislikeComment> getAllUserLikeDislikeOnComment(Long userId) {

        List<LikeDislikeComment> result = new ArrayList<>();

        var mainComment = commentRepository.getAllUsersLikeDislikeFromMainComment(userId).orElseGet(() -> new ArrayList<>());
        var replies = commentRepository.getAllUserLikeDislikeFromReplies(userId).orElseGet(() -> new ArrayList<>());

        result.addAll(mainComment);
        result.addAll(replies);

        return result;
    }

}
