package com.toogoodtogo.web;

import com.toogoodtogo.domain.security.exceptions.CAccessDeniedException;
import com.toogoodtogo.domain.security.exceptions.CAuthenticationEntryPointException;
import com.toogoodtogo.domain.security.exceptions.CExpiredAccessTokenException;
import com.toogoodtogo.web.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    @GetMapping("/expired")
    public ErrorResponse expiredException() {
        throw new CExpiredAccessTokenException();
    }

    @GetMapping("/entryPoint")
    public ErrorResponse entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessDenied")
    public ErrorResponse accessDeniedException() {
        throw new CAccessDeniedException();
    }
}