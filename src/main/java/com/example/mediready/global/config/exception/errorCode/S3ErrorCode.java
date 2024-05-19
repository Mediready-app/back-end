package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    EMPTY_FILE_EXCEPTION("S3_001", "빈 파일 입니다.", HttpStatus.BAD_REQUEST),

    NO_FILE_EXTENSION("S3_002", "파일 확장자가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    INVALID_FILE_EXTENSION("S3_003", "유효하지 않은 파일 확장자 입니다.", HttpStatus.BAD_REQUEST),

    PUT_OBJECT_EXCEPTION("S3_004", "Amazon S3에 파일을 업로드 하는데 실패했습니다.",
        HttpStatus.INTERNAL_SERVER_ERROR),

    IO_EXCEPTION_ON_FILE_UPLOAD("S3_005",
        "file upload 도중 file.getInputStream() 또는 is.readAllBytes()에서 에러가 발생했습니다.",
        HttpStatus.INTERNAL_SERVER_ERROR),

    ADDRESS_URL_ERROR_ON_IMAGE_DELETE("S3_006", "image url에 문제가 있습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
