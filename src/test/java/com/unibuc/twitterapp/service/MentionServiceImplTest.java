package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MentionServiceImplTest {
    @InjectMocks
    private MentionServiceImpl mentionService;
    @Mock
    private AuthHelper authHelper;
    @Mock
    private MentionRepository mentionRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void likePost_whenInputIsValid_thenSave() {

        //when
        when(authHelper.getUserDetails()).thenReturn(new User("1", "1", Collections.emptySet()));

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TestObjectsBuilder.getUser()));
        when(mentionRepository.findByUser(any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getMention()));


        //given
        var result = mentionService.getMentions();

        //then
        Assertions.assertThat(result).isNotEmpty().hasSize(1);
    }


}
