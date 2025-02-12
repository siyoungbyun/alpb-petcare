package com.siyoungbyun.buddyserver.global.response;


public class SuccessWithDataResponse<T> extends SuccessResponse {
    public final T data;

    public SuccessWithDataResponse(String message, T data) {
        super(message);
        this.data = data;
    }
}
