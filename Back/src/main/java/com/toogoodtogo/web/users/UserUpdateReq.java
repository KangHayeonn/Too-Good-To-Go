package com.toogoodtogo.web.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateReq {
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Length(min = 8, message = "비밀번호는 최소 {min}자 이상입니다.")
    private String password;
    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp = "01([0|1|6|7|8|9]?)-?([0-9]{4})-?([0-9]{4})", message = "전화번호 양식이 틀렸습니다.")
    private String phone;
}
