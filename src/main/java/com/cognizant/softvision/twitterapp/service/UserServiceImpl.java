package com.cognizant.softvision.twitterapp.service;

import com.cognizant.softvision.twitterapp.config.UserContextHolder;
import com.cognizant.softvision.twitterapp.persistence.entity.Token;
import com.cognizant.softvision.twitterapp.persistence.entity.User;
import com.cognizant.softvision.twitterapp.persistence.repository.TokenRepository;
import com.cognizant.softvision.twitterapp.persistence.repository.UserRepository;
import com.cognizant.softvision.twitterapp.pojo.converter.UserConverter;
import com.cognizant.softvision.twitterapp.pojo.dto.UserDto;
import com.cognizant.softvision.twitterapp.pojo.payload.LoginRequest;
import com.cognizant.softvision.twitterapp.pojo.payload.UserRegistrationRequest;
import com.cognizant.softvision.twitterapp.service.exception.AlreadyExistsException;
import com.cognizant.softvision.twitterapp.service.exception.InvalidCredentialsException;
import com.cognizant.softvision.twitterapp.service.exception.UserNotFoundException;
import com.cognizant.softvision.twitterapp.service.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContextHolder userContextHolder;


    public List<UserDto> searchUser(String firstName, String lastName, String mail) {
        return StringUtils.isNotEmpty(firstName) ||
                StringUtils.isNotEmpty(lastName) ||
                StringUtils.isNotEmpty(mail) ?
                userRepository.findByFirstNameOrLastNameOrMail(firstName, lastName, mail).stream()
                        .map(UserConverter::fromEntityToDto)
                        .collect(Collectors.toList()) :
                userRepository.findAll().stream()
                        .map(UserConverter::fromEntityToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public void registerUser(UserRegistrationRequest userRegistrationRequest) {

        if (userRepository.findUserByMail(userRegistrationRequest.getMail()).isPresent())
            throw new AlreadyExistsException("User with this email already exists !");

        User user = UserConverter.fromRequestToEntity(userRegistrationRequest);
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));

        userRepository.save(user);

    }

    @Override
    public String loginUser(LoginRequest loginRequest) {

        var user = userRepository.findUserByMail(loginRequest.getMail());

        if (user.isEmpty())
            throw new UserNotFoundException("User with provided email does not exist !");

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword()))
            throw new InvalidCredentialsException("User email or password are incorrect !");

        Optional<Token> tokenOptional = tokenRepository.findByMail(loginRequest.getMail());

        return tokenOptional
                .map(token -> tokenRepository.save(TokenUtils.updateExistingToken(token)).getToken())
                .orElseGet(() -> tokenRepository.save(TokenUtils.createTokenEntity(user.get())).getToken());
    }

    @Override
    public void unregisterUser() {
        userRepository.deleteById(userContextHolder.getUser().getId());
    }

}
