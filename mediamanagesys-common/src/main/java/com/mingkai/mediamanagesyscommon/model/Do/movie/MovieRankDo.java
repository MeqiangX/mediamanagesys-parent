package com.mingkai.mediamanagesyscommon.model.Do.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 11:14
 */
@Data
@TableName("mesys_movie_hot_rank")
public class MovieRankDo extends BaseDo {

    private String movieId;

    private String movieName;

    private String originalName;

    private String image;

    private String genres;

    private String rating;

    private Integer ratingsCount;

    private String directors;

    private String casts;

    private String year;

    private String countries;

    private String summary;

    private String anotherName;

    private String pubdates;

    private String languages;

    private String writers;

    private String tags;

    private String duration;

}
