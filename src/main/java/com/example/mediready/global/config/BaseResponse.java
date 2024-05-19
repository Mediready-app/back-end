package com.example.mediready.global.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> { //BaseResponse 객체를 사용할때 성공, 실패 경우

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(String message, T result) {
        this.isSuccess = true;
        this.message = message;
        this.code = 200;
        this.result = result;
    }

    public BaseResponse(String message) {
        this.isSuccess = true;
        this.message = message;
        this.code = 200;
    }

}