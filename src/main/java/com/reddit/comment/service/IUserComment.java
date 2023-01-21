package com.reddit.comment.service;

import com.reddit.comment.model.comment.Comment;

import java.util.List;

public interface IUserComment {

    List<Comment> getAllUserComments(Long userId);


    //TODO
    /*
     * Kad user izbrise svoj account da se svi njegovi komentari izbrisu
     * to treba da bude uradjeno kao event, jer ce kafka poslati svim slusateljima
     * da je user izbrisan
     * */
    void deleteAllUserComments(Long userId);

}
