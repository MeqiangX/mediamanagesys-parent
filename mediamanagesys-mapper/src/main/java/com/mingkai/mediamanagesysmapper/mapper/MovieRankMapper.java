package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieRankDo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieRankPagePo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 11:16
 */
@Repository
@Mapper
public interface MovieRankMapper extends BaseMapper<MovieRankDo> {

    /**
     * 清空表数据的动态sql
     * @param tableName
     */
    @Delete("delete from ${tableName}")
    Boolean dynamicSqlExcute(@Param("tableName") String tableName);

    /**
     * 插入
     * @param tableName
     * @param movieDetailDo
     * @return
     */
    @Insert("insert into ${tableName}(movie_id,movie_name,original_name,image,genres,rating,ratings_count,directors,casts" +
            ",year,countries,summary,another_name,pubdates,languages,writers,tags,duration,create_time,update_time,creator,updator,is_deleted) values(" +
            "#{movieDetailDo.movieId},#{movieDetailDo.movieName},#{movieDetailDo.originalName}," +
            "#{movieDetailDo.image},#{movieDetailDo.genres},#{movieDetailDo.rating}," +
            "#{movieDetailDo.ratingsCount},#{movieDetailDo.directors},#{movieDetailDo.casts}," +
            "#{movieDetailDo.year},#{movieDetailDo.countries},#{movieDetailDo.summary}," +
            "#{movieDetailDo.anotherName},#{movieDetailDo.pubdates},#{movieDetailDo.languages}," +
            "#{movieDetailDo.writers},#{movieDetailDo.tags},#{movieDetailDo.duration},#{movieDetailDo.createTime},#{movieDetailDo.updateTime},#{movieDetailDo.creator},#{movieDetailDo.updator},#{movieDetailDo.isDeleted})")
    Boolean insertIntoTable(@Param("tableName") String tableName, @Param("movieDetailDo") MovieDetailDo movieDetailDo);


    // 针对非baseMapper 无法自动分页的 情况 无需 这里 只有有Page 就会自动分页
/*
    @Select("select count(1) from ${movieRankPagePo.tableName}")
    Integer selectMovieRankCount(@Param("movieRankPagePo") MovieRankPagePo movieRankPagePo);*/


    //@Select("select * from ${movieRankPagePo.tableName}")
    Page<MovieRankDo> selectMovieRanksByPage(@Param("movieRankPagePo") MovieRankPagePo movieRankPagePo);


}
