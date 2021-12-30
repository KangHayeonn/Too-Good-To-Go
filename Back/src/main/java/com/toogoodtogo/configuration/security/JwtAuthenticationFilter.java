package com.toogoodtogo.configuration.security;

import com.toogoodtogo.configuration.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
// Jwt이 유효한 토큰인지 인증하기 위한 filter
// 이 필터를 Security 설정 시 UsernamePasswordAuthentication 앞에 설정해서 로그인폼으로 반환하기 전에 인증 여부를 Json으로 반환
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // request 로 들어오는 Jwt 의 유효성을 검증 - JwtProvider.validationToken() 을 필터로서 FilterChain 에 추가
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        // request 에서 token 을 취한다.
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        // 검증
        log.info("[Verifying token]");
        log.info(((HttpServletRequest) request).getRequestURL().toString());
        if (token != null && jwtProvider.validationToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        HttpServletRequest request2 = (HttpServletRequest) request;
        HttpServletResponse response2 = (HttpServletResponse) response;
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        ((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        filterChain.doFilter(request, response);

    }
}