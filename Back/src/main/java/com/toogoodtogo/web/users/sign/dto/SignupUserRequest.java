package com.toogoodtogo.web.users.sign.dto;

import com.toogoodtogo.advice.ValidationGroups;
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
public class SignupUserRequest {
    @NotBlank(message = "이메일은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Email(regexp = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}",
            message = "Email 형식이 아닙니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String email;
    @NotBlank(message = "비밀번호는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String password;
    @NotBlank(message = "이름은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String name;
    @NotBlank(message = "전화번호는 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})", message = "전화번호 양식이 틀렸습니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String phone;
    @NotBlank(message = "역할은 필수 값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
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
}