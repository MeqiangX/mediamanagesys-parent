package com.mingkai.systemcommon.model.Vo.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-30 12:39
 */
@Data
@ApiModel("排片电影详情Vo")
public class MovieArrangeDetailVo {

    @ApiModelProperty(name = "id",value = "排片id",example = "1")
    private Integer id;

    @ApiModelProperty(name = "movieId",value = "电影id")
    private String movieId;

    @ApiModelProperty(name = "image",value = "图片")
    private String image;

    @ApiModelProperty(name = "movieName",value = "电影名")
    private String movieName;

    @ApiModelProperty(name = "duration",value = "时长")
    private String duration;

    @ApiModelProperty(name = "genres",value = "类别")
    private String genres;

    @ApiModelProperty(name = "cinemaName",value = "影院名")
    private String cinemaName;

    @ApiModelProperty(name = "screeningHallName",value = "放映厅名称")
    private String screeningHallName;

    @ApiModelProperty(name = "arrangeDate",value = "放映日期")
    private String arrangeDate;

    @ApiModelProperty(name = "timeScopeStart",value = "开始时间")
    private String timeScopeStart;

    @ApiModelProperty(name = "price",value = "价格")
    private BigDecimal price;

    @ApiModelProperty(name = "language",value = "语言版本")
    private String language;


}
