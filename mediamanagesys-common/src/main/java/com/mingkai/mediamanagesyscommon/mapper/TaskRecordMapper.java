package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.task.TaskRecordDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-20 15:26
 */
@Mapper
@Repository
public interface TaskRecordMapper extends BaseMapper<TaskRecordDo> {
}
