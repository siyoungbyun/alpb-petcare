package com.siyoungbyun.buddyserver.user.request;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private String password;
    private String name;
}
