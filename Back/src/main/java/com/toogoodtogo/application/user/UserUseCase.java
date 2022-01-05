package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.UserDetailResponse;
import com.toogoodtogo.web.users.UserUpdateRequest;

import java.util.List;

public interface UserUseCase {
    UserDetailResponse findUser(Long id);
//    List<UserDetailResponse> findAllUser();
    UserDetailResponse update(Long id, UserUpdateRequest UserUpdateRequest);
//    void delete(Long id);
}