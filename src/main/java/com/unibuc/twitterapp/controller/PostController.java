package com.unibuc.twitterapp.controller;

import com.unibuc.twitterapp.pojo.dto.FeedPostDto;
import com.unibuc.twitterapp.pojo.dto.MentionDto;
import com.unibuc.twitterapp.pojo.dto.PostDto;
import com.unibuc.twitterapp.service.LikeService;
import com.unibuc.twitterapp.service.MentionService;
import com.unibuc.twitterapp.service.PostService;
import com.unibuc.twitterapp.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;
    private final LikeService likeService;
    private final MentionService mentionService;


    @PostMapping("/add")
    public ModelAndView addPost(@Valid @RequestParam(value = "post_message", required = false) String message,
                                @PathVariable(value = "mentionIds", required = false) List<String> mentionIds) {
        log.info("Add post request with message: {}", message);
        ModelAndView modelAndView = new ModelAndView("redirect:/posts/feed");
        if (!StringUtils.isEmpty(message))
            postService.addPost(message, mentionIds);
        return modelAndView;
    }

    @GetMapping()
    public List<PostDto> getOwnPosts() {
        log.info("Getting own posts");
        return postService.getOwnPosts();
    }

    @GetMapping("/feed")
    public ModelAndView getPostFeed(@RequestParam(value = "page", defaultValue = "0") String pageNr,
                                    @RequestParam(value="sort", required = false) Optional<String> sort) {
        String pageSize = "5";
        log.info("Getting post feed with pageNr: {} and pageSize: {}", pageNr, pageSize);
        ModelAndView modelAndView = new ModelAndView("feed");
        Page<FeedPostDto> feedPostDtos = postService.getPostFeed(Integer.parseInt(pageNr), Integer.parseInt(pageSize), sort);

        modelAndView.addObject("posts", feedPostDtos);

        return modelAndView;
    }

    @PostMapping("/delete/{postId}")
    public ModelAndView deletePost(@NotEmpty @PathVariable(value = "postId") String postId) {
        log.info("Attempting to delete post with ID: {}", postId);
        ModelAndView modelAndView = new ModelAndView("redirect:/posts/feed");
        postService.deletePost(Long.parseLong(postId));
        return modelAndView;
    }

    @PostMapping("/{postId}/reply")
    public ModelAndView addPostReply(@PathVariable(value = "postId") String postId, @RequestParam(value = "message") String message) {
        log.info("Attempting to add reply to post with postId: {}, message: {}", postId, message);
        ModelAndView modelAndView = new ModelAndView("redirect:/posts/" + postId + "/replies");
        replyService.addReplyToPost(postId, message);
        return modelAndView;
    }

    @GetMapping("/{postId}/replies")
    public ModelAndView addPostReply(@NotEmpty @PathVariable(value = "postId") String postId) {
        log.info("Get all replies from post with ID: {}", postId);
        ModelAndView modelAndView = new ModelAndView("replies");
        modelAndView.addObject("replies", replyService.getPostReplies(Long.parseLong(postId)));
        return modelAndView;
    }

    @PostMapping("/{postId}/like")
    public ModelAndView likePost(@NotEmpty @PathVariable(value = "postId") String postId, @RequestParam(value = "page") String page) {
        log.info("Attempting to like post with ID: {}", postId);
        ModelAndView modelAndView = new ModelAndView("redirect:/posts/feed?page=" + page);
        likeService.likePost(Long.parseLong(postId));
        return modelAndView;
    }

    @GetMapping("/{postId}/likes")
    public ModelAndView getPostlikes(@NotEmpty @PathVariable(value = "postId") String postId) {
        log.info("Attempting to like post with ID: {}", postId);
        ModelAndView modelAndView = new ModelAndView("likes_by_users");
        modelAndView.addObject("users", likeService.getPostLikes(Long.parseLong(postId)));
        return modelAndView;
    }

    @GetMapping("/mentions")
    public List<MentionDto> getMentions() {
        log.info("Attempting to get mentions");
        return mentionService.getMentions();
    }

    @GetMapping(path = "/filter")
    public ModelAndView filterPosts(@RequestParam("page") Optional<Integer> page,
                                    @RequestParam("filter_username") String username,
                                    @RequestParam("filter_message") String message) {

        log.info("Attempting to filter posts by username: {} and message: {}", username, message);
        ModelAndView modelAndView = new ModelAndView("redirect:/posts/feed");


        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(message)) {
            return modelAndView;
        }
        modelAndView.setViewName("feed");

        int currentPage = page.orElse(1);
        int pageSize = 5;

        Page<FeedPostDto> feedPage = postService.findPaginatedFiltered(PageRequest.of(currentPage - 1, pageSize), username, message);
        modelAndView.addObject("posts", feedPage);
        return modelAndView;
    }


}
