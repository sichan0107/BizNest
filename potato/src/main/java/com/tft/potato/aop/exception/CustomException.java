package com.tft.potato.aop.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    private int errorCode;
    private String message;

    public CustomException(ErrorEnum errorEnum){
        this.errorCode = errorEnum.getStatusCode();
        this.message = errorEnum.getMessage();
    }


}
