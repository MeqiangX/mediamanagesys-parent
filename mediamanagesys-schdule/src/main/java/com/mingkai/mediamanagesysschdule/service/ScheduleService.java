package com.mingkai.mediamanagesysschdule.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mingkai.mediamanagesysmapper.manager.ScreenSeatManager;
import com.mingkai.mediamanagesysmapper.manager.TicketDetailManager;
import com.mingkai.mediamanagesysmapper.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesysmapper.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesysmapper.utils.redis.RedisUtil;
import com.mingkai.mediamanagesysmapper.utils.time.LocalDateTimeUtils;
import com.mingkai.mediamanagesysschdule.constant.anno.TaskType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 定时任务service
 * @author: Created by 云风 on 2019-04-11 11:27
 */
@Service
@Slf4j
public class ScheduleService {

    @Autowired
    private SendDoubanRequestService sendDoubanRequestService;

    @Autowired
    private ScreenArrangeMapper screenArrangeMapper;

    @Autowired
    private ScreenSeatManager screenSeatManager;

    @Autowired
    private TicketDetailManager ticketDetailManager;

    @Autowired
    private RedisUtil redisUtil;

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

    // 每天0点开始 跑任务 1次/天
    @Scheduled(cron = "${schedule.movie-cron}")
    public void clearDataScheduleMethod(){


        // 因为aop 不会拦截类内的同类调用方法，动态代理的方式 所以要用当前的代理对象的类内方法来触发调用 即
        // 即 当前被调用的 第一层方法 在aop拦截的时候 会生成一个代理对象，通过拦截这个代理对象的这个方法，来进行增强
        // 而这个方法再次调用类内的方法时，会再次生成代理对象，但是不是同一个，所以不拦截，
        // 可以得到通过得到当前的代理对象  来执行拦截

        if (null != AopContext.currentProxy()){
            ((ScheduleService)(AopContext.currentProxy())).startCleanData();
            ((ScheduleService)(AopContext.currentProxy())).saveData();
        }else{
            startCleanData();
            saveData();
        }



    }

    /**
     * 过期的排片清理  放映结束 先寻找排片记录 对比当前时间和 结束时间time_scope_end
     * 超过结束时间 清理对应的坐席
     * 然后查询订单表  查询坐席如果没有找到 则是过期了，如果是已经支付的 则状态变更为2 删除  如果是未支付的 清除redis 过期时间  删除记录
     * 每隔一小时执行一次
     */
    @Transactional
    @TaskType(type = 2)
    @Scheduled(cron = "${schedule.order-cron}")
    public void movieArrangeCleanTask(){

        //得到当前时间 清除排片 结束时间 比 当前时间小的记录

        // 清除排片
        List<ScreenArrangeDo> screenArrangeDos = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                .le("time_scope_end", LocalDateTimeUtils.formatLocalDateTime(LocalDateTime.now(), LocalDateTimeUtils.DATE_TIME)));

        if (Objects.isNull(screenArrangeDos) || screenArrangeDos.size() == 0){
            return ;
        }

        List<Integer> arrangeIds = screenArrangeDos.stream().map(ScreenArrangeDo::getId).collect(Collectors.toList());

        // 得到排片的坐席表 删除

        // 删除排片记录
        int batchIds = screenArrangeMapper.deleteBatchIds(arrangeIds);

        boolean deleteArrangeSeats = screenSeatManager.remove(new QueryWrapper<ScreenSeatDo>()
                .in("screen_arrange_id", arrangeIds));

    }


    @Transactional
    @TaskType(type = 3)
    @Scheduled(cron = "${schedule.order-cron}")
    public void cleanOrder(){
        // 查找所有的订单

        // 如果订单的坐席不存在，则说明已经失效了， 如果没有付款，则直接删除
        // 如果付了款，将订单变为 2 已完成状态， 删除 （如果有取票操作，也是置为已经完成）

        // 订单的信息 应该是在放映完成后 删除  2019年5月24日10:51:32  更新 已完成订单不删除 后台做统计

        List<TicketDetailDo> list = ticketDetailManager.list(null);

        for (TicketDetailDo ticketDetailDo : list) {

            String[] split = ticketDetailDo.getSeatIds().split(",");

            if (null == screenSeatManager.getById(Integer.valueOf(split[0]))){

                // 订单过期

                // 查看订单的支付状态 成功的置为2 完成
                Integer payStatus = ticketDetailDo.getStatus();
                if (1 == payStatus){
                    ticketDetailDo.setStatus(2);
                    //跟新
                    boolean update = ticketDetailManager.update(ticketDetailDo, new UpdateWrapper<TicketDetailDo>()
                            .eq("id", ticketDetailDo.getId()));

                }else if (0 == payStatus){
                    // 待支付  Redis 清除  删除订单
                    redisUtil.del(ticketDetailDo.getOrderId());
                    //跟新
                    boolean update = ticketDetailManager.update(ticketDetailDo, new UpdateWrapper<TicketDetailDo>()
                            .eq("id", ticketDetailDo.getId()));

                    // 删除订单
                    ticketDetailManager.removeById(ticketDetailDo.getId());

                }else{
                    // 2 已经完成的状态  不变

                }




            }

        }

    }



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
