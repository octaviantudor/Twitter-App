package com.unibuc.twitterapp.persistence.repository;

import com.unibuc.twitterapp.persistence.entity.Mention;
import com.unibuc.twitterapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentionRepository extends JpaRepository<Mention,Long> {
    List<Mention> findByUser(User user);
}
