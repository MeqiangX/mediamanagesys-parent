package com.mingkai.mediamanagesysschdule.aspect;

import com.mingkai.mediamanagesyscommon.common.ScheduleEnum;
import com.mingkai.mediamanagesyscommon.mapper.TaskRecordMapper;
import com.mingkai.mediamanagesyscommon.model.Do.task.TaskRecordDo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Pointcut("execution(public * com.mingkai.mediamanagesysschdule.service.ScheduleService.startCleanData())")
    public void clearTime(){}

    @Pointcut("execution(public * com.mingkai.mediamanagesysschdule.service.ScheduleService.saveData())")
    public void saveTime(){}


    @Before("clearTime()")
    public void clearStart(JoinPoint joinPoint){
        joinPoint.getArgs(); // 得到参数列表   在执行任务的方法都加上一个注解 获得注解值
        log.info("开始清理排行榜数据......");
        start.set(System.currentTimeMillis());
    }


    @After("clearTime()")
    public void clearEnd(JoinPoint joinPoint){
        log.info("清理完成...耗时：{}ms",System.currentTimeMillis() - start.get());
        // 插入记录
        TaskRecordDo taskRecordDo = new TaskRecordDo();
        taskRecordDo.setTaskType(ScheduleEnum.CLEAN_RANK);
        taskRecordDo.setSpengTime(System.currentTimeMillis() - start.get());
        taskRecordMapper.insert(taskRecordDo);
    }


    @Before("saveTime()")
    public void saveStart(JoinPoint joinPoint){
        log.info("清理开始...");
        start.set(System.currentTimeMillis());
    }

    @After("saveTime()")
    public void saveEnd(JoinPoint joinPoint){
        log.info("跟新完成...耗时：{}ms",System.currentTimeMillis() - start.get());

        // 插入记录
        TaskRecordDo taskRecordDo = new TaskRecordDo();
        taskRecordDo.setTaskType(ScheduleEnum.INSERT_RECORD);
        taskRecordDo.setSpengTime(System.currentTimeMillis() - start.get());
        taskRecordMapper.insert(taskRecordDo);
    }

}
