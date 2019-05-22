package com.mingkai.mediamanagesyscommon.model.Vo.task;

import com.mingkai.mediamanagesyscommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-22 21:31
 */
@Data
@ApiModel("任务类型VO")
public class TaskTypeVo extends BaseVo {

    private Integer typeId;

    private String taskName;

}
