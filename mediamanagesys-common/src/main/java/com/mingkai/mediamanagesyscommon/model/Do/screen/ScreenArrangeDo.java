package com.mingkai.mediamanagesyscommon.model.Do.screen;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:27
 */
@Data
@TableName("mesys_screen_arrange")
public class ScreenArrangeDo extends BaseDo {

    private Integer cinemaScreenId;

    private String movieId;

    private String arrangeDate;

    private String timeScopeStart;

    private String timeScopeEnd;

    private BigDecimal price;

    private String language;

}
