package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.dto.UserDto;
import com.toogoodtogo.web.users.dto.UpdateUserRequest;

public interface UserUseCase {
    UserDto findUser(Long id);
    UserDto update(Long id, UpdateUserRequest request);
}