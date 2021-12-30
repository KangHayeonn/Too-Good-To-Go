package com.toogoodtogo.web.users;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEmailRequest {
    private String email;
}
