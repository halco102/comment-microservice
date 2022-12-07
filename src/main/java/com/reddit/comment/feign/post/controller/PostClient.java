package com.reddit.comment.feign.post.controller;

import com.reddit.comment.feign.post.dto.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "postClient", url = "${POST_CLIENT_URL}")
public interface PostClient {

    @GetMapping("/{id}")
    PostDto getPostById(@PathVariable("id") Long id);

}
