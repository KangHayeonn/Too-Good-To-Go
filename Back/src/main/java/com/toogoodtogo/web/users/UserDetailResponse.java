package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDetailResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String phone;
    private final String role;

    public UserDetailResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}