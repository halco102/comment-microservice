package com.reddit.comment.repository;

import com.reddit.comment.model.comment.Comment;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CouchbaseRepository<Comment, String> {
}
