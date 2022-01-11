package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping(value = "/login")
    public ApiResponse<TokenDto> login(@RequestBody @Valid UserLoginReq userLoginReq) {
        TokenDto tokenDto = signService.login(userLoginReq);
        return new ApiResponse<>(tokenDto);
    }

    @PostMapping("/signup")
    public ApiResponse<UserSignupRes> signup(@RequestBody @Valid UserSignupReq userSignupReq) {
        return new ApiResponse<>(signService.signup(userSignupReq));
    }

    @PostMapping("/reissue") //토큰 재발급 요청
    public ApiResponse<TokenDto> reissue(@RequestBody @Valid TokenReq tokenReq) {
        return new ApiResponse<>(signService.reissue(tokenReq));
    }

    @GetMapping("/logout")
    public ApiResponse<String> logout(@CurrentUser User user) {
        return new ApiResponse<>(signService.logout(user.getId()));
    }
}
