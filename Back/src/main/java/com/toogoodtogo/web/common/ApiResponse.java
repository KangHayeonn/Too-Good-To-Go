package com.toogoodtogo.web.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class ApiResponse<T>{
    // private final boolean success;  //응답 성공여부 : true/false
    // private final int code; // 응답 코드 번호 : >= 0 정상, < 0 비정상
    //private final String msg; // 응답 메시지
    private final T data;
}
