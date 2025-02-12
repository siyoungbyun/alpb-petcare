package com.siyoungbyun.buddyserver.global.response;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class BaseResponse {
    public final ResponseStatus status;
    public final String message;
}
