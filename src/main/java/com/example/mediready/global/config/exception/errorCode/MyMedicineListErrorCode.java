package com.example.mediready.global.config.exception.errorCode;

import com.example.mediready.global.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyMedicineListErrorCode implements ErrorCode {
    MEDICINE_ALREADY_EXISTS("Medicine already exists", "이미 보관함에 존재하는 의약품입니다.",
        HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
