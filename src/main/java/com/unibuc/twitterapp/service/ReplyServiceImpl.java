package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.ReplyRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.ReplyConverter;
import com.unibuc.twitterapp.pojo.dto.ReplyDto;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final AuthHelperImpl authHelper;
    private final ReplyConverter replyConverter;

    @Override
    @Transactional
    public void addReplyToPost(String postId, String message) {

        var post = postRepository.findById(Long.parseLong(postId));

        if (!post.isPresent())
            throw new PostNotFoundException("Post with this ID doesn't exists !");

        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));


        replyRepository.save(replyConverter.fromReplyToEntity(message, post.get(), loggedUser));


    }

    @Transactional
    @Override
    public List<ReplyDto> getPostReplies(long postId) {
        return replyRepository.findAllByPostId(postId).stream().map(replyConverter::toDto).collect(Collectors.toList());
    }
}
