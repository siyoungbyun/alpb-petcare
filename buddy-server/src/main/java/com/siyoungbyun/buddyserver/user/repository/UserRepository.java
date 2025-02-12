package com.siyoungbyun.buddyserver.user.repository;

import com.siyoungbyun.buddyserver.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
