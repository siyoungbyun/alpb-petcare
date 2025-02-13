package com.siyoungbyun.buddyserver.user.service;

import com.siyoungbyun.buddyserver.global.exception.UserNotFoundException;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.repository.UserRepository;
import com.siyoungbyun.buddyserver.user.request.LoginRequest;
import com.siyoungbyun.buddyserver.user.request.SignupRequest;
import com.siyoungbyun.buddyserver.user.request.UpdateUserRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // TODO: Hash the password before saving
        User user = User.of(signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName());

        return userRepository.save(user);
    }

    public User getUserById(Long userId) {

        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            return optUser.get();
        }
        throw new UserNotFoundException("유저를 찾을 수 없습니다.");
    }

    public String loginUser(LoginRequest loginRequest, HttpSession session) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.getPassword().equals(loginRequest.getPassword())) {
            session.setAttribute("user", user.getId());
            return session.getId();
        }
        return null;
    }

    public boolean resetPassword(Long userId, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Update Password
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public User updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (updateUserRequest.getName() != null) {
                user.setName(updateUserRequest.getName());
            }
            userRepository.save(user);
            return user;
        }
        // TODO: fix null return
        return null;
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
