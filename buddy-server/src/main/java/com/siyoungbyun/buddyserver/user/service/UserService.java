package com.siyoungbyun.buddyserver.user.service;

import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.dto.SignupRequestDto;
import com.siyoungbyun.buddyserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // TODO: Hash the password before saving
        User user = User.of(signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getName());

        return userRepository.save(user);
    }

    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user.getPassword().equals(password);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
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
}
