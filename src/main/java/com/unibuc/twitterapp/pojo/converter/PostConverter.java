package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Like;
import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.dto.PostDto;
import com.unibuc.twitterapp.service.AuthHelperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostConverter {

    private final AuthHelperImpl authHelper;

    public Post fromPostToEntity(String message, User user) {
        return Post.builder()
                .user(user)
                .message(message)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public PostDto fromEntityToPost(Post post) {
        return PostDto.builder()
                .message(post.getMessage())
                .timeStamp(post.getTimeStamp())
                .build();

    }

    public FeedPostDto fromEntityToPost(Post post, User user) {
        return FeedPostDto.builder()
                .id(post.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .message(post.getMessage())
                .timeStamp(post.getTimeStamp())
                .ownPost(post.getUser().getUsername().equals(authHelper.getUserDetails().getUsername()))
                .currentUserLicked(post.getLikes().stream().map(Like::getUser).map(User::getUsername).collect(Collectors.toList()).contains(authHelper.getUserDetails().getUsername()))
                .build();

    }


}
