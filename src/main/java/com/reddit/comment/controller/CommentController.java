package com.reddit.comment.controller;

import com.reddit.comment.payload.comment.CommentRequest;
import com.reddit.comment.service.IComment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final IComment iComment;

    @PostMapping
    public ResponseEntity<?> saveComment(@RequestBody CommentRequest request) {
        return new ResponseEntity<>(iComment.saveComment(request), HttpStatus.OK);
    }

    @GetMapping("/latest/{postId}")
    public ResponseEntity<?> getLatestCommentsFromPost(@PathVariable Long postId) {
        return new ResponseEntity<>(iComment.getLatestCommentsFromPost(postId), HttpStatus.OK);
    }

}
