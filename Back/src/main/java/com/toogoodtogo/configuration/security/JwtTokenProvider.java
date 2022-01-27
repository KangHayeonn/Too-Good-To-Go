package com.toogoodtogo.configuration.security;

import com.toogoodtogo.domain.security.exceptions.CAuthenticationEntryPointException;
import com.toogoodtogo.web.users.sign.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
// JWT토큰 생성 및 유효성을 검증하는 컴포넌트
// Jwts는 여러가지 암호화 알고리즘을 제공하고 알고리즘과 비밀키 가지고 토큰 생성
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private String ROLES = "roles";
    private final Long accessTokenValidMillisecond = 60 * 60 * 1000L; // 1 hour
    private final Long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L; // 14 day
    private final UserDetailsService userDetailsService;

    @PostConstruct
    // Jwt 생성 시 서명으로 사용할 secret key를 BASE64로 인코딩
    protected void init() {
        SECRET_KEY = Base64UrlCodec.BASE64URL.encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Jwt 생성. 토큰에 저장할 유저 pk와 리스트를 매개변수
    // pk는 setsubject로 저장하고 roles들은 key-value 형태로 넣어준다. ("roles : {"권한1", "권한2", ...})
    // access, refresh 토큰을 각각 만들어서 tokenDto 로 만든 후 반환
    public TokenDto createTokenDto(Long userPk, String role) {
        // Claims 에 user 구분을 위한 User pk 및 authorities 목록 삽입
        // Claims에 회원 구분할 수 있는 값 세팅하고 토큰 들어오면 해당 값으로 회원 구분해서 리소스 제공
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES, role);

        // 생성날짜, 만료날짜를 위한 Date
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(accessTokenValidMillisecond)
                .build();
    }

    // Jwt 로 인증정보를 조회
    // Jwt에서 권한정보 확인하기 위해 시크릿키로 검증 후 권한 목록 가져온다 (키에 문제 있다면 SignatureException 발생)
    // claims에서 토큰 빼온 후 권한 있는지 확인하고, 있다면 pk값을 가지고 loadUserByUsername()으로 유저 엔티티 받는다
    public Authentication getAuthentication(String token) {
        // Jwt 에서 claims 추출
        Claims claims = parseClaims(token);

        // 권한 정보가 없음
        if (claims.get(ROLES) == null) {
            throw new CAuthenticationEntryPointException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰 복호화해서 가져오기
    // 만료된 토큰이여도 refresh token 검증 후 재발급 받을 수 있도록 claims 반환
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // HTTP Request 의 Header 에서 Token Parsing -> "Authorization: jwt"
    // HTTP Request header에서 세팅된 토큰값 가져와서 유효성 검사
    // 제한된 리소스에 접근할 때 Http header에 토큰 세팅하여 호출하면 유효성 검사 통해 사용자 인증 받을 수 있다.
    public String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
            return authHeader.substring(7);
        return null;
    }

    // jwt 의 유효성 및 만료일자 확인
    // Jwts에서 제공하는 예외처리 이용
    public boolean validationToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다.");
        }
        return false;
    }
}