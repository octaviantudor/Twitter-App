package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.config.UserContextHolder;
import com.unibuc.twitterapp.persistence.entity.Mention;
import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import com.unibuc.twitterapp.pojo.converter.MentionConvertor;
import com.unibuc.twitterapp.pojo.dto.MentionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

    private final MentionRepository mentionRepository;
    private final UserContextHolder userContextHolder;

    @Override
    public List<MentionDto> getMentions() {

        List<Mention> mentions = mentionRepository.findByUser(userContextHolder.getUser());

        return mentions.stream().map(MentionConvertor::fromEntityToDto).collect(Collectors.toList());
    }
}
