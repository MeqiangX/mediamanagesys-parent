package com.mingkai.orderserviceapi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.systemcommon.model.Do.screen.ScreenRoomDo;
import com.mingkai.systemcommon.model.Po.cinema.ScreenPagePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:39
 */
@Repository
@Mapper
public interface ScreenRoomMapper extends BaseMapper<ScreenRoomDo> {

    @Select("select count(1) from mesys_screen_room where is_deleted = 0 and screening_hall_name = #{screenName}")
    Integer selectRepeatNameCount(@Param("screenName") String screenName);

    @Select("select * from mesys_screen_room where is_deleted = 0 and id = #{id}")
    ScreenRoomDo selectById(@Param("id") Integer id);

    Page<ScreenRoomDo> queryScreensPage(@Param("screenPagePo") ScreenPagePo screenPagePo);
}
