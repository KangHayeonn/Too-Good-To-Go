package com.toogoodtogo.web.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean success; //응답 성공여부 : true/false

    private int code; //응답 코드 번호 : >= 0 정상, < 0 비정상

    private String msg; //응답 메시지
}
