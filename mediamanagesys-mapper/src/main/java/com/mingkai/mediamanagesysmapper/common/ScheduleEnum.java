package com.mingkai.mediamanagesysmapper.common;

/**
 * @description: 定时任务种类
 * @author: Created by 云风 on 2019-05-19 0:58
 */
public class ScheduleEnum {

    /**
     * 清除榜单任务
     *
     */
    public static Integer CLEAN_RANK = 0;

    /**
     * 跟新电影榜单
     */
    public static Integer INSERT_RECORD = 1;


    /**
     * 清除完成排片
     */
    public static Integer CLEAN_ARRANGE = 2;

    /**
     * 清除完成订单
     */
    public static Integer CLEAN_ORDER = 3;
}