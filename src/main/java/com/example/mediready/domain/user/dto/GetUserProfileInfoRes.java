package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.user.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserProfileInfoRes {
    private UserRole type;
    private String nickname;
    private String profileImgUrl;
    private String info;

}
