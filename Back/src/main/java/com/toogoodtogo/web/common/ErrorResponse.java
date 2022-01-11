package com.toogoodtogo.web.common;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public final class ErrorResponse {
    private final String reason;
    private final String message;
    private List<Error> errors = new ArrayList<>();

    @Getter
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor
    public static class Error {
        private final String field;
        private final String value;
        private final String message;
    }
}
