package com.reddit.comment.service.impl;

import com.reddit.comment.exception.NotFoundException;
import com.reddit.comment.feign.user.controller.UserClient;
import com.reddit.comment.model.comment.Comment;
import com.reddit.comment.repository.CommentRepository;
import com.reddit.comment.service.IUserComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserCommentService implements IUserComment {

    private final CommentRepository commentRepository;

    private final UserClient userClient;

    @Override
    public List<Comment> getAllUserComments(Long userId) {
        var fetchUserById = userClient.getUserById(userId);

        if (fetchUserById == null)
            throw new NotFoundException("The user was not found");

        var fetchAllUserComments = commentRepository.getAllUserComments(userId);

        return fetchAllUserComments;
    }

    @KafkaListener(topics = "USER_DELETE_EVENT", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void deleteAllUserComments(@Payload Long id) {

    }


}
