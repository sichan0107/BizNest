package com.tft.potato.rest.welcome;

import com.tft.potato.aop.exception.ErrorEnum;
import com.tft.potato.common.vo.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRestController {

    @GetMapping("health")
    public ResponseEntity<ApiResponse<String>> checkHealth()
    {
        ApiResponse apiResponse = ApiResponse.builder()
                                            .response("Potato server is running.")
                                            .errorCode(ErrorEnum.OK.getStatusCode())
                                            .errorDescription(ErrorEnum.OK.getMessage())
                                            .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
