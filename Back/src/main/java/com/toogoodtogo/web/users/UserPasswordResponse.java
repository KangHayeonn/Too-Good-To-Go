package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.Getter;

@Getter
public class UserPasswordResponse {
    private final String password;

    public UserPasswordResponse(User user) {
        this.password = user.getPassword();
    }
}
