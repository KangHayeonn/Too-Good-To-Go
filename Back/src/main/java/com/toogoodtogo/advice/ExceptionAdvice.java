package com.toogoodtogo.advice;

import com.toogoodtogo.advice.exception.*;
import com.toogoodtogo.domain.order.exceptions.OrderCancelException;
import com.toogoodtogo.domain.order.exceptions.OrderNotFoundException;
import com.toogoodtogo.domain.security.exceptions.*;
import com.toogoodtogo.domain.shop.exceptions.CShopNotFoundException;
import com.toogoodtogo.domain.shop.product.exceptions.CProductNotFoundException;
import com.toogoodtogo.domain.user.exceptions.CUserExistException;
import com.toogoodtogo.domain.user.exceptions.CUserNotFoundException;
import com.toogoodtogo.web.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
        e.printStackTrace();
        return new ErrorResponse("Unknown error", getMessage("unKnown.msg"));
    }

    /***
     * -0000
     * Request Body validation Exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse requestBodyNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            log.error("error field : \"{}\", value : \"{}\", message : \"{}\"", error.getField(), error.getRejectedValue(), error.getDefaultMessage());
        }
        return new ErrorResponse("Request body's field is not valid", getMessage("requestBodyNotValid.msg"));
    }

    /***
     * -1111
     * Request Body wrong Exception
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse requestBodyWrongException(HttpServletRequest request, HttpMessageNotReadableException e) {
        return new ErrorResponse("Request Body is wrong", getMessage("requestBodyWrong.msg"));
    }

    /***
     * -2222
     * Path Variable, Query Parameter validation Exception
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse argumentNotValidException(HttpServletRequest request, ConstraintViolationException e) {
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            log.error("error field : \"{}\", value : \"{}\", message : \"{}\"",
                    error.getPropertyPath().toString().split(".")[1], error.getInvalidValue(), error.getMessage());
        }
        return new ErrorResponse("Path Variable or Query Parameter Not Valid", getMessage("pathQueryNotValid.msg"));
    }

    /***
     * -3333
     * Path Variable missing Exception
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse pathVariableMissingException(HttpServletRequest request, MissingPathVariableException e) {
        log.error("error field : \"{}\", message : \"{}\"", e.getVariableName(), e.getMessage());
        return new ErrorResponse("Path Variable is missing", getMessage("pathVariableMissing.msg"));
    }

    /***
     * -4444
     * Path Variable Type Mismatch Exception
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse pathVariableTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        log.error("error field : \"{}\", value : \"{}\", message : \"{}\"", e.getName(), e.getValue(), e.getMessage());
        return new ErrorResponse("Path Variable Type is mismatch", getMessage("pathVariableTypeMismatch.msg"));
    }

    /***
     * -5555
     * Query Parameter missing Exception
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse pathVariableMissingException(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("error field : \"{}\", message : \"{}\"", e.getParameterName(), e.getMessage());
        return new ErrorResponse("Query Parameter is missing", getMessage("queryParameterMissing.msg"));
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
     * 가게를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CShopNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse shopNotFoundException(HttpServletRequest request, CShopNotFoundException e) {
        return new ErrorResponse("Shop Not Found", getMessage("shopNotFound.msg"));
    }

    /***
     * -1002
     * 상품를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse productNotFoundException(HttpServletRequest request, CProductNotFoundException e) {
        return new ErrorResponse("Product Not Found", getMessage("productNotFound.msg"));
    }

    /***
     * -1003
     * 유저 이메일 로그인 시 이메일이 틀렸을때 발생시키는 예외
     */
    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse emailLoginFailedException(HttpServletRequest request, CEmailLoginFailedException e) {
        return new ErrorResponse("Login Email Wrong", getMessage("emailLoginFailed.msg"));
    }

    /***
     * -1004
     * 유저 이메일 로그인 시 비밀번호가 틀렸을때 발생시키는 예외
     */
    @ExceptionHandler(CPasswordLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse passwordLoginFailedException(HttpServletRequest request, CPasswordLoginFailedException e) {
        return new ErrorResponse("Login Password Wrong", getMessage("passwordLoginFailed.msg"));
    }

    /***
     * -1005
     * 로그인 시 이미 로그인 된 유저일때 발생시키는 예외
     */
    @ExceptionHandler(CAlreadyLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse alreadyLoginException(HttpServletRequest request, CAlreadyLoginException e) {
        return new ErrorResponse("Already Login", getMessage("alreadyLoginFailed.msg"));
    }

    /***
     * -1006
     * 로그아웃 시 로그인 되지 않은 유저일때 발생시키는 예외
     */
    @ExceptionHandler(CNoLoginException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse noLoginException(HttpServletRequest request, CNoLoginException e) {
        return new ErrorResponse("No Login", getMessage("noLoginFailed.msg"));
    }

    /***
     * -1007
     * 회원 가입 시 이미 로그인 된 이메일인 경우 발생 시키는 예외
     */
    @ExceptionHandler(CEmailSignupFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse emailSignupFailedException(HttpServletRequest request, CEmailSignupFailedException e) {
        return new ErrorResponse("Email Sign up Failed", getMessage("emailSignupFailed.msg"));
    }

    /**
     * -1008
     * 틀린 URL 로 접근했을 경우 발생 시키는 예외
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse wrongURLException(HttpServletRequest request, NoHandlerFoundException e) {
        log.error("error field : \"{}\", value : \"{}\", message : \"{}\"", "url", e.getRequestURL(), "Wrong URL");
        return new ErrorResponse("Wrong URL", getMessage("noHandlerFound.msg"));
    }

    /**
     * -1009
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse authenticationEntrypointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return new ErrorResponse("Authentication Entrypoint", getMessage("authenticationEntrypoint.msg"));
    }

    /**
     * -1010
     * 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse accessDeniedException(HttpServletRequest request, CAccessDeniedException e) {
        return new ErrorResponse("Access Denied", getMessage("accessDenied.msg"));
    }

    /**
     * -1011
     * refresh token 에러시 발생 시키는 에러
     */
    @ExceptionHandler(CRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse refreshTokenException(HttpServletRequest request, CRefreshTokenException e) {
        return new ErrorResponse("Refresh Token InValid", getMessage("refreshTokenInValid.msg"));
    }

    /**
     * -1012
     * 액세스 토큰 만료시 발생하는 에러
     */
    @ExceptionHandler(CExpiredAccessTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResponse expiredAccessTokenException(HttpServletRequest request, CExpiredAccessTokenException e) {
        return new ErrorResponse("Expired Access Token", getMessage("expiredAccessToken.msg"));
    }

//    /***
//     * -1013
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
     * -1014
     * 기 가입자 에러
     */
    @ExceptionHandler(CUserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse existUserException(HttpServletRequest request, CUserExistException e) {
        return new ErrorResponse("User Exist Exception", getMessage("userExistException.msg"));
    }

//    /***
//     * -1015
//     * 소셜 로그인 시 필수 동의항목 미동의시 에러
//     */
//    @ExceptionHandler(CSocialAgreementException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected CommonResult socialAgreementException(HttpServletRequest request, CSocialAgreementException e) {
//        return responseService.getFailResult(
//                Integer.parseInt(getMessage("agreementException.code")), getMessage("agreementException.msg")
//        );
//    }

    /***
     * -1016
     * 유효성 검사 실패
     */
    @ExceptionHandler(CValidCheckException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse validCheckException(HttpServletRequest request, CValidCheckException e) {
        log.error("message : \"{}\"",e.getMessage());
        return new ErrorResponse("Valid Exception", getMessage("validCheckException.msg"));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse orderNotFoundException(HttpServletRequest request, OrderNotFoundException e) {
        return new ErrorResponse("OrderNotFoundException", getMessage("orderNotFoundException.msg"));
    }

    @ExceptionHandler(OrderCancelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse orderCancelException(HttpServletRequest request, OrderCancelException e) {
        return new ErrorResponse("OrderCancelException", getMessage("orderCancelException.msg"));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
