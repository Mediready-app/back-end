package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {


    USER_EMAIL_ALREADY_EXISTS("USER_001", "이미 계정이 존재하는 이메일입니다.",
        HttpStatus.BAD_REQUEST),
    USER_NICKNAME_ALREADY_EXISTS("USER_002", "이미 존재하는 닉네임입니다",
        HttpStatus.BAD_REQUEST),
    PHARMACIST_LICENSE_FILE_IS_EMPTY("USER_003", "면허증 파일이 첨부되지 않았습니다.",
        HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USER_004", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    PASSWORD_MISMATCH("USER_005", "비밀번호가 일치하지 않는 유저입니다.", HttpStatus.UNAUTHORIZED);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
