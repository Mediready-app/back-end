package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MedicineErrorCode implements ErrorCode {
    INVALID_MEDICINE_ID("Invalid medicine id", "유효하지 않은 의약품 ID입니다.", HttpStatus.BAD_REQUEST);
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
