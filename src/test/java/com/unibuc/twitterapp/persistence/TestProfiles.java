package com.unibuc.twitterapp.persistence;

import com.unibuc.twitterapp.persistence.entity.Post;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.persistence.repository.PostRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(value = false)
public class TestProfiles {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void findUser(){
        Optional<User> user = userRepository.findByUsername("admin");
        assertTrue(user.isPresent());
    }

    @Test
    public void testAddPost(){
        Optional<User> user = userRepository.findByUsername("admin");


        assertTrue(user.isPresent());
        postRepository.save(Post.builder()
                .message("Some nice message")
                .user(user.get())
                .timeStamp(LocalDateTime.now())
                .build());
        assertEquals(1, postRepository.findAll().size());

    }
}
