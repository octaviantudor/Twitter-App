package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.repository.FollowRepository;
import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.PostConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MentionRepository mentionRepository;
    @Mock
    private PostConverter postConverter;

    @Test
    void addPost_whenInputIsValid_thenSave() {
        //when
        when(postRepository.save(any())).thenReturn(TestObjectsBuilder.getPost());

        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        when(postConverter.fromPostToEntity(any(),any())).thenReturn(TestObjectsBuilder.getPost());

        //given
        postService.addPost("someMessage", Collections.emptyList());

        //then
        verify(postRepository, times(1)).save(any());
    }


    @Test
    void addPost_whenProvidedMentionsAndUserFound_thenSaveMention() {
        //when
        when(postRepository.save(any())).thenReturn(TestObjectsBuilder.getPost());
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(userRepository.findAllById(any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getUser()));
        //given

        //given
        postService.addPost("Some message", Collections.singletonList("1"));

        //then
        verify(mentionRepository, times(1)).save(any());
    }

    @Test
    void getOwnPosts_whenUserHasNoPosts_thenReturnEmptyList() {
        //when
        when(postRepository.findByUser(any())).thenReturn(Collections.emptyList());
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        //given
        var result = postService.getOwnPosts();

        //then
        Assertions.assertThat(result).isEmpty();

    }

    @Test
    void getOwnPosts_whenUsersHasAtLeastOnePost_thenReturnPostList() {
        //when
        when(postRepository.findByUser(any())).thenReturn(Collections.singletonList(new Post()));
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        //given
        var result = postService.getOwnPosts();

        //then
        Assertions.assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    void getPostFeed_whenUserDoesntFollow_thenReturnEmptyList() {
        //when
        when(followRepository.findByFrom(any())).thenReturn(Collections.emptyList());
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        //given
        var result = postService.getPostFeed(1, 3, Optional.of("sort"));

        //then
        Assertions.assertThat(result).isEmpty();

    }


}
