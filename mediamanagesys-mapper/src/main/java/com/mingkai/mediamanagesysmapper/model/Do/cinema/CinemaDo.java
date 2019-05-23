package com.mingkai.mediamanagesysmapper.model.Do.cinema;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesysmapper.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:13
 */
@Data
@TableName("mesys_cinema")
public class CinemaDo extends BaseDo {

    private String cinemaName;

    private Integer cinemaAreaId;

    private String cinemaAreaFullName;

    private String cinemaFullAddress;

    private String phone;

    private String image;

}
