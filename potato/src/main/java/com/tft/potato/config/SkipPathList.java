package com.tft.potato.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkipPathList {


    public static List<String> getPermitAllPaths(){
        return Arrays.asList(

                // welcome page
                "/main"

                // login / logout
                ,"/login"
                ,"/logout"

                // tests
                ,"/aopLogTest"
                ,"/health"
                ,"/api/user/info"

                // swagger resources
                ,"/swagger-ui/**"
                ,"/swagger-resources/**"
                ,"/webjars/**"
                ,"/csrf/**"
                ,"/v2/api-docs"
                ,"/webjars/**"
                ,"/swagger-resources"

                // web static resources


                ,"/css/**"
                ,"/js/**"
                ,"/img/**"
                ,"/lib/**"
                ,"/error"
                ,"/favicon.ico"

        );
    }



}
