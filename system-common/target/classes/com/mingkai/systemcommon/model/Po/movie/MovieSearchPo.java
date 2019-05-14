package com.mingkai.systemcommon.model.Po.movie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-22 16:43
 */
@ApiModel("搜索电影Po")
@Data
public class MovieSearchPo extends Page implements Serializable {

    @ApiModelProperty(name = "search",value = "搜索值")
    private String search;

}
