package com.mingkai.mediamanagesyscommon.model.Do.screen;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:22
 */
@Data
@TableName("mesys_screen_room")
public class ScreenRoomDo extends BaseDo {

    private String screeningHallName;

    private Integer screeningHallX;

    private Integer screeningHallY;

}
