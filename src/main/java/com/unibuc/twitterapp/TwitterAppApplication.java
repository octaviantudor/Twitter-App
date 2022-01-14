package com.unibuc.twitterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TwitterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterAppApplication.class, args);
    }


}
