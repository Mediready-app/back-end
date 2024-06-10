package com.example.mediready.domain.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ModifyProfileReq {
    private MultipartFile imgFile;
    private String nickname;
}