package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDetailResponse {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;
    private final String role;

    public UserDetailResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
    }
}