package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.dto.PostDto;
import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.payload.PostRequest;

import java.time.LocalDateTime;

public class PostConverter {

    public static Post fromPostToEntity(PostRequest postRequest, User user) {
        return Post.builder()
                .user(user)
                .message(postRequest.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static PostDto fromEntityToPost(Post post) {
        return PostDto.builder()
                .message(post.getMessage())
                .timeStamp(post.getTimeStamp())
                .build();

    }

    public static FeedPostDto fromEntityToPost(Post post, User user) {
        return FeedPostDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .message(post.getMessage())
                .timeStamp(post.getTimeStamp())
                .build();

    }


}
