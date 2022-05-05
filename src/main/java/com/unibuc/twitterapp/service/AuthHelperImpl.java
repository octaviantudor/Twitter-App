package com.unibuc.twitterapp.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthHelperImpl implements AuthHelper {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public String getName() {
        return getAuthentication().getName();
    }
    public UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }



}