package com.siyoungbyun.buddyserver.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
}
