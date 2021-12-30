package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping(value = "/login")
    public ApiResponse<TokenDto> login(@RequestBody UserLoginRequest userLoginRequest) {
        TokenDto tokenDto = signService.login(userLoginRequest);
        return new ApiResponse(tokenDto);
    }

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody UserSignupRequest userSignupRequest) {
        Long signupId = signService.signup(userSignupRequest);
        return new ApiResponse(signupId);
    }

    @PostMapping("/reissue") //토큰 재발급 요청
    public ApiResponse<TokenDto> reissue(@RequestBody TokenRequest tokenRequest) {
        return new ApiResponse(signService.reissue(tokenRequest));
    }

}