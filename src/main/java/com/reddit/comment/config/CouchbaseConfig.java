package com.reddit.comment.config;


import com.couchbase.client.java.Cluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;


@EnableCouchbaseRepositories
@ConfigurationProperties(prefix = "spring.couchbase")
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${COUCHBASE_BUCKET_NAME}")
    private String bucketName;

    @Value("${COUCHBASE_USERNAME}")
    private String username;

    @Value("${COUCHBASE_PASSWORD}")
    private String password;

    @Value("${COUCHBASE_CONNECTION_STRING}")
    private String connectionString;


    @Override
    public CouchbaseClientFactory couchbaseClientFactory(Cluster couchbaseCluster) {
        return super.couchbaseClientFactory(couchbaseCluster);
    }

    @Override
    @Bean(value = "customConversions")
    public CustomConversions customConversions() {
        return super.customConversions();
    }

    @Override
    public String getConnectionString() {
        return this.connectionString;
    }

    @Override
    public String getUserName() {

        return this.username;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getBucketName() {

        return this.bucketName;
    }

}
