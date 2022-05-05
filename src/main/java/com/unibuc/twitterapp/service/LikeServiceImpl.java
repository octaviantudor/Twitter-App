package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Like;
import com.unibuc.twitterapp.persistence.repository.LikeRepository;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.LikeConvertor;
import com.unibuc.twitterapp.pojo.converter.UserConverter;
import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final AuthHelperImpl authHelper;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public void likePost(long idPost) {

        var optionalPost = postRepository.findById(idPost);
        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

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
    @Transactional
    public List<UserDto> getPostLikes(Long postId) {

        var optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty())
            throw new PostNotFoundException("Post with this ID doesn't exists !");

        return optionalPost.get().getLikes()
                .stream()
                .map(like -> userConverter.fromEntityToDto(like.getUser()))
                .collect(Collectors.toList());
    }
}
