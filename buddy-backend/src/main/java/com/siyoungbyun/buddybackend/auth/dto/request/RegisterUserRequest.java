package com.siyoungbyun.buddybackend.auth.dto.request;

import lombok.Getter;

@Getter
public class RegisterUserRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}

