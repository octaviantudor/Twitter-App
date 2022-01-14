package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.Reply;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.payload.ReplyRequest;

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

