package com.cognizant.softvision.twitterapp.pojo.converter;

import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import com.cognizant.softvision.twitterapp.persistence.entity.Reply;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import com.cognizant.softvision.twitterapp.pojo.payload.ReplyRequest;

import java.time.LocalDateTime;

public class ReplyConverter {

    public static Reply fromReplyToEntity(ReplyRequest replyRequest, Post post, User user) {
        return Reply.builder()
                .post(post)
                .message(replyRequest.getMessage())
                .timeStamp(LocalDateTime.now())
                .user(user)
                .build();
    }
}

