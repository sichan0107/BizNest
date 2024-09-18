package com.tft.potato.aop.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {

    NULL_POINT(400, "NULL_POINT"),
    ILLEGAL_ARGUMENT(400, "ILLEGAL_ARGUMENT"),
    INVALID_TOKEN(401, "INVALID_TOKEN"),
    UNHANDLED_ERROR(500, "UNHANDLED_ERROR");

    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
