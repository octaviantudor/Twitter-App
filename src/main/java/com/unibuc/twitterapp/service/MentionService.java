package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.pojo.dto.MentionDto;

import java.util.List;

public interface MentionService {

    /**
     *
     */
    List<MentionDto> getMentions();
}
