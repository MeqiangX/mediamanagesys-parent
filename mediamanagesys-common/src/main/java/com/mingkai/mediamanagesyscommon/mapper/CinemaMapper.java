package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesyscommon.model.Do.EchartsDo;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaDo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaSearchPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:15
 */
@Repository
@Mapper
public interface CinemaMapper extends BaseMapper<CinemaDo> {

    List<EchartsDo> cinemaEchartsData(@Param("startYear") Integer startYear, @Param("startMonth") Integer startMonth
            , @Param("endYear") Integer endYear, @Param("endMonth") Integer endMonth);

    Page<CinemaDo> searchCinemaPage(@Param("cinemaSearchPo") CinemaSearchPo cinemaSearchPo);

}
