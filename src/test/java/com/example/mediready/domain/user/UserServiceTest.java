package com.example.mediready.domain.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private static final String INFO = "현재 먹고 있는 약이 없어요.";

    @Autowired
    private UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    public void 회원가입_성공() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        String result = userService.singupUser(signupReq);

        assertEquals("회원가입이 완료되었습니다.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    public void 회원가입_실패_이메일_중복() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.singupUser(signupReq));
        assertEquals(UserErrorCode.USER_EMAIL_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());

    }

    @Test
    @DisplayName("회원가입 실패 - 닉네임 중복")
    public void 회웝가입_실패_닉네임_중복() {
        PostUserSignupReq signupReq = new PostUserSignupReq(EMAIL, PASSWORD, NICKNAME, INFO);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(true);

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.singupUser(signupReq));
        assertEquals(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
    }
}
