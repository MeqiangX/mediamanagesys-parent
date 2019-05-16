package com.mingkai.mappermodule.model.Po.uc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 17:25
 */
@Data
@ApiModel("添加权限PO")
public class PermAddPo {

    private String permisionName;

    private String description;

}
