package com.tft.potato.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkipPathList {


    public static List<String> getPermitAllPaths(){
        return Arrays.asList(

                // welcome page
                "/index.html"

                // socket chat
                ,"/ws/**"                      // 웹소켓 엔드포인트
                ,"/topic/**"                   // 메세지 브로커 경로
                ,"/app/**"                     // 클라이언트에서 서버로 메시지를 보낼 경로

                // login / logout
                ,"/login"
                ,"/logout"

                // tests
                ,"/aopLogTest"
                ,"/health"
                ,"/user/info"
                ,"/user/all"

                // file tests
                ,"/file/upload"
                ,"/file/list"

                // swagger resources
                ,"/swagger-ui/**"
                ,"/swagger-resources/**"
                ,"/webjars/**"
                ,"/csrf/**"
                ,"/v2/api-docs"
                ,"/webjars/**"
                ,"/swagger-resources"

                // web static resources

                ,"/static/**"
                ,"/css/**"
                ,"/js/**"
                ,"/img/**"
                ,"/lib/**"
                ,"/error"
                ,"/favicon.ico"

        );
    }



}
