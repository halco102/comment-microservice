package com.reddit.comment.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class Test {

        @Autowired
        private repo testRepo;

        @PostMapping
        public ResponseEntity<?> addComment(RequestComment requestComment) {

            ToTest toTest = new ToTest();

            if (requestComment.getParentId() == null) {
                toTest.setComment(requestComment.getComment());
                return new ResponseEntity<>(testRepo.save(toTest), HttpStatus.OK);
            }

            //fetch patern comment
            toTest = testRepo.findById(requestComment.getParentId()).get();

            ToTest reply = new ToTest();
            reply.setComment(requestComment.getComment());
            reply.getParentIds().addAll(toTest.getParentIds());
            reply.getParentIds().add(requestComment.getParentId());

            var repl = testRepo.save(reply);

            var temp = returnType(repl);

            return new ResponseEntity<>(testRepo.save(temp), HttpStatus.OK);
        }

        public ToTest returnType(ToTest reply) {
            //nadji glavni
            var mainParent = testRepo.findById(reply.getParentIds().stream().findFirst().get());

            //u reply imam koji su leveli u pitanju za potraznju svog komentara kojeg trebam update-at
            List<String> levels = reply.getParentIds().stream().collect(Collectors.toList());

            if (mainParent.get().getReplies().isEmpty()) {
                mainParent.get().getReplies().add(reply);
                return mainParent.get();
            }

            if (levels.size() == 1 && mainParent.get().getId().matches(levels.get(0))) {
                mainParent.get().getReplies().add(reply);
                return mainParent.get();
            }

            String lastlevel = levels.get(levels.size() - 1);

            addReply(mainParent.get(), reply, lastlevel);
            
            return mainParent.get();
        }

    private void addReply(ToTest comment, ToTest newComment, String level) {

        if (comment.getId().matches(level)) {
            comment.getReplies().add(newComment);
            return;
        }

        for (ToTest reply : comment.getReplies()) {
            addReply(reply, newComment, level);
        }

    }
    


}
