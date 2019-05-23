package com.mingkai.mediamanagesysmapper.model.Po.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-21 22:50
 */
@ApiModel("任务PO")
@Data
public class TaskPo extends Page {

    /**
     * 任务类型
     */
    private Integer taskType;

    private String startDate;

    private String endDate;

}
