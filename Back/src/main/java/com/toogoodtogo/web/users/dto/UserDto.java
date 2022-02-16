package com.toogoodtogo.web.users.dto;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;

@Getter
public class UserDto {
    private final Long id;
    private final String email;
    private final String name;
    private final String phone;
    private final String role;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}