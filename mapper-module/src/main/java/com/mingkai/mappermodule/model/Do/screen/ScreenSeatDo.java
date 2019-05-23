package com.mingkai.mappermodule.model.Do.screen;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mappermodule.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:36
 */
@Data
@TableName("mesys_screen_seat")
public class ScreenSeatDo extends BaseDo {

    private Integer screenArrangeId;

    private Integer screeningHallX;

    private Integer screeningHallY;

    private Integer status;

    private Integer isPurchased;

}
