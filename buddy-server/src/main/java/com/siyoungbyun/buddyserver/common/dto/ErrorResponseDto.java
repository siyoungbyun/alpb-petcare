package com.siyoungbyun.buddyserver.common.dto;

public class ErrorResponseDto extends ResponseDto {
    private ErrorResponseDto(String message) {
        super("error", message);
    }

    public static ErrorResponseDto of(String message) {
        return new ErrorResponseDto(message);
    }
}
