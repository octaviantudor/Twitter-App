package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.pojo.dto.UserDto;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;

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
