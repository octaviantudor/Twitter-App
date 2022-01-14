package com.unibuc.twitterapp.service.exception;

public class PostNotFoundException extends ServiceException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
