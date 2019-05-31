package com.mingkai.mediamanagesysbackend.service.cinema;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mingkai.mediamanagesysmapper.common.ProvinceShortEnum;
import com.mingkai.mediamanagesysmapper.manager.*;
import com.mingkai.mediamanagesysmapper.mapper.MovieDetailMapper;
import com.mingkai.mediamanagesysmapper.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesysmapper.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesysmapper.mapper.common.AreaMapper;
import com.mingkai.mediamanagesysmapper.model.Do.cinema.CinemaDo;
import com.mingkai.mediamanagesysmapper.model.Do.cinema.CinemaScreenDo;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieCastDo;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesysmapper.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenSeatDo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaAddPo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaPagePo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArgBackPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArrangePo;
import com.mingkai.mediamanagesysmapper.model.Vo.MapVo;
import com.mingkai.mediamanagesysmapper.model.Vo.PercentPaneVo;
import com.mingkai.mediamanagesysmapper.model.Vo.cinema.*;
import com.mingkai.mediamanagesysmapper.model.common.Area;
import com.mingkai.mediamanagesysmapper.model.common.City;
import com.mingkai.mediamanagesysmapper.model.common.Province;
import com.mingkai.mediamanagesysmapper.utils.convert.ConvertUtil;
import com.mingkai.mediamanagesysmapper.utils.page.PageUtils;
import com.mingkai.mediamanagesysmapper.utils.time.LocalDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:31
 */
@Service
@Slf4j
public class CinemaService {

    @Autowired
    private ScreenArrangeMapper screenArrangeMapper;

    @Autowired
    private CinemaManager cinemaManager;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private MovieCastManager movieCastManager;

    @Autowired
    private ScreenRoomMapper screenRoomMapper;

    @Autowired
    private CinemaScreenManager cinemaScreenManager;

    @Autowired
    private MovieDetailMapper movieDetailMapper ;

    @Autowired
    private ScreenSeatManager screenSeatManager;

    @Autowired
    private TicketDetailManager ticketDetailManager;
    /**
     *  查找影院 by id
     * @param cinemaId
     * @return
     */
    public CinemaVo searchCinemaById(String cinemaId){

        CinemaDo cinemaDo = cinemaManager.getById(cinemaId);

        if (Objects.isNull(cinemaDo)){
            log.info("未找到对应id 下的 影院：-- " + cinemaId);
        }

        return ConvertUtil.convert(cinemaDo,CinemaVo.class);
    }

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
     * 删除影院 by id
     * @param cinemaId
     */
    public Boolean deleteCinemaById(Integer cinemaId){

        // 当前删除的影院是否有排片记录  查询cinemaScreen --> 得到id --> 通过Ids 查询screenArrange -->
        List<CinemaScreenDo> cinemaScreens = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                .eq("cinema_id", cinemaId));

        List<Integer> cinemaScreenIds = cinemaScreens.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());

        if (Objects.nonNull(cinemaScreenIds) && cinemaScreenIds.size() != 0){
            List<ScreenArrangeDo> cinemaArranges = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .in("cinema_screen_id", cinemaScreenIds));


            if (Objects.nonNull(cinemaArranges) && cinemaArranges.size() != 0){
                // 当前要删除的影院有排片记录 需要先解绑排片记录
                throw new BizException("当前要删除的影院含有排片记录，请先将排片记录清楚再删除");
            }else{

                // 没有排片记录 可以正常删除

                // 删除 影院
                boolean removeById = cinemaManager.removeById(cinemaId);

                // 删除 影院和放映厅的绑定记录
                boolean cinemaScreenDels = cinemaScreenManager.remove(new QueryWrapper<CinemaScreenDo>()
                        .eq("cinema_id", cinemaId));

                // 相关联的 坐席 的 删除 应该是在订单呢完成后  应该有一个定时任务 对完成的排片进行自动清除 坐席和订单

                return removeById && cinemaScreenDels;
            }

        }else{

            // 没有和放映厅相关的记录 可以直接删除
            // 删除 影院
            boolean removeById = cinemaManager.removeById(cinemaId);

            return removeById;
        }


    }

    /**
     * 添加影院
     * @param cinemaAddPo
     * @return
     */
    @Transactional
    public Boolean cinemaAdd(CinemaAddPo cinemaAddPo){

        CinemaDo cinemaDo = new CinemaDo();

        cinemaDo.setPhone(cinemaAddPo.getPhone());
        cinemaDo.setImage(cinemaAddPo.getImage());
        cinemaDo.setCinemaAreaId(cinemaAddPo.getAreaId());
        cinemaDo.setCinemaName(cinemaAddPo.getCinemaName());
        cinemaDo.setCinemaFullAddress(cinemaAddPo.getCinemaFullAddress());

        //查询 地区所在的 县市区

        Area area = areaMapper.selectByAreaId(cinemaAddPo.getAreaId());

        City city = areaMapper.selectByCityId(area.getFatherId());

        Province province = areaMapper.selectByProvinceId(city.getFatherId());

        cinemaDo.setCinemaAreaFullName(province.getProvince()+city.getCity()+area.getArea());


        if (Objects.nonNull(cinemaAddPo.getId())){

            cinemaDo.setId(cinemaAddPo.getId());

            // 不为空 是 修改  重名校验 地址校验
            CinemaDo cinemaAddr = cinemaManager.getOne(new QueryWrapper<CinemaDo>()
                    .eq("cinema_full_address", cinemaAddPo.getCinemaFullAddress())
                    .eq("cinema_area_id",cinemaAddPo.getAreaId())
                    .ne("id", cinemaAddPo.getId()));

            if (Objects.nonNull(cinemaAddr)){
                    throw new BizException("同一位置上不能出现两个不同的影院");
            }


            // 修改
            return cinemaManager.updateById(cinemaDo);

        }else{
            // 重名校验
            CinemaDo cinema = cinemaManager.getOne(new QueryWrapper<CinemaDo>()
                    .eq("cinema_full_address", cinemaAddPo.getCinemaFullAddress())
            .eq("cinema_area_id",cinemaAddPo.getAreaId()));

            if (Objects.nonNull(cinema)){
                throw new BizException("同一位置上不能出现两个不同的影院");
            }
            return cinemaManager.save(cinemaDo);
        }





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


            // 然后通过这些id 回去查找对应的放映厅 不能被删除  在有记录的情况下
            if (Objects.nonNull(hasArrangedIds) && hasArrangedIds.size() > 0){
                List<CinemaScreenDo> cinemaScreenDos = (List)cinemaScreenManager.listByIds(hasArrangedIds);

                if (Objects.nonNull(cinemaScreenDos) && cinemaScreenDos.size() > 0){
                    throw new BizException("下列放映厅在该影院有排片，无法删除："+Strings.join(cinemaScreenDos.stream().map(CinemaScreenDo::getScreenHallId).collect(Collectors.toList()), ','));
                }
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
        screenArrangeDo.setLanguage(movieArrangePo.getLanguage());
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


    /**
     * 不分页 搜索地域下的 所有影院
     * @param areaId
     * @return
     */
    public List<CinemaVo> findAllCinemasAreaId(Integer areaId){

        List<CinemaDo> cinemaDos = cinemaManager.list(new QueryWrapper<CinemaDo>()
                .eq("cinema_area_id", areaId));

        return ConvertUtil.listConvert(cinemaDos,CinemaVo.class);
    }

    /**
     *   搜索影院 by  areaId
     * @param cinemaSearchPo
     * @return
     */
    public Page<CinemaVo> searchCinemas(CinemaSearchPo cinemaSearchPo){

        if (Strings.isNotBlank(cinemaSearchPo.getMovieId())){
            // 如果电影id 不为空  查找有排片记录的影院 ids
            List<ScreenArrangeDo> screenArrangeDos = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .eq("movie_id", cinemaSearchPo.getMovieId()));

            if (Objects.isNull(screenArrangeDos) || screenArrangeDos.size() == 0){
                return PageUtils.emptyPage(cinemaSearchPo,new Page());
            }

            // 不为空  提取排片cinemaIds;
            List<Integer> cinemaAndScreenIds = screenArrangeDos.stream().map(ScreenArrangeDo::getCinemaScreenId).distinct().collect(Collectors.toList());

            // 查找影院 将Ids 放入cinemaSearchPo参数中
            List<CinemaScreenDo> cinemaScreenDos = cinemaScreenManager.listByIds(cinemaAndScreenIds).stream().collect(Collectors.toList());

            if (Objects.isNull(cinemaScreenDos) || cinemaScreenDos.size() == 0){
                return PageUtils.emptyPage(cinemaSearchPo,new Page());
            }

            List<Integer> cinemaIds = cinemaScreenDos.stream().map(CinemaScreenDo::getCinemaId).distinct().collect(Collectors.toList());

            if (Objects.nonNull(cinemaIds) && cinemaIds.size() > 0){
                // 加入到查询条件
                cinemaSearchPo.setCinemaIds(cinemaIds);
            }
        }




        Page<CinemaDo> cinemaDoPage = cinemaManager.getBaseMapper().searchCinemaPage(cinemaSearchPo);


        if (cinemaDoPage != null && cinemaDoPage.getRecords() != null){
            return ConvertUtil.pageConvert(cinemaDoPage,CinemaVo.class);
        }

        return new Page<>();
    }


    /**
     * 查找排片电影记录 by cinemaId   重新定义返回的
     * @param cinemaId
     * @return
     */
    public List<MovieArgUnderCinemaVo> cinemaUnderMovies(String cinemaId){

        // 先查找 对应的 cinema+screen 的 ids
        List<CinemaScreenDo> cinemaScreenDoList = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                .eq("cinema_id", cinemaId));

        if (Objects.isNull(cinemaScreenDoList) || cinemaScreenDoList.size() == 0){
            return Lists.newArrayList();
        }

        // 得到 排片id 对应的 放映厅id --> 得到放映厅名称
        Map<Integer, Integer> ArrangeIdAndScreenIdMap = cinemaScreenDoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getScreenHallId()));

        // 得到放映厅ids
        List<Integer> screenHallIds = cinemaScreenDoList.stream().map(CinemaScreenDo::getScreenHallId).distinct().collect(Collectors.toList());

        //得到放映厅 名称map
        List<ScreenRoomDo> screenRoomDoList = screenRoomMapper.selectList(new QueryWrapper<ScreenRoomDo>()
                .in("id", screenHallIds));

        Map<Integer, String> idAndNameMaps = screenRoomDoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getScreeningHallName()));

        // 最终得到的 id --> 放映厅名称Maps
        Map<Integer,String> screenNameMaps = Maps.newHashMap();

        //得到 排片id --> 放映厅名称
        for (Map.Entry<Integer, Integer> entry : ArrangeIdAndScreenIdMap.entrySet()) {
            screenNameMaps.put(entry.getKey(),idAndNameMaps.get(entry.getValue()));
        }

        // 得到 排片的id
        List<Integer> cinemaScreenIds = cinemaScreenDoList.stream().map(e -> e.getId()).collect(Collectors.toList());

        //  查找对应的排片的记录
        List<ScreenArrangeDo> movieArranges = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                .in("cinema_screen_id", cinemaScreenIds)
                .gt("time_scope_start",LocalDateTimeUtils.formatLocalDateTime(LocalDateTime.now(),LocalDateTimeUtils.DATE_TIME)));

        if (Objects.isNull(movieArranges) || movieArranges.size() == 0){
            return Lists.newArrayList();
        }


        // 记录排片信息，相同的movie_id 放入一起
        Map<String,List<ScreenArrangeDo>> movieArrangeMaps = Maps.newHashMap();

        movieArranges.stream()
                .forEach(e -> {

                    if (Objects.isNull(movieArrangeMaps.get(e.getMovieId()))){
                        movieArrangeMaps.put(e.getMovieId(),Lists.newArrayList());
                    }
                    movieArrangeMaps.get(e.getMovieId()).add(e);
                });

        List<String> movieIds = movieArranges.stream().map(ScreenArrangeDo::getMovieId).distinct().collect(Collectors.toList());


        // 查找所有的 movieIds 的 电影信息
        List<MovieDetailDo> movieInfos = movieDetailMapper.selectList(new QueryWrapper<MovieDetailDo>()
                .in("movie_id", movieIds));


        Map<String, MovieDetailDo> movieInfoMaps = movieInfos.stream().collect(Collectors.toMap(MovieDetailDo::getMovieId, e -> e));



        //根据排片记录 来补足返回的信息
        List<MovieArgUnderCinemaVo> result = Lists.newArrayList();

        for (Map.Entry<String, List<ScreenArrangeDo>> entry : movieArrangeMaps.entrySet()) {

            // 电影基本信息
            MovieArgUnderCinemaVo movieArgUnderCinemaVo = ConvertUtil.convert(movieInfoMaps.get(entry.getKey()), MovieArgUnderCinemaVo.class);

            // 根据主演ids 查找主演名称
            String[] casts = movieArgUnderCinemaVo.getCasts().split(",");

            List<MovieCastDo> castDoList = movieCastManager.list(new QueryWrapper<MovieCastDo>()
                    .in("actor_id", casts));

            movieArgUnderCinemaVo.setCastList(castDoList);

            movieArgUnderCinemaVo.setMovieArrangeList(Lists.newArrayList());

            // 场次信息
            for (ScreenArrangeDo screenArrangeDo : entry.getValue()) {

                MovieArrangeVo movieArrangeVo = ConvertUtil.convert(screenArrangeDo, MovieArrangeVo.class);
                // 找 放映厅名称
                movieArrangeVo.setScreeningHallName(screenNameMaps.get(movieArrangeVo.getCinemaScreenId()));

                movieArgUnderCinemaVo.getMovieArrangeList().add(movieArrangeVo);
            }

            result.add(movieArgUnderCinemaVo);
        }

        return result;
    }


    /**
     * 排片的详细信息 电影 影院 放映厅 等
     * @param arrangeId
     * @return
     */
    public MovieArrangeDetailVo arrangeDetails(String arrangeId){

        // 排片记录
        ScreenArrangeDo screenArrangeRecord = screenArrangeMapper.selectOne(new QueryWrapper<ScreenArrangeDo>()
                .eq("id", arrangeId));

        if (Objects.isNull(screenArrangeRecord)){
            log.info("没有找到对应场次的排片记录 - " + arrangeId);
            throw new BizException("没有找到排片记录");
        }


        // 电影信息
        MovieDetailDo movieRecord = movieDetailMapper.selectOne(new QueryWrapper<MovieDetailDo>()
                .eq("movie_id", screenArrangeRecord.getMovieId()));


        // 影院信息 放映厅信息
        CinemaScreenDo cinemaScreenRecord = cinemaScreenManager.getOne(new QueryWrapper<CinemaScreenDo>()
                .eq("id", screenArrangeRecord.getCinemaScreenId()));

        CinemaDo cinemaRecord = cinemaManager.getOne(new QueryWrapper<CinemaDo>()
                .eq("id", cinemaScreenRecord.getCinemaId()));

        ScreenRoomDo screenRecord = screenRoomMapper.selectOne(new QueryWrapper<ScreenRoomDo>()
                .eq("id", cinemaScreenRecord.getScreenHallId()));

        // 语言 场次 单价


        MovieArrangeDetailVo movieArrangeDetailVo = new MovieArrangeDetailVo();
        movieArrangeDetailVo.setLanguage(screenArrangeRecord.getLanguage());
        movieArrangeDetailVo.setTimeScopeStart(screenArrangeRecord.getTimeScopeStart());
        movieArrangeDetailVo.setPrice(screenArrangeRecord.getPrice());

        movieArrangeDetailVo.setCinemaName(cinemaRecord.getCinemaName());
        movieArrangeDetailVo.setScreeningHallName(screenRecord.getScreeningHallName());

        movieArrangeDetailVo.setMovieId(movieRecord.getMovieId());
        movieArrangeDetailVo.setMovieName(movieRecord.getMovieName());
        movieArrangeDetailVo.setImage(movieRecord.getImage());
        movieArrangeDetailVo.setGenres(movieRecord.getGenres());
        movieArrangeDetailVo.setDuration(movieRecord.getDuration());

        movieArrangeDetailVo.setId(Integer.valueOf(arrangeId));


        return movieArrangeDetailVo;
    }


    /**
     * 排片的座位信息
     * @param arrangeId
     * @return
     */
    public List<ScreenSeatMapVo>  seatsInfo(String arrangeId){

        List<ScreenSeatDo> screenSeatDos = screenSeatManager.list(new QueryWrapper<ScreenSeatDo>()
                .eq("screen_arrange_id", arrangeId));


        if (Objects.isNull(screenSeatDos)){
            return Lists.newArrayList();
        }

        List<ScreenSeatVo> screenSeatVos = ConvertUtil.listConvert(screenSeatDos, ScreenSeatVo.class);

        // 转换成map
        Map<Integer,List<ScreenSeatVo>> seatsMaps = Maps.newHashMap();

        for (ScreenSeatVo screenSeatVo : screenSeatVos) {
            if (Objects.isNull(seatsMaps.get(screenSeatVo.getScreeningHallX()))){
                seatsMaps.put(screenSeatVo.getScreeningHallX(),Lists.newArrayList());
            }
            seatsMaps.get(screenSeatVo.getScreeningHallX()).add(screenSeatVo);
        }

        List<ScreenSeatMapVo> result = Lists.newArrayList();


        for (Map.Entry<Integer, List<ScreenSeatVo>> integerListEntry : seatsMaps.entrySet()) {

            ScreenSeatMapVo screenSeatMapVo = new ScreenSeatMapVo();

            screenSeatMapVo.setScreeningHallX(integerListEntry.getKey());
            screenSeatMapVo.setSeatRowList(integerListEntry.getValue());

            result.add(screenSeatMapVo);
        }

        //排序

        return result
                .stream()
                .sorted(Comparator.comparing(ScreenSeatMapVo::getScreeningHallX))
                .collect(Collectors.toList());
    }



    /**
     * 搜索排片记录
     * @param movieArgBackPo
     * @return
     */
    public Page<MovieArgVo> arrangeRecords(MovieArgBackPo movieArgBackPo){


        // 前端传的时间为 时间范围  2019-05-01 T 2019-06-05  格式 用T 来分割  去除空格

        //  如果时间不为空 查时间 的排片记录


        // 如果电影不为空 查询电影的ids


        // 如果影院不为空 查询影院的ids

        // 如果时间和电影不为空

        // 如果时间和影院不为空

        // 如果都不为空 查询在这些影院下的记录 根据这些记录 再去筛选在ids 中的 和 时间内的
        if (!Strings.isBlank(movieArgBackPo.getArrangeDate()) && !Strings.isBlank(movieArgBackPo.getCinemaName()) && !Strings.isBlank(movieArgBackPo.getMovieName())){

            // 如果都不为空  先找时间段

            String[] ts = movieArgBackPo.getArrangeDate().split("T");
            String startDate = ts[0].trim();
            String endDate = ts[1].trim();

            // 查找在时间范围内的排片记录  在内存中分页
            List<ScreenArrangeDo> arrangeDates
                    = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .between("arrange_date", startDate, endDate));

            if (Objects.isNull(arrangeDates) || Objects.isNull(arrangeDates) || arrangeDates.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 模糊查询影院
            List<CinemaDo> cinemas = cinemaManager.list(new QueryWrapper<CinemaDo>()
                    .like("cinema_name", movieArgBackPo.getCinemaName()));

            if (Objects.isNull(cinemas) || cinemas.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 模糊查询电影
            List<MovieDetailDo> movies = movieDetailMapper.selectList(new QueryWrapper<MovieDetailDo>()
                    .like("movie_name", movieArgBackPo.getMovieName()));

            if (Objects.isNull(movies) || movies.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 查询 影院+ 放映厅
            List<CinemaScreenDo> cinemaScreenDos
                        = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                        .in("cinema_id", cinemas.stream().map(CinemaDo::getId).collect(Collectors.toList())));

            if (Objects.isNull(cinemaScreenDos) || cinemaScreenDos.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 因为三个条件都要满足 所以一个为空 都为空

            // 三个条件都可以查找到

            // 合并条件 arrangeDates 时间范围内 找到 对应的记录  因为涉及到两个In 所以查询 应该是查询 两个 not in

            List<ScreenArrangeDo> matchResult = Lists.newArrayList();

            List<Integer> cinemaScreenIds = cinemaScreenDos.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());

            List<String> movieIds = movies.stream().map(MovieDetailDo::getMovieId).collect(Collectors.toList());
            for (Integer cinemaScreenId : cinemaScreenIds) {

                for (ScreenArrangeDo record : arrangeDates) {

                    for (String movieId : movieIds) {

                        if (record.getMovieId().equals(movieId) && record.getCinemaScreenId().equals(cinemaScreenId)){
                            matchResult.add(record);
                        }

                    }


                }

            }
            List<MovieArgVo> resultList = Lists.newArrayList();
            for (ScreenArrangeDo record : matchResult) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);
            }

            // 这是所有的 并没有分页  进行分页  代码内分页

            return PageUtils.codePage(resultList,movieArgBackPo);

        }else if (!Strings.isBlank(movieArgBackPo.getArrangeDate()) && !Strings.isBlank(movieArgBackPo.getCinemaName())){

            // 电影名为空

            String[] ts = movieArgBackPo.getArrangeDate().split("T");
            String startDate = ts[0].trim();
            String endDate = ts[1].trim();

            // 查找在时间范围内的排片记录  在内存中分页
            List<ScreenArrangeDo> arrangeDates
                    = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .between("arrange_date", startDate, endDate));

            if (Objects.isNull(arrangeDates) || Objects.isNull(arrangeDates) || arrangeDates.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 模糊查询影院
            List<CinemaDo> cinemas = cinemaManager.list(new QueryWrapper<CinemaDo>()
                    .like("cinema_name", movieArgBackPo.getCinemaName()));

            if (Objects.isNull(cinemas) || cinemas.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 查询 影院+ 放映厅
            List<CinemaScreenDo> cinemaScreenDos
                    = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                    .in("cinema_id", cinemas.stream().map(CinemaDo::getId).collect(Collectors.toList())));

            if (Objects.isNull(cinemaScreenDos) || cinemaScreenDos.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            List<ScreenArrangeDo> matchResult = Lists.newArrayList();

            List<Integer> cinemaScreenIds = cinemaScreenDos.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());


            for (Integer cinemaScreenId : cinemaScreenIds) {

                for (ScreenArrangeDo record : arrangeDates) {

                    if (record.getCinemaScreenId().equals(cinemaScreenId)){
                        matchResult.add(record);
                    }

                }

            }
            List<MovieArgVo> resultList = Lists.newArrayList();
            for (ScreenArrangeDo record : matchResult) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);
            }

            // 这是所有的 并没有分页  进行分页  代码内分页

            return PageUtils.codePage(resultList,movieArgBackPo);

        }else if (!Strings.isBlank(movieArgBackPo.getCinemaName()) && !Strings.isBlank(movieArgBackPo.getMovieName())){

            // 日期为空


            // 模糊查询影院
            List<CinemaDo> cinemas = cinemaManager.list(new QueryWrapper<CinemaDo>()
                    .like("cinema_name", movieArgBackPo.getCinemaName()));

            if (Objects.isNull(cinemas) || cinemas.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 查询 影院+ 放映厅
            List<CinemaScreenDo> cinemaScreenDos
                    = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                    .in("cinema_id", cinemas.stream().map(CinemaDo::getId).collect(Collectors.toList())));

            if (Objects.isNull(cinemaScreenDos) || cinemaScreenDos.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            // 模糊查询电影
            List<MovieDetailDo> movies = movieDetailMapper.selectList(new QueryWrapper<MovieDetailDo>()
                    .like("movie_name", movieArgBackPo.getMovieName()));

            if (Objects.isNull(movies) || movies.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }


            // 查询 in cinema
            List<Integer> cinemaScreenIds = cinemaScreenDos.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());
            List<String> movieIds = movies.stream().map(MovieDetailDo::getMovieId).collect(Collectors.toList());

            List<ScreenArrangeDo>  screenArrangeDos = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
            .in("cinema_screen_id",cinemaScreenIds));


            List<ScreenArrangeDo> matchResult = Lists.newArrayList();

                for (ScreenArrangeDo record : screenArrangeDos) {

                    for (String movieId : movieIds) {
                        if (record.getMovieId().equals(movieId)){
                            matchResult.add(record);
                        }
                    }


                }

            List<MovieArgVo> resultList = Lists.newArrayList();
            for (ScreenArrangeDo record : matchResult) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);
            }

            // 这是所有的 并没有分页  进行分页  代码内分页

            return PageUtils.codePage(resultList,movieArgBackPo);

        }else if (!Strings.isBlank(movieArgBackPo.getArrangeDate()) && !Strings.isBlank(movieArgBackPo.getMovieName())){

            // 影院名为空

            String[] ts = movieArgBackPo.getArrangeDate().split("T");
            String startDate = ts[0].trim();
            String endDate = ts[1].trim();

            // 查找在时间范围内的排片记录  在内存中分页
            List<ScreenArrangeDo> arrangeDates
                    = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .between("arrange_date", startDate, endDate));

            if (Objects.isNull(arrangeDates) || Objects.isNull(arrangeDates) || arrangeDates.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }


            // 模糊查询电影
            List<MovieDetailDo> movies = movieDetailMapper.selectList(new QueryWrapper<MovieDetailDo>()
                    .like("movie_name", movieArgBackPo.getMovieName()));

            if (Objects.isNull(movies) || movies.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }

            List<ScreenArrangeDo> matchResult = Lists.newArrayList();

            List<String> movieIds = movies.stream().map(MovieDetailDo::getMovieId).collect(Collectors.toList());


                for (ScreenArrangeDo record : arrangeDates) {

                    for (String movieId : movieIds) {

                        if (record.getMovieId().equals(movieId)){
                            matchResult.add(record);
                        }

                    }
                }

            List<MovieArgVo> resultList = Lists.newArrayList();
            for (ScreenArrangeDo record : matchResult) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);
            }

            // 这是所有的 并没有分页  进行分页  代码内分页

            return PageUtils.codePage(resultList,movieArgBackPo);

        }else if (!Strings.isBlank(movieArgBackPo.getArrangeDate())){

            // 单条件 日期

            String[] ts = movieArgBackPo.getArrangeDate().split("T");
            String startDate = ts[0].trim();
            String endDate = ts[1].trim();

            Page<ScreenArrangeDo> arrangeDoPage = (Page<ScreenArrangeDo>)screenArrangeMapper.selectPage(movieArgBackPo, new QueryWrapper<ScreenArrangeDo>()
                    .between("arrange_date", startDate, endDate));

            // 转化成需要的
            List<ScreenArrangeDo> records = arrangeDoPage.getRecords();

            List<MovieArgVo> resultList = Lists.newArrayList();

            //  电影名 放映厅名 影院名

            for (ScreenArrangeDo record : records) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);

            }

            return PageUtils.page(resultList,arrangeDoPage);

        }else if (!Strings.isBlank(movieArgBackPo.getCinemaName())){

            // 单条件 影院名

            // 模糊查询影院
            List<CinemaDo> cinemas = cinemaManager.list(new QueryWrapper<CinemaDo>()
                    .like("cinema_name", movieArgBackPo.getCinemaName()));

            if (Objects.isNull(cinemas) || cinemas.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }


            // 查询 影院+ 放映厅
            List<CinemaScreenDo> cinemaScreenDos
                    = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                    .in("cinema_id", cinemas.stream().map(CinemaDo::getId).collect(Collectors.toList())));

            if (Objects.isNull(cinemaScreenDos) || cinemaScreenDos.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }


            // in
            List<Integer> cinemaScreenIds = cinemaScreenDos.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());
            Page<ScreenArrangeDo> arrangeDoPage = (Page<ScreenArrangeDo>)screenArrangeMapper.selectPage(movieArgBackPo,new QueryWrapper<ScreenArrangeDo>()
            .in("cinema_screen_id",cinemaScreenIds));

            // 转化成需要的
            List<ScreenArrangeDo> records = arrangeDoPage.getRecords();

            List<MovieArgVo> resultList = Lists.newArrayList();

            //  电影名 放映厅名 影院名

            for (ScreenArrangeDo record : records) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);

            }

            return PageUtils.page(resultList,arrangeDoPage);

        }else if (!Strings.isBlank(movieArgBackPo.getMovieName())){

            // 单条件 电影名


            // 模糊查询电影
            List<MovieDetailDo> movies = movieDetailMapper.selectList(new QueryWrapper<MovieDetailDo>()
                    .like("movie_name", movieArgBackPo.getMovieName()));

            if (Objects.isNull(movies) || movies.size() == 0){
                return PageUtils.emptyPage(movieArgBackPo,new Page());
            }


            // 电影ids
            List<String> movieIds = movies.stream().map(MovieDetailDo::getMovieId).collect(Collectors.toList());
            Page<ScreenArrangeDo> arrangeDoPage = (Page<ScreenArrangeDo>)screenArrangeMapper.selectPage(movieArgBackPo,new QueryWrapper<ScreenArrangeDo>()
                    .in("movie_id",movieIds));

            // 转化成需要的
            List<ScreenArrangeDo> records = arrangeDoPage.getRecords();

            List<MovieArgVo> resultList = Lists.newArrayList();

            //  电影名 放映厅名 影院名

            for (ScreenArrangeDo record : records) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);

            }

            return PageUtils.page(resultList,arrangeDoPage);

        }else{

            // 都为空

            // 直接查询所有的

            Page<ScreenArrangeDo> screenArrangeDoPage = (Page<ScreenArrangeDo>)screenArrangeMapper.selectPage(movieArgBackPo, null);


            // 转化成需要的
            List<ScreenArrangeDo> records = screenArrangeDoPage.getRecords();

            List<MovieArgVo> resultList = Lists.newArrayList();

            //  电影名 放映厅名 影院名

            for (ScreenArrangeDo record : records) {

                MovieArgVo convert = ConvertUtil.convert(record, MovieArgVo.class);

                MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(record.getMovieId());

                CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(record.getCinemaScreenId());

                CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

                ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


                convert.setCinemaName(cinema.getCinemaName());

                convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

                convert.setMovieName(movieDetailDo.getMovieName());

                resultList.add(convert);

            }

            return PageUtils.page(resultList,screenArrangeDoPage);

        }

    }

    /**
     * 通过id 来查找排片信息 -- 后台
     * @param id
     * @return
     */
    public MovieArgVo arrangeRecordById(Integer id){

        ScreenArrangeDo screenArrangeDo = screenArrangeMapper.selectById(id);

        MovieArgVo convert = ConvertUtil.convert(screenArrangeDo, MovieArgVo.class);

        MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(screenArrangeDo.getMovieId());

        CinemaScreenDo cinemaScreenDo = cinemaScreenManager.getById(screenArrangeDo.getCinemaScreenId());

        CinemaDo cinema = cinemaManager.getById(cinemaScreenDo.getCinemaId());

        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(cinemaScreenDo.getScreenHallId());


        convert.setCinemaName(cinema.getCinemaName());

        convert.setScreeningHallName(screenRoomDo.getScreeningHallName());

        convert.setMovieName(movieDetailDo.getMovieName());

        convert.setMovieId(movieDetailDo.getMovieId());

        return convert;

    }


    /**
     * 删除排片记录
     * @param arrangeId
     * @return
     */
    public Boolean deleteArrangeRecord(Integer arrangeId){

        // 主要 是 看当前排片记录 是否已经售票了 如果已经售票了 则不允许删除
        ScreenArrangeDo screenArrangeDo = screenArrangeMapper.selectById(arrangeId);

        if (Objects.isNull(screenArrangeDo)){
            throw new BizException("记录不存在");
        }

        //  查找是否坐席已经出售了
        List<ScreenSeatDo> screenSeatDos = screenSeatManager.list(new QueryWrapper<ScreenSeatDo>()
                .eq("screen_arrange_id", arrangeId)
                .eq("is_purchased",1));

        if (Objects.nonNull(screenSeatDos) && screenSeatDos.size() != 0){
            throw new BizException("当前排片已经出售了，请先取消相关订单再操作");
        }


        // 当前坐席没有出售 可以删除

        // 先删除相关的坐席记录
        boolean removeSeats = screenSeatManager.remove(new QueryWrapper<ScreenSeatDo>()
                .eq("screen_arrange_id", arrangeId));

        // 在删除排片记录
        int deleteArrange = screenArrangeMapper.deleteById(arrangeId);

        return removeSeats && 1 == deleteArrange;
    }


    /**
     * 跟新排片记录 已售票无法修改
     * @param movieArrangePo
     * @return
     */
    public Boolean updateArrangeInfo(MovieArrangePo movieArrangePo){

        // 查看是否 有售票
        List<ScreenSeatDo> screenArrangeDos = screenSeatManager.list(new QueryWrapper<ScreenSeatDo>()
                .eq("screen_arrange_id", movieArrangePo.getId())
                .eq("is_purchased",1));

        if (Objects.nonNull(screenArrangeDos) && screenArrangeDos.size() != 0){
            throw new BizException("当前排片记录有售票订单记录，请取消后再操作");
        }

        // 没有售票 可以修改

        // 查询当前的排片信息 未修改的记录 因为影院和放映厅不修改
        ScreenArrangeDo screenArrangeDo1 = screenArrangeMapper.selectById(movieArrangePo.getId());

        if (Objects.isNull(screenArrangeDo1)){
            throw new BizException("当前记录不存在");
        }


        // 得到修改后的 时间安排 看是否和其他的 排片有冲突


        /**
         * 逻辑
         *
         * 主要是解决 排片时间 上的 冲突
         */

        // 查询 当前日期 下的 当前影院放映厅的 排片情况
        List<ScreenArrangeDo> screenArrangeDateDos = screenArrangeMapper.selectByDateAndHallId(screenArrangeDo1.getCinemaScreenId(), movieArrangePo.getDate());

        // 查询 当前排片的 电影时长
        MovieDetailDo movieDetailDo = movieDetailMapper.selectByMovieId(movieArrangePo.getMovieId());

        if (null == movieDetailDo){
            throw new BizException("排片电影不存在，请重新选择");
        }


        LocalDateTime startTime = LocalDateTimeUtils.getLocalDateTimeFromStr(movieArrangePo.getStartTime(), LocalDateTimeUtils.DATE_TIME);
        Long movieDurationFormat = LocalDateTimeUtils.getMovieDurationFormat(movieDetailDo.getDuration());

        LocalDateTime endTime = startTime.plusMinutes(movieDurationFormat);

        // 遍历 看是否 开始时间 和 结束时间+20 在  已经排片的记录 (start-end) 中
        for (ScreenArrangeDo screenArrangeDo : screenArrangeDateDos) {

            // 不同于添加 跟新 要跳过自己 另外 跟新 由于不能跟新 影院和放映厅 所以无设计到坐席的变更
            if (screenArrangeDo.getId().equals(movieArrangePo.getId())){
                continue;
            }
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


        // 修改后的 时间上也不冲突 正常修改

        ScreenArrangeDo screenArrangeDo = ConvertUtil.convert(movieArrangePo, ScreenArrangeDo.class);
        screenArrangeDo.setArrangeDate(movieArrangePo.getDate());
        screenArrangeDo.setTimeScopeStart(movieArrangePo.getStartTime());
        screenArrangeDo.setTimeScopeEnd(LocalDateTimeUtils.formatLocalDateTime(endTime,LocalDateTimeUtils.DATE_TIME));

        int update = screenArrangeMapper.updateById(screenArrangeDo);

        return 1 == update;
    }

    // 统计截止当前的各个省份下的影院数 name + value
    public List<List<MapVo>> selectCinemaCountsProvs(){

        // return map
        List<List<MapVo>> returnList = Lists.newArrayList();
        List<MapVo> cinemaMapList = Lists.newArrayList();
        List<MapVo> orderMapList = Lists.newArrayList();
        List<MapVo> priceMapList = Lists.newArrayList();


        // 查找所有的省份
        List<Province> provinces = areaMapper.selectProvincesByName(null);

        // 影院是以地区为单位的， 找到省份下的地区影院数

        for (Province province : provinces) {

            String provinceName = ProvinceShortEnum.getProvinceEnum(province.getProvince()).getShortname();

            List<City> cities = areaMapper.selectUnderProvinceCitys(province.getProvinceId());

            // 得到在这些城市下的地区  通过地区来搜索地区下的影院数
            List<Integer> citys = cities.stream().map(City::getCityId).collect(Collectors.toList());
            if (Objects.isNull(citys) || citys.size() == 0){
                MapVo mapVo = new MapVo();
                mapVo.setName(provinceName);
                mapVo.setValue(new BigDecimal(0));
                cinemaMapList.add(mapVo);
                orderMapList.add(mapVo);
                priceMapList.add(mapVo);
                continue;
            }
            List<Area> areas = areaMapper.selectAreasUnderCities(citys);

            if (Objects.isNull(areas) || areas.size() == 0){
                MapVo mapVo = new MapVo();
                mapVo.setName(provinceName);
                mapVo.setValue(new BigDecimal(0));
                cinemaMapList.add(mapVo);
                orderMapList.add(mapVo);
                priceMapList.add(mapVo);
                continue;
            }
            // 查找在这些 地区下的影院数量
            List<CinemaDo> cinemas = cinemaManager.list(new QueryWrapper<CinemaDo>()
                    .in("cinema_area_id", areas.stream().map(Area::getAreaId).collect(Collectors.toList())));

            if (Objects.isNull(cinemas) || cinemas.size() == 0){
                MapVo mapVo = new MapVo();
                mapVo.setName(provinceName);
                mapVo.setValue(new BigDecimal(0));
                cinemaMapList.add(mapVo);
                orderMapList.add(mapVo);
                priceMapList.add(mapVo);
            }else{

                // 数量
                MapVo mapVo = new MapVo();
                mapVo.setName(provinceName);
                mapVo.setValue(new BigDecimal(cinemas.size()));
                cinemaMapList.add(mapVo);

                // 订单数
                List<TicketDetailDo> ticketDetailDoList = ticketDetailManager.list(new QueryWrapper<TicketDetailDo>()
                        .eq("status", 2)
                        .in("cinema_id", cinemas.stream().map(CinemaDo::getId).collect(Collectors.toList())));

                if (Objects.isNull(ticketDetailDoList) || ticketDetailDoList.size() == 0){
                    MapVo emptyMapVo = new MapVo();
                    emptyMapVo.setName(provinceName);
                    emptyMapVo.setValue(new BigDecimal(0));
                    orderMapList.add(emptyMapVo);
                    priceMapList.add(emptyMapVo);
                }else{
                    // 交易总额
                    MapVo orderMap = new MapVo();
                    orderMap.setName(provinceName);
                    orderMap.setValue(new BigDecimal(ticketDetailDoList.size()));
                    orderMapList.add(orderMap);

                    BigDecimal total = new BigDecimal(0);

                    for (TicketDetailDo ticketDetailDo : ticketDetailDoList) {
                        total = total.add(ticketDetailDo.getPrice());
                    }


                    MapVo priceMap = new MapVo();
                    priceMap.setName(provinceName);
                    priceMap.setValue(total);
                    priceMapList.add(priceMap);
                }

            }

        }

        returnList.add(cinemaMapList);
        returnList.add(orderMapList);
        returnList.add(priceMapList);

        return returnList;

    }


    /**
     * 三个变量  一个是柱状图的坐标轴 一个是坐标轴的 名称和数值的对应数组 还有一个是饼图的 影院省份分布
     */
    public PercentPaneVo percentTen(){


        // 最终返回
        PercentPaneVo percentPaneVo = new PercentPaneVo();

        // 获取交易额前十的影院
        List<TicketDetailDo> ticketDetailDos = ticketDetailManager.getBaseMapper().percentTen();

        if (Objects.isNull(ticketDetailDos) || ticketDetailDos.size() == 0){
            return new PercentPaneVo();
        }

        // 获取对应的名字列表
        List<String> cinemaNames = ticketDetailDos.stream().map(TicketDetailDo::getCinema).collect(Collectors.toList());

        List<Integer> cinemaIds = ticketDetailDos.stream().map(TicketDetailDo::getCinemaId).collect(Collectors.toList());
        //获取id列表

        // 获取影院所在的地区省份
        List<CinemaDo> cinemaDos = (List<CinemaDo>)cinemaManager.listByIds(cinemaIds);

        // 转换成地区 + 影院的 map

        // 地区id 对应 省份id

        // 最后是要 省份 和 影院数量

        List<Integer> areaIds = cinemaDos.stream().map(CinemaDo::getCinemaAreaId).collect(Collectors.toList());

        // 统计相同areaId 出现次数
        Map<Integer,Integer> areaCount = Maps.newHashMap();

        for (Integer areaId : areaIds) {
            if (null == areaCount.get(areaId)){
                areaCount.put(areaId,0);
            }
            areaCount.put(areaId,areaCount.get(areaId) + 1);

        }

        List<Area> areas = areaMapper.selectAreasByIds(areaIds);

        List<City> cities = areaMapper.selectCitysByIds(areas.stream().map(Area::getFatherId).distinct().collect(Collectors.toList()));

        // 查询city 上的省份
        List<Province> provinces = areaMapper.selectProvincesByIds(cities.stream().map(City::getFatherId).distinct().collect(Collectors.toList()));

        // 城市和的对应关系map
        Map<Integer,Integer> proAndCityMaps = Maps.newHashMap();

        // 城市下的地区影院数
        Map<Integer,Integer> underCityAreaCounts = Maps.newHashMap();

        // 数量 - 》 城市影院数量
        for (Area area : areas) {
            if (null == underCityAreaCounts.get(area.getFatherId())){
                underCityAreaCounts.put(area.getFatherId(),areaCount.get(area.getAreaId()));
            }
        }


        // 省份 影院数量
        for (City city : cities) {
            if (null == proAndCityMaps.get(city.getFatherId())){
                proAndCityMaps.put(city.getFatherId(),underCityAreaCounts.get(city.getCityId()));
            }
        }


        // 省份id 换成 省份名称
        Map<Integer, String> provinceMap = provinces.stream().collect(Collectors.toMap(Province::getProvinceId, Province::getProvince));




        //  ticketDetailDos; 交易额前十影院

        //  cinemaNames;影院名称

        //proAndCityMaps;  各个影院的省份占比


        percentPaneVo.setTimeNames(cinemaNames);

        List<MapVo> cinemaHistogramMap = Lists.newArrayList();
        for (TicketDetailDo ticketDetailDo : ticketDetailDos) {

            MapVo mapVo = new MapVo();
            mapVo.setName(ticketDetailDo.getCinema());
            mapVo.setValue(ticketDetailDo.getPrice());
            cinemaHistogramMap.add(mapVo);
        }
        percentPaneVo.setHistogramMap(cinemaHistogramMap);


        // 省份占比
        List<MapVo> provincePercentMap = Lists.newArrayList();
        for (Map.Entry<Integer, Integer> integerIntegerEntry : proAndCityMaps.entrySet()) {
            MapVo mapVo = new MapVo();
            mapVo.setName(provinceMap.get(integerIntegerEntry.getKey()));
            mapVo.setValue(new BigDecimal(integerIntegerEntry.getValue()));
            provincePercentMap.add(mapVo);
        }

        percentPaneVo.setPaneMap(provincePercentMap);

        return percentPaneVo;
    }
}
