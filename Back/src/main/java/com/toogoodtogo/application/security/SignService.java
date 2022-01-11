package com.toogoodtogo.application.security;

import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.configuration.security.JwtTokenProvider;
import com.toogoodtogo.domain.security.RefreshToken;
import com.toogoodtogo.domain.security.RefreshTokenRepository;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.advice.exception.*;
import com.toogoodtogo.web.users.sign.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository tokenRepository;

    @Transactional
    public TokenDto login(UserLoginRequest userLoginRequest) {
        // 회원 정보 존재하는지 확인
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);
        // 로그인 된 상태인지 확인
        List<RefreshToken> all = tokenRepository.findAll();
        if (tokenRepository.findByUserId(user.getId()).isPresent()) throw new CAlreadyLoginException();
        // 회원 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new CPasswordLoginFailedException();
        // AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(user.getId(), user.getRole());
        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public UserSignupResponse signup(UserSignupRequest userSignupDto) {
        if (userRepository.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new CEmailSignupFailedException();
        return new UserSignupResponse(userRepository.save(userSignupDto.toEntity(passwordEncoder)).getId());
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
        if (!jwtTokenProvider.validationToken(tokenRequest.getRefreshToken())) {
            throw new CRefreshTokenException();
        }
        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequest.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        // user pk로 유저 검색 / repo 에 저장된 Refresh Token 이 없음
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken = tokenRepository.findByUserId(user.getId())
                .orElseThrow(CRefreshTokenException::new);
        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequest.getRefreshToken()))
            throw new CRefreshTokenException();
        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtTokenProvider.createTokenDto(user.getId(), user.getRole());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenRepository.save(updateRefreshToken); //더티체킹으로 token 값만 update

        return newCreatedToken;
    }

    @Transactional
    public String logout(Long userId) {
        // 이미 로그아웃 되어서 해당 유저의 아이디를 갖는 refresh token 이 없으면 에러
        RefreshToken refreshToken = tokenRepository.findByUserId(userId).orElseThrow(CNoLoginException::new);
        tokenRepository.delete(refreshToken);
        return "success";
    }
}
