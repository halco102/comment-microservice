package com.reddit.comment.service;

import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
import com.reddit.comment.payload.comment.LikeDislikeCommentRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ILikeDislike {

    Comment likeDislikeComment(LikeDislikeCommentRequest request, String jwt);

    List<LikeDislikeComment> getAllUserLikeDislikeOnComment(Long userId);

}
