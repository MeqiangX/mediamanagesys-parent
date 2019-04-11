package com.mingkai.mediamanagesysschdule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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


    // 每天0点开始 跑任务
    @Scheduled(cron = "0 0 0-23 * * ?")
    public void clearDataScheduleMethod(){

        log.info("现在开始执行定时任务,执行清除数据,现在时间："+ LocalDateTime.now() + " 定时任务启动.....");

        startCleanData();

        log.info("清除完成，现在时间：" +  LocalDateTime.now());

        log.info("开始执行 插入数据：");

        saveData();

        log.info("结束插入------------> 时间：" + LocalDateTime.now());

    }


    /**
     * 清除数据
     */
    @Transactional
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
