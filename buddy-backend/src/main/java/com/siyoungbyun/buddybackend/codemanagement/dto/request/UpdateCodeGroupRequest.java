package com.siyoungbyun.buddybackend.codemanagement.dto.request;


import lombok.Getter;

@Getter
public class UpdateCodeGroupRequest {
    private String name;
    private String codeGroupId;
    private String description;
}
