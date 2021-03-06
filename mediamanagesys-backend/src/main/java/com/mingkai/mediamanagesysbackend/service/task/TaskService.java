package com.mingkai.mediamanagesysbackend.service.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesyscommon.mapper.TaskRecordMapper;
import com.mingkai.mediamanagesyscommon.mapper.TaskTypeMapper;
import com.mingkai.mediamanagesyscommon.model.Do.task.TaskTypeDo;
import com.mingkai.mediamanagesyscommon.model.Po.task.TaskPo;
import com.mingkai.mediamanagesyscommon.model.Vo.task.TaskTypeVo;
import com.mingkai.mediamanagesyscommon.model.Vo.task.TaskVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-22 20:10
 */
@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRecordMapper taskRecordMapper;

    @Autowired
    private TaskTypeMapper taskTypeMapper;


    /**
     * 查询任务分页
     * @param taskPo
     * @return
     */
    public Page<TaskVo> taskRecords(TaskPo taskPo){

        Page<TaskVo> taskRecordDoPage = taskRecordMapper.taskPage(taskPo);

        return taskRecordDoPage;

    }


    /**
     * 任务类型
     * @return
     */
    public List<TaskTypeVo> taskTypeList(){

        List<TaskTypeDo> taskTypeDos = taskTypeMapper.selectList(null);

        return ConvertUtil.listConvert(taskTypeDos,TaskTypeVo.class);

    }

}
