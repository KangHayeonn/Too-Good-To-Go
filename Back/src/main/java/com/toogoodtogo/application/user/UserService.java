package com.toogoodtogo.application.user;

import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.domain.user.exceptions.CUserNotFoundException;
import com.toogoodtogo.web.users.dto.UserDto;
import com.toogoodtogo.web.users.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDto findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return new UserDto(user);
    }

    @Transactional
    public UserDto update(Long userId, UpdateUserRequest updateUserRequest) {
        User modifiedUser = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        modifiedUser.update(passwordEncoder.encode(updateUserRequest.getPassword()), updateUserRequest.getPhone());
        return new UserDto(modifiedUser);
    }
}