package com.mingkai.mediamanagesysschdule.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description: 测试切面类
 * @author: Created by 云风 on 2019-05-19 23:50
 */
@Aspect
@Component
@Slf4j
public class HelloAspect {

    /**
     * @Pointcut 定义切入点 匹配需要切入的class 或者 classes
     */

    /**
     * 切入方式
     * @Before
     * @After
     * @AfterReturning
     * @Around
     * @AfterThrowing 抛出异常后
     */

    @Pointcut("execution(public void com.mingkai.mediamanagesysschdule.service.ScheduleService.test(..))")
    public void saveRecords(){}

    @Before("saveRecords()")
    public void recordTask(JoinPoint joinPoint) throws Throwable{
        log.info("执行之前....");
    }

    @After("saveRecords()")
    public void afterTask(JoinPoint joinPoint){
        log.info("执行之后....");
    }


}
