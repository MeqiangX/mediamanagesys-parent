package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieRankDo;
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
            ",year,countries,summary,another_name,pubdates,languages,writers,tags,duration) values(" +
            "#{movieDetailDo.movieId},#{movieDetailDo.movieName},#{movieDetailDo.originalName}," +
            "#{movieDetailDo.image},#{movieDetailDo.genres},#{movieDetailDo.rating}," +
            "#{movieDetailDo.ratingsCount},#{movieDetailDo.directors},#{movieDetailDo.casts}," +
            "#{movieDetailDo.year},#{movieDetailDo.countries},#{movieDetailDo.summary}," +
            "#{movieDetailDo.anotherName},#{movieDetailDo.pubdates},#{movieDetailDo.languages}," +
            "#{movieDetailDo.writers},#{movieDetailDo.tags},#{movieDetailDo.duration})")
    Boolean insertIntoTable(@Param("tableName") String tableName, @Param("movieDetailDo") MovieDetailDo movieDetailDo);


}
