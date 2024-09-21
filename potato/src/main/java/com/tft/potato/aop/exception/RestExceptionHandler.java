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

        int errorCode;
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
        } else {
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



}
