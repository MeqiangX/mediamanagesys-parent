package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.screen.ScreenArrangeDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:39
 */
@Repository
@Mapper
public interface ScreenArrangeMapper extends BaseMapper<ScreenArrangeDo> {

    @Select("select * from mesys_screen_arrange where is_deleted = 0 and screening_hall_id = #{hallId} and arrange_date = #{date}")
    List<ScreenArrangeDo> selectByDateAndHallId(@Param("hallId") Integer hallId,@Param("date") String date);

}
