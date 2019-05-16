package com.mingkai.mappermodule.model.Po.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 10:32
 */
@Data
@ApiModel("添加影院po")
public class CinemaAddPo {

    @ApiModelProperty(name = "id",value = "id")
    private Integer id;

    @ApiModelProperty(name = "provinceId", value = "省份id", hidden = true,example = "1")
    private Integer provinceId;

    @ApiModelProperty(name = "cityId", value = "城市id", hidden = true,example = "1")
    private Integer cityId;

    @ApiModelProperty(name = "areaId", value = "地区id", required = true,example = "1")
    private Integer areaId;

    @ApiModelProperty(name = "cinemaName",value = "影院名",required = true)
    private String cinemaName;

    @ApiModelProperty(name = "cinemaAreaFullName",value = "影院所在地区全称",hidden = true)
    private String cinemaAreaFullName;

    @ApiModelProperty(name = "cinemaFullAddress",value = "影院详细地址(不包括省市区)")
    private String cinemaFullAddress;

    @ApiModelProperty(name = "phone",value = "影院联系方式")
    private String phone;

    @ApiModelProperty(name = "image",value = "影院图片")
    private String image;

}