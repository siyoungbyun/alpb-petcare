package com.siyoungbyun.buddyserver.user.controller;

import com.siyoungbyun.buddyserver.global.response.BaseResponse;
import com.siyoungbyun.buddyserver.global.response.ErrorResponse;
import com.siyoungbyun.buddyserver.global.response.SuccessWithDataResponse;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.request.ResetPasswordRequest;
import com.siyoungbyun.buddyserver.user.request.UpdateUserRequest;
import com.siyoungbyun.buddyserver.user.response.UserResponse;
import com.siyoungbyun.buddyserver.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/me")
public class MeController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<BaseResponse> getMe(@RequestAttribute("userId") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessWithDataResponse<>("내 정보 조회 성공", UserResponse.fromEntity(user)));
    }

    @PatchMapping
    public ResponseEntity<BaseResponse> updateMe(@RequestAttribute("userId") Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
        User user = userService.updateUser(userId, updateUserRequest);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessWithDataResponse<>("유저 업데이트 성공", UserResponse.fromEntity(user)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("존재하지 않는 유저입니다."));
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteMe(@RequestAttribute("userId") Long userId) {
        boolean success = userService.deleteUser(userId);
        if (success) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("삭제할 수 없습니다."));
        }
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<BaseResponse> resetPassword(HttpServletRequest request, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("권한이 없습니다."));
        }
        Long id = (Long) session.getAttribute("user");
        boolean success = userService.resetPassword(id, resetPasswordRequest.getNewPassword());
        if (success) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("비밀번호 변경 실패."));
        }
    }
}
