package com.example.mediready.global.config.auth.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.mediready.domain.user.User;
import com.example.mediready.global.config.exception.BaseException;
import io.jsonwebtoken.MalformedJwtException;
import java.lang.reflect.Constructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

@SpringBootTest
@DisplayName("JwtTokenProvider 테스트")
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PrincipalDetailService principalDetailService;

    private User createTestUser() throws Exception {
        Constructor<User> constructor = User.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @Test
    @DisplayName("AccessToken 생성")
    void generateAccessTokenTest() {
        Long userId = 1L;
        String token = jwtTokenProvider.generateAccessToken(userId);
        assertNotNull(token);
    }

    @Test
    @DisplayName("RefreshToken 생성")
    void generateRefreshTokenTest() {
        Long userId = 1L;
        String token = jwtTokenProvider.generateRefreshToken(userId);
        assertNotNull(token);
    }

    @Test
    @DisplayName("유효한 AccessToken")
    void validateAccessTokenTest() {
        String token = jwtTokenProvider.generateAccessToken(1L);
        assertTrue(jwtTokenProvider.validateAccessToken(token));
    }

    @Test
    @DisplayName("유효하지 않은 AccessToken")
    void validateInvalidAccessTokenTest() {
        assertThrows(BaseException.class,
            () -> jwtTokenProvider.validateAccessToken("invalidToken"));
    }

    @Test
    @DisplayName("유효한 RefreshToken")
    void validateRefreshTokenTest() {
        String token = jwtTokenProvider.generateRefreshToken(1L);
        assertDoesNotThrow(() -> jwtTokenProvider.validateRefreshToken(token));
    }

    @Test
    @DisplayName("유효하지 않은 RefreshToken")
    void validateInvalidRefreshTokenTest() {
        assertThrows(BaseException.class,
            () -> jwtTokenProvider.validateRefreshToken("invalidToken"));
    }

    @Test
    @DisplayName("AccessToken에서 userId 가져오기")
    void getUserIdByAccessTokenTest() {
        Long userId = 1L;
        String token = jwtTokenProvider.generateAccessToken(userId);
        String extractedUserId = jwtTokenProvider.getUserIdByAccessToken(token);
        assertEquals(userId.toString(), extractedUserId);
    }

    @Test
    @DisplayName("RefreshToken에서 userId 가져오기")
    void getUserIdByRefreshTokenTest() {
        Long userId = 1L;
        String token = jwtTokenProvider.generateRefreshToken(userId);
        String extractedUserId = jwtTokenProvider.getUserIdByRefreshToken(token);
        assertEquals(userId.toString(), extractedUserId);
    }

    @Test
    @DisplayName("유효하지 않은 AccessToken에서 userId 가져오기")
    void getUserIdByInvalidAccessTokenTest() {
        assertThrows(MalformedJwtException.class,
            () -> jwtTokenProvider.getUserIdByAccessToken("invalidToken"));
    }

    @Test
    @DisplayName("getAuthentication 메서드")
    void getAuthenticationTest() throws Exception {
        PrincipalDetails principalDetails = new PrincipalDetails(createTestUser());
        when(principalDetailService.loadUserByUsername(anyString())).thenReturn(principalDetails);

        String token = jwtTokenProvider.generateAccessToken(1L);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        assertNotNull(authentication);
    }

    @Test
    @DisplayName("유효하지 않은 AccessToken으로 getAuthentication 메서드 호출")
    void getAuthenticationWithInvalidTokenTest() {
        String invalidToken = "invalidToken";
        assertThrows(MalformedJwtException.class,
            () -> jwtTokenProvider.getAuthentication(invalidToken));
    }
}