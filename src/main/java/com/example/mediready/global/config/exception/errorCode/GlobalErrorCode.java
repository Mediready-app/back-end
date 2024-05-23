package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    INVALID_ARGUMENT_ERROR("Invalid argument error ", "올바른 argument를 입력해주세요.", HttpStatus.BAD_REQUEST),
    NOT_SUPPORTED_URI_ERROR("Not supported uri error", "올바른 URI로 접근해주세요.", HttpStatus.NOT_FOUND),
    NOT_SUPPORTED_METHOD_ERROR("Not supported method error", "지원하지 않는 Method입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR("Not supported media type error", "지원하지 않는 Media type입니다.",
        HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    SERVER_ERROR("Server error", "서버와의 연결에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED("Access denied", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}