package com.mingkai.mediamanagesyscommon.model.Do.task;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-22 20:05
 */
@Data
@TableName("mesys_task_type")
public class TaskTypeDo extends BaseDo {

    private Integer typeId;

    private String taskName;

}
