package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
    }
}