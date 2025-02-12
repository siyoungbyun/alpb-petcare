package com.siyoungbyun.buddyserver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ResponseDto {
    private final String status;
    private final String message;

    public static ResponseDto of(String status, String message) {
        return new ResponseDto(status, message);
    }
}
