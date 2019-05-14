package com.mingkai.systemcommon.model.Po.uc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 12:27
 */
@Data
@ApiModel("修改用户PO")
public class UserAddPo {

    private Integer id;

    private String userName;

    private String userPassword;

    private String email;

    private String phone;

    private Integer status;

}
