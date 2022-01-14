package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.entity.Follow;
import com.cognizant.softvision.twitterapp.persistence.entity.Mention;
import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import com.cognizant.softvision.twitterapp.persistence.repository.FollowRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.MentionRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.PostRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.UserRepository;
import com.cognizant.softvision.twitterapp.pojo.converter.PostConverter;
import com.cognizant.softvision.twitterapp.pojo.dto.FeedPostDto;
import com.cognizant.softvision.twitterapp.pojo.dto.PostDto;
import com.cognizant.softvision.twitterapp.pojo.payload.PostRequest;
import com.cognizant.softvision.twitterapp.service.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MentionRepository mentionRepository;
    private final UserContextHolder userContextHolder;
    private final FollowRepository followRepository;

    @Override
    public void addPost(PostRequest postRequest, List<String> mentionIds) {
        var post = PostConverter.fromPostToEntity(postRequest, userContextHolder.getUser());
        Post savedPost = postRepository.save(post);
        if (!CollectionUtils.isEmpty(mentionIds)) {
            createMention(savedPost, mentionIds);
        }
    }

    private void createMention(Post post, List<String> mentionIds) {
        List<User> mentionedUsers = userRepository.findAllById(mentionIds.stream().map(Long::valueOf).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(mentionedUsers)) {
            mentionedUsers.forEach(mentionedUser -> {
                mentionRepository.save(Mention.builder()
                        .user(mentionedUser)
                        .post(post)
                        .build());
            });
        }

    }

    @Override
    public List<PostDto> getOwnPosts() {
        return postRepository.findByUser(userContextHolder.getUser()).stream()
                .map(PostConverter::fromEntityToPost)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedPostDto> getPostFeed(int pageNumber, int pageSize) {

        List<User> userList = followRepository.findByFrom(userContextHolder.getUser()).stream()
                .map(Follow::getTo).collect(Collectors.toList());

        return postRepository.findByUserIn(userList, PageRequest.of(pageNumber, pageSize, Sort.by("timeStamp").descending())).stream()
                .map(p -> PostConverter.fromEntityToPost(p, p.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(long postId) {
        if(postRepository.findById(postId).isEmpty())
            throw new PostNotFoundException("Post doesn't exist or has already been deleted !");
        else
            postRepository.deleteById(postId);
    }

}
