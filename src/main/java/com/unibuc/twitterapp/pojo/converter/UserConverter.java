package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Follow;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;
import com.unibuc.twitterapp.service.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final AuthHelper authHelper;

    public User fromRequestToEntity(UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .mail(userRegistrationRequest.getMail())
                .build();
    }

    public UserDto fromEntityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .isCurrentUser(user.getUsername().equals(authHelper.getUserDetails().getUsername()))
                .currentUserFollows(user.getFollowers().stream().map(Follow::getFrom).map(User::getUsername).collect(Collectors.toList()).contains(authHelper.getUserDetails().getUsername()))
                .build();
    }

}
