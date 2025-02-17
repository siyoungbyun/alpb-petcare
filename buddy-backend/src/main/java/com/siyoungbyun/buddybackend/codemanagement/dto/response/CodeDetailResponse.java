package com.siyoungbyun.buddybackend.codemanagement.dto.response;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeDetail;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CodeDetailResponse {
    private Long id;
    private String name;
    private String value;
    private String codeDetailId;
    private int sortOrder;
    private boolean isActive;
    private Long codeGroupId;

    static public CodeDetailResponse fromEntity(CodeDetail codeDetail) {
        return CodeDetailResponse.builder()
                .id(codeDetail.getId())
                .name(codeDetail.getName())
                .value(codeDetail.getValue())
                .codeDetailId(codeDetail.getCodeDetailId())
                .sortOrder(codeDetail.getSortOrder())
                .isActive(codeDetail.isActive())
                .codeGroupId(codeDetail.getCodeGroup().getId())
                .build();
    }
}
