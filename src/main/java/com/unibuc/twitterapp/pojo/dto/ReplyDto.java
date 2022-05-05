package com.unibuc.twitterapp.pojo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private String id;
    private Boolean ownReply;
    private String fromUsername;
    private String message;
    private LocalDateTime timeStamp;
}
