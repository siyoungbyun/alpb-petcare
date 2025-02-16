package com.siyoungbyun.buddybackend.global.dto.response;

import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class BaseResponse {
    public final ResponseStatus status;
    public final String message;
}
