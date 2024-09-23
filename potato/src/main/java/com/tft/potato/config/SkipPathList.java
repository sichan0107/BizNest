package com.tft.potato.config;

import java.util.ArrayList;

public class SkipPathList extends ArrayList<String> {

    public SkipPathList() {
        // swagger
        this.add("/swagger-ui/**");
        this.add("/swagger-resources/**");
        this.add("/webjars/**");
        this.add("/csrf/**");
        this.add("/v2/api-docs");
        this.add("/webjars/**");
        this.add("/swagger-resources");

        // login
        this.add("/login/**");
        //this.add("/aws/image");
        //this.add("/aws/**");
        //this.add("/posts/**");

        // aspect test
        this.add("/user/info");
        this.add("/user/greeting");


        // web static resource
        this.add("/css/**");
        this.add("/js/**");
        this.add("/img/**");
        this.add("/lib/**");
        this.add("/error");
        this.add("/favicon.ico");
    }


}
