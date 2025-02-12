package com.siyoungbyun.buddyserver.user.controller;

import com.siyoungbyun.buddyserver.common.dto.ResponseDto;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.dto.ResetPasswordRequestDto;
import com.siyoungbyun.buddyserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO: move to /me
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@PathVariable Long id,
                                                     @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        boolean success = userService.resetPassword(id, resetPasswordRequestDto.getNewPassword());
        if (success) {
            return ResponseEntity.ok(ResponseDto.of("success", "비밀번호 변경 성공"));
        } else {
            return ResponseEntity.badRequest().body(ResponseDto.of("error", "비밀번호 변경 실패"));
        }
    }
}
