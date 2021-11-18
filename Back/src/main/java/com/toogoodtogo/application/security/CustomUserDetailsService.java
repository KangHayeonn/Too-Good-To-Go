package com.toogoodtogo.application.security;

import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.advice.exception.CUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
// 토큰에 세팅된 유저 정보로 회원정보 조회하는 인터페이스(UserDetailService) 재정의
// DB에서 유저 정보 직접 가져오는 인터페이스 구현
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
    }
}
