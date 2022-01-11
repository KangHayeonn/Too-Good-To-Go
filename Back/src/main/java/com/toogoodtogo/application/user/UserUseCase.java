package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.UserDto;
import com.toogoodtogo.web.users.UserUpdateReq;

public interface UserUseCase {
    UserDto findUser(Long id);
//    List<UserDetailResponse> findAllUser();
    UserDto update(Long id, UserUpdateReq UserUpdateReq);
//    void delete(Long id);
}