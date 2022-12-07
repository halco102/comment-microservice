package com.reddit.comment.service.impl;

import com.reddit.comment.exception.NotFoundException;
import com.reddit.comment.feign.post.controller.PostClient;
import com.reddit.comment.feign.user.controller.UserClient;
import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.payload.comment.CommentRequest;
import com.reddit.comment.repository.CommentRepository;
import com.reddit.comment.service.IComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService implements IComment {

    private final CommentRepository commentRepository;

    private final UserClient userClient;

    private final PostClient postClient;


    @Override
    public Comment saveComment(CommentRequest request) {

        var fetchUser = userClient.getUserById(request.getUserId());

        var fetchPost = postClient.getPostById(request.getPostId());

        if (fetchPost == null || fetchUser == null)
            throw new NotFoundException("User or post are null");

        return commentRepository.save(new Comment(null, fetchPost, fetchUser, request.getComment()));
    }

    @Override
    public void deleteCommentById(String id) {

    }

    @Override
    public Set<Comment> getCommentsByPostId(Long postId) {
        return null;
    }

}
