package com.siyoungbyun.buddybackend.codemanagement.dto.request;


import lombok.Getter;

@Getter
public class CreateCodeGroupRequest {
    private String name;
    private String codeGroupId;
    private String description;
}
