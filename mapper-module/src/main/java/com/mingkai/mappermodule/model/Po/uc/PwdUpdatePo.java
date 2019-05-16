package com.mingkai.mappermodule.model.Po.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-27 15:59
 */
@Data
@ApiModel("前台修改密码PO")
public class PwdUpdatePo {

    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;

    @ApiModelProperty(name = "newPwd",value = "新密码")
    private String newPwd;

}
