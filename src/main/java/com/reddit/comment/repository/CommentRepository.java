package com.reddit.comment.repository;

import com.reddit.comment.model.comment.Comment;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends BaseCommentRepository<Comment> {

    @Query("#{#n1ql.selectEntity} WHERE postDto.id = $1 AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<Comment> getLatestCommentFromPost(Long postId);

    @Query("#{#n1ql.selectEntity} WHERE userDto.id = $1 AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<Comment> getAllUserComments(Long userId);

    @Query("Select count(comment) from `reddit-comment` as comment where comment.postDto.id = $1")
    Long numberOfCommentsInPost(Long postId);

    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} and meta().id = $1 or $1 in replies[*].id")
    Optional<Comment> likeDislikeComment(String id);
}
