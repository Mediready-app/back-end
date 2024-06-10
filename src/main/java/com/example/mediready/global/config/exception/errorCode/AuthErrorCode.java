package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_JWT("AUTH_001", "JWT 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT("AUTH_002", "JWT 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_JWT("AUTH_003", "지원되지 않는 JWT 토큰입니다.", HttpStatus.BAD_REQUEST),
    EMPTY_JWT("AUTH_004", "JWT 토큰이 비어있습니다.", HttpStatus.BAD_REQUEST),
    LOGOUT_USER("AUTH_005", "로그아웃된 유저입니다.", HttpStatus.BAD_REQUEST);


    private final String errorCode;
    private final String message;
    private final HttpStatus status;

}