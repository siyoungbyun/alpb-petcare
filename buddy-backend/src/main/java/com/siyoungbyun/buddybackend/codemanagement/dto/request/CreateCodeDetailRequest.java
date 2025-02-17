package com.siyoungbyun.buddybackend.codemanagement.dto.request;

import lombok.Getter;

@Getter
public class CreateCodeDetailRequest {
    private String name;
    private String value;
    private String codeDetailId;
}
