package com.siyoungbyun.buddyserver.user.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String sessionId;
}
