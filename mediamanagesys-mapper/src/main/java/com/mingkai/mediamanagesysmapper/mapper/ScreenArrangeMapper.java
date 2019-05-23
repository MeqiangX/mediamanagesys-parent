package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysmapper.model.Do.cinema.CinemaDo;
import com.mingkai.mediamanagesysmapper.model.Do.screen.ScreenArrangeDo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaPagePo;
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

    @Select("select * from mesys_screen_arrange where cinema_screen_id = #{cinemaScreenId} and arrange_date = #{date}")
    List<ScreenArrangeDo> selectByDateAndHallId(@Param("cinemaScreenId") Integer cinemaScreenId, @Param("date") String date);

    /**
     * 查询影院 地区 movieId 分页
     * @param cinemaPagePo
     * @return
     */
    Page<CinemaDo> selectArrangeByPage(@Param("cinemaPagePo") CinemaPagePo cinemaPagePo);

}
