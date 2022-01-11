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
public class UserLoginReq {
    private String email;
    private String password;
}
