package com.siyoungbyun.buddybackend.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
}
