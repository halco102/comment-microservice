package com.reddit.comment.feign.user.controller;

import com.reddit.comment.feign.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${USER_CLIENT_URL}")
public interface UserClient {

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/security/find-by-username")
    UserDto findUserByUsername(@RequestParam("username") String username);

}
