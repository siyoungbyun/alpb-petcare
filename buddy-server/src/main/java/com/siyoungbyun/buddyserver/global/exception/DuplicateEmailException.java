package com.siyoungbyun.buddyserver.global.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("중복된 이메일입니다.");
    }
}
