package com.toogoodtogo.application.user;

import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.users.UserRequestDto;
import com.toogoodtogo.web.users.UserResponseDto;

import java.util.List;

public interface UserUseCase {
    //Long save(UserRequestDto userDto);
    UserResponseDto findById(Long id);
    UserResponseDto findByEmail(String email);
    List<UserResponseDto> findAllUser();
    //Long update(Long id, UserRequestDto userRequestDto);
    void delete(Long id);
}