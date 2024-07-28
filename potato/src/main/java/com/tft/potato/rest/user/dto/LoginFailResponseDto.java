package com.tft.potato.rest.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class LoginFailResponseDto {
    private HttpStatus httpStatus;
    //private GoogleIdTokenDto googleIdTokenDto;

    private String successLogin;
    private String errorMsg;
}
