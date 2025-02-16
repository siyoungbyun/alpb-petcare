package com.siyoungbyun.buddybackend.auth.controller;

import com.siyoungbyun.buddybackend.auth.dto.request.LoginRequest;
import com.siyoungbyun.buddybackend.auth.dto.request.RegisterUserRequest;
import com.siyoungbyun.buddybackend.auth.dto.response.LoginResponse;
import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.global.exception.DuplicateEmailException;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.dto.response.UserResponse;
import com.siyoungbyun.buddybackend.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> signup(@RequestBody RegisterUserRequest registerUserRequest) {
        User registeredUser = userService.registerUser(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DataResponse<>(ResponseStatus.SUCCESS,"회원가입 성공", UserResponse.fromEntity(registeredUser)));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) {
        // Check if user exists and password matches
        String token = userService.getAccessToken(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(
                        ResponseStatus.SUCCESS,
                        "로그인 성공",
                        LoginResponse.builder().token(token).build()));
    }
}
