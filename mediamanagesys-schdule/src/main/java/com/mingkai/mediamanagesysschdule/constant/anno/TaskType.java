package com.mingkai.mediamanagesysschdule.constant.anno;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-21 20:10
 */
@Retention(RetentionPolicy.RUNTIME)  // 运行时期可以被捕获到
@Documented
@Target(ElementType.METHOD) // 声明注解的作用实体
public @interface TaskType {

    int type() default 0; // 定时任务类型 默认是清除 详情看ScheduleEnum


}
