package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.repository.TokenRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;
import com.unibuc.twitterapp.service.exception.AlreadyExistsException;
import com.unibuc.twitterapp.service.exception.InvalidCredentialsException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void registerUser_whenMailNotFound_thenSaveUser() {
        //when
        when(userRepository.findUserByMail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(TestObjectsBuilder.getUser());
        when(passwordEncoder.encode(anyString())).thenReturn("someSimpleHashedPassword");

        //given
        userService.registerUser(TestObjectsBuilder.getUserRegistrationRequest());

        //then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerUser_whenMailFound_thenThrowAlreadyExistsException() {
        //when
        when(userRepository.findUserByMail(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));

        //given
        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.registerUser(new UserRegistrationRequest());
        });

        //then
        assertTrue(exception.getMessage().contains("User with this email already exists !"));

    }


    @Test
    void loginUser_whenUserIsFoundAndValidPassword_thenCreateOrUpdateToken() {
        //when
        when(userRepository.findUserByMail(anyString())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Boolean.TRUE);
        when(tokenRepository.findByMail(anyString())).thenReturn(Optional.empty());
        when(tokenRepository.save(any())).thenReturn(TestObjectsBuilder.getToken());

        //given
        var token = userService.loginUser(TestObjectsBuilder.getLoginRequest());

        //then
        verify(tokenRepository, times(1)).save(any());
        assertEquals("someRandomToken", token);
    }

    @Test
    void loginUser_whenUserIsNotFound_thenThrowUserNotFoundException() {
        //when
        when(userRepository.findUserByMail(TestObjectsBuilder.getUserRegistrationRequest().getMail())).thenReturn(Optional.empty());

        //given
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userService.loginUser(TestObjectsBuilder.getLoginRequest()));

        //then
        assertTrue(exception.getMessage().contains("User with provided email does not exist !"));


    }

    @Test
    void loginUser_whenUserIsFoundAndInvalidPassword_thenThrowInvalidCredentialsException() {
        //when
        when(userRepository.findUserByMail(TestObjectsBuilder.getUserRegistrationRequest().getMail())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Boolean.FALSE);

        //given
        Exception exception = assertThrows(InvalidCredentialsException.class, () ->
                userService.loginUser(TestObjectsBuilder.getLoginRequest()));

        //then
        assertTrue(exception.getMessage().contains("User email or password are incorrect !"));


    }









}