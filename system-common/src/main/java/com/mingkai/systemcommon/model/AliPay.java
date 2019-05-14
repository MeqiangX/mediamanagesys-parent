package com.mingkai.systemcommon.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-09 16:22
 */
@Data
@ApiModel("支付接口")
public class AliPay {

    private String orderId;

}
