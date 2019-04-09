package com.mingkai.mediamanagesysbackend.service.screen;

import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenArrangePo;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenRoomAddPo;
import com.mingkai.mediamanagesyscommon.manager.ScreenSeatManager;
import com.mingkai.mediamanagesyscommon.mapper.MovieDetailMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenSeatMapper;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesyscommon.model.Vo.screen.ScreenRoomVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import com.mingkai.mediamanagesyscommon.utils.time.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 放映厅相关service
 * @author: Created by 云风 on 2019-04-03 17:42
 */
@Service
public class ScreenService {

    @Autowired
    private ScreenRoomMapper screenRoomMapper;

    @Autowired
    private ScreenArrangeMapper screenArrangeMapper;

    @Autowired
    private MovieDetailMapper movieDetailMapper;

    @Autowired
    private ScreenSeatMapper screenSeatMapper;

    @Autowired
    private ScreenSeatManager screenSeatManager;

    /**
     *  查询放映厅
     * @return
     */
    public ScreenRoomVo queryScreenRoom(){
        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(4);
        return null == screenRoomDo ? null : ConvertUtil.convert(screenRoomDo, ScreenRoomVo.class);
    }


    /**
     *  添加放映厅
     * @param screenRoomAddPo
     * @return
     */
    @Transactional
    public Boolean addScreenRoom(ScreenRoomAddPo screenRoomAddPo){

        //重名校验
        Integer repeatCount = screenRoomMapper.selectRepeatNameCount(screenRoomAddPo.getScreeningHallName());

        if (0 != repeatCount){
            throw new BizException("存在重名放映厅");
        }

        ScreenRoomDo screenRoomDo = new ScreenRoomDo();

        screenRoomDo.setScreeningHallName(screenRoomAddPo.getScreeningHallName());
        screenRoomDo.setScreeningHallX(screenRoomAddPo.getScreeningHallX());
        screenRoomDo.setScreeningHallY(screenRoomAddPo.getScreeningHallY());

        int insert = screenRoomMapper.insert(screenRoomDo);

        return insert == 1 ? true : false;
    }


    /**
     * 排片
     * @param screenArrangePo
     * @return
     */
    @Transactional
    public Boolean arrangeMovie(ScreenArrangePo screenArrangePo){

        /**
         * 逻辑
         *
         * 主要是解决 排片时间 上的 冲突
         */

        // 查询 当前日期 下的 当前放映厅的 排片情况
        List<ScreenArrangeDo> screenArrangeDos = screenArrangeMapper.selectByDateAndHallId(screenArrangePo.getScreenHallId(), screenArrangePo.getDate());

        // 查询 当前排片的 电影时长
        MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(screenArrangePo.getMovieId());

        if (null == movieDetailDo){
            throw new BizException("排片电影不存在，请重新选择");
        }


        LocalDateTime startTime = LocalDateTimeUtils.getLocalDateTimeFromStr(screenArrangePo.getStartTime(), LocalDateTimeUtils.DATE_TIME);
        Long movieDurationFormat = LocalDateTimeUtils.getMovieDurationFormat(movieDetailDo.getDuration());

        LocalDateTime endTime = startTime.plusMinutes(movieDurationFormat);

        // 遍历 看是否 开始时间 和 结束时间+20 在  已经排片的记录 (start-end) 中
        for (ScreenArrangeDo screenArrangeDo : screenArrangeDos) {
            LocalDateTime start = LocalDateTimeUtils.getLocalDateTimeFromStr(screenArrangeDo.getTimeScopeStart(), LocalDateTimeUtils.DATE_TIME);

            LocalDateTime end = LocalDateTimeUtils.getLocalDateTimeFromStr(screenArrangeDo.getTimeScopeEnd(), LocalDateTimeUtils.DATE_TIME);

            // 有 冲突
             if (startTime.compareTo(start) >= 0 && startTime.compareTo(end) < 0){
                 throw new BizException("时间冲突，请重新排片");
             }

             if (endTime.compareTo(start) > 0 && endTime.compareTo(end) <= 0){
                 throw new BizException("时间冲突，请重新排片");
             }
        }

        // 无 正常排片

        //插入记录  并且 同时插入到 坐席表
        ScreenArrangeDo screenArrangeDo = new ScreenArrangeDo();

        screenArrangeDo.setScreeningHallId(screenArrangePo.getScreenHallId());
        screenArrangeDo.setArrangeDate(screenArrangePo.getDate());
        screenArrangeDo.setMovieId(screenArrangePo.getMovieId());
        screenArrangeDo.setTimeScopeStart(LocalDateTimeUtils.formatLocalDateTime(startTime,LocalDateTimeUtils.DATE_TIME));
        screenArrangeDo.setTimeScopeEnd(LocalDateTimeUtils.formatLocalDateTime(endTime,LocalDateTimeUtils.DATE_TIME));
        screenArrangeDo.setPrice(screenArrangePo.getPrice());
        int insert = screenArrangeMapper.insert(screenArrangeDo);

        // 插入到坐席表  com

        //查找对应放映厅的 x y
        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(screenArrangePo.getScreenHallId());

        if (null == screenRoomDo){
            throw new BizException("未找到对应的放映厅");
        }


        // 插入坐席表
        List<ScreenSeatDo> seatList = Lists.newArrayList();

        for (int i = 1; i <= screenRoomDo.getScreeningHallX();++i){
            for (int j = 1; j <= screenRoomDo.getScreeningHallY();++j){
                ScreenSeatDo screenSeatDo = new ScreenSeatDo();
                screenSeatDo.setScreenArrangeId(screenArrangeDo.getId());
                screenSeatDo.setScreeningHallX(i);
                screenSeatDo.setScreeningHallY(j);
                screenSeatDo.setStatus(0);
                screenSeatDo.setIsPurchased(0);
                seatList.add(screenSeatDo);
            }
        }

        //批量插入
        screenSeatMapper.selectTest();

        boolean saveBatch = screenSeatManager.saveBatch(seatList);

        return insert == 1 && saveBatch ? true : false;
    }


    // TODO  选座
    public Boolean chooseSeat(){
        return true;
    }




}
