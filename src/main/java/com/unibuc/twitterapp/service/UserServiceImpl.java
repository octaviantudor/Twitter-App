package com.unibuc.twitterapp.service;

import com.unibuc.twitterapp.persistence.entity.Token;
import com.unibuc.twitterapp.persistence.entity.User;
import com.unibuc.twitterapp.persistence.repository.TokenRepository;
import com.unibuc.twitterapp.persistence.repository.UserRepository;
import com.unibuc.twitterapp.pojo.converter.UserConverter;
import com.unibuc.twitterapp.pojo.dto.UserDto;
import com.unibuc.twitterapp.pojo.payload.LoginRequest;
import com.unibuc.twitterapp.pojo.payload.UserRegistrationRequest;
import com.unibuc.twitterapp.service.exception.AlreadyExistsException;
import com.unibuc.twitterapp.service.exception.InvalidCredentialsException;
import com.unibuc.twitterapp.service.exception.UserNotFoundException;
import com.unibuc.twitterapp.service.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthHelperImpl authHelper;
    private final UserConverter userConverter;


    @Transactional
    public List<UserDto> searchUser(String firstName, String lastName, String mail, String username) {
        return StringUtils.isNotEmpty(firstName) ||
                StringUtils.isNotEmpty(lastName) ||
                StringUtils.isNotEmpty(mail) ||
                StringUtils.isNotEmpty(username) ?
                userRepository.findByFirstNameOrLastNameOrMailOrUsername(firstName, lastName, mail, username).stream()
                        .map(userConverter::fromEntityToDto)
                        .collect(Collectors.toList()) :
                userRepository.findAll().stream()
                        .map(userConverter::fromEntityToDto)
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationRequest userRegistrationRequest) {

        if (userRepository.findUserByMail(userRegistrationRequest.getMail()).isPresent())
            throw new AlreadyExistsException("User with this email already exists !");

        User user = userConverter.fromRequestToEntity(userRegistrationRequest);
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));

        userRepository.save(user);

    }

    @Override
    @Transactional
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
    @Transactional
    public void unregisterUser() {
        var loggedUser = userRepository.findByUsername(authHelper.getUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username"));

        userRepository.deleteById(loggedUser.getId());
    }

    @Override
    public List<UserDto> findAll() {

        return userRepository.findAll().stream().map(userConverter::fromEntityToDto).collect(Collectors.toList());

    }

}
