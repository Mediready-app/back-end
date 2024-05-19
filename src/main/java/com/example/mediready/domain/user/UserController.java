package com.example.mediready.domain.user;

import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup-user")
    public BaseResponse<String> signupUser(@RequestPart MultipartFile imgFile,
        @RequestPart(name = "request") PostUserSignupReq postUserSignupReq) {
        String nickname = userService.signupUser(imgFile, postUserSignupReq);
        return new BaseResponse<>(nickname + "님의 회원가입이 완료되었습니다.");
    }

    @PostMapping("/signup-pharmacist")
    public BaseResponse<String> signupPharmacist(
        @RequestPart MultipartFile imgFile,
        @RequestPart MultipartFile licenseFile,
        @RequestPart(name = "request") PostPharmacistSignupReq postPharmacistSignupReq) {
        String nickname = userService.signupPharmacist(imgFile, licenseFile,
            postPharmacistSignupReq);
        return new BaseResponse<>(nickname + "님의 약사 회원가입이 완료되었습니다.");
    }
}
