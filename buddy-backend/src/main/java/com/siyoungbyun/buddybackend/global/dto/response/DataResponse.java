package com.siyoungbyun.buddybackend.global.dto.response;

import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;

public class DataResponse<T> extends BaseResponse {
    public final T data;

    public DataResponse(ResponseStatus status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
