package com.siyoungbyun.buddybackend.user.service;

import com.siyoungbyun.buddybackend.auth.core.JwtTokenProvider;
import com.siyoungbyun.buddybackend.auth.dto.request.LoginRequest;
import com.siyoungbyun.buddybackend.global.exception.DuplicateEmailException;
import com.siyoungbyun.buddybackend.global.exception.UserNotFoundException;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.auth.dto.request.RegisterUserRequest;
import com.siyoungbyun.buddybackend.user.dto.request.UpdateSelfRequest;
import com.siyoungbyun.buddybackend.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User findByEmail(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 유저입니다.");
        }
        return optUser.get();
    }

    @Transactional
    public User UpdateUser(Long userId, UpdateSelfRequest updateSelfRequest) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 유저입니다.");
        }
        User user = userOpt.get();
        if (updateSelfRequest.getName() != null) {
            user.setName(updateSelfRequest.getName());
        }
        if (updateSelfRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(updateSelfRequest.getPhoneNumber());
        }
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 유저입니다.");
        }
        User user = userOpt.get();
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public User registerUser(RegisterUserRequest registerUserRequest) {
        if (isEmailTaken(registerUserRequest.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        User newUser = User.builder()
                .email(registerUserRequest.getEmail())
                .name(registerUserRequest.getName())
                .phoneNumber(registerUserRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .isAdmin(false)
                .build();

        return userRepository.save(newUser);
    }

    public String getAccessToken(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Generate JWT token
        return jwtTokenProvider.generateToken(authentication);
    }
}