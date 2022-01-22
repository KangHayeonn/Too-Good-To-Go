package com.toogoodtogo.web;

import com.toogoodtogo.advice.exception.CAccessDeniedException;
import com.toogoodtogo.advice.exception.CAuthenticationEntryPointException;
import com.toogoodtogo.web.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    @GetMapping("/entryPoint")
    public ErrorResponse entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessDenied")
    public ErrorResponse accessDeniedException() {
        throw new CAccessDeniedException();
    }
}