package com.dongnaebook.common.exception;

import org.springframework.security.crypto.password.PasswordEncoder;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super(message);
    }
}
