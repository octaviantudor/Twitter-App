package com.unibuc.twitterapp.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedPostDto {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String message;

    private LocalDateTime timeStamp;

    private Boolean currentUserLicked;

    private Boolean ownPost;


}
