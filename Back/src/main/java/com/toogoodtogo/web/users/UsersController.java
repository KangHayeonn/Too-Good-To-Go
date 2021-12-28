package com.toogoodtogo.web.users;

import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.web.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {
    private final UserUseCase userUseCase;

    @GetMapping("/user/id/{userId}")
    public ApiResponse<UserDetailResponse> userInfo(@PathVariable Long userId, @RequestParam String lang) {
        return new ApiResponse(userUseCase.findUser(userId));
    }

    @PostMapping("/user/email")
    public ApiResponse<UserPasswordResponse> findPasswordByEmail(@RequestBody UserEmailRequest userEmailRequest) {
        return new ApiResponse(userUseCase.findPasswordByEmail(userEmailRequest.getEmail()));
    }

    @GetMapping("/users")
    public ApiResponse<UserDetailResponse> findAllUser() {
        return new ApiResponse(userUseCase.findAllUser());
    }

    @PutMapping("/user/{userId}")
    public ApiResponse<Long> update (@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return new ApiResponse(userUseCase.update(userId, userUpdateRequest));
    }

    @DeleteMapping("/user/{userId}")
    public ApiResponse delete(@PathVariable Long userId) {
        userUseCase.delete(userId);
        return new ApiResponse(0);
    }



}
