package com.siyoungbyun.buddybackend.codemanagement.controller;


import com.siyoungbyun.buddybackend.codemanagement.domain.CodeGroup;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.CreateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.UpdateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.service.CodeManagementService;
import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/code-groups")
public class CodeGroupController {

    @Autowired
    private CodeManagementService codeManagementService;

    @GetMapping
    public ResponseEntity<DataResponse<List<CodeGroup>>> getAllCodeGroups() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "코드 그룹 목록 조회 성공",
                        codeManagementService.getCodeGroups()));
    }

    @GetMapping("/{codeGroupId}")
    public ResponseEntity<DataResponse<CodeGroup>> getCodeGroup(@PathVariable Long codeGroupId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "코드 그룹 조회 성공",
                        codeManagementService.getCodeGroup(codeGroupId)));
    }

    @PostMapping
    public ResponseEntity<DataResponse<CodeGroup>> createCodeGroup(
            @RequestBody CreateCodeGroupRequest createCodeGroupRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "코드 그룹 생성 성공",
                        codeManagementService.createCodeGroup(createCodeGroupRequest)));
    }

    @PatchMapping("/{codeGroupId}")
    public ResponseEntity<DataResponse<CodeGroup>> updateCodeGroup(
            @PathVariable Long codeGroupId,
            @RequestBody UpdateCodeGroupRequest updateCodeGroupRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "코드 그룹 수정 성공",
                        codeManagementService.updateCodeGroup(codeGroupId, updateCodeGroupRequest)));
    }

    @DeleteMapping("/{codeGroupId}")
    public ResponseEntity<BaseResponse> deleteCodeGroup(@PathVariable Long codeGroupId) {
        codeManagementService.deleteCodeGroup(codeGroupId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ResponseStatus.SUCCESS, "코드 그룹 삭제 성공"));
    }
}
