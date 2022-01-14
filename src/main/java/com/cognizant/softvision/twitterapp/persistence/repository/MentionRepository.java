package com.cognizant.softvision.twitterapp.persistence.repository;

import com.cognizant.softvision.twitterapp.persistence.entity.Mention;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentionRepository extends JpaRepository<Mention,Long> {
    List<Mention> findByUser(User user);
}
