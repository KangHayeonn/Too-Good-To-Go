package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.application.response.ResponseService;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SignController {
    private final ResponseService responseService;
    private final SignService signService;

    @PostMapping(value = "/login")
    public ApiResponse<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        TokenDto tokenDto = signService.login(userLoginRequestDto);
        return responseService.getSingleResult(tokenDto);
    }

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        Long signupId = signService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }

    @PostMapping("/reissue") //토큰 재발급 요청
    public ApiResponse<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }

}