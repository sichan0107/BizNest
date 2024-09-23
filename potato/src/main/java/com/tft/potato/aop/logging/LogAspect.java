package com.tft.potato.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * @param joinPoint
     *
     * 메소드 설명
     *  getArgs() : 대상 메서드의 인자 목록을 반환합니다
     *  getSignature() : 대상 메서드의 정보를 반환합니다.
     *  getSourceLocation() : 대상 메서드가 선언된 위치를 반환합니다.
     *
     */

    // com / tft / potato / rest / controller / *  / * / (..)
    //  패 /  패  /  패   /   패  /    패      / 클 / 메 / 인자값
    // (..)은 인자값이 0개 이상인 모든것

    @Before("execution(public * com.tft.potato.rest.*.controller.*.*(..))")
    public void before(JoinPoint joinPoint){
        log.info("Aop before test : {}", joinPoint.getSignature().getName());
        log.info("===== Controller 메소드 실행 전");
    }
    @After("execution(public * com.tft.potato.rest.*.controller.*.*(..))")
    public void after(JoinPoint joinPoint){
        log.info("Aop after test : {}", joinPoint.getSignature().getName());
        log.info("===== Controller 메소드 실행 후");
    }
}
