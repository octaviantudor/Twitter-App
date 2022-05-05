package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.*;
import com.unibuc.twitterapp.persistence.repository.FollowRepository;
import com.unibuc.twitterapp.persistence.repository.MentionRepository;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.PostConverter;
import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.dto.PostDto;
import com.unibuc.twitterapp.service.exception.PostNotFoundException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MentionRepository mentionRepository;
    private final FollowRepository followRepository;
    private final AuthHelper authHelper;
    private final PostConverter postConverter;

    @Override
    @Transactional
    public void addPost(String message, List<String> mentionIds) {
        var user = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        var post = postConverter.fromPostToEntity(message, user);
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

    @Transactional
    @Override
    public List<PostDto> getOwnPosts() {
        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        return postRepository.findByUser(loggedUser).stream()
                .map(postConverter::fromEntityToPost)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<FeedPostDto> getPostFeed(int pageNumber, int pageSize, Optional<String> sort) {

        var sortColumn = sort.orElse("timeStamp");

        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));


        List<User> userList = followRepository.findByFrom(loggedUser).stream()
                .map(Follow::getTo).collect(Collectors.toList());

        userList.add(loggedUser);

        List<FeedPostDto> feedPostDtos;

        if (sortColumn.equals("username")) {
            feedPostDtos = postRepository.findByUserIn(userList, Sort.by(Sort.Direction.DESC, "user")).stream()
                    .map(p -> postConverter.fromEntityToPost(p, p.getUser()))
                    .collect(Collectors.toList());
        } else {
            feedPostDtos = postRepository.findByUserIn(userList, Sort.by(Sort.Direction.DESC, sortColumn)).stream()
                    .map(p -> postConverter.fromEntityToPost(p, p.getUser()))
                    .collect(Collectors.toList());
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("timeStamp").descending());
        int size = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * size;
        List<FeedPostDto> list;
        if (feedPostDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + size, feedPostDtos.size());
            list = feedPostDtos.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage,
                size), feedPostDtos.size());
    }

    @Override
    public Boolean currentUserLikedPost(String postId) {
        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        Optional<Post> optionalPost = postRepository.findById(Long.valueOf(postId));

        if (optionalPost.isPresent()) {
            var list = optionalPost.get().getLikes().stream().map(Like::getUser).collect(Collectors.toList());
            return list.contains(loggedUser) ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    @Override
    @Transactional
    public void deletePost(long postId) {
        if (postRepository.findById(postId).isEmpty())
            throw new PostNotFoundException("Post doesn't exist or has already been deleted !");
        else
            postRepository.deleteById(postId);
    }

    @Override
    public Page<FeedPostDto> findPaginatedFiltered(Pageable pageable, String username, String message) {

        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));


        List<User> userList = followRepository.findByFrom(loggedUser).stream()
                .map(Follow::getTo).collect(Collectors.toList());
        userList.add(loggedUser);

        var feedPostDtos = postRepository.findByUserIn(userList).stream()
                .filter(post -> post.getUser().getUsername().contains(username) &&
                        post.getMessage().contains(message))
                .map(post -> postConverter.fromEntityToPost(post, post.getUser()))
                .sorted(Comparator.comparing(FeedPostDto::getTimeStamp).reversed())
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<FeedPostDto> list;
        if (feedPostDtos.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, feedPostDtos.size());
            list = feedPostDtos.subList(startItem, toIndex);
        }
        //list = list.stream().sorted(Comparator.comparing(FeedPostDto::getTimeStamp)).collect(Collectors.toList());
        return new PageImpl<>(list, PageRequest.of(currentPage,
                pageSize), feedPostDtos.size());

    }

    @Override
    public FeedPostDto getPost(String postId) {

        return postRepository.findById(Long.valueOf(postId))
                .map(post -> postConverter.fromEntityToPost(post, post.getUser()))
                .orElse(new FeedPostDto());

    }

    @Override
    public void updatePost(String postId, String message) {
        var optionalPost = postRepository.findById(Long.valueOf(postId));

        if (optionalPost.isEmpty())
            throw new PostNotFoundException("Post with this ID doesn't exists !");

        var post = optionalPost.get();

        post.setMessage(message);
        post.setTimeStamp(LocalDateTime.now());

        postRepository.save(post);

    }

}
