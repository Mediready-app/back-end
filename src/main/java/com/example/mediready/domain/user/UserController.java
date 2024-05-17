package com.example.mediready.domain.user;

import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup-user")
    public BaseResponse<String> signupUser(@RequestBody PostUserSignupReq postUserSignupReq) {
        String nickname = userService.signupUser(postUserSignupReq);
        return new BaseResponse<>(nickname + "님의 회원가입이 완료되었습니다.");
    }

    @PostMapping("/user/signup-pharmacist")
    public BaseResponse<String> signupPharmacist(
        @RequestBody PostPharmacistSignupReq postPharmacistSignupReq) {
        String nickname = userService.signupPharmacist(postPharmacistSignupReq);
        return new BaseResponse<>(nickname + "님의 약사 회원가입이 완료되었습니다.");
    }
}
