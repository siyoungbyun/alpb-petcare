package com.siyoungbyun.buddybackend.codemanagement.controller;


import com.siyoungbyun.buddybackend.codemanagement.domain.CodeDetail;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.UpdateCodeDetailRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.response.CodeDetailResponse;
import com.siyoungbyun.buddybackend.codemanagement.service.CodeManagementService;
import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/code-details")
public class CodeDetailController {

    @Autowired
    private CodeManagementService codeManagementService;

    @GetMapping("/{codeDetailId}")
    public ResponseEntity<DataResponse<CodeDetailResponse>> getCodeDetail(@PathVariable Long codeDetailId) {
        CodeDetail codeDetail = codeManagementService.getCodeDetail(codeDetailId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(
                        ResponseStatus.SUCCESS, "코드 조회 성공",
                        CodeDetailResponse.fromEntity(codeDetail)));
    }

    @PatchMapping("/{codeDetailId}")
    public ResponseEntity<DataResponse<CodeDetailResponse>> updateCodeDetail(
            @PathVariable Long codeDetailId,
            @RequestBody UpdateCodeDetailRequest updateCodeDetailRequest) {
        CodeDetail codeDetail = codeManagementService.updateCodeDetail(codeDetailId, updateCodeDetailRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "코드 수정 성공",
                        CodeDetailResponse.fromEntity(codeDetail)));
    }

    @DeleteMapping("/{codeDetailId}")
    public ResponseEntity<BaseResponse> updateCodeDetail(@PathVariable Long codeDetailId) {
        codeManagementService.deleteCodeDetail(codeDetailId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ResponseStatus.SUCCESS, "코드 삭제 성공"));
    }
}
