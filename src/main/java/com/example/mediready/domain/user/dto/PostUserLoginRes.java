package com.example.mediready.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostUserLoginRes {

    private String accessToken;
    private String refreshToken;
}
