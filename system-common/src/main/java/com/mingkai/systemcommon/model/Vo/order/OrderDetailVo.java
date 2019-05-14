package com.mingkai.systemcommon.model.Vo.order;


import com.mingkai.systemcommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-30 21:04
 */
@Data
@ApiModel("订单Vo")
public class OrderDetailVo extends BaseVo {

    private String orderId;

    private Integer userId;

    private String seatIds;

    private BigDecimal price;

    private Integer status;


}
