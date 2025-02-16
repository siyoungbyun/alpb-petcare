package com.siyoungbyun.buddybackend.auth.service;

import com.siyoungbyun.buddybackend.auth.core.CustomUserDetails;
import com.siyoungbyun.buddybackend.global.exception.DeactivatedUserException;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (user.getDeletedAt() != null) {
            throw new DeactivatedUserException("삭제된 유저입니다.");
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (user.getDeletedAt() != null) {
            throw new DeactivatedUserException("삭제된 유저입니다.");
        }
        return new CustomUserDetails(user);
    }
}