package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.ReplyRepository;
import com.cognizant.softvision.twitterapp.pojo.converter.ReplyConverter;
import com.cognizant.softvision.twitterapp.pojo.payload.ReplyRequest;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final UserContextHolder userContextHolder;

    @Override
    public void addReplyToPost(ReplyRequest replyRequest) {

        var post = postRepository.findById(Long.parseLong(replyRequest.getPostId()));

        if (!post.isPresent())
            throw new PostNotFoundException("Post with this ID doesn't exists !");


        replyRepository.save(ReplyConverter.fromReplyToEntity(replyRequest, post.get(), userContextHolder.getUser()));


    }
}
