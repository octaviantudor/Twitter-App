package com.unibuc.twitterapp.controller;

import com.unibuc.twitterapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;


    @GetMapping("/user-licked")
    public Boolean userLikedPost(@RequestParam("postId") String postId){
        return postService.currentUserLikedPost(postId);
    }

    @GetMapping
    public String showHomeInForm(){ return "login"; }
    @GetMapping("/show-login")
    public String showLogInForm(){ return "login"; }
    @GetMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
