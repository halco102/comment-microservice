package com.reddit.comment.service;

import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.payload.comment.CommentRequest;

import java.util.List;
import java.util.Set;

public interface IComment {

    Comment saveComment(CommentRequest request);

    void deleteCommentById(String id);

    Set<Comment> getCommentsByPostId(Long postId);

    List<Comment> getLatestCommentsFromPost(Long postId);

}
