package com.mingkai.mediamanagesyscommon.model.Po.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-01 9:40
 */
@Data
@ApiModel("订单PO")
public class OrderPagePo extends Page {

    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;

}
