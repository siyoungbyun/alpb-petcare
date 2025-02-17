package com.siyoungbyun.buddybackend.codemanagement.dto.request;


import lombok.Getter;

@Getter
public class UpdateCodeDetailRequest {
    private String name;
    private String value;
    private String codeDetailId;
    private int sortOrder;
    private boolean isActive;
}
