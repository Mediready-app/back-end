package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {


    USER_EMAIL_ALREADY_EXISTS("USER_001", "이미 계정이 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    USER_NICKNAME_ALREADY_EXISTS("USER_002", "이미 존재하는 닉네임입니다", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
