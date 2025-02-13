package com.siyoungbyun.buddyserver.user.controller;

import com.siyoungbyun.buddyserver.global.response.BaseResponse;
import com.siyoungbyun.buddyserver.global.response.ErrorResponse;
import com.siyoungbyun.buddyserver.global.response.SuccessWithDataResponse;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.response.UserResponse;
import com.siyoungbyun.buddyserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessWithDataResponse<>("유저 조회 성공", UserResponse.fromEntity(user)));
    }
}
