package com.cognizant.softvision.twitterapp.controller;

import com.cognizant.softvision.twitterapp.service.exception.AlreadyExistsException;
import com.cognizant.softvision.twitterapp.service.exception.InvalidCredentialsException;
import com.cognizant.softvision.twitterapp.service.exception.InvalidUserRequestException;
import com.cognizant.softvision.twitterapp.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserRequestException.class)
    public ResponseEntity<String> handleInvalidUserRequestException(InvalidUserRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value=NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex)
    {
        return new ResponseEntity<>("Invalid number format !", HttpStatus.BAD_REQUEST);
    }

}
