package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysmapper.model.Do.EchartsDo;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MoviePagePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: MovieDetailMapper
 * @author: Created by 云风 on 2019-04-02 15:46
 */
@Repository
@Mapper
public interface MovieDetailMapper extends BaseMapper<MovieDetailDo> {

    @Select("<script>" +
            "select movie_id from mesys_movie_detail where is_deleted = 0 and movie_id in " +
            "<foreach collection='movieIds' open='(' item='movieId' separator=',' close=')'>" +
            "#{movieId}" +
            "</foreach>" +
            "</script>")
    List<String> selectIdsIn(@Param("movieIds") List<String> movieIds);


    @Select("select * from mesys_movie_detail where is_deleted = 0 and movie_id = #{movieId}")
    MovieDetailDo selectByMovieId(@Param("movieId") String movieId);


    Page<MovieDetailDo> moviePage(@Param("moviePagePo") MoviePagePo moviePagePo);

    List<EchartsDo> movieEchartsData(@Param("startYear") Integer startYear, @Param("startMonth") Integer startMonth
            , @Param("endYear") Integer endYear, @Param("endMonth") Integer endMonth);

    List<EchartsDo> movieEchartsDataLast30D(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
