package com.mingkai.mediamanagesysschdule.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.google.common.collect.Sets;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesysschdule.service.ScheduleService;
import com.mingkai.mediamanagesysschdule.service.SendDoubanRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

/**
 * @description: 发送http请求
 * @author: Created by 云风 on 2019-04-01 21:47
 */
@RestController
@RequestMapping("send")
@Api(tags = API.HTTP_CEND)
public class SendHttpRequestController {

    @Autowired
    private SendDoubanRequestService sendDoubanRequestService;

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("测试")
    @GetMapping("test")
    public R<Boolean> test(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                scheduleService.test();
                return null;
            }
        }.execute();
    }

    @ApiOperation("清除排片信息")
    @GetMapping("clear-arrange")
    public R<Boolean> movieArrangeCleanTask(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                scheduleService.movieArrangeCleanTask();
                return Boolean.TRUE;
            }
        }.execute();
    }

    @ApiOperation("清除订单信息")
    @GetMapping("clear-order")
    public R<Boolean> cleanOrder(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                scheduleService.cleanOrder();
                return Boolean.TRUE;
            }
        }.execute();
    }

    @ApiOperation("跟新库数据")
    @GetMapping("update-data")
    public R<Boolean> updateMoviesDatas(String city,Integer start,Integer count){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return sendDoubanRequestService.saveBatch(city,start,count);
            }
        }.execute();
    }



    @ApiOperation("手动执行定时任务")
    @GetMapping("task-run")
    public R<Boolean> taskRun(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                try{
                    scheduleService.clearDataScheduleMethod();
                }catch (Exception e){
                    throw new BizException(e);
                }

                return Boolean.TRUE;
            }
        }.execute();
    }

    @ApiOperation("TOP250")
    @GetMapping("top250")
    public void top250(){
        RestTemplate restTemplate = new RestTemplate();
        Map map=restTemplate.getForObject("https://api.douban.com/v2/movie/top250?apikey=0b2bdeda43b5688921839c8ecb20399b&start=0&count=100",Map.class);

        map.get("subjects");


        Set s = Sets.newHashSet();

    }
}
