package com.mingkai.mediamanagesyscommon.model.Po.uc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 14:56
 */
@Data
@ApiModel("管理员添加/修改PO")
public class UserAdminAddPo {

    private Integer id;

    private String userName;

    private String userPassword;

    private Integer status;

    private Integer parent;

}
