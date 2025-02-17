package com.siyoungbyun.buddybackend.codemanagement.dto.response;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeGroup;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CodeGroupResponse {
    private Long id;
    private String name;
    private String codeGroupId;
    private String description;

    static public CodeGroupResponse fromEntity(CodeGroup codeGroup) {
        return CodeGroupResponse.builder()
                .id(codeGroup.getId())
                .name(codeGroup.getName())
                .description(codeGroup.getDescription())
                .codeGroupId(codeGroup.getCodeGroupId())
                .build();
    }
}
