package com.mingkai.systemcommon.model.Vo.cinema;


import com.mingkai.systemcommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:27
 */
@Data
@ApiModel("影院Vo")
public class CinemaVo extends BaseVo {

    @ApiModelProperty(name = "cinemaName",value = "影院名")
    private String cinemaName;

    @ApiModelProperty(name = "cinemaAreaId",value = "地域id")
    private Integer cinemaAreaId;

    @ApiModelProperty(name = "cinemaAreaFullName",value = "地域全称")
    private String cinemaAreaFullName;

    @ApiModelProperty(name = "cinemaFullAddress",value = "详细地址(不包括省市区)")
    private String cinemaFullAddress;

    @ApiModelProperty(name = "phone",value = "联系方式")
    private String phone;

    @ApiModelProperty(name = "image",value = "图片")
    private String image;
}
