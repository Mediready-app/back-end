package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FolderErrorCode implements ErrorCode {
    FOLDER_NAME_DUPLICATE("FOLDER_001", "중복된 폴더 이름이 존재합니다.", HttpStatus.BAD_REQUEST),
    FOLDER_PRIORITY_DUPLICATE("FOLDER_002", "중복된 폴더 우선순위가 존재합니다.",
        HttpStatus.BAD_REQUEST),
    FOLDER_NAME_NOT_FOUND("FOLDER_003", "일치하는 폴더명을 찾지 못했습니다.", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR("FOLDER_004", "데이터베이스 관련 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FOLDER_ID("FOLDER_005", "유효하지 않은 폴더 id입니다.", HttpStatus.BAD_REQUEST),
    FOLDER_NOT_OWNED_BY_USER("FOLDER_006", "로그인한 유저의 folder가 아닙니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
