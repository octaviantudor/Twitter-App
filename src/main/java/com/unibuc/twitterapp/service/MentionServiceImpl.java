package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Mention;
import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.MentionConvertor;
import com.unibuc.twitterapp.pojo.dto.MentionDto;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

    private final MentionRepository mentionRepository;
    private final UserRepository userRepository;
    private final AuthHelper authHelper;

    @Override
    @Transactional
    public List<MentionDto> getMentions() {

        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        List<Mention> mentions = mentionRepository.findByUser(loggedUser);

        return mentions.stream().map(MentionConvertor::fromEntityToDto).collect(Collectors.toList());
    }
}
