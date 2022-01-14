package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Follow;
import com.unibuc.twitterapp.persistence.entity.Like;
import com.unibuc.twitterapp.persistence.entity.Mention;
import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.Reply;
import com.unibuc.twitterapp.persistence.entity.Token;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.payload.LoginRequest;
import com.unibuc.twitterapp.pojo.payload.PostRequest;
import com.unibuc.twitterapp.pojo.payload.ReplyRequest;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestObjectsBuilder {

    private static final String genericPassword = "someGenericPassword111!";
    private static final String genericMail = "someGenericMail@gmail.com";
    private static final String genericMessage = "someGenericMessage";


    public static Mention getMention() {
        return Mention.builder()
                .id(123L)
                .user(getUser())
                .post(getPost())
                .build();

    }

    public static Like getLike() {
        return Like.builder()
                .post(getPost())
                .user(getUser())
                .build();
    }

    public static ReplyRequest getReplyRequest() {
        return ReplyRequest.builder()
                .postId("8")
                .message(genericMessage)
                .build();
    }

    public static Reply getReply() {
        return Reply.builder()
                .post(getPost())
                .message(genericMessage)
                .timeStamp(LocalDateTime.now())
                .build();

    }

    public static PostRequest getPostRequest() {
        return PostRequest.builder()
                .message(genericMessage)
                .build();
    }


    public static Post getPost() {
        return Post.builder()
                .id(123L)
                .user(getUser())
                .message(genericMessage)
                .timeStamp(LocalDateTime.now())
                .likes(new ArrayList<>())
                .build();
    }

    public static User getUser() {
        return User.builder()
                .id(123L)
                .password(genericPassword)
                .build();
    }

    public static UserRegistrationRequest getUserRegistrationRequest() {
        return UserRegistrationRequest.builder()
                .firstName("John")
                .mail(genericMail)
                .password(genericPassword)
                .build();
    }

    public static Token getToken() {
        return Token.builder()
                .mail(genericMail)
                .token("someRandomToken")
                .build();
    }

    public static LoginRequest getLoginRequest() {
        return LoginRequest.builder()
                .mail(genericMail)
                .password(genericPassword)
                .build();
    }

    public static Follow getFollowEntity() {
        return Follow.builder()
                .from(new User())
                .to(new User())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
