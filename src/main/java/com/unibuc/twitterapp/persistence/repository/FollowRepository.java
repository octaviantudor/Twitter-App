package com.unibuc.twitterapp.persistence.repository;

import com.unibuc.twitterapp.persistence.entity.Follow;
import com.unibuc.twitterapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromAndTo(User from, User to);

    List<Follow> findByFrom(User from);

    List<Follow> findByTo(User to);
}
