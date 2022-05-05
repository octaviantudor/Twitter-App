package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.service.exception.InvalidUserRequestException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;

import java.util.List;

public interface FollowService {

    /**
     * This method allows an user to follow another account on social media
     * @param userId contains user id which will be followed
     * @throws UserNotFoundException if user with provided ID is not found
     * @throws InvalidUserRequestException if the user tries to follow himself or already follows the account with provided userId.
     */
    void followUser(Long userId);

    /**
     * This method allows an user to unfollow another account on social media
     * @param userId contains user id which will be unfollowed
     * @throws UserNotFoundException if user with provided ID is not found
     * @throws InvalidUserRequestException if the user does not follow the account with the provided userId.
     */
    void unfollowUser(Long userId);

    List<UserDto> getUserFollowers();

    List<UserDto> getUserFollowing();
}
