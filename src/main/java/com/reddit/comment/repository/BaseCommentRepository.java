package com.reddit.comment.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseCommentRepository<T> extends CouchbaseRepository<T, String> {

    //void deleteAllCommentsByUser(String userId);
    @Query("Select * from `reddit-comment` where meta().id = $1")
    T returnObjectByMetaId(String metaId);

}
