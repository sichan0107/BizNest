package com.tft.potato.aop.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {

    OK(0, "System is OK."),

    /*
        Error : 400 Bad Request (일반적인 오류)
        Description : 주로 API에 필요한 필수 파라미터와 관련하여 서버가 클라이언트 오류를 감지해 요청을 처리하지 못한 상태입니다.
     */
    NULL_POINT(-1, "NPE is ocurred."),
    ILLEGAL_ARGUMENT(-2, "This param is illegal."),

    /*
        Error : 401 Unauthorized (인증 오류)
        Description : 해당 리소스에 유효한 인증 자격 증명이 없어 요청에 실패한 상태입니다.
     */

    INVALID_TOKEN(-100, "This token is invalid."),

    /*
        Error : 403 Forbidden (권한  오류)
        Description : 서버에 요청이 전달되었지만, 권한 때문에 거절된 상태입니다.
     */
    EXPIRED_REFRESH_TOKEN(-101, "Please retry login."),


    ACCESS_DENIED(-200, "Access Denied. Please retry login."),

    /*
        Error : 429 Too Many Request (쿼터 초과)
        Description : 정해진 사용량이나 초당 요청 한도를 초과한 경우
     */


    /*
        Error : 500 Internal Server Error (시스템 오류)
        Description : 서버 에러를 총칭하는 에러 코드로, 요청을 처리하는 과정에서 서버가 예상하지 못한 상황에 놓인 상태입니다.
     */

    UNHANDLED_ERROR(-9999, "This is unhandled error.");

    /*
        Error : 502 Bad Gateway (시스템 오류)
        Description : 서로 다른 프로토콜을 연결해주는 게이트웨이가 잘못된 프로토콜을 연결하거나 연결된 프로토콜에 문제가 있어 통신이 제대로 되지 않은 상태입니다.
     */

    /*
        Error : 503 Service Unavailable (서비스 점검중)
        Description : 서버가 요청을 처리할 준비가 되지 않은 상태입니다.
     */



    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }


}
