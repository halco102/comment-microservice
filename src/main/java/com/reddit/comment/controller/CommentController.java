package com.reddit.comment.controller;

import com.reddit.comment.payload.comment.CommentRequest;
import com.reddit.comment.security.JwtTokenUtil;
import com.reddit.comment.service.IComment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final IComment iComment;


    @PostMapping
    public ResponseEntity<?> saveComment(@RequestBody CommentRequest request, HttpServletRequest servletRequest) {
        return new ResponseEntity<>(iComment.saveComment(request, JwtTokenUtil.parseJwt(servletRequest)), HttpStatus.OK);
    }

    @GetMapping("/latest/{postId}")
    public ResponseEntity<?> getLatestCommentsFromPost(@PathVariable Long postId) {
        return new ResponseEntity<>(iComment.getLatestCommentsFromPost(postId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> getNumberOfCommentsInPost(@PathVariable Long postId) {
        return new ResponseEntity<>(iComment.numberOfCommentsInPost(postId), HttpStatus.OK);
    }

}
