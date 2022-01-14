package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import com.cognizant.softvision.twitterapp.persistence.repository.FollowRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.MentionRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private UserContextHolder userContextHolder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MentionRepository mentionRepository;


    @Test
    void addPost_whenInputIsValid_thenSave() {
        //when
        when(postRepository.save(any())).thenReturn(TestObjectsBuilder.getPost());
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());

        //given
        postService.addPost(TestObjectsBuilder.getPostRequest(), Collections.emptyList());

        //then
        verify(postRepository, times(1)).save(any());
    }


    @Test
    void addPost_whenProvidedMentionsAndUserFound_thenSaveMention() {
        //when
        when(postRepository.save(any())).thenReturn(TestObjectsBuilder.getPost());
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
        when(userRepository.findAllById(any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getUser()));
        //given

        //given
        postService.addPost(TestObjectsBuilder.getPostRequest(), Collections.singletonList("1"));

        //then
        verify(mentionRepository, times(1)).save(any());
    }

    @Test
    void getOwnPosts_whenUserHasNoPosts_thenReturnEmptyList() {
        //when
        when(postRepository.findByUser(any())).thenReturn(Collections.emptyList());
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());

        //given
        var result = postService.getOwnPosts();

        //then
        Assertions.assertThat(result).isEmpty();

    }

    @Test
    void getOwnPosts_whenUsersHasAtLeastOnePost_thenReturnPostList() {
        //when
        when(postRepository.findByUser(any())).thenReturn(Collections.singletonList(new Post()));
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());

        //given
        var result = postService.getOwnPosts();

        //then
        Assertions.assertThat(result).isNotEmpty().hasSize(1);
    }

    @Test
    void getPostFeed_whenUserDoesntFollow_thenReturnEmptyList() {
        //when
        when(followRepository.findByFrom(any())).thenReturn(Collections.emptyList());
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());

        //given
        var result = postService.getPostFeed(1, 3);

        //then
        Assertions.assertThat(result).isEmpty();

    }

    @Test
    void getPostFeed_whenUserFollowsAtLeastOneUser_thenReturnPostFeedDtoList() {
        //when
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
        when(followRepository.findByFrom(any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getFollowEntity()));
        when(postRepository.findByUserIn(any(), any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getPost()));

        //given
        var result = postService.getPostFeed(1, 3);

        //then
        Assertions.assertThat(result).isNotEmpty().hasSize(1);

    }

}
