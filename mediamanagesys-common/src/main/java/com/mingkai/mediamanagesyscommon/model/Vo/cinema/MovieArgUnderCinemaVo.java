package com.mingkai.mediamanagesyscommon.model.Vo.cinema;

import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieCastDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-24 14:38
 */
@Data
@ApiModel("排片记录Vo")
public class MovieArgUnderCinemaVo {

    @ApiModelProperty(name = "movieId",value = "电影id")
    private String movieId;

    @ApiModelProperty(name = "movieName",value = "电影名")
    private String movieName;

    @ApiModelProperty(name = "image",value = "图片")
    private String image;

    @ApiModelProperty(name = "genres",value = "类别")
    private String genres;

    @ApiModelProperty(name = "rating",value = "评分")
    private String rating;

    @ApiModelProperty(name = "casts",value = "主演")
    private String casts;

    @ApiModelProperty(name = "castList",value = "主演List")
    private List<MovieCastDo> castList;

    @ApiModelProperty(name = "languages",value = "语言")
    private String languages;

    @ApiModelProperty(name = "duration",value = "时长")
    private String duration;


    /*以上基本信息*/
    @ApiModelProperty(name = "movieArrangeList",value = "场次信息List")
    private List<MovieArrangeVo> movieArrangeList;


}
