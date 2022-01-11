package com.toogoodtogo.web.users.sign;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class TokenReq {
    @NotBlank(message = "access token 은 필수값입니다.")
    String accessToken;
    @NotBlank(message = "refresh token 은 필수값입니다.")
    String refreshToken;

    @Builder
    public TokenReq(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}