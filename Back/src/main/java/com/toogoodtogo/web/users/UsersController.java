package com.toogoodtogo.web.users;

import com.toogoodtogo.application.response.ResponseService;
import com.toogoodtogo.application.user.UserUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.common.ApiResponseList;
import com.toogoodtogo.web.common.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {
    private final UserUseCase userUseCase;
    private final ResponseService responseService;

    @GetMapping("/common/user/id/{userId}")
    public ApiResponse<UserResponse> findUserById (@PathVariable Long userId, @RequestParam String lang) {
        return responseService.getSingleResult(userUseCase.findById(userId));
    }

    @GetMapping("/common/user/email/{email}")
    public ApiResponse<UserResponse> findUserByEmail (@PathVariable String email, @RequestParam String lang) {
        return responseService.getSingleResult(userUseCase.findByEmail(email));
    }

    @GetMapping("/admin/users")
    public ApiResponseList<UserResponse> findAllUser() {
        return responseService.getListResult(userUseCase.findAllUser());
    }

//    @PutMapping("/user")
//    public ApiResponse<Long> update (@RequestParam Long userId, @RequestParam String nickName) {
//        UserRequestDto userRequestDto = UserRequestDto.builder().build();
//        return responseService.getSingleResult(userService.update(userId, userRequestDto));
//    }

    @DeleteMapping("/admin/user/{userId}")
    public CommonResult delete(@PathVariable Long userId) {
        userUseCase.delete(userId);
        return responseService.getSuccessResult();
    }

}