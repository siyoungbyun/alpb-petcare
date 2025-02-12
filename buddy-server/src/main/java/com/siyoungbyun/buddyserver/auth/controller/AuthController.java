package com.siyoungbyun.buddyserver.auth.controller;

import com.siyoungbyun.buddyserver.global.response.BaseResponse;
import com.siyoungbyun.buddyserver.global.response.ErrorResponse;
import com.siyoungbyun.buddyserver.global.response.SuccessWithDataResponse;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.request.LoginRequest;
import com.siyoungbyun.buddyserver.user.response.LoginResponse;
import com.siyoungbyun.buddyserver.user.response.UserResponse;
import com.siyoungbyun.buddyserver.user.request.SignupRequest;
import com.siyoungbyun.buddyserver.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> signup(@RequestBody SignupRequest signupRequest) {
        try {
            User registeredUser = userService.registerUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessWithDataResponse<>("회원가입 성공", UserResponse.fromEntity(registeredUser)));
        } catch (Exception e) {
            // TODO: Handle specific exceptions
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("이미 존재하는 이메일입니다."));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // Check if user exists and password matches
        String sessionId = userService.loginUser(loginRequest, session);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessWithDataResponse<>("로그인 성공", LoginResponse.builder().sessionId(sessionId).build()));
        // TODO: Handle failed login
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
