package com.mingkai.mediamanagesyscommon.model.Po.movie;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 20:30
 */
@Data
@ApiModel("榜单的查询PO")
public class MovieRankPagePo extends Page {

    @ApiModelProperty(name = "tableName",value = "表名",hidden = true)
    private String tableName;

    @ApiModelProperty(name = "rankType",value = "榜单类型(详情见MovieRankEnum)")
    private String rankType;


    // 排序 按出版时间 按热门  按评分 按时长

    // 筛选条件：  类型 区域 年代

}
