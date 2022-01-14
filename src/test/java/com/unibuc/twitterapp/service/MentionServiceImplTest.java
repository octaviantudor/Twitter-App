package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.config.UserContextHolder;
import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MentionServiceImplTest {
    @InjectMocks
    private MentionServiceImpl mentionService;
    @Mock
    private UserContextHolder userContextHolder;
    @Mock
    private MentionRepository mentionRepository;

    @Test
    void likePost_whenInputIsValid_thenSave() {

        //when
        when(userContextHolder.getUser()).thenReturn(TestObjectsBuilder.getUser());
        when(mentionRepository.findByUser(any())).thenReturn(Collections.singletonList(TestObjectsBuilder.getMention()));


        //given
        var result = mentionService.getMentions();

        //then
        Assertions.assertThat(result).isNotEmpty().hasSize(1);
    }


}
