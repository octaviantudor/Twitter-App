package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.Reply;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.dto.ReplyDto;
import com.unibuc.twitterapp.service.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReplyConverter {

    private final AuthHelper authHelper;

    public Reply fromReplyToEntity(String message, Post post, User user) {
        return Reply.builder()
                .post(post)
                .message(message)
                .timeStamp(LocalDateTime.now())
                .user(user)
                .build();
    }

    public ReplyDto toDto(Reply reply){
        return ReplyDto.builder()
                .id(reply.getId().toString())
                .fromUsername(reply.getUser().getUsername())
                .message(reply.getMessage())
                .timeStamp(reply.getTimeStamp())
                .ownReply(reply.getUser().getUsername().equals(authHelper.getUserDetails().getUsername()))
                .build();
    }
}

