package com.toogoodtogo.application.security;

import com.toogoodtogo.configuration.security.JwtTokenProvider;
import com.toogoodtogo.domain.security.exceptions.*;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.domain.user.exceptions.CUserNotFoundException;
import com.toogoodtogo.web.users.sign.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public TokenDto login(LoginUserRequest loginUserRequest) {
        // 회원 정보 존재하는지 확인
        User user = userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow(CEmailLoginFailedException::new);
        // 회원 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPassword())) throw new CPasswordLoginFailedException();
        // AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(user.getId(), user.getRole());
        // Redis 에 RefreshToken 저장
        redisTemplate.opsForValue()
                .set("RT:" + user.getId(),
                        tokenDto.getRefreshToken(), jwtTokenProvider.getRefreshTokenValidMillisecond(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    @Transactional
    public SignupUserResponse signup(SignupUserRequest userSignupDto) {
        if (userRepository.findByEmail(userSignupDto.getEmail()).isPresent()) throw new CEmailSignupFailedException();
        return new SignupUserResponse(userRepository.save(userSignupDto.toEntity(passwordEncoder)).getId());
    }

//    @Transactional
//    public Long socialSignup(UserSignupRequestDto userSignupRequestDto) {
//        if (userRepository
//                .findByEmail(userSignupRequestDto.getEmail())
//                .isPresent()
//        ) throw new CUserExistException();
//        return userRepository.save(userSignupRequestDto.toEntity()).getId();
//    }

    @Transactional
    public TokenDto reissue(TokenRequest tokenRequest) {
        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validationToken(tokenRequest.getRefreshToken()))
            throw new CRefreshTokenException();
        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequest.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        // user pk로 유저 검색하고 해당 id 로 redis 에서 Refresh Token 검색
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(CUserNotFoundException::new);
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + user.getId());
        // 리프레시 토큰 불일치 에러
        if (!refreshToken.equals(tokenRequest.getRefreshToken()))
            throw new CRefreshTokenException();
        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 redis 업데이트
        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(user.getId(), user.getRole());

        redisTemplate.opsForValue()
                .set("RT:" + user.getId(),
                        newCreatedToken.getRefreshToken(), jwtTokenProvider.getRefreshTokenValidMillisecond(), TimeUnit.MILLISECONDS);
        return newCreatedToken;
    }

    @Transactional
    public void logout(TokenRequest tokenRequest) {
        String accessToken = tokenRequest.getAccessToken();

        // accessToken 검증
        if (!jwtTokenProvider.validationToken(accessToken)) throw new CAccessDeniedException();

        // accessToken 에서 user email 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(CUserNotFoundException::new);

        // Redis 에서 해당 User id 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + user.getId()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + user.getId());
        }

        // 해당 Access Token 남은 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(tokenRequest.getAccessToken());
        redisTemplate.opsForValue()
                .set(tokenRequest.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }
}
