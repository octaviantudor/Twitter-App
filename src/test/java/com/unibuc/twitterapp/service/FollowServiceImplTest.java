//package com.unibuc.twitterapp.service;
//
//import com.unibuc.twitterapp.config.UserContextHolder;
//import com.unibuc.twitterapp.persistence.entity.User;
//import com.unibuc.twitterapp.persistence.repository.FollowRepository;
//import com.unibuc.twitterapp.persistence.repository.UserRepository;
//import com.unibuc.twitterapp.service.exception.InvalidUserRequestException;
//import com.unibuc.twitterapp.service.exception.UserNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class FollowServiceImplTest {
//
//    @InjectMocks
//    private FollowServiceImpl followService;
//    @Mock
//    private UserContextHolder userContextHolder;
//    @Mock
//    private FollowRepository followRepository;
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void followUser_whenUserIsFound_thenFollow() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.of(new User()) );
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//        when(followRepository.findByFromAndTo(any(), any())).thenReturn(Optional.empty());
//
//        //given
//        followService.followUser(123L);
//
//        //then
//        verify(followRepository, times(1)).save(any());
//
//    }
//
//    @Test
//    void followUser_whenUserIsNotFound_thenThrowUserNotFoundException() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//
//        //given
//        Exception exception = assertThrows(UserNotFoundException.class, () ->
//                followService.followUser(123L));
//
//        //then
//        assertTrue(exception.getMessage().contains("User with provided ID does not exist "));
//
//    }
//
//    @Test
//    void followUser_whenUserIsFoundAndHasSameId_thenThrowInvalidUserRequestException() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//
//        //given
//        Exception exception = assertThrows(InvalidUserRequestException.class, () ->
//                followService.followUser(123L));
//
//        //then
//        assertTrue(exception.getMessage().contains("You can not follow your own account "));
//
//    }
//
//    @Test
//    void followUser_whenUserIsFoundAndAlreadyFollows_thenThrowInvalidUserRequestException() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//        when(followRepository.findByFromAndTo(any(), any())).thenReturn(Optional.of(TestObjectsBuilder.getFollowEntity()));
//
//        //given
//        Exception exception = assertThrows(InvalidUserRequestException.class, () ->
//                followService.followUser(123L));
//
//        //then
//        assertTrue(exception.getMessage().contains("You already follow this account "));
//
//    }
//
//    @Test
//    void unfollowUser_whenUserIsNotFound_thenThrowUserNotFoundException(){
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//
//        //given
//        Exception exception = assertThrows(UserNotFoundException.class, () ->
//                followService.unfollowUser(123L));
//
//        //then
//        assertTrue(exception.getMessage().contains("User with provided ID does not exist "));
//
//    }
//
//    @Test
//    void unfollowUser_whenUserFoundAndFollowNotFound_thenThrowInvalidUserRequestException() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//        when(followRepository.findByFromAndTo(any(), any())).thenReturn(Optional.empty());
//
//        //given
//        Exception exception = assertThrows(InvalidUserRequestException.class, () ->
//                followService.unfollowUser(123L));
//
//        //then
//        assertTrue(exception.getMessage().contains("You are not following this account"));
//
//    }
//
//    @Test
//    void unfollowUser_whenUserFoundAndFollowFound_thenDeleteFollow() {
//        //when
//        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
//        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
//        when(followRepository.findByFromAndTo(any(), any())).thenReturn(Optional.of(TestObjectsBuilder.getFollowEntity()));
//
//        //given
//        followService.unfollowUser(123L);
//
//        //then
//        verify(followRepository, times(1)).delete(any());
//
//    }
//}