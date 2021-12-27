package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.UserDetailResponse;
import com.toogoodtogo.web.users.UserPasswordResponse;

import java.util.List;

public interface UserUseCase {
    UserDetailResponse findUser(Long id);
    UserPasswordResponse findPasswordByEmail(String email);
    List<UserDetailResponse> findAllUser();
    //Long update(Long id, UserRequestDto userRequestDto);
    void delete(Long id);
}