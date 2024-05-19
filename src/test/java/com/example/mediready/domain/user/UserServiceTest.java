package com.example.mediready.domain.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mediready.domain.pharmacist.Pharmacist;
import com.example.mediready.domain.pharmacist.PharmacistRepository;
import com.example.mediready.domain.user.dto.PostPharmacistSignupReq;
import com.example.mediready.domain.user.dto.PostUserSignupReq;
import com.example.mediready.global.config.S3.S3Service;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.EmailErrorCode;
import com.example.mediready.global.config.exception.errorCode.UserErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@DisplayName("User Service Test")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private RedisService redisService;
    @MockBean
    private S3Service s3Service;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PharmacistRepository pharmacistRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("일반유저 회원가입 성공")
    public void 일반유저_회원가입_성공() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        PostUserSignupReq signupReq = new PostUserSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        when(redisService.getData("emailVerified:" + email)).thenReturn("true");

        UserService userService = new UserService(userRepository, pharmacistRepository, s3Service,
            redisService, passwordEncoder);
        String result = userService.signupUser(imgFile, signupReq);

        // Verify
        assertEquals(nickname, result);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("일반유저 회원가입 실패 - 닉네임 중복")
    public void 일반유저_회웝가입_실패_닉네임_중복() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        PostUserSignupReq signupReq = new PostUserSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        when(redisService.getData("emailVerified:" + email)).thenReturn("true");
        when(userRepository.existsByNickname(nickname)).thenReturn(true);

        UserService userService = new UserService(userRepository, pharmacistRepository, s3Service,
            redisService, passwordEncoder);
        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupUser(imgFile, signupReq));

        assertEquals(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("약사유저 회원가입 성공")
    public void 약사유저_회원가입_성공() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        MultipartFile licenseFile = new MockMultipartFile("testLicense.pdf", new byte[0]);
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        when(redisService.getData("emailVerified:" + email)).thenReturn("true");

        // Test
        UserService userService = new UserService(userRepository, pharmacistRepository, s3Service,
            redisService, passwordEncoder);
        String result = userService.signupPharmacist(imgFile, licenseFile, signupReq);

        assertEquals(nickname, result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(pharmacistRepository, times(1)).save(any(Pharmacist.class));
    }

    @Test
    @DisplayName("약사유저 회원가입 실패 - 닉네임 중복")
    public void 약사유저_회웝가입_실패_닉네임_중복() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        MultipartFile licenseFile = new MockMultipartFile("testLicense.pdf", new byte[0]);
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        when(redisService.getData("emailVerified:" + email)).thenReturn("true");
        when(userRepository.existsByNickname(nickname)).thenReturn(true);

        UserService userService = new UserService(userRepository, pharmacistRepository, s3Service,
            redisService, passwordEncoder);
        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupPharmacist(imgFile, licenseFile, signupReq));

        assertEquals(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS.getErrorCode(),
            exception.getErrorCode());
        verify(userRepository, never()).save(any());
        verify(userRepository, never()).save(any(User.class));
        verify(pharmacistRepository, never()).save(any(Pharmacist.class));
    }

    @Test
    @DisplayName("일반유저 회원가입 실패 - 인증되지 않은 이메일")
    void 일반유저_회원가입_실패_인증되지_않은_이메일() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        PostUserSignupReq signupReq = new PostUserSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        doThrow(new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED)).when(redisService)
            .getData("emailVerified:" + signupReq.getEmail());

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupUser(imgFile, signupReq));
        assertNotNull(exception);
        assertEquals(EmailErrorCode.EMAIL_NOT_VERIFIED.getErrorCode(), exception.getErrorCode());
        verify(userRepository, never()).existsByNickname(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("약사유저 회원가입 실패 - 인증되지 않은 이메일")
    void 약사유저_회원가입_실패_인증되지_않은_이메일() {
        String email = "user@example.com";
        String nickname = "testUser";
        MultipartFile imgFile = new MockMultipartFile("test.jpg", new byte[0]);
        MultipartFile licenseFile = new MockMultipartFile("testLicense.pdf", new byte[0]);
        PostPharmacistSignupReq signupReq = new PostPharmacistSignupReq();
        signupReq.setEmail(email);
        signupReq.setNickname(nickname);

        doThrow(new BaseException(EmailErrorCode.EMAIL_NOT_VERIFIED)).when(redisService)
            .getData("emailVerified:" + signupReq.getEmail());

        BaseException exception = assertThrows(BaseException.class,
            () -> userService.signupPharmacist(imgFile, licenseFile, signupReq));
        assertNotNull(exception);
        assertEquals(EmailErrorCode.EMAIL_NOT_VERIFIED.getErrorCode(), exception.getErrorCode());
        verify(userRepository, never()).existsByNickname(any());
        verify(userRepository, never()).save(any());
        verify(pharmacistRepository, never()).save(any());
    }
}
