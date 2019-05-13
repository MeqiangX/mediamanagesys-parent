package com.mingkai.orderserviceapi.model.Do.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.orderserviceapi.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 16:09
 */
@Data
@TableName("mesys_class_user_rel")
public class ClassUserRelDo extends BaseDo {

    private Integer userId;

    private Integer classId;

}
