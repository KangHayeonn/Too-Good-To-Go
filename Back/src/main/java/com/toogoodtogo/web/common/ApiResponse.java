package com.toogoodtogo.web.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ApiResponse<T> {
    private final T data;
}
