package com.unibuc.twitterapp.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
