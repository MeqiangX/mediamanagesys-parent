package com.mingkai.mediamanagesyscommon.model.Po.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-23 16:52
 */
@Data
@ApiModel("影院搜索PO")
public class CinemaSearchPo extends Page {

    @ApiModelProperty(name = "areaId",value = "地域id")
    private String areaId;

    @ApiModelProperty(name = "search",value = "影院名")
    private String search;

    @ApiModelProperty(name = "movieId",value = "电影id",notes = "在电影选择影院的时候的限制-有当前电影排片记录的影院")
    private String movieId;

    @ApiModelProperty(name = "cinemaIds",value = "影院ids")
    private List<Integer> cinemaIds;
}
