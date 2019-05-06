package com.mingkai.mediamanagesysbackend.service.screen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenRoomAddPo;
import com.mingkai.mediamanagesyscommon.manager.CinemaScreenManager;
import com.mingkai.mediamanagesyscommon.manager.ScreenSeatManager;
import com.mingkai.mediamanagesyscommon.mapper.MovieDetailMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenSeatMapper;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaScreenDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.ScreenPagePo;
import com.mingkai.mediamanagesyscommon.model.Vo.screen.ScreenRoomVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 放映厅相关service
 * @author: Created by 云风 on 2019-04-03 17:42
 */
@Service
public class ScreenService {

    @Autowired
    private CinemaScreenManager cinemaScreenManager;

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
     * 通过id 查询放映厅
     * @param id
     * @return
     */
    public ScreenRoomVo queryScreenById(Integer id){
        ScreenRoomDo screenRoomDo = screenRoomMapper.selectById(id);

        if (Objects.isNull(screenRoomDo)){
            throw new BizException("记录不存在");
        }

        return null == screenRoomDo ? null : ConvertUtil.convert(screenRoomDo, ScreenRoomVo.class);
    }

    /**
     *  分页查询 放映厅  模糊查询
     * @param screenPagePo
     * @return
     */
    public Page<ScreenRoomVo> queryScreenRoomPage(ScreenPagePo screenPagePo){

        Page<ScreenRoomDo> screenRoomDoPage = screenRoomMapper.queryScreensPage(screenPagePo);

        return ConvertUtil.pageConvert(screenRoomDoPage,ScreenRoomVo.class);

    }

    /**
     *  添加放映厅
     * @param screenRoomAddPo
     * @return
     */
    @Transactional
    public Boolean addScreenRoom(ScreenRoomAddPo screenRoomAddPo){

        ScreenRoomDo screenRoomDo = new ScreenRoomDo();

        screenRoomDo.setScreeningHallName(screenRoomAddPo.getScreeningHallName());
        screenRoomDo.setScreeningHallX(screenRoomAddPo.getScreeningHallX());
        screenRoomDo.setScreeningHallY(screenRoomAddPo.getScreeningHallY());

        // 新增？ 跟新  跟新要看是否有排片记录 如果有 则不能修改
        Integer id = screenRoomAddPo.getId();

        if (Objects.isNull(id)){

            // 新增

            //重名校验
            Integer repeatCount = screenRoomMapper.selectRepeatNameCount(screenRoomAddPo.getScreeningHallName());

            if (0 != repeatCount){
                throw new BizException("存在重名放映厅");
            }

            int insert = screenRoomMapper.insert(screenRoomDo);

            return insert == 1 ? true : false;


        }else{

            // 查看是否有排片记录
            Boolean arranged = isArranged(id);

            if (arranged){
                throw new BizException("当前放映厅存在排片记录，无法跟新");
            }

            screenRoomDo.setId(id);
            // 跟新
            List<ScreenRoomDo> screenRoomDos = screenRoomMapper.selectList(new QueryWrapper<ScreenRoomDo>()
                    .eq("screening_hall_name", screenRoomDo.getScreeningHallName())
                    .ne("id", screenRoomDo.getId()));

            if (Objects.nonNull(screenRoomDos) && screenRoomDos.size() != 0){
                    throw new BizException("存在重名放映厅");
            }else{

                // 执行跟新
                int updateCount = screenRoomMapper.updateById(screenRoomDo);

                return 1 == updateCount;
            }

        }


    }


    /**
     * 查找当前放映厅是否排片
     * @param id
     * @return
     */
    public Boolean isArranged(Integer id){

        List<CinemaScreenDo> cinemaScreenRels = cinemaScreenManager.list(new QueryWrapper<CinemaScreenDo>()
                .eq("screen_hall_id", id));

        if (Objects.isNull(cinemaScreenRels) || cinemaScreenRels.size() == 0){
            // 没有影院关联
            return Boolean.FALSE;
        }else{

            // 有影院关联  是否排片
            List<Integer> cinemaScreenIds = cinemaScreenRels.stream().map(CinemaScreenDo::getId).collect(Collectors.toList());


            List<ScreenArrangeDo> arrangeRecords = screenArrangeMapper.selectList(new QueryWrapper<ScreenArrangeDo>()
                    .in("cinema_screen_id", cinemaScreenIds));

            if (Objects.isNull(arrangeRecords) || arrangeRecords.size() == 0){
                // 未排片
                return Boolean.FALSE;
            }else{
                // 排片
                return Boolean.TRUE;
            }

        }

    }

    /**
     *  通过id 来删除放映厅
     * @param id
     * @return
     */
    public Boolean deleteScreenById(Integer id){

        // 是否排片
        if (isArranged(id)){
            throw new BizException("当前放映厅有排片记录，无法删除");
        }

        // 没有排片 删除放映厅  同时删除和影院的关联记录

        boolean deleteCount = cinemaScreenManager.remove(new QueryWrapper<CinemaScreenDo>()
                .eq("screen_hall_id", id));

        return deleteCount;

    }

    // TODO  选座
    public Boolean chooseSeat(){
        return true;
    }




}
