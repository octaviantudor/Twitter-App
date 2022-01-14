package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.pojo.dto.MentionDto;

import java.util.List;

public interface MentionService {

    /**
     *
     */
    List<MentionDto> getMentions();
}
