package com.mingkai.systemcommon.model.Po.uc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 9:46
 */
@Data
@ApiModel("管理员PagePo")
public class UserAdminPagePo extends Page {

    private Integer parentId;

    private String userName;

}
