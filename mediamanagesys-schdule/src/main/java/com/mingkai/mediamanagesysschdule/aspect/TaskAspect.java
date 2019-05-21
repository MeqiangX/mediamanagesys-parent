package com.mingkai.mediamanagesysschdule.aspect;

import com.mingkai.mediamanagesyscommon.common.StatusEnum;
import com.mingkai.mediamanagesyscommon.mapper.TaskRecordMapper;
import com.mingkai.mediamanagesyscommon.model.Do.task.TaskRecordDo;
import com.mingkai.mediamanagesysschdule.constant.anno.TaskType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: 任务执行情况的记录切面
 * @author: Created by 云风 on 2019-05-20 0:23
 */
@Aspect
@Component
@Slf4j
public class TaskAspect {

    @Autowired
    private TaskRecordMapper taskRecordMapper;

    private ThreadLocal<Long> start = new ThreadLocal<>();

    @Pointcut("execution(public * com.mingkai.mediamanagesysschdule.service.ScheduleService.*(..))")
    public void executeTask(){}


    /**
     * 关于在类中 通过this来调用 类内方法时候 aop不拦截的情况 https://www.cnblogs.com/intsmaze/p/5206584.html
     *  有种解决方法 是通过在调用方法中 通过调用当前的代理对象的类内方法 来触发 拦截
     * @param joinPoint
     */

    @Before("executeTask()")
    public void clearStart(JoinPoint joinPoint){
        // 获得任务的TaskType() 如果没有任何注解 则直接返回
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();

        if (method != null){

            // 将当前方法转为Method 对象  得到方法上的 TaskType
            TaskType annotation = method.getAnnotation(TaskType.class);

            if (annotation != null){
                // annotation.type() 得到当前注解的值

                // 开始计时
                log.info("执行任务类型： {}",annotation.type());
                start.set(System.currentTimeMillis());
            }


        }

    }


    @After("executeTask()")
    public void clearEnd(JoinPoint joinPoint){
        // 执行任务之后

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();

        if (null != method){

            TaskType annotation = method.getAnnotation(TaskType.class);

            if (null != annotation){
                // 插入到数据库中的 执行记录
                Long spend = System.currentTimeMillis() - start.get();
                log.info("任务完成,类型:{} 耗时：{}ms",annotation.type(),spend);
                // 插入记录
                TaskRecordDo taskRecordDo = new TaskRecordDo(annotation.type(),spend, StatusEnum.SUCCESS,null);
                taskRecordMapper.insert(taskRecordDo);
            }



        }


    }


    /**
     * 任务执行异常 执行
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executeTask()",throwing = "exception")
    public void exceptionTask(JoinPoint joinPoint,Throwable exception){
        //获得方法的tasktype

        // 只捕获带有TaskType注解的任务
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (null != method){

            TaskType annotation = method.getAnnotation(TaskType.class);

            if (annotation != null){

                // 记录方法的执行异常
                String err = exception.getMessage();
                // 执行耗时
                Long spend = System.currentTimeMillis() - start.get();

                // 插入到数据库
                TaskRecordDo taskRecordDo = new TaskRecordDo(annotation.type(),spend,StatusEnum.FAIL,err);
                taskRecordMapper.insert(taskRecordDo);
            }

        }

    }


}
