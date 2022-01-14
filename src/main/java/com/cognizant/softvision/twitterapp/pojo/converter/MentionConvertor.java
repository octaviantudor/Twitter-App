package com.cognizant.softvision.twitterapp.pojo.converter;

import com.cognizant.softvision.twitterapp.persistence.entity.Mention;
import com.cognizant.softvision.twitterapp.pojo.dto.MentionDto;

public class MentionConvertor {
    public static MentionDto fromEntityToDto(Mention mention) {
        return MentionDto.builder()
                .idPost(mention.getPost().getId())
                .postMessage(mention.getPost().getMessage())
                .postDate(mention.getPost().getTimeStamp())
                .build();
    }
}
