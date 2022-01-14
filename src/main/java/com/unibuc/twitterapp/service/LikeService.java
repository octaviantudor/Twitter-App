package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;

import java.util.List;

public interface LikeService {

    /**
     * Like a post. Users can have only one like at a specific post.
     *
     * @param idPost
     * @throws PostNotFoundException if post does not exist.
     */
    void likePost(long idPost);

    /**
     * Return a list of users which liked a specific post.
     *
     * @param postId
     * @throws PostNotFoundException if post does not exist.
     */
    List<UserDto> getPostLikes(Long postId);
}
