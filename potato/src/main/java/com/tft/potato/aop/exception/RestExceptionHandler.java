package com.tft.potato.aop.exception;

import com.tft.potato.common.vo.ApiResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse response = ApiResponse.builder()
                                        .errorCode(ErrorEnum.ILLEGAL_ARGUMENT.getStatusCode())
                                        .errorDescription(ErrorEnum.ILLEGAL_ARGUMENT.getMessage())
                                        .exceptionMessage(ex.getMessage())
                                        .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    // Exception 일때의 통합 처리로 변경해야함.
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ApiResponse> handleException(Exception ex) {

        String exType = ex.getClass().getTypeName();

        ApiResponse response = ApiResponse.builder()
                .errorCode(ErrorEnum.ILLEGAL_ARGUMENT.getStatusCode())
                .errorDescription(ErrorEnum.ILLEGAL_ARGUMENT.getMessage())
                .exceptionMessage(ex.getMessage())
                .build();

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
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
