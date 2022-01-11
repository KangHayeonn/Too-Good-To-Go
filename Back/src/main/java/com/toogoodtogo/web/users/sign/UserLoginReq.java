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
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {
    @Email(message = "Email 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.")
    private String password;
}
