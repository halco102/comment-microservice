package com.reddit.comment.controller;

import com.reddit.comment.service.IUserComment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment/user")
@RequiredArgsConstructor
public class UserCommentController {

    private final IUserComment iUserComment;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllUserComments(@PathVariable Long id) {
        return new ResponseEntity<>(iUserComment.getAllUserComments(id), HttpStatus.OK);
    }

}
