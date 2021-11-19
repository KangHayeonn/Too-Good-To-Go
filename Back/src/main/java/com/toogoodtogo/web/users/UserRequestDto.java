package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }
}