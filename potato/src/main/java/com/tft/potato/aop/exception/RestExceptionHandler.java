package com.tft.potato.aop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    // 예외 처리 메서드
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 예외 메시지와 HTTP 상태 코드를 반환
        return new ResponseEntity<>("잘못된 입력입니다: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // 예외 메시지와 HTTP 상태 코드를 반환
        return new ResponseEntity<>("널 포인터 예외 발생: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 그 외의 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("알 수 없는 오류가 발생했습니다: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
