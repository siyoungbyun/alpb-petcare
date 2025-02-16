package com.siyoungbyun.buddybackend.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateSelfRequest {
    private String name;
    private String phoneNumber;
}
