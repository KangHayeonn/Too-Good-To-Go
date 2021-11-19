package com.toogoodtogo.web.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public final class ApiResponseList<T> extends CommonResult {
    private final List<T> data;
}
