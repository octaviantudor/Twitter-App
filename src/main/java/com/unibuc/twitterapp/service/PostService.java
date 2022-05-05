package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    /**
     * Adding a new post based on logged user
     *
     * @param message
     * @param mentionIds
     */
    void addPost(String message, List<String> mentionIds);


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
    Page<FeedPostDto> getPostFeed(int pageNumber, int pageSize, Optional<String> sort);

    Boolean currentUserLikedPost(String postId);

    /**
     * Deletes a post based on id
     * @param postId
     */
    void deletePost(long postId);

    Page<FeedPostDto> findPaginatedFiltered(Pageable pageable, String username, String message);
}
