package com.unibuc.twitterapp.controller;

import com.unibuc.twitterapp.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    @GetMapping("/followers")
    public ModelAndView getUserFollowers(){
        log.info("Attempting to fetch followers for current user");
        ModelAndView modelAndView = new ModelAndView("users");

        modelAndView.addObject("users", followService.getUserFollowers());

        return modelAndView;
    }

    @GetMapping("/following")
    public ModelAndView getUserFollowing(){
        log.info("Attempting to fetch followers for current user");
        ModelAndView modelAndView = new ModelAndView("users");

        modelAndView.addObject("users", followService.getUserFollowing());

        return modelAndView;
    }
}
