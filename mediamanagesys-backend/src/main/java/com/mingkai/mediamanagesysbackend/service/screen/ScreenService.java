package com.mingkai.mediamanagesysbackend.service.screen;

import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenRoomAddPo;
import com.mingkai.mediamanagesyscommon.manager.ScreenSeatManager;
import com.mingkai.mediamanagesyscommon.mapper.MovieDetailMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenArrangeMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenRoomMapper;
import com.mingkai.mediamanagesyscommon.mapper.ScreenSeatMapper;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.mediamanagesyscommon.model.Vo.screen.ScreenRoomVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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




    // TODO  选座
    public Boolean chooseSeat(){
        return true;
    }




}
