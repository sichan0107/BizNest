package com.tft.potato.aop.exception;

import com.tft.potato.common.vo.ApiResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.Null;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    private final String REDIRECT_LOGIN = "/login";

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ApiResponse> handleException(Exception ex) {

        int errorCode = ErrorEnum.OK.getStatusCode();
        String errorDescription = "";
        String redirectUrl = "";
        
        if(ex instanceof IllegalArgumentException){
            errorCode = ErrorEnum.ILLEGAL_ARGUMENT.getStatusCode();
            errorDescription = ErrorEnum.ILLEGAL_ARGUMENT.getMessage();
        } else if (ex instanceof NullPointerException) {
            errorCode = ErrorEnum.NULL_POINT.getStatusCode();
            errorDescription = ErrorEnum.NULL_POINT.getMessage();
        } else if (ex instanceof AccessDeniedException) {
            errorCode = ErrorEnum.UNHANDLED_ERROR.getStatusCode();
            errorDescription = ErrorEnum.UNHANDLED_ERROR.getMessage();
            redirectUrl = REDIRECT_LOGIN;
        }else {
            errorCode = ErrorEnum.UNHANDLED_ERROR.getStatusCode();
            errorDescription = ErrorEnum.UNHANDLED_ERROR.getMessage();
        }

        ApiResponse response = ApiResponse.builder()
                .errorCode(errorCode)
                .errorDescription(errorDescription)
                .exceptionMessage(ex.getMessage())
                .redirectUrl(redirectUrl)
                .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<ApiResponse> handleException(CustomException ex) {

        int errorCode = ErrorEnum.OK.getStatusCode();
        String errorDescription = "";
        String redirectUrl = "";

        classifyCustomException(ex, errorCode, errorDescription, redirectUrl);

        ApiResponse response = ApiResponse.builder()
                .errorCode(errorCode)
                .errorDescription(errorDescription)
                .exceptionMessage(ex.getMessage())
                .redirectUrl(redirectUrl)
                .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    private void classifyCustomException(CustomException ex, int errorCode, String errorDes, String redirectUrl){
        // Refesh Token이 만료되었다면 403으로 재로그인 응답
        if(ex.getErrorCode() == ErrorEnum.EXPIRED_REFRESH_TOKEN.getStatusCode()){
            errorCode = ErrorEnum.EXPIRED_REFRESH_TOKEN.getStatusCode();
            errorDes = ErrorEnum.EXPIRED_REFRESH_TOKEN.getMessage();
            redirectUrl = REDIRECT_LOGIN;
        }

    }



}
