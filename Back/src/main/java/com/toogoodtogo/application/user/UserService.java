package com.toogoodtogo.application.user;

import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.advice.exception.CUserNotFoundException;
import com.toogoodtogo.web.users.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public Long update(Long id, UserRequestDto userRequestDto) {
//        User modifiedUser = userRepository
//                .findById(id).orElseThrow(CUserNotFoundException::new);
//        modifiedUser.updateNickName(userRequestDto.getNickName());
//        return id;
//    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}