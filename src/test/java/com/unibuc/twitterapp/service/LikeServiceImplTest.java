package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.repository.LikeRepository;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Optional;

import static com.unibuc.twitterapp.service.TestObjectsBuilder.getLike;
import static com.unibuc.twitterapp.service.TestObjectsBuilder.getPost;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTest {

    @InjectMocks
    private LikeServiceImpl likeService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserRepository userRepository;


    @Test
    void likePost_whenInputIsValid_thenSave() {
        //when
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(likeRepository.save(any())).thenReturn(getLike());
        when(postRepository.findById(any())).thenReturn(Optional.of(getPost()));

        //given
        likeService.likePost(3);

        //then
        verify(likeRepository, times(1)).save(any());
    }

    @Test
    void likePost_whenIdPostDoesNotExists_thenThrowPostNotFound() {
        //when
        when(postRepository.findById(any())).thenReturn(Optional.empty());
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        //given
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            likeService.likePost(999);
        });

        //then
        assertTrue(exception.getMessage().contains("Post with this ID doesn't exists !"));
    }

}
