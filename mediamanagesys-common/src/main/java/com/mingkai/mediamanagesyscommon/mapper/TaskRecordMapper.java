package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesyscommon.model.Do.task.TaskRecordDo;
import com.mingkai.mediamanagesyscommon.model.Po.task.TaskPo;
import com.mingkai.mediamanagesyscommon.model.Vo.task.TaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-20 15:26
 */
@Mapper
@Repository
public interface TaskRecordMapper extends BaseMapper<TaskRecordDo> {

    Page<TaskVo> taskPage(@Param("taskPo") TaskPo taskPo);

}
