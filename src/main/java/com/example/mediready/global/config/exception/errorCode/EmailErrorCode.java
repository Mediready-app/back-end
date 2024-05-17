package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailErrorCode implements ErrorCode {

    NO_MATCHING_AUTH_CODE("EMAIL_001", "인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    MAIL_SEND_FAILED("EMAIL_002", "메일 발송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_MAIL_FORMAT("EMAIL_003", "잘못된 메일 형식입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VERIFIED("EMAIL_004", "인증되지 않은 이메일입니다.", HttpStatus.FORBIDDEN);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
