package com.unibuc.twitterapp.pojo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String mail;
    private Boolean isCurrentUser;
    private Boolean currentUserFollows;

}