package com.siyoungbyun.buddyserver.global.response;

public class ErrorResponse extends BaseResponse {

    public ErrorResponse(String message) {
        super(ResponseStatus.ERROR, message);
    }
}
