package com.toogoodtogo.application.user;

import com.toogoodtogo.web.users.UserResponse;

import java.util.List;

public interface UserUseCase {
    UserResponse findById(Long id);
    UserResponse findByEmail(String email);
    List<UserResponse> findAllUser();
    //Long update(Long id, UserRequestDto userRequestDto);
    void delete(Long id);
}