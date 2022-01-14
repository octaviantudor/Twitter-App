package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.ReplyDto;
import com.unibuc.twitterapp.pojo.payload.ReplyRequest;

import java.util.List;

public interface ReplyService {

    /**
     * Adds a reply to a specific post.
     * @param postId
     */
    void addReplyToPost(ReplyRequest replyRequest);

    List<ReplyDto> getPostReplies(long postId);
}
