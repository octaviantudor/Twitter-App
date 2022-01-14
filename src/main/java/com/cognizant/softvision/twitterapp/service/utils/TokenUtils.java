package com.cognizant.softvision.twitterapp.service.utils;

import com.cognizant.softvision.twitterapp.persistence.entity.Token;
import com.cognizant.softvision.twitterapp.persistence.entity.User;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

public class TokenUtils {

    private static final long expirationInMins = 180;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    public static Token createTokenEntity(User user) {
        return Token.builder()
                .token(generateToken())
                .mail(user.getMail())
                .expirationDate(LocalDateTime.now().plusMinutes(expirationInMins))
                .build();
    }

    public static Token updateExistingToken(Token token) {
        token.setToken(generateToken());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(expirationInMins));

        return token;

    }

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
