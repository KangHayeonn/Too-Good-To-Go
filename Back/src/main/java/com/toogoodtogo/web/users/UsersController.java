package com.toogoodtogo.web.users;

import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UserUseCase userUseCase;

    @GetMapping("/me")
    public ApiResponse<UserDetailResponse> userInfo(@CurrentUser User user, @RequestParam String lang) {
        return new ApiResponse(userUseCase.findUser(user.getId()));
    }

    @PatchMapping("/user")
    public ApiResponse<UserDetailResponse> updateUser (@CurrentUser User user, @RequestBody UserUpdateRequest userUpdateRequest) {
        return new ApiResponse(userUseCase.update(user.getId(), userUpdateRequest));
    }
}
