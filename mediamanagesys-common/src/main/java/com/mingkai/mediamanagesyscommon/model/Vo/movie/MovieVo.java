package com.mingkai.mediamanagesyscommon.model.Vo.movie;

import com.mingkai.mediamanagesyscommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 9:17
 */
@Data
@ApiModel("电影Vo")
public class MovieVo extends BaseVo {

    @ApiModelProperty(name = "movieId",value = "电影id")
    private String movieId;

    @ApiModelProperty(name = "movieName",value = "电影名")
    private String movieName;

    @ApiModelProperty(name = "originalName",value = "电影原名")
    private String originalName;

    @ApiModelProperty(name = "image",value = "图片")
    private String image;

    @ApiModelProperty(name = "genres",value = "类别")
    private String genres;

    @ApiModelProperty(name = "rating",value = "评分")
    private String rating;

    @ApiModelProperty(name = "ratingsCount",value = "评分人数")
    private Integer ratingsCount;

    @ApiModelProperty(name = "directors",value = "导演")
    private String directors;

    @ApiModelProperty(name = "casts",value = "主演")
    private String casts;

    @ApiModelProperty(name = "year",value = "上映年份")
    private String year;

    @ApiModelProperty(name = "countries",value = "国家地区")
    private String countries;

    @ApiModelProperty(name = "summary",value = "简介")
    private String summary;

    @ApiModelProperty(name = "anotherName",value = "别名")
    private String anotherName;

    @ApiModelProperty(name = "pubdates",value = "上映日期")
    private String pubdates;

    @ApiModelProperty(name = "languages",value = "语言")
    private String languages;

    @ApiModelProperty(name = "writers",value = "编剧")
    private String writers;

    @ApiModelProperty(name = "tags",value = "标签")
    private String tags;

    @ApiModelProperty(name = "duration",value = "时长")
    private String duration;

}
