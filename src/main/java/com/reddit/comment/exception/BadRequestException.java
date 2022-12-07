package com.reddit.comment.exception;

public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = -4146860850978717726L;

    public BadRequestException(final String message){
        super(message);
    }
}
