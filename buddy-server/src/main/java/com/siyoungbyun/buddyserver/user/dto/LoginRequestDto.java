package com.siyoungbyun.buddyserver.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}