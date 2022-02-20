package com.toogoodtogo.web.users.sign;

import com.toogoodtogo.advice.ValidationSequence;
import com.toogoodtogo.application.security.SignService;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.users.sign.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TokenDto> login(@RequestBody @Validated(ValidationSequence.class) LoginUserRequest request) {
        TokenDto tokenDto = signService.login(request);
        return new ApiResponse<>(tokenDto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SignupUserResponse> signup(@RequestBody @Validated(ValidationSequence.class) SignupUserRequest request) {
        return new ApiResponse<>(signService.signup(request));
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TokenDto> reissue(@RequestBody @Valid TokenRequest request) {
        return new ApiResponse<>(signService.reissue(request));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody @Valid TokenRequest request) {
        signService.logout(request);
    }
}
