package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupReq {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .role(role)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .role(role)
                .build();
    }
}