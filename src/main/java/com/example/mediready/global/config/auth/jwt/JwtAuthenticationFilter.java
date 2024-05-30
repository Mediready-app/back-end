package com.example.mediready.global.config.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if ("/auth/email".equals(path) || "/auth/email/verify".equals(path)
            || "/users/signup-user".equals(path) || "/users/signup-pharmacist".equals(path)
            || "/medicines/search".equals(path)) {
            chain.doFilter(request, response);
            return;
        }
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (!Objects.equals(token, "")) {
            Authentication authentication =
                jwtTokenProvider.validateAccessToken(token) ?
                    jwtTokenProvider.getAuthentication(token) : null;

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}