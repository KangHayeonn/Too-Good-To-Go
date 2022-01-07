package com.toogoodtogo.application.user;

import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.domain.user.UserRepository;
import com.toogoodtogo.advice.exception.CUserNotFoundException;
import com.toogoodtogo.web.users.UserDetailResponse;
import com.toogoodtogo.web.users.UserUpdateRequest;
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
    public UserDetailResponse findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserDetailResponse(user);
    }

//    @Transactional(readOnly = true)
//    public List<UserDetailResponse> findAllUser() {
//        return userRepository.findAll()
//                .stream()
//                .map(UserDetailResponse::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public UserDetailResponse update(Long id, UserUpdateRequest userUpdateRequest) {
        User modifiedUser = userRepository
                .findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.update(passwordEncoder.encode(userUpdateRequest.getPassword()), userUpdateRequest.getPhone());
        return new UserDetailResponse(modifiedUser);
    }

//    @Transactional
//    public void delete(Long id) {
//        userRepository.deleteById(id);
//    }
}