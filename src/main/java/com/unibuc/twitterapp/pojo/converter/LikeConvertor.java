package com.unibuc.twitterapp.pojo.converter;

import com.unibuc.twitterapp.persistence.entity.Like;
import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.User;

public class LikeConvertor {

    public static Like toEntity(Post post, User user) {
        return Like.builder()
                .post(post)
                .user(user)
                .build();
    }

}
