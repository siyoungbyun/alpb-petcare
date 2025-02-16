package com.siyoungbyun.buddybackend.user.controller;


import com.siyoungbyun.buddybackend.auth.core.CustomUserDetails;
import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreatePetsitterRequest;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetsitterProfileResponse;
import com.siyoungbyun.buddybackend.petsitter.service.PetsitterProfileService;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.dto.request.UpdateSelfRequest;
import com.siyoungbyun.buddybackend.user.dto.response.UserResponse;
import com.siyoungbyun.buddybackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/me")
public class MeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetsitterProfileService petsitterProfileService;

    @GetMapping
    public ResponseEntity<DataResponse<UserResponse>> getSelf(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 정보 조회 성공",
                        UserResponse.fromEntity(customUserDetails.getUser())));
    }

    @PatchMapping
    public ResponseEntity<DataResponse<UserResponse>> updateSelf(@RequestBody UpdateSelfRequest updateSelfRequest,
                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.UpdateUser(customUserDetails.getId(), updateSelfRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 정보 수정 성공",
                        UserResponse.fromEntity(user)));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteSelf(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.deleteUser(customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ResponseStatus.SUCCESS, "회원탈퇴 성공"));
    }

    @PostMapping("/petsitter-profile")
    public ResponseEntity<DataResponse<PetsitterProfileResponse>> createPetsitterProfile(@RequestBody CreatePetsitterRequest createPetsitterRequest,
                                                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.createPetsitterProfile(user, createPetsitterRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 펫시터 프로필 생성 성공",
                        PetsitterProfileResponse.fromEntity(profile)));
    }
}
