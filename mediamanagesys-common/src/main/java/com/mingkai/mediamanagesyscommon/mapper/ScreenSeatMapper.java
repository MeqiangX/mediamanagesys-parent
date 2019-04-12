package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenSeatDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:39
 */
@Repository
@Mapper
public interface ScreenSeatMapper extends BaseMapper<ScreenSeatDo> {

    Integer insertBatch(@Param("seatList") List<ScreenSeatDo> seatList);


}
