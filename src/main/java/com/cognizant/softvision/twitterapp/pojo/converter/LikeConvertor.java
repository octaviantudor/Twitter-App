package com.cognizant.softvision.twitterapp.pojo.converter;

import com.cognizant.softvision.twitterapp.persistence.entity.Like;
import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import com.cognizant.softvision.twitterapp.persistence.entity.User;

public class LikeConvertor {

    public static Like toEntity(Post post, User user) {
        return Like.builder()
                .post(post)
                .user(user)
                .build();
    }

}
