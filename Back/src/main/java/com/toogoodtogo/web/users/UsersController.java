package com.toogoodtogo.web.users;

import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.web.common.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {
    private final UserUseCase userUseCase;

    @GetMapping("/user/id/{userId}")
    public ApiResponse<UserDetailResponse> userInfo(@PathVariable Long userId, @RequestParam String lang) {
        return new ApiResponse(userUseCase.findUser(userId));
    }

//    @GetMapping("/users")
//    public ApiResponse<List<UserDetailResponse>> findAllUser() {
//        return new ApiResponse(userUseCase.findAllUser());
//    }

    @PatchMapping("/user/{userId}")
    public ApiResponse<Long> updateUser (@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return new ApiResponse(userUseCase.update(userId, userUpdateRequest));
    }

//    @DeleteMapping("/user/{userId}")
//    public ApiResponse deleteUser (@PathVariable Long userId) {
//        userUseCase.delete(userId);
//        return new ApiResponse(0);
//    }
}
