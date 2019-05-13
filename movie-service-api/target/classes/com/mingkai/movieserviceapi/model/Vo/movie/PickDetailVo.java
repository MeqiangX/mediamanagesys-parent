package com.mingkai.movieserviceapi.model.Vo.movie;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 10:08
 */
@Data
@ApiModel("挑选结果Vo")
public class PickDetailVo {

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

    @ApiModelProperty(name = "countries",value = "国家地区")
    private String countries;

    @ApiModelProperty(name = "duration",value = "时长")
    private String duration;



}
