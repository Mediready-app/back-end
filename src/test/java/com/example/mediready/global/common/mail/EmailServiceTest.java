package com.example.mediready.global.common.mail;

import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.EmailErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Mail Service Test")
public class EmailServiceTest {

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    @Test
    @DisplayName("인증 메일 전송 성공")
    void 인증_메일_전송_성공() {
        String email = "test@example.com";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doReturn(mimeMessage).when(mailSender).createMimeMessage();

        emailService.sendAuthEmail(email);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send((MimeMessage) any());
        verify(redisService, times(1)).setDataExpire(eq(email), anyString(), anyLong());
    }

    @Test
    @DisplayName("인증 메일 전송 실패 - MailException")
    void 인증_메일_전송_실패_MailException() {
        String email = "test@example.com";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doReturn(mimeMessage).when(mailSender).createMimeMessage();

        doThrow(new BaseException(EmailErrorCode.MAIL_SEND_FAILED)).when(mailSender)
            .send(mimeMessage);

        BaseException exception = assertThrows(BaseException.class,
            () -> emailService.sendAuthEmail(email));
        assertNotNull(exception);
        assertEquals(EmailErrorCode.MAIL_SEND_FAILED.getErrorCode(), exception.getErrorCode());
        verify(redisService, never()).setDataExpire(any(), any(), anyInt());
    }

    @Test
    @DisplayName("인증 메일 전송 실패 - MessagingException")
    void 인증_메일_전송_실패_MessagingException() {
        String email = "test@examplecom";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doReturn(mimeMessage).when(mailSender).createMimeMessage();

        doThrow(new BaseException(EmailErrorCode.INVALID_MAIL_FORMAT)).when(mailSender)
            .send(mimeMessage);

        BaseException exception = assertThrows(BaseException.class,
            () -> emailService.sendAuthEmail(email));
        assertNotNull(exception);
        assertEquals(EmailErrorCode.INVALID_MAIL_FORMAT.getErrorCode(), exception.getErrorCode());
        verify(redisService, never()).setDataExpire(any(), any(), anyInt());
    }

    @Test
    @DisplayName("인증 코드 확인 - 코드 일치하는 경우")
    void 인증_코드_확인_코드_일치() {
        String email = "test@example.com";
        String authCode = "123456";
        when(redisService.checkData(email, authCode)).thenReturn(true);

        assertDoesNotThrow(() -> emailService.checkEmailAuthCode(email, authCode));
    }

    @Test
    @DisplayName("인증 코드 확인 - 코드 불일치하는 경우")
    void 인증_코드_확인_코드_불일치() {
        String email = "test@example.com";
        String authCode = "123456";
        when(redisService.checkData(email, authCode)).thenReturn(false);

        BaseException exception = assertThrows(BaseException.class,
            () -> emailService.checkEmailAuthCode(email, authCode));
        assertEquals(EmailErrorCode.NO_MATCHING_AUTH_CODE.getErrorCode(), exception.getErrorCode());
    }

    @Test
    @DisplayName("인증 코드 확인 - 코드가 만료된 경우")
    void 인증_코드_확인_코드_만료() {
        String email = "test@example.com";
        String authCode = "123456";
        when(redisService.checkData(email, authCode)).thenReturn(false);

        BaseException exception = assertThrows(BaseException.class,
            () -> emailService.checkEmailAuthCode(email, authCode));
        assertEquals(EmailErrorCode.NO_MATCHING_AUTH_CODE.getErrorCode(), exception.getErrorCode());
    }
}