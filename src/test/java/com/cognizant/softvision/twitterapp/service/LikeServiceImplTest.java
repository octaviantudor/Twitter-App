package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.repository.LikeRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cognizant.softvision.twitterapp.service.TestObjectsBuilder.getLike;
import static com.cognizant.softvision.twitterapp.service.TestObjectsBuilder.getPost;
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
    private UserContextHolder userContextHolder;
    @Mock
    private LikeRepository likeRepository;


    @Test
    void likePost_whenInputIsValid_thenSave() {
        //when
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
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

        //given
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            likeService.likePost(999);
        });

        //then
        assertTrue(exception.getMessage().contains("Post with this ID doesn't exists !"));
    }

}
