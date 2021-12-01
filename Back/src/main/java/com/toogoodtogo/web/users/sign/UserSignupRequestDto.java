package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String roles;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phoneNumber(phoneNumber)
                .roles(Collections.singletonList(roles))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .roles(Collections.singletonList(roles))
                .build();
    }
}