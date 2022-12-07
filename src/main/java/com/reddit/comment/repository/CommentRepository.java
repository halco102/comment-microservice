package com.reddit.comment.repository;

import com.reddit.comment.model.comment.Comment;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CouchbaseRepository<Comment, String> {

    @Query("#{#n1ql.selectEntity} WHERE postDto.id = $1 AND #{#n1ql.filter} ORDER BY createdAt DESC")
    List<Comment> getLatestCommentFromPost(Long postId);

}
