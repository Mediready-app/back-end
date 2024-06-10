package com.example.mediready.domain.user.dto;

import com.example.mediready.domain.user.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetPharmacistProfileInfoRes {
    private UserRole type;
    private String nickname;
    private String profileImgUrl;
    private String info;
    private String location;
    private int reviewCnt;
    private int mannerScore;
    private int likeCnt;
}
