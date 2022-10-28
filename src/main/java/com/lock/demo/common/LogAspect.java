package com.lock.demo.common;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zhangguichang
 * @date 2022-10-28 15:59
 */
@Aspect
@Component
@Log4j2
public class LogAspect {
    private Long startTime;
    private Long endTime;

    public LogAspect(){

    }
    @Pointcut("execution(* com.lock.demo.manager..*.*(..))")
    public void LogPointcut(){

    }
    @Before("LogPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        //打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("请求开始时间:" + LocalDateTime.now());
    }
    @AfterReturning(returning = "ret", pointcut = "LogPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.info("请求结束时间:" + LocalDateTime.now());
        log.info("线程名字:"+Thread.currentThread().getName()+"请求耗时:" + (endTime - startTime));
    }
}
