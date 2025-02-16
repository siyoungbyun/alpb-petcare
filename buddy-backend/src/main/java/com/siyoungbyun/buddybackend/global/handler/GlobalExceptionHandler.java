package com.siyoungbyun.buddybackend.global.handler;

import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.global.exception.AlreadyExistsException;
import com.siyoungbyun.buddybackend.global.exception.DeactivatedUserException;
import com.siyoungbyun.buddybackend.global.exception.DuplicateEmailException;
import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<BaseResponse> handle(final NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(final UsernameNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(value = DuplicateEmailException.class)
    public ResponseEntity<BaseResponse> handle(final DuplicateEmailException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse> handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse(ResponseStatus.ERROR, "잘못된 인증입니다. 관리자에게 연락 바랍니다."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(DeactivatedUserException.class)
    public ResponseEntity<BaseResponse> handleDeactivatedUserException(DeactivatedUserException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse(ResponseStatus.ERROR, "알 수 없는 에러입니다. 관리자에게 연락 바랍니다."));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(ResponseStatus.ERROR, exception.getMessage()));
    }
}

