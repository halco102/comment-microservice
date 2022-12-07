package com.reddit.comment.repository;

import com.reddit.comment.model.comment.Reply;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends CouchbaseRepository<Reply, String> {
}
