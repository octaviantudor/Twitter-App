package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.pojo.dto.UserDto;
import com.cognizant.softvision.twitterapp.pojo.payload.LoginRequest;
import com.cognizant.softvision.twitterapp.pojo.payload.UserRegistrationRequest;
import com.cognizant.softvision.twitterapp.service.exception.AlreadyExistsException;
import com.cognizant.softvision.twitterapp.service.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    /**
     * Creates a new user if there is no other account with given email.
     *
     * @param userRegistrationRequest not null, contains user data
     * @throws AlreadyExistsException if user email already exists.
     */
    void registerUser(UserRegistrationRequest userRegistrationRequest);

    /**
     * Search for users. Optional parameters.
     * @param firstName
     * @param lastName
     * @param mail
     * @return list of users found
     */
    List<UserDto> searchUser(String firstName, String lastName, String mail);

    /**
     * Log in of the current user by username and password, generates and stores a token for each user
     *
     * @param loginRequest contains user email and password
     * @throws UserNotFoundException if user email does not exists.
     * @return String which contains the generated token
     */
    String loginUser(LoginRequest loginRequest);


    void unregisterUser();
}
