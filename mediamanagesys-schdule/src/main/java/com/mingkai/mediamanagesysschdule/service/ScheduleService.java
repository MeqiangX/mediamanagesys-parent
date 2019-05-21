package com.mingkai.mediamanagesysschdule.service;

import com.mingkai.mediamanagesysschdule.constant.anno.TaskType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 定时任务service
 * @author: Created by 云风 on 2019-04-11 11:27
 */
@Service
@Slf4j
public class ScheduleService {

    @Autowired
    private SendDoubanRequestService sendDoubanRequestService;

    public static final String HOT_RANK_TABLE = "mesys_movie_hot_rank";

    public static final String NEW_RANK_TABLE = "mesys_movie_new_rank";

    public static final String NORTH_RANK_TABLE = "mesys_movie_north_rank";

    public static final String TOP250_RANK_TABLE = "mesys_movie_top250_rank";

    public static final String COMMING_RANK_TABLE = "mesys_movie_comming_rank";

    public static final String WEEK_RANK_TABLE = "mesys_movie_week_rank";

    public static final String city = "北京";

    public static final Integer start = 0;

    public static final Integer count = 200;

    public void test(){
        log.info("执行测试方法");
    }

    // 每天0点开始 跑任务
    @Scheduled(cron = "${schedule.cron}")
    public void clearDataScheduleMethod(){


        // 因为aop 不会拦截类内的同类调用方法，动态代理的方式 所以要用当前的代理对象的类内方法来触发调用 即
        // 即 当前被调用的 第一层方法 在aop拦截的时候 会生成一个代理对象，通过拦截这个代理对象的这个方法，来进行增强
        // 而这个方法再次调用类内的方法时，会再次生成代理对象，但是不是同一个，所以不拦截，
        // 可以得到通过得到当前的代理对象  来执行拦截

        if (null != AopContext.currentProxy()){
            ((ScheduleService)(AopContext.currentProxy())).startCleanData();
        }else{
            startCleanData();
        }


        if (null != AopContext.currentProxy()){
            ((ScheduleService)(AopContext.currentProxy())).saveData();
        }else{
            saveData();
        }


    }



    // 过期的订单


    /**
     * 清除数据
     */
    @Transactional
    @TaskType(type = 0)
    public void startCleanData(){
        sendDoubanRequestService.clearData(HOT_RANK_TABLE);
        sendDoubanRequestService.clearData(NEW_RANK_TABLE);
        sendDoubanRequestService.clearData(NORTH_RANK_TABLE);
        sendDoubanRequestService.clearData(TOP250_RANK_TABLE);
        sendDoubanRequestService.clearData(COMMING_RANK_TABLE);
        sendDoubanRequestService.clearData(WEEK_RANK_TABLE);
    }


    /**
     * 保存数据
     */
    @Transactional
    @TaskType(type = 1)
    public void saveData(){
        List<String> newRankMovieIds = sendDoubanRequestService.newRankQuery();

        for (String newRankMovieId : newRankMovieIds) {
            sendDoubanRequestService.saveRank(NEW_RANK_TABLE,newRankMovieId);
        }

        List<String> comingRankMovieIds = sendDoubanRequestService.comingRankQuery(city, start, count);

        for (String comingRankMovieId : comingRankMovieIds) {
            sendDoubanRequestService.saveRank(COMMING_RANK_TABLE,comingRankMovieId);
        }

        List<String> nowRankMovieIds = sendDoubanRequestService.nowRankQuery(city, start, count);

        for (String nowRankMovieId : nowRankMovieIds) {
            sendDoubanRequestService.saveRank(HOT_RANK_TABLE,nowRankMovieId);
        }

        List<String> northAmericaRankMovieIds = sendDoubanRequestService.northAmericaRankQuery();

        for (String northAmericaRankMovieId : northAmericaRankMovieIds) {
            sendDoubanRequestService.saveRank(NORTH_RANK_TABLE,northAmericaRankMovieId);
        }

        List<String> weekRankMovieIds = sendDoubanRequestService.weekRankQuery();

        for (String weekRankMovieId : weekRankMovieIds) {
            sendDoubanRequestService.saveRank(WEEK_RANK_TABLE,weekRankMovieId);
        }

        List<String> top250RankMovieIds = sendDoubanRequestService.top250RankQuery(city, start, count);

        for (String top250RankMovieId : top250RankMovieIds) {
            sendDoubanRequestService.saveRank(TOP250_RANK_TABLE,top250RankMovieId);
        }

    }






}
