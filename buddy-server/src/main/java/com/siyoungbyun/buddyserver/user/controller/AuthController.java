package com.siyoungbyun.buddyserver.user.controller;

import com.siyoungbyun.buddyserver.common.dto.ErrorResponseDto;
import com.siyoungbyun.buddyserver.common.dto.ResponseDto;
import com.siyoungbyun.buddyserver.common.dto.SuccessResponseWithDataDto;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.dto.LoginRequestDto;
import com.siyoungbyun.buddyserver.user.dto.SignupRequestDto;
import com.siyoungbyun.buddyserver.user.service.UserService;
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
    public ResponseEntity<ResponseDto> signup(@RequestBody SignupRequestDto userDTO) {
        try {
            User registeredUser = userService.registerUser(userDTO);
            return new ResponseEntity<>(
                    SuccessResponseWithDataDto.of("회원가입 성공", registeredUser), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: Specific Exception
            return new ResponseEntity<>(
                    ErrorResponseDto.of("중복된 이메일입니다."),  HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        // Check if user exists and password matches
        if (userService.validateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword())) {
        }
        if (USER_DB.containsKey(user.getUsername()) && USER_DB.get(user.getUsername()).equals(user.getPassword())) {
            session.setAttribute("user", user.getEmail()); // Store user in session
            response.put("message", "Login successful");
            response.put("sessionId", session.getId());
        } else {
            response.put("message", "Invalid credentials");
        }

        return response;
    }
}
