package com.cognizant.softvision.twitterapp.config;

import com.cognizant.softvision.twitterapp.persistence.entity.User;
import com.cognizant.softvision.twitterapp.persistence.repository.TokenRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserContextHolder userContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var providedToken = request.getHeader("Authorization");
        return tokenRepository.findByToken(providedToken)
                .filter(token -> token.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(token -> setUserContextHolder(userRepository.findUserByMail(token.getMail())))
                .orElse(Boolean.FALSE);

    }

    private boolean setUserContextHolder(Optional<User> userOptional) {
        return userOptional
                .map(user -> {
                    userContextHolder.setUser(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);

    }

}
