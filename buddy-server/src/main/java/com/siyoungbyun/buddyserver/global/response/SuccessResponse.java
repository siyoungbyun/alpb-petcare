package com.siyoungbyun.buddyserver.global.response;


public class SuccessResponse extends BaseResponse {

    public SuccessResponse(String message) {
        super(ResponseStatus.SUCCESS, message);
    }
}
