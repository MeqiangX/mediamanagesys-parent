package com.mingkai.mediamanagesysmapper.model.Po.movie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-19 18:19
 */
@Data
@ApiModel("电影页PO")
public class MoviePagePo extends Page {

    @ApiModelProperty(name = "movieType",value = "电影类型")
    private String movieType;

    @ApiModelProperty(name = "area",value = "地区")
    private String area;

    @ApiModelProperty(name = "year",value = "年份")
    private String year;

    @ApiModelProperty(name = "sortType",value = "排序")
    private String sortType;

}
