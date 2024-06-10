package com.example.mediready.domain.user;

import com.example.mediready.domain.user.dto.ModifyProfileReq;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostResetAccessTokenRes;
import com.example.mediready.domain.user.dto.PostUserLoginReq;
import com.example.mediready.domain.user.dto.PostUserLoginRes;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.auth.AuthUser;
import io.lettuce.core.api.reactive.BaseRedisReactiveCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
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

    @PostMapping("/login")
    public BaseResponse<PostUserLoginRes> login(@RequestBody PostUserLoginReq postUserLoginReq) {
        return new BaseResponse<>("로그인에 성공했습니다.", userService.login(postUserLoginReq));
    }

    @PostMapping("/reset-accesstoken")
    public BaseResponse<PostResetAccessTokenRes> resetAccessToken(
        @RequestHeader("refresh-token") String refreshToken) {
        return new BaseResponse<>("새로운 access token 발급이 완료되었습니다.",
            userService.resetAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(
        @AuthUser User user,
        @RequestHeader("Authorization") String token) {
        userService.logout(user, token);
        return new BaseResponse<>("로그아웃에 성공했습니다.");
    }

    @DeleteMapping
    public BaseResponse<String> deactivateUser(@AuthUser User user) {
        userService.deleteUser(user);
        return new BaseResponse<>("탈퇴에 성공했습니다.");
    }

    @GetMapping("/profile")
    public BaseResponse<?> getProfileInfo(@AuthUser User user) {
        return new BaseResponse<>("프로필 정보입니다.", userService.getProfileInfo(user));
    }

    @PutMapping("/profile")
    public BaseResponse<String> modifyProfile(@AuthUser User user,
        @RequestPart MultipartFile imgFile, @RequestPart(name = "nickname") String nickname) {
        userService.modifyProfile(user, imgFile, nickname);
        return new BaseResponse<>("프로필이 수정되었습니다.");
    }

    @PutMapping("/info")
    public BaseResponse<String> modifyInfo(@AuthUser User user, @RequestBody String info) {
        userService.modifyInfo(user, info);
        return new BaseResponse<>("상태설명/소개글이 수정되었습니다.");
    }

    @PutMapping("/password")
    public BaseResponse<String> modifyPassword(@RequestParam String email, @RequestBody String password) {
        userService.modifyPassword(email, password);
        return new BaseResponse<>("비밀번호가 수정되었습니다.");
    }
}
