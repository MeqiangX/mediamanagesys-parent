package com.mingkai.mediamanagesysbackend.controller.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.model.Po.task.TaskPo;
import com.mingkai.mediamanagesyscommon.model.Vo.task.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 后台定时任务管理
 * @author: Created by 云风 on 2019-05-21 22:10
 */
@Api(tags = API.BACKEND_TASK)
@RestController
@RequestMapping(API.API_TASK_MANAGE)
public class TaskManageController {


    @ApiOperation("手动执行任务")
    @GetMapping("task-run")
    public R<Boolean> taskRun(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return null;
            }
        }.execute();
    }

    @ApiOperation("任务记录列表")
    @GetMapping("task-records")
    public R<Page<TaskVo>> taskRecords(TaskPo taskPo){
       return new APITemplate<Page<TaskVo>>() {
           @Override
           protected void checkParams() throws IllegalArgumentException {

           }

           @Override
           protected Page<TaskVo> process() throws BizException {
               return null;
           }
       }.execute();
    }


    @ApiOperation("最近一次执行时间")
    @GetMapping("last-execution")
    public R<TaskVo> lastExecuton(){
        return new APITemplate<TaskVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected TaskVo process() throws BizException {
                return null;
            }
        }.execute();
    }


}
