package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.entity.Like;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import com.cognizant.softvision.twitterapp.persistence.repository.LikeRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.pojo.converter.LikeConvertor;
import com.cognizant.softvision.twitterapp.pojo.converter.UserConverter;
import com.cognizant.softvision.twitterapp.pojo.dto.UserDto;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserContextHolder userContextHolder;

    @Override
    public void likePost(long idPost) {

        var optionalPost = postRepository.findById(idPost);
        User loggedUser = userContextHolder.getUser();

        if (optionalPost.isEmpty())
            throw new PostNotFoundException("Post with this ID doesn't exists !");

        Like like = LikeConvertor.toEntity(optionalPost.get(), loggedUser);

        Optional<Like> initialLike = optionalPost.get().getLikes().stream()
                .filter(l -> l.getUser().getId().equals(loggedUser.getId())).findAny();

        if (!initialLike.isEmpty()) {

            likeRepository.deleteById(initialLike.get().getId());
        } else {

            likeRepository.save(like);
        }

    }

    @Override
    public List<UserDto> getPostLikes(Long postId) {

        var optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty())
            throw new PostNotFoundException("Post with this ID doesn't exists !");

        return optionalPost.get().getLikes()
                .stream()
                .map(like -> UserConverter.fromEntityToDto(like.getUser()))
                .collect(Collectors.toList());
    }
}
