package com.example.mediready.global.common.mail;

import com.example.mediready.global.config.redis.RedisService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailService emailService;
    private final RedisService redisService;

    @PostMapping
    public ResponseEntity<String> sendAuthEmail(@RequestParam("email") String email)
        throws MessagingException {
        emailService.sendAuthEmail(email);
        return ResponseEntity.ok("인증 이메일이 발송되었습니다.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAuthCode(@RequestParam("email") String email,
        @RequestParam("authCode") String authCode) {
        emailService.checkEmailAuthCode(email, authCode);
        redisService.setDataExpire("emailVerified:" + email, "true", 1200);
        return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
    }
}
