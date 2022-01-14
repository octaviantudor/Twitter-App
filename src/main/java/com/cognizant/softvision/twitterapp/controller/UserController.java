package com.cognizant.softvision.twitterapp.controller;

import com.cognizant.softvision.twitterapp.pojo.dto.UserDto;
import com.cognizant.softvision.twitterapp.pojo.payload.LoginRequest;
import com.cognizant.softvision.twitterapp.pojo.payload.UserRegistrationRequest;
import com.cognizant.softvision.twitterapp.service.FollowService;
import com.cognizant.softvision.twitterapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/search")
    public List<UserDto> findByFirstNameOrLastNameOrMail(@RequestParam(name = "firstName", required = false) String firstName,
                                                         @RequestParam(name = "lastName", required = false) String lastName,
                                                         @RequestParam(name = "mail", required = false) String mail) {
        log.info("Search for users using first_name: {} or last_name: {} or mail: {}", firstName, lastName, mail);
        return userService.searchUser(firstName, lastName, mail);
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

    @GetMapping("/{userId}/follow")
    public void followUser(@NotEmpty @PathVariable(value = "userId") String userId){
        log.info("Attempting to follow user with ID: {}", userId);
        followService.followUser(Long.valueOf(userId));
    }

    @GetMapping("/{userId}/unfollow")
    public void unfollowUser(@NotEmpty @PathVariable(value = "userId") String userId){
        log.info("Attempting to unfollow user with ID: {}", userId);
        followService.unfollowUser(Long.valueOf(userId));
    }

}
