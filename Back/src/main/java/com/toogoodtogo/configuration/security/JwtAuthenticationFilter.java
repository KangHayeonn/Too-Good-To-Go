package com.toogoodtogo.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Slf4j
// Jwt이 유효한 토큰인지 인증하기 위한 filter
// 이 필터를 Security 설정 시 UsernamePasswordAuthentication 앞에 설정해서 로그인폼으로 반환하기 전에 인증 여부를 Json으로 반환
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider, StringRedisTemplate redisTemplate) {
        this.jwtProvider = jwtProvider;
        this.redisTemplate = redisTemplate;
    }

    // request 로 들어오는 Jwt 의 유효성을 검증 - JwtProvider.validationToken() 을 필터로서 FilterChain 에 추가
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        // request 에서 accessToken 을 취한다.
        String accessToken = jwtProvider.resolveToken((HttpServletRequest) request);
        // 검증
        log.info("[Verifying token]");
        log.info(((HttpServletRequest) request).getRequestURL().toString());
        if (accessToken != null && jwtProvider.validationToken(request, accessToken)) {
            // redis 에서 accessToken logout 검증
            log.info("value : " + redisTemplate.opsForValue().get(accessToken));
            String isLogout = redisTemplate.opsForValue().get(accessToken);
            if (ObjectUtils.isEmpty(isLogout)) {
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}