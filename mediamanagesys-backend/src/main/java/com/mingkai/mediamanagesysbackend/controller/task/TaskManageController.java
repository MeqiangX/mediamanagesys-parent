package com.mingkai.mediamanagesysbackend.controller.task;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.service.task.TaskService;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.task.TaskPo;
import com.mingkai.mediamanagesysmapper.model.Vo.task.TaskTypeVo;
import com.mingkai.mediamanagesysmapper.model.Vo.task.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 后台定时任务管理
 * @author: Created by 云风 on 2019-05-21 22:10
 */
@Api(tags = API.BACKEND_TASK)
@RestController
@RequestMapping(API.API_TASK_MANAGE)
public class TaskManageController {

    @Autowired
    private TaskService taskService;

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

    @ApiOperation("任务类型")
    @GetMapping("task-type")
    public R<List<TaskTypeVo>> taskTypeList(){
        return new APITemplate<List<TaskTypeVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<TaskTypeVo> process() throws BizException {
                return taskService.taskTypeList();
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
               return taskService.taskRecords(taskPo);
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
