package com.reddit.comment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.reddit.comment.controller.test.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class CommentService {

    private ToTest singleMainComment;

    private ToTest withReplies;

    private RequestComment requestComment;

    @BeforeEach
    public void beforeEach() {

        singleMainComment = new ToTest("1", "main", new LinkedHashSet<>(), new ArrayList<>());

        withReplies = new ToTest("1", "main", new LinkedHashSet<>(), new ArrayList<>());
        withReplies.setReplies(new ArrayList<>(Arrays.asList(new ToTest("2", "r1", new LinkedHashSet<>(Arrays.asList("1")), new ArrayList<>()))));
    }


    @Test
    public void addingReplyToMainComment() {
        //test when adding first reply to comment
        ToTest main = singleMainComment;

        //request from fe
        requestComment = new RequestComment();
        requestComment.setComment("r1");
        requestComment.setParentId("1");

        //reply
        ToTest reply = new ToTest();
        reply.setId("2");
        reply.setComment(requestComment.getComment());
        //add parent comment ids
        reply.getParentIds().addAll(main.getParentIds());
        //add the requested id
        reply.getParentIds().add(requestComment.getParentId());

        //add the comment to main parent
        addReplyToComment(main, reply);

        Assertions.assertEquals(withReplies.getReplies().size(), main.getReplies().size());
        Assertions.assertEquals(withReplies.getReplies().get(0), main.getReplies().get(0));
    }

    @Test
    public void testAddingReplyToAReply() {
        //find the parent from request in this case the first element in replies
        var fetchFromDbById = withReplies.getReplies().get(0);

        //request from fe
        requestComment = new RequestComment();
        requestComment.setComment("r1.1");
        requestComment.setParentId("2");

        //reply
        ToTest reply = new ToTest();
        reply.setId("3");
        reply.setComment(requestComment.getComment());
        //add parent comment ids
        reply.getParentIds().addAll(fetchFromDbById.getParentIds());
        //add the requested id
        reply.getParentIds().add(requestComment.getParentId());

        addReplyToComment(fetchFromDbById, reply);

        Assertions.assertEquals(1, fetchFromDbById.getReplies().size());
        Assertions.assertEquals(reply.getId(), fetchFromDbById.getReplies().get(0).getId());
        Assertions.assertEquals(reply.getParentIds().size(), fetchFromDbById.getReplies().get(0).getParentIds().size());


        System.out.println("end");
    }

    private void addReplyToComment(ToTest parentComment, ToTest reply) {
        parentComment.getReplies().add(reply);
    }




}
