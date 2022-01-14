package com.unibuc.twitterapp.controller;

import com.unibuc.twitterapp.pojo.dto.*;
import com.unibuc.twitterapp.pojo.payload.PostRequest;
import com.unibuc.twitterapp.pojo.payload.ReplyRequest;
import com.unibuc.twitterapp.service.LikeService;
import com.unibuc.twitterapp.service.MentionService;
import com.unibuc.twitterapp.service.PostService;
import com.unibuc.twitterapp.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;
    private final LikeService likeService;
    private final MentionService mentionService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@Valid @RequestBody PostRequest postRequest,
                        @PathVariable(value = "mentionIds", required = false) List<String> mentionIds) {
        log.info("Add post request with message: {}", postRequest.getMessage());
        postService.addPost(postRequest, mentionIds);
    }

    @GetMapping()
    public List<PostDto> getOwnPosts() {
        log.info("Getting own posts");
        return postService.getOwnPosts();
    }

    @GetMapping("/feed")
    public List<FeedPostDto> getPostFeed(@RequestParam(value = "pageNr", defaultValue = "0") String pageNr,
                                         @RequestParam(value = "pageSize", defaultValue = "20") String pageSize) {
        log.info("Getting post feed with pageNr: {} and pageSize: {}", pageNr, pageSize);
        return postService.getPostFeed(Integer.parseInt(pageNr), Integer.parseInt(pageSize));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@NotEmpty @PathVariable(value = "postId") String postId) {
        log.info("Attempting to delete post with ID: {}", postId);
        postService.deletePost(Long.parseLong(postId));
    }

    @PostMapping("/reply")
    public void addPostReply(@Valid @RequestBody ReplyRequest replyRequest) {
        log.info("Attempting to add reply to post with postId: {}, message: {}", replyRequest.getPostId() ,replyRequest.getMessage());
        replyService.addReplyToPost(replyRequest);
    }

    @GetMapping("/{postId}/replies")
    public List<ReplyDto> addPostReply(@NotEmpty @PathVariable(value ="postId") String postId) {
        log.info("Get all replies from post with ID: {}", postId);
        return replyService.getPostReplies(Long.parseLong(postId));
    }

    @PostMapping("/{postId}/like")
    public void likePost(@NotEmpty @PathVariable(value ="postId") String postId) {
        log.info("Attempting to like post with ID: {}", postId);
        likeService.likePost(Long.parseLong(postId));
    }

    @GetMapping("/{postId}/likes")
    public List<UserDto> getPostlikes(@NotEmpty @PathVariable(value ="postId") String postId) {
        log.info("Attempting to like post with ID: {}", postId);
        return likeService.getPostLikes(Long.parseLong(postId));
    }

    @GetMapping("/mentions")
    public List<MentionDto> getMentions() {
        log.info("Attempting to get mentions");
        return mentionService.getMentions();
    }



}
