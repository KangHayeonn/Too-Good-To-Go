package com.toogoodtogo.web.common;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class ApiResponse2<T> {
    private final boolean success;
    private final int code; // 응답 코드 번호 : >= 0 정상, < 0 비정상
    private final String msg; // 응답 메시지
    private final T data;

    public static <E> ApiResponse2<E> success(E data) {
        return new ApiResponse2<>(
                true,
                0,
                "성공하였습니다",
                data
        );
    }
}
