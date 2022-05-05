package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.ReplyRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.ReplyConverter;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceImplTest {

    @InjectMocks
    private ReplyServiceImpl replyService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private ReplyConverter replyConverter;


    @Test
    void addReply_whenInputIsValid_thenSave() {
        //when
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(replyRepository.save(any())).thenReturn(TestObjectsBuilder.getReply());
        when(postRepository.findById(any())).thenReturn(Optional.of(TestObjectsBuilder.getPost()));
        when(replyConverter.fromReplyToEntity(anyString(), any(), any())).thenReturn(TestObjectsBuilder.getReply());
        //given
        replyService.addReplyToPost("1", "message");

        //then
        verify(replyRepository, times(1)).save(any());
    }

    @Test
    void addReply_whenIdPostDoesNotExists_thenThrowPostNotFound() {
        //when
        when(postRepository.findById(any())).thenReturn(Optional.empty());
        //given
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            replyService.addReplyToPost("1", "message");
        });

        //then
        assertTrue(exception.getMessage().contains("Post with this ID doesn't exists !"));
    }


}
