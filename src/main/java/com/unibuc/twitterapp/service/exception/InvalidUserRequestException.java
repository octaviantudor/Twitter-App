package com.unibuc.twitterapp.service.exception;

public class InvalidUserRequestException extends ServiceException{
    public InvalidUserRequestException(String message) {
        super(message);
    }
}
