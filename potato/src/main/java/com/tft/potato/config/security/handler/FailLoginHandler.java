package com.tft.potato.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tft.potato.rest.user.dto.LoginFailResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FailLoginHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginFailResponse = "";
        LoginFailResponseDto loginFailResponseDto = new LoginFailResponseDto();

        if(exception instanceof UsernameNotFoundException){
            loginFailResponseDto.setErrorMsg("등록된 회원이 아닙니다.");
        }else{
            loginFailResponseDto.setErrorMsg("Internal Server Error");
        }

        loginFailResponseDto.setSuccessLogin("N");
        loginFailResponseDto.setHttpStatus(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();

        loginFailResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginFailResponseDto);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(loginFailResponse);
        out.flush();

    }
}
