package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.config.UserContextHolder;
import com.unibuc.twitterapp.persistence.entity.Follow;
import com.unibuc.twitterapp.persistence.repository.FollowRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.service.exception.InvalidUserRequestException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final UserContextHolder userContextHolder;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void followUser(Long userId) {
        var user = userContextHolder.getUser();
        var followed = userRepository.findById(userId);

        if (followed.isEmpty())
            throw new UserNotFoundException("User with provided ID does not exist !");

        if (user.getId().equals(followed.get().getId()))
            throw new InvalidUserRequestException("You can not follow your own account !");

        if (followRepository.findByFromAndTo(user, followed.get()).isPresent())
            throw new InvalidUserRequestException("You already follow this account !");

        followRepository.save(Follow.builder()
                .from(user)
                .to(followed.get())
                .timeStamp(LocalDateTime.now())
                .build());

    }

    @Override
    public void unfollowUser(Long userId) {
        var user = userContextHolder.getUser();
        var followed = userRepository.findById(userId);

        if (followed.isEmpty())
            throw new UserNotFoundException("User with provided ID does not exist !");

        var followEntityOptional = followRepository.findByFromAndTo(user, followed.get());

        if (followEntityOptional.isEmpty())
            throw new InvalidUserRequestException("You are not following this account !");

        followRepository.delete(followEntityOptional.get());

    }
}
