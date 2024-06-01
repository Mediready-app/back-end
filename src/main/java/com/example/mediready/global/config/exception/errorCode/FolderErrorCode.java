package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FolderErrorCode implements ErrorCode {
    FOLDER_NAME_DUPLICATE("Folder name duplicate", "중복된 폴더 이름이 존재합니다.", HttpStatus.BAD_REQUEST),
    FOLDER_PRIORITY_DUPLICATE("Folder priority duplicate", "중복된 폴더 우선순위가 존재합니다.",
        HttpStatus.BAD_REQUEST),
    FOLDER_NAME_NOT_FOUND("Folder name not found", "일치하는 폴더명을 찾지 못했습니다.", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR("Database error", "데이터베이스 관련 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
