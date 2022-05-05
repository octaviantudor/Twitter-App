package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Authority;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        log.info("se incearca userul: " + username);

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent())
            user = userOpt.get();
        else
            throw new UsernameNotFoundException("Username: " + username);

        log.info(user.toString());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getEnabled(), user.getAccountNotExpired(),
                user.getCredentialsNotExpired(), user.getAccountNotLocked(), getAuthorities(user.getAuthorities()));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null) {
            return new HashSet<>();
        } else if (authorities.size() == 0) {
            return new HashSet<>();
        } else {
            return authorities.stream()
                    .map(Authority::getName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }
}
