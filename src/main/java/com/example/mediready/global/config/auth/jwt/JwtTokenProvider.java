package com.example.mediready.global.config.auth.jwt;

import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.AuthErrorCode;
import com.example.mediready.global.config.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.key.access}")
    private String jwtAccessSecretKey;

    @Value("${jwt.key.refresh}")
    private String jwtRefreshSecretKey;

    private final PrincipalDetailService principalDetailService;
    private final RedisService redisService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final long ACCESS_TOKEN_VALID_TIME = 1000 * 60 * 60 * 6L;  // 유효기간 6시간
    private static final long REFRESH_TOKEN_VALID_TIME = 2 * 30 * 24 * 60 * 60 * 1000L;  // 유효기간 6시간

    @PostConstruct
    protected void init() {
        jwtAccessSecretKey = Base64.getEncoder()
            .encodeToString(jwtAccessSecretKey.getBytes(StandardCharsets.UTF_8));
        jwtRefreshSecretKey = Base64.getEncoder()
            .encodeToString(jwtRefreshSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("access-userId", userId);

        Date now = new Date();
        Date accessTokenExpirationTime = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpirationTime)
            .signWith(SignatureAlgorithm.HS256, jwtAccessSecretKey)
            .compact();
    }

    public String generateRefreshToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("refresh-userId", userId);

        Date now = new Date();
        Date refreshTokenExpirationTime = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(refreshTokenExpirationTime)
            .signWith(SignatureAlgorithm.HS256, jwtRefreshSecretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        try {
            PrincipalDetails principalDetails = principalDetailService.loadUserByUsername(
                getUserIdByAccessToken(token));
            return new UsernamePasswordAuthenticationToken(principalDetails,
                "", principalDetails.getAuthorities());
        } catch (UsernameNotFoundException exception) {
            throw new BaseException(AuthErrorCode.INVALID_JWT);
        }
    }

    public String getUserIdByAccessToken(String token) {
        System.out.println("getUserIdByAccessToken : " + token);
        return Jwts.parser().setSigningKey(jwtAccessSecretKey).parseClaimsJws(token).
            getBody().get("access-userId").toString();
    }

    public String getUserIdByRefreshToken(String token) {
        return Jwts.parser().setSigningKey(jwtRefreshSecretKey).parseClaimsJws(token).
            getBody().get("refresh-userId").toString();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public boolean validateAccessToken(String token) {
        if (redisService.isBlacklisted(token)) {
            throw new BaseException(AuthErrorCode.LOGOUT_USER);
        }
        try {
            Jwts.parser().setSigningKey(jwtAccessSecretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new BaseException(AuthErrorCode.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            throw new BaseException(AuthErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(AuthErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new BaseException(AuthErrorCode.EMPTY_JWT);
        }
    }

    public void validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecretKey).parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new BaseException(AuthErrorCode.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            throw new BaseException(AuthErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(AuthErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new BaseException(AuthErrorCode.EMPTY_JWT);
        }
    }

    public long getExpirationTime(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtAccessSecretKey)
            .parseClaimsJws(token)
            .getBody();
        return claims.getExpiration().getTime();
    }
}
