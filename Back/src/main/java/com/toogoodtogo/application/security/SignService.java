package com.toogoodtogo.application.security;

import com.toogoodtogo.configuration.security.JwtTokenProvider;
import com.toogoodtogo.domain.security.RefreshToken;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.advice.exception.*;
import com.toogoodtogo.web.users.sign.TokenDto;
import com.toogoodtogo.web.users.sign.TokenRequest;
import com.toogoodtogo.web.users.sign.UserLoginRequest;
import com.toogoodtogo.web.users.sign.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository tokenRepository;

    public TokenDto login(UserLoginRequest userLoginRequest) {
        // 회원 정보 존재하는지 확인
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);
        // 회원 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();
        // AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(user.getId(), user.getRoles());
        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);
        return tokenDto;
    }

    public Long signup(UserSignupRequest userSignupDto) {
        if (userRepository.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new CEmailSignupFailedException();
        return userRepository.save(userSignupDto.toEntity(passwordEncoder)).getId();
    }

//    @Transactional
//    public Long socialSignup(UserSignupRequestDto userSignupRequestDto) {
//        if (userRepository
//                .findByEmail(userSignupRequestDto.getEmail())
//                .isPresent()
//        ) throw new CUserExistException();
//        return userRepository.save(userSignupRequestDto.toEntity()).getId();
//    }

    public TokenDto reissue(TokenRequest tokenRequest) {
        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validationToken(tokenRequest.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequest.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // user pk로 유저 검색 / repo 에 저장된 Refresh Token 이 없음
        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken = tokenRepository.findByKey(user.getId())
                .orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequest.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(user.getId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }
}
