package com.example.mediready.domain.user;


import com.example.mediready.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String type;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String profileImgUrl;

    private String info;

    private String fcmToken;
}