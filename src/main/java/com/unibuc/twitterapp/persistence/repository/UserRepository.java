package com.unibuc.twitterapp.persistence.repository;

import com.unibuc.twitterapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstNameOrLastNameOrMailOrUsername(String firstName, String lastName, String mail, String username);

    Optional<User> findUserByMail(String mail);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}

