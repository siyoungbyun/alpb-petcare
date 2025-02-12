package com.siyoungbyun.buddyserver.user.response;

import com.siyoungbyun.buddyserver.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String name;

    static public UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
