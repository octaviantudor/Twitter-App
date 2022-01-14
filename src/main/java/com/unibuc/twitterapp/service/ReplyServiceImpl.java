package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.config.UserContextHolder;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.ReplyRepository;
import com.unibuc.twitterapp.pojo.converter.ReplyConverter;
import com.unibuc.twitterapp.pojo.dto.ReplyDto;
import com.unibuc.twitterapp.pojo.payload.ReplyRequest;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ReplyDto> getPostReplies(long postId) {
        return replyRepository.findAllByPostId(postId).stream().map(ReplyConverter::toDto).collect(Collectors.toList());
    }
}
