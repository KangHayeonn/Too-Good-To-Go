package com.toogoodtogo.web.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class ApiResponse<T> extends CommonResult{
    private final T data;
}
