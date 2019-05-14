package com.mingkai.systemcommon.model.Do.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @description: 基础DO
 * @author: Created by 云风 on 2019-04-02 15:35
 */
@Data
public class BaseDo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;

    @TableField(value = "updator",fill = FieldFill.INSERT_UPDATE)
    private String updator;

    @TableLogic
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    private Integer isDeleted;

}
