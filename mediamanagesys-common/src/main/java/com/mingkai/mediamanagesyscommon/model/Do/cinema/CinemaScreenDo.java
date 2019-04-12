package com.mingkai.mediamanagesyscommon.model.Do.cinema;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 影院放映厅关联表
 * @author: Created by 云风 on 2019-04-12 9:48
 */
@Data
@TableName("mesys_cinema_screen")
public class CinemaScreenDo extends BaseDo {

    private Integer cinemaId;

    private Integer screenHallId;

}
