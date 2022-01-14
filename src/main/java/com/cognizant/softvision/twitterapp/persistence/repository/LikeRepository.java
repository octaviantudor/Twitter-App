package com.cognizant.softvision.twitterapp.persistence.repository;

import com.cognizant.softvision.twitterapp.persistence.entity.Like;
import com.cognizant.softvision.twitterapp.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPost(Post post);
    void deleteById(long id);
}
