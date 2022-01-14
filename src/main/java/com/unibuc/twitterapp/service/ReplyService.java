package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.payload.ReplyRequest;

public interface ReplyService {

    /**
     * Adds a reply to a specific post.
     * @param postId
     */
    void addReplyToPost(ReplyRequest replyRequest);
}
