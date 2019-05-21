package com.mingkai.mediamanagesyscommon.model.Vo.task;

import com.mingkai.mediamanagesyscommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-21 22:37
 */
@ApiModel("定时任务记录VO")
@Data
public class TaskVo extends BaseVo {

    private Integer taskType;

    private String taskName;

    private Long spendTime;

    private Integer success;

    private String errMessage;

    public TaskVo() {
    }

    public TaskVo(Integer taskType, String taskName, Long spendTime, Integer success, String errMessage) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.spendTime = spendTime;
        this.success = success;
        this.errMessage = errMessage;
    }
}
