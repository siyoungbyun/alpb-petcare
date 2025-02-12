package com.siyoungbyun.buddyserver.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessResponseWithDataDto<T> extends ResponseDto {
    @JsonProperty("data")
    private final T data;

    private SuccessResponseWithDataDto(String message, T data) {
        super("success", message);
        this.data = data;
    }

    public static <T> SuccessResponseWithDataDto<T> of(String message, T data) {
        return new SuccessResponseWithDataDto<>(message, data);
    }
}
