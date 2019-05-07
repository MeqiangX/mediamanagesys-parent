package com.mingkai.mediamanagesyscommon.model.Po.uc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 9:33
 */
@Data
@ApiModel("用户PagePo")
public class UserPagePo extends Page {

    private String search;


}
