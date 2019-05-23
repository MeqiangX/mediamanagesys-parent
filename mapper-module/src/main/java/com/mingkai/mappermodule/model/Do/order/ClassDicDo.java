package com.mingkai.mappermodule.model.Do.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mappermodule.model.Do.base.BaseDo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 会员等级维表
 * @author: Created by 云风 on 2019-04-12 16:06
 */
@Data
@TableName("mesys_class_dic")
public class ClassDicDo extends BaseDo {

    private String grade;

    private BigDecimal discount;

}
