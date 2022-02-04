package com.toogoodtogo.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        log.info("exception : " + exception);
        if(exception != null && exception.equals("ExpiredJwtException")) { // 만료된 토큰 일 때
            response.sendRedirect("/exception/expired");
        }
        else response.sendRedirect("/exception/entryPoint"); // 그 외
    }
}
