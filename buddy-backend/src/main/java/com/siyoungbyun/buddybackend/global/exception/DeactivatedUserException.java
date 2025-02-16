package com.siyoungbyun.buddybackend.global.exception;

public class DeactivatedUserException extends RuntimeException {
    public DeactivatedUserException(String message) {
        super(message);
    }
}
