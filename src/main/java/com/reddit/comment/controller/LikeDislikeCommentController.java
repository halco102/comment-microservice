package com.reddit.comment.controller;

import com.reddit.comment.payload.comment.LikeDislikeCommentRequest;
import com.reddit.comment.security.JwtTokenUtil;
import com.reddit.comment.service.ILikeDislike;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment/like-dislike")
public class LikeDislikeCommentController {

    private final ILikeDislike iLikeDislike;

    @PostMapping
    public ResponseEntity<?> likeDislikeComment(@RequestBody LikeDislikeCommentRequest request, HttpServletRequest servletRequest) {
        return new ResponseEntity<>(iLikeDislike.likeDislikeComment(request, JwtTokenUtil.parseJwt(servletRequest)), HttpStatus.OK);
    }

}
