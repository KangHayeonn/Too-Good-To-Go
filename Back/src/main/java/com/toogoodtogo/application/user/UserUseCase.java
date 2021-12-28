package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.UserDetailResponse;
import com.toogoodtogo.web.users.UserPasswordResponse;
import com.toogoodtogo.web.users.UserUpdateRequest;

import java.util.List;

public interface UserUseCase {
    UserDetailResponse findUser(Long id);
    UserPasswordResponse findPasswordByEmail(String email);
    List<UserDetailResponse> findAllUser();
    UserDetailResponse update(Long id, UserUpdateRequest UserUpdateRequest);
    void delete(Long id);
}