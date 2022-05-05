package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Follow;
import com.unibuc.twitterapp.persistence.repository.FollowRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.UserConverter;
import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.service.exception.InvalidUserRequestException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final AuthHelper authHelper;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public void followUser(Long userId) {
        var user = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));
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
    @Transactional
    public void unfollowUser(Long userId) {
        var user = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));
        var followed = userRepository.findById(userId);

        if (followed.isEmpty())
            throw new UserNotFoundException("User with provided ID does not exist !");

        var followEntityOptional = followRepository.findByFromAndTo(user, followed.get());

        if (followEntityOptional.isEmpty())
            throw new InvalidUserRequestException("You are not following this account !");

        followRepository.delete(followEntityOptional.get());

    }

    @Override
    public List<UserDto> getUserFollowers() {
        var user = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        return followRepository.findByTo(user).stream()
                .map(Follow::getFrom)
                .map(userConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserFollowing() {
        var user = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        return followRepository.findByFrom(user).stream()
                .map(Follow::getTo)
                .map(userConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }


}
