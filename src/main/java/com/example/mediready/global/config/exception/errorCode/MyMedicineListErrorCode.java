package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyMedicineListErrorCode implements ErrorCode {
    MEDICINE_ALREADY_EXISTS("MYMEDICINELIST_001", "이미 보관함에 존재하는 의약품입니다.",
        HttpStatus.BAD_REQUEST),
    MY_MEDICINE_LIST_NOT_OWNED_BY_USER("MYMEDICINELIST_002", "보관 의약품 소유자와 로그인한 유저가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    MY_MEDICINE_LIST_NOT_FOUND("MYMEDICINELIST_003", " 일치하는 보관함 정보를 찾을 수 없습니다.",
        HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
