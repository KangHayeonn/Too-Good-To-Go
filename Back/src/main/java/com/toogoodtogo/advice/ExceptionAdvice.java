package com.toogoodtogo.advice;

import com.toogoodtogo.advice.exception.*;
import com.toogoodtogo.web.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final MessageSource messageSource;

    /***
     * -9999
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse defaultException(HttpServletRequest request, Exception e) {
        log.info(String.valueOf(e));
        return new ErrorResponse("Unknown error", getMessage("unKnown.msg"));
    }

    /***
     * -1000
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return new ErrorResponse("User Not Found", getMessage("userNotFound.msg"));
    }

    /***
     * -1001
     * 유저 이메일 로그인 시 이메일이 틀렸을때 발생시키는 예외
     */
    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse emailLoginFailedException(HttpServletRequest request, CEmailLoginFailedException e) {
        return new ErrorResponse("Login Email Wrong", getMessage("emailLoginFailed.msg"));
    }

    /***
     * -1001
     * 유저 이메일 로그인 시 비밀번호가 틀렸을때 발생시키는 예외
     */
    @ExceptionHandler(CPasswordLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse passwordLoginFailedException(HttpServletRequest request, CPasswordLoginFailedException e) {
        return new ErrorResponse("Login Password Wrong", getMessage("passwordLoginFailed.msg"));
    }

    /***
     * -1001
     * 로그인 시 이미 로그인 된 유저일때 발생시키는 예외
     */
    @ExceptionHandler(CAlreadyLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse alreadyLoginException(HttpServletRequest request, CAlreadyLoginException e) {
        return new ErrorResponse("Already Login", getMessage("alreadyLoginFailed.msg"));
    }

    /***
     * -1001
     * 로그아웃 시 로그인 되지 않은 유저일때 발생시키는 예외
     */
    @ExceptionHandler(CNoLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse noLoginException(HttpServletRequest request, CNoLoginException e) {
        return new ErrorResponse("No Login", getMessage("noLoginFailed.msg"));
    }

    /***
     * -1002
     * 회원 가입 시 이미 로그인 된 이메일인 경우 발생 시키는 예외
     */
    @ExceptionHandler(CEmailSignupFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse emailSignupFailedException(HttpServletRequest request, CEmailSignupFailedException e) {
        return new ErrorResponse("Email Sign up Failed", getMessage("emailSignupFailed.msg"));
    }

    /**
     * -1003
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse authenticationEntrypointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return new ErrorResponse("Authentication Entrypoint", getMessage("authenticationEntrypoint.msg"));
    }

    /**
     * -1004
     * 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse accessDeniedException(HttpServletRequest request, CAccessDeniedException e) {
        return new ErrorResponse("Access Denied", getMessage("accessDenied.msg"));
    }

    /**
     * -1005
     * refresh token 에러시 발생 시키는 에러
     */
    @ExceptionHandler(CRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse refreshTokenException(HttpServletRequest request, CRefreshTokenException e) {
        return new ErrorResponse("Refresh Token InValid", getMessage("refreshTokenInValid.msg"));
    }

    /**
     * -1006
     * 액세스 토큰 만료시 발생하는 에러
     */
    @ExceptionHandler(CExpiredAccessTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse expiredAccessTokenException(HttpServletRequest request, CExpiredAccessTokenException e) {
        return new ErrorResponse("Expired Access Token", getMessage("expiredAccessToken.msg"));
    }

//    /***
//     * -1007
//     * Social 인증 과정에서 문제 발생하는 에러
//     */
//    @ExceptionHandler(CCommunicationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
//        return responseService.getFailResult(
//                Integer.parseInt(getMessage("communicationException.code")), getMessage("communicationException.msg")
//        );
//    }

    /***
     * -1008
     * 기 가입자 에러
     */
    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse existUserException(HttpServletRequest request, CUserExistException e) {
        return new ErrorResponse("User Exist Exception", getMessage("userExistException.msg"));
    }

//    /***
//     * -1009
//     * 소셜 로그인 시 필수 동의항목 미동의시 에러
//     */
//    @ExceptionHandler(CSocialAgreementException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected CommonResult socialAgreementException(HttpServletRequest request, CSocialAgreementException e) {
//        return responseService.getFailResult(
//                Integer.parseInt(getMessage("agreementException.code")), getMessage("agreementException.msg")
//        );
//    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
