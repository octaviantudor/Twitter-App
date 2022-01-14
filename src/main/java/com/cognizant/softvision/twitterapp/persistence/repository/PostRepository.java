package com.cognizant.softvision.twitterapp.persistence.repository;

import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    List<Post> findByUser(User user);

    List<Post> findByUserIn(List<User> userList, Pageable pageable);
}
