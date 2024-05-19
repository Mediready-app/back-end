package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPharmacistSignupReq {

    private String email;
    private String password;
    private String nickname;
    private String info;
    private String location;

    public User toUserEntity() {
        return User
            .builder()
            .email(this.email)
            .password(this.password)
            .nickname(this.nickname)
            .info(this.info)
            .type("pharmacist")
            .build();
    }

    public Pharmacist toPharmacistEntity(User user) {
        return Pharmacist
            .builder()
            .user(user)
            .location(this.location)
            .mannerScore(50)
            .build();
    }
}
