package com.mingkai.systemcommon.model.Do;

import lombok.Data;

/**
 * @description: 为了接受最近一年的图表数据的DO
 * @author: Created by 云风 on 2019-05-04 21:25
 */
@Data
public class EchartsDo {

    private Integer year;

    private Integer month;

    private Integer count;

}
