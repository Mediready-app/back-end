package com.example.mediready.global.common.mail;

import com.example.mediready.global.config.BaseResponse;
import com.example.mediready.global.config.redis.RedisService;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailService emailService;
    private final RedisService redisService;

    @PostMapping("/signup")
    public BaseResponse<String> sendAuthEmailSignup(@RequestParam("email") String email) {
        emailService.sendAuthEmailSignup(email);
        return new BaseResponse<>("회원가입을 위한 인증 이메일이 발송되었습니다.");
    }

    @PostMapping("/signup/verify")
    public BaseResponse<String> verifyAuthCodeSignup(@RequestParam("email") String email,
        @RequestParam("authCode") String authCode) {
        emailService.checkEmailAuthCode("signup", email, authCode);
        redisService.setDataExpire("emailVerifiedSignup:" + email, "true", 1200);
        return new BaseResponse<>("회원가입을 위한 이메일 인증이 완료되었습니다.");
    }

    @PostMapping("/password")
    public BaseResponse<String> sendAuthEmailPassword(@RequestParam("email") String email) {
        emailService.sendAuthEmailPassword(email);
        return new BaseResponse<>("비밀번호 재설정을 위한 인증 이메일이 발송되었습니다.");
    }

    @PostMapping("/password/verify")
    public BaseResponse<String> verifyAuthCodePassword(@RequestParam("email") String email,
        @RequestParam("authCode") String authCode) {
        emailService.checkEmailAuthCode("password", email, authCode);
        redisService.setDataExpire("emailVerifiedPassword:" + email, "true", 1200);
        return new BaseResponse<>("비밀번호 재설정을 위한 이메일 인증이 완료되었습니다.");
    }
}
