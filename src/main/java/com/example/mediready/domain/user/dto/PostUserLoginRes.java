package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostUserLoginRes {

    private UserRole type;
    private String accessToken;
    private String refreshToken;
}
