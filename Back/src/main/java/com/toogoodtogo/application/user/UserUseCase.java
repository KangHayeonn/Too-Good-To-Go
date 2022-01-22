package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.dto.UserDto;
import com.toogoodtogo.web.users.dto.UpdateUserRequest;

public interface UserUseCase {
    UserDto findUser(Long id);
//    List<UserDetailResponse> findAllUser();
    UserDto update(Long id, UpdateUserRequest UpdateUserRequest);
//    void delete(Long id);
}