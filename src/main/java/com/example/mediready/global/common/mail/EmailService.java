package com.example.mediready.global.common.mail;

import com.example.mediready.domain.user.UserRepository;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.EmailErrorCode;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;
    private final UserRepository userRepository;

    public static final int AUTH_CODE_LENGTH = 6;
    public static final int EXPIRE_TIME = 300;

    public void sendAuthEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BaseException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }

        String authCode = createAuthCode(); // 인증 코드 생성

        try {
            MimeMessage mail = mailSender.createMimeMessage();
            String mailContent = "<h1>[이메일 인증]</h1><br><h3>이메일 인증 번호 : " + authCode + "</h3>";
            mail.setSubject("회원가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MailException e) {
            throw new BaseException(EmailErrorCode.MAIL_SEND_FAILED);
        } catch (MessagingException e) {
            throw new BaseException(EmailErrorCode.INVALID_MAIL_FORMAT);
        }
        redisService.setDataExpire(email, authCode, EXPIRE_TIME);
    }

    public void checkEmailAuthCode(String email, String authCode) {
        if (!redisService.checkData(email, authCode)) {
            throw new BaseException(EmailErrorCode.NO_MATCHING_AUTH_CODE);
        }
    }

    private String createAuthCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < EmailService.AUTH_CODE_LENGTH; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }
        return sb.toString();
    }

}
