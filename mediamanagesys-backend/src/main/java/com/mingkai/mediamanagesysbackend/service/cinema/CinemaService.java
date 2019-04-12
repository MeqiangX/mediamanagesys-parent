package com.mingkai.mediamanagesysbackend.service.cinema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.mingkai.mediamanagesysbackend.model.PO.MovieArrangePo;
import com.mingkai.mediamanagesyscommon.manager.CinemaManager;
import com.mingkai.mediamanagesyscommon.manager.CinemaScreenManager;
import com.mingkai.mediamanagesyscommon.manager.ScreenSeatManager;
import com.mingkai.mediamanagesyscommon.mapper.MovieDetailMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesyscommon.mapper.common.AreaMapper;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaDo;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaScreenDo;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaAddPo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaPagePo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.CinemaVo;
import com.mingkai.mediamanagesyscommon.model.common.Area;
import com.mingkai.mediamanagesyscommon.model.common.City;
import com.mingkai.mediamanagesyscommon.model.common.Province;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import com.mingkai.mediamanagesyscommon.utils.time.LocalDateTimeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:31
 */
@Service
public class CinemaService {

    @Autowired
    private ScreenArrangeMapper screenArrangeMapper;

    @Autowired
    private CinemaManager cinemaManager;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private ScreenRoomMapper screenRoomMapper;

    @Autowired
    private CinemaScreenManager cinemaScreenManager;

    @Autowired
    private MovieDetailMapper movieDetailMapper ;

    @Autowired
    private ScreenSeatManager screenSeatManager;

    /**
     * 查询影院by 电影
     * @param cinemaPagePo
     * @return
     */
    public Page<CinemaVo> cinemaByMovie(CinemaPagePo cinemaPagePo){
        //通过 movieId 查询 有场次的影院id
        Page<CinemaDo> screenArrangeDoPage = screenArrangeMapper.selectArrangeByPage(cinemaPagePo);
        // 展示
        return ConvertUtil.pageConvert(screenArrangeDoPage,CinemaVo.class);
    }


    /**
     * 添加影院
     * @param cinemaAddPo
     * @return
     */
    @Transactional
    public Boolean cinemaAdd(CinemaAddPo cinemaAddPo){

        // 重名校验
        CinemaDo cinema = cinemaManager.getOne(new QueryWrapper<CinemaDo>()
                .eq("cinema_full_address", cinemaAddPo.getCinemaFullAddress()));

        if (Objects.nonNull(cinema)){
            throw new BizException("统一位置上不能出现两个不同的影院");
        }

        // 正常添加

        CinemaDo cinemaDo = new CinemaDo();

        cinemaDo.setCinemaAreaId(cinemaAddPo.getAreaId());
        cinemaDo.setCinemaName(cinemaAddPo.getCinemaName());
        cinemaDo.setCinemaFullAddress(cinemaAddPo.getCinemaFullAddress());

        //查询 地区所在的 县市区

        Area area = areaMapper.selectByAreaId(cinemaAddPo.getAreaId());

        City city = areaMapper.selectByCityId(area.getFatherId());

        Province province = areaMapper.selectByProvinceId(city.getFatherId());

        cinemaDo.setCinemaAreaFullName(province.getProvince()+city.getCity()+area.getArea());

        return cinemaManager.save(cinemaDo);
    }

    /**
     * 配置影院 放映厅
     * @param cinemaScreenUpdatePo
     * @return
     */
    @Transactional
    public Boolean cinemaScreenConfig(CinemaScreenUpdatePo cinemaScreenUpdatePo){
        if (cinemaScreenUpdatePo.getOption().equals(Integer.valueOf(0))){
            // add

            // 看要添加的放映厅是否存在
            List<ScreenRoomDo> screenRoomList = screenRoomMapper.selectBatchIds(cinemaScreenUpdatePo.getScreenHallIdList());

            List<Integer> screenRoomIdList = screenRoomList.stream().map(e -> e.getId()).collect(Collectors.toList());

            List<Integer> notContainList = Lists.newArrayList();

            for (Integer addScreenId : cinemaScreenUpdatePo.getScreenHallIdList()) {
                if (!screenRoomIdList.contains(addScreenId)){
                    notContainList.add(addScreenId);
                }
            }


            if (notContainList.size() != 0){
                throw new BizException("放映厅不存在："+Strings.join(notContainList,','));
            }


            // 看是否已经有重复的
            List<CinemaScreenDo> cinemaScreenDoList = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                    .eq("cinema_id", cinemaScreenUpdatePo.getCinemaId())
                    .in("screen_hall_id", cinemaScreenUpdatePo.getScreenHallIdList()));

            if (Objects.nonNull(cinemaScreenDoList) && cinemaScreenDoList.size() > 0){
                throw new BizException("重复添加：" + Strings.join(cinemaScreenDoList,','));
            }

            // 否则 插入记录
            List<CinemaScreenDo> addList = Lists.newArrayList();
            for (Integer screenId : cinemaScreenUpdatePo.getScreenHallIdList()) {
                CinemaScreenDo cinemaScreenDo = new CinemaScreenDo();
                cinemaScreenDo.setCinemaId(cinemaScreenUpdatePo.getCinemaId());
                cinemaScreenDo.setScreenHallId(screenId);
                addList.add(cinemaScreenDo);
            }

            return cinemaScreenManager.saveBatch(addList);

        } else if (cinemaScreenUpdatePo.getOption().equals(Integer.valueOf(1))){
            // delete

            // 放映厅是否存在
            List<ScreenRoomDo> screenRoomList = screenRoomMapper.selectBatchIds(cinemaScreenUpdatePo.getScreenHallIdList());

            List<Integer> screenRoomIdList = screenRoomList.stream().map(e -> {
                return e.getId();
            }).collect(Collectors.toList());

            List<Integer> notContainList = Lists.newArrayList();

            for (Integer addScreenId : cinemaScreenUpdatePo.getScreenHallIdList()) {
                if (!screenRoomIdList.contains(addScreenId)){
                    notContainList.add(addScreenId);
                }
            }


            if (notContainList.size() != 0){
                throw new BizException("放映厅不存在："+Strings.join(notContainList,','));
            }

            // 看删除的是否存在
            List<CinemaScreenDo> cinemaScreenDoList = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                    .eq("cinema_id", cinemaScreenUpdatePo.getCinemaId())
                    .in("screen_hall_id", cinemaScreenUpdatePo.getScreenHallIdList()));

            List<Integer> cinemaScreenRelList = cinemaScreenDoList.stream().map(CinemaScreenDo::getScreenHallId).collect(Collectors.toList());

            List<Integer> notRelScreenList = Lists.newArrayList();

            for (Integer delScreenId : cinemaScreenUpdatePo.getScreenHallIdList()) {
                if (!cinemaScreenRelList.contains(delScreenId)){
                    notRelScreenList.add(delScreenId);
                }
            }

            if (Objects.nonNull(notRelScreenList) && notRelScreenList.size() > 0){
                throw new BizException("影院没有这些放映厅：" + Strings.join(notRelScreenList,','));
            }

            // 看是否删除的正在使用 查排片表  是否有记录

            // 通过cinemaId 和 screenIds 拿到ids  然后拿到ids 去查找排片表 是否有记录
            List<Integer> cinemaScreenRelIdList = cinemaScreenDoList.stream().map(e -> e.getId()).collect(Collectors.toList());

            List<ScreenArrangeDo> movieArrangeRecords = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .in("cinema_screen_id", cinemaScreenRelIdList));

            List<Integer> hasArrangedIds = movieArrangeRecords.stream().map(e -> e.getCinemaScreenId()).collect(Collectors.toList());
            // 然后通过这些id 回去查找对应的放映厅 不能被删除
            List<CinemaScreenDo> cinemaScreenDos = (List)cinemaScreenManager.listByIds(hasArrangedIds);

            if (Objects.nonNull(cinemaScreenDos) && cinemaScreenDos.size() > 0){
                throw new BizException("下列放映厅在该影院有排片，无法删除："+Strings.join(cinemaScreenDos.stream().map(CinemaScreenDo::getScreenHallId).collect(Collectors.toList()), ','));
            }


            // 正常删除
            return cinemaScreenManager.remove(new QueryWrapper<CinemaScreenDo>()
            .eq("cinema_id",cinemaScreenUpdatePo.getCinemaId())
            .in("screen_hall_id",cinemaScreenUpdatePo.getScreenHallIdList()));

        }else{
            throw new BizException("无效选项");
        }

    }

    /**
     * 排片
     * @param movieArrangePo
     * @return
     */
    @Transactional
    public Boolean arrangeMovie(MovieArrangePo movieArrangePo){

        // 根据选择的 影院id 和 放映厅id 来查找 relId
        CinemaScreenDo cinemaScreenRel = cinemaScreenManager.getOne(new QueryWrapper<CinemaScreenDo>()
                .eq("cinema_id", movieArrangePo.getCinemaId())
                .eq("screen_hall_id", movieArrangePo.getScreenHallId()));

        if (Objects.isNull(cinemaScreenRel)){
            throw new BizException("当前影院没有这个放映厅");
        }

        //填充relId
        movieArrangePo.setCinemaScreenId(cinemaScreenRel.getId());

        /**
         * 逻辑
         *
         * 主要是解决 排片时间 上的 冲突
         */

        // 查询 当前日期 下的 当前影院放映厅的 排片情况
        List<ScreenArrangeDo> screenArrangeDos = screenArrangeMapper.selectByDateAndHallId(movieArrangePo.getCinemaScreenId(), movieArrangePo.getDate());

        // 查询 当前排片的 电影时长
        MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(movieArrangePo.getMovieId());

        if (null == movieDetailDo){
            throw new BizException("排片电影不存在，请重新选择");
        }


        LocalDateTime startTime = LocalDateTimeUtils.getLocalDateTimeFromStr(movieArrangePo.getStartTime(), LocalDateTimeUtils.DATE_TIME);
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

        screenArrangeDo.setCinemaScreenId(movieArrangePo.getCinemaScreenId());
        screenArrangeDo.setArrangeDate(movieArrangePo.getDate());
        screenArrangeDo.setMovieId(movieArrangePo.getMovieId());
        screenArrangeDo.setTimeScopeStart(LocalDateTimeUtils.formatLocalDateTime(startTime,LocalDateTimeUtils.DATE_TIME));
        screenArrangeDo.setTimeScopeEnd(LocalDateTimeUtils.formatLocalDateTime(endTime,LocalDateTimeUtils.DATE_TIME));
        screenArrangeDo.setPrice(movieArrangePo.getPrice());
        int insert = screenArrangeMapper.insert(screenArrangeDo);

        // 插入到坐席表  com

        //查找对应放映厅的 x y
        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(movieArrangePo.getScreenHallId());

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


        boolean saveBatch = screenSeatManager.saveBatch(seatList);

        return insert == 1 && saveBatch ? true : false;
    }
}
