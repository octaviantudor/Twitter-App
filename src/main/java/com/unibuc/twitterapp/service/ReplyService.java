package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.ReplyDto;

import java.util.List;

public interface ReplyService {

    /**
     * Adds a reply to a specific post.
     * @param postId
     */
    void addReplyToPost(String postId, String message);

    List<ReplyDto> getPostReplies(long postId);
}
