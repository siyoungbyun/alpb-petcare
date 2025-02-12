package com.siyoungbyun.buddyserver.user.dto;

import lombok.Builder;

@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
}
