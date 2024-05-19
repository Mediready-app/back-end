package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
            .type("user")
            .build();
    }
}
