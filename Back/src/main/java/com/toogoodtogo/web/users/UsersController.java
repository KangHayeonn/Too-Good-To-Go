package com.toogoodtogo.web.users;

import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.*;
import com.toogoodtogo.web.users.dto.UpdateUserRequest;
import com.toogoodtogo.web.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UserUseCase userUseCase;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserDto> userInfo(@CurrentUser User user/*, @RequestParam String lang*/) {
        return new ApiResponse<>(userUseCase.findUser(user.getId()));
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserDto> updateUser (@CurrentUser User user, @RequestBody @Valid UpdateUserRequest request) {
        return new ApiResponse<>(userUseCase.update(user.getId(), request));
    }
}
