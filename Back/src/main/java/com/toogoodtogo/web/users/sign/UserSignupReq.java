package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupReq {
    @Email(message = "Email 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.")
    private String password;
    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp = "01([0|1|6|7|8|9]?)-?([0-9]{4})-?([0-9]{4})", message = "전화번호 양식이 틀렸습니다.")
    private String phone;
    @NotBlank(message = "역할은 필수 값입니다.")
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