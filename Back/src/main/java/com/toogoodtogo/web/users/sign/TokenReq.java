package com.toogoodtogo.web.users.sign;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenReq {
    String accessToken;
    String refreshToken;

    @Builder
    public TokenReq(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}