package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.ReplyRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.UserRepository;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    private UserContextHolder userContextHolder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReplyRepository replyRepository;


    @Test
    void addReply_whenInputIsValid_thenSave() {
        //when
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
        when(replyRepository.save(any())).thenReturn(TestObjectsBuilder.getReply());
        when(postRepository.findById(any())).thenReturn(Optional.of(TestObjectsBuilder.getPost()));

        //given
        replyService.addReplyToPost(TestObjectsBuilder.getReplyRequest());

        //then
        verify(replyRepository, times(1)).save(any());
    }

    @Test
    void addReply_whenIdPostDoesNotExists_thenThrowPostNotFound() {
        //when
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        //given
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            replyService.addReplyToPost(TestObjectsBuilder.getReplyRequest());
        });

        //then
        assertTrue(exception.getMessage().contains("Post with this ID doesn't exists !"));
    }


}
