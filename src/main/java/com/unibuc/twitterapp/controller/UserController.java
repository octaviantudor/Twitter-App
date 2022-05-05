package com.unibuc.twitterapp.controller;

import com.unibuc.twitterapp.pojo.payload.LoginRequest;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;
import com.unibuc.twitterapp.service.FollowService;
import com.unibuc.twitterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/search")
    public ModelAndView findByFirstNameOrLastNameOrMail(@RequestParam(name = "firstName", required = false) String firstName,
                                                         @RequestParam(name = "lastName", required = false) String lastName,
                                                         @RequestParam(name = "mail", required = false) String mail,
                                                        @RequestParam(name = "username", required = false) String username) {
        log.info("Search for users using first_name: {} or last_name: {} or mail: {}", firstName, lastName, mail);
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users",userService.searchUser(firstName, lastName, mail, username) );
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllUsers(){
        log.info("Search for all the users");
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("Attempting user registration for email: {}, firstName: {}, lastName: {}", userRegistrationRequest.getMail(), userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName());
        userService.registerUser(userRegistrationRequest);
    }

    @DeleteMapping("/unregister")
    public void unregisterUser() {
        userService.unregisterUser();
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequestBody) {
        log.info("Authentication request for email: {}", loginRequestBody.getMail());
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", userService.loginUser(loginRequestBody));

        return ResponseEntity.ok(authHeader);
    }

    @PostMapping("/{userId}/follow")
    public ModelAndView followUser(@NotEmpty @PathVariable(value = "userId") String userId){
        log.info("Attempting to follow user with ID: {}", userId);
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all");
        followService.followUser(Long.valueOf(userId));
        return modelAndView;
    }

    @PostMapping("/{userId}/unfollow")
    public ModelAndView unfollowUser(@NotEmpty @PathVariable(value = "userId") String userId){
        log.info("Attempting to unfollow user with ID: {}", userId);
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all");
        followService.unfollowUser(Long.valueOf(userId));
        return modelAndView;
    }

}
