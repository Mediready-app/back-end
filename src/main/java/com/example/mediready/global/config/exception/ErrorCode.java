package com.example.mediready.global.config.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getErrorCode();

    String getMessage();

    HttpStatus getStatus();
}