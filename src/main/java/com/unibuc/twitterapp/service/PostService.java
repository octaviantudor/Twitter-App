package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.PostDto;
import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.payload.PostRequest;

import java.util.List;

public interface PostService {

    /**
     * Adding a new post based on logged user
     *
     * @param postRequest
     * @param mentionIds
     */
    void addPost(PostRequest postRequest, List<String> mentionIds);


    /**
     * Get all the posts from the logged user
     *
     * @return list of posts
     */
    List<PostDto> getOwnPosts();


    /**
     * Get all posts from all the followers, in order by timestamp
     *
     * @return list of posts
     */
    List<FeedPostDto> getPostFeed(int pageNumber, int pageSize);

    /**
     * Deletes a post based on id
     * @param postId
     */
    void deletePost(long postId);
}
