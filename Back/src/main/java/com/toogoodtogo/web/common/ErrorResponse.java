package com.toogoodtogo.web.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class ErrorResponse {
    private final String reason;
    private final String message;
}
