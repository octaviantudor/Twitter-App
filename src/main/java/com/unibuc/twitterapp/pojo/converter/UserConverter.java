package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;


public class UserConverter {

    public static User fromRequestToEntity(UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .mail(userRegistrationRequest.getMail())
                .build();
    }

    public static UserDto fromEntityToDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .build();
    }

}
