package com.example.mediready.domain.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.pharmacist.PharmacistRepository;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@DisplayName("User Service Test")
public class UserServiceTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "mediready2024@";
    private static final String NICKNAME = "김유리";
    private static final String INFO = "소개 및 설명";
    private static final String LOCATION = "인천시 미추홀구";

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PharmacistRepository pharmacistRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("일반유저 회원가입 성공")
    public void 일반유저_회원가입_성공() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        String result = userService.signupUser(signupReq);

        assertEquals("회원가입이 완료되었습니다.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("일반유저 회원가입 실패 - 이메일 중복")
    public void 일반유저_회원가입_실패_이메일_중복() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupUser(signupReq));
        assertEquals(UserErrorCode.USER_EMAIL_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("일반유저 회원가입 실패 - 닉네임 중복")
    public void 일반유저_회웝가입_실패_닉네임_중복() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupUser(signupReq));
        assertEquals(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("약사유저 회원가입 성공")
    public void 약사유저_회원가입_성공() {
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq(EMAIL, PASSWORD, NICKNAME,
            INFO, LOCATION);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        String result = userService.signupPharmacist(signupReq);

        assertEquals("회원가입이 완료되었습니다.", result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(pharmacistRepository, times(1)).save(any(Pharmacist.class));
    }

    @Test
    @DisplayName("약사유저 회원가입 실패 - 이메일 중복")
    public void 약사_회원가입_실패_이메일_중복() {
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq(EMAIL, PASSWORD, NICKNAME,
            INFO, LOCATION);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupPharmacist(signupReq));
        assertEquals(UserErrorCode.USER_EMAIL_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any(User.class));
        verify(pharmacistRepository, never()).save(any(Pharmacist.class));
    }

    @Test
    @DisplayName("약사유저 회원가입 실패 - 닉네임 중복")
    public void 약사유저_회웝가입_실패_닉네임_중복() {
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq(EMAIL, PASSWORD, NICKNAME,
            INFO, LOCATION);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupPharmacist(signupReq));
        assertEquals(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any(User.class));
        verify(pharmacistRepository, never()).save(any(Pharmacist.class));
    }
}
