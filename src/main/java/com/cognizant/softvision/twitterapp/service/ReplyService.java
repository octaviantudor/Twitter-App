package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.pojo.payload.ReplyRequest;

public interface ReplyService {

    /**
     * Adds a reply to a specific post.
     * @param postId
     */
    void addReplyToPost(ReplyRequest replyRequest);
}
