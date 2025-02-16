package com.siyoungbyun.buddybackend.user.dto.response;

import com.siyoungbyun.buddybackend.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;

    static public UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
