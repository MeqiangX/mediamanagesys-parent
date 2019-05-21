package com.mingkai.mediamanagesyscommon.model.Do.task;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-20 15:24
 */
@Data
@TableName("mesys_task_record")
public class TaskRecordDo extends BaseDo {

    private Integer taskType;

    private Long spendTime;

    private Integer success;

    private String errMessage;


    public TaskRecordDo() {
    }

    public TaskRecordDo(Integer taskType, Long spendTime, Integer success, String errMessage) {
        this.taskType = taskType;
        this.spendTime = spendTime;
        this.success = success;
        this.errMessage = errMessage;
    }
}
