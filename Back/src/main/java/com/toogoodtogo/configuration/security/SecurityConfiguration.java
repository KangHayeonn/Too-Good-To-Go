package com.toogoodtogo.configuration.security;

import com.toogoodtogo.configuration.security.JwtAuthenticationFilter;
import com.toogoodtogo.configuration.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // 기본설정은 비 인증시 로그인 폼 화면으로 리다이렉트 되는데 미설정
                .csrf().disable() // 상태 저장하지 않으므로 csrf 보안 미설정

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 미생성

                .and()
                .authorizeRequests() // URL별 권한 관리 설정하는 옵션의 시작점
                        // 권한 관리 대상 지정 옵션
                        // 메인 화면, 로그인 및 가입 접근은 누구나 가능
                        // 더 손봐야 함!!
                .antMatchers("/api/signup", "/api/login", "/index").permitAll()
//                .antMatchers(HttpMethod.GET, "/oauth/kakao/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/exception/**").permitAll()
                .anyRequest().hasRole("USER") // 그 외 나머지 요청은 인증된 회원만 가능
//                .anyRequest().permitAll()
//                        .antMatchers("/admin/**").hasRole("ADMIN")
//                        .antMatchers("/user/**").hasRole("USER")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                    // jwt 인증 필터를 UsernamePasswordAuthenticationFilter.class 전에 넣는다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 관련 url 예외처리 손봐야 함!!
        web.ignoring().antMatchers("/api.adoc");
        // static 경로
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}