package com.reddit.comment.repository;

import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.model.likedislike.LikeDislikeComment;
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

/*    @Query("Select *, meta().id as __id, meta().cas as __cas from `reddit-comment`" +
            " WHERE any v in likeDislikeComments satisfies v.userDto.id = $1 END")*/
    @Query("SELECT  META(`rc`).`id` AS __id, ld.commentId, ld.userDto, ld.isLike " +
            "FROM `reddit-comment` as rc unnest rc.likeDislikeComments as ld where ld.userDto.id = $1")
    Optional<List<LikeDislikeComment>> getAllUsersLikeDislikeFromMainComment(Long userId);

    @Query("SELECT  META(`rc`).`id` AS __id, ld.commentId, ld.userDto, ld.isLike " +
            "FROM `reddit-comment` as rc unnest rc.replies as rp unnest rp.likeDislikeComments as ld where ld.userDto.id = $1")
    Optional<List<LikeDislikeComment>> getAllUserLikeDislikeFromReplies(Long userId);

}
