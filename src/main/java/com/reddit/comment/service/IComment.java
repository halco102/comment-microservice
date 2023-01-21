package com.reddit.comment.service;

import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.payload.comment.CommentRequest;

import java.util.List;
import java.util.Set;

public interface IComment {

    /*
    * Create a comment
    *
    * @param CommentRequest (userId, postId, content of the comment, parentId)
    *
    * The parent ID can be null, when its null it is the main comment.
    * If it is not null then it is a reply of a comment
    * */
    Comment saveComment(CommentRequest request, String jwt);

    /*
    * Delete comment by id
    * */
    void deleteCommentById(String id);

    /*
    * Get all comments by post Id
    * */
    Set<Comment> getCommentsByPostId(Long postId);

    /*
    * Sort comments by createdAt
    * */
    List<Comment> getLatestCommentsFromPost(Long postId);

    /*
    * Get number of comments from a post
    * */
    Long numberOfCommentsInPost(Long postId);


}
