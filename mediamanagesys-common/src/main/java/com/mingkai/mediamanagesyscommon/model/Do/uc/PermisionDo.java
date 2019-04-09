package com.mingkai.mediamanagesyscommon.model.Do.uc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 权限表
 * @author: Created by 云风 on 2019-04-08 15:24
 */
@Data
@TableName("mesys_permision")
public class PermisionDo extends BaseDo {


    private String permisionName;

    private String description;

}
