package com.unibuc.twitterapp.pojo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String mail;

}