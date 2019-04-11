package com.mingkai.mediamanagesyscommon.model.Po.movie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 9:59
 */
@Data
@ApiModel("挑选场次PO")
public class PickArrangePo extends Page {

    @ApiModelProperty(name = "movieId",value = "电影id")
    private String movieId;

    @ApiModelProperty(name = "dates",value = "日期")
    private List<String> dates;

    @ApiModelProperty(name = "screenHallIds",value = "放映厅")
    private List<String> screenHallIds;

}
