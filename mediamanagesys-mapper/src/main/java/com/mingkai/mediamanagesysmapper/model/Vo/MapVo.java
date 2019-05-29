package com.mingkai.mediamanagesysmapper.model.Vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-27 19:39
 */
@Data
@ApiModel("地图数据")
public class MapVo {
    private String name;

    private BigDecimal value;

}
