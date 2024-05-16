package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostUserSignupReq {

    private String email;
    private String password;
    private String nickname;
    private String info;

    public User toUserEntity() {
        return User
            .builder()
            .email(this.email)
            .password(this.password)
            .nickname(this.nickname)
            .info(this.info)
            .build();
    }
}
