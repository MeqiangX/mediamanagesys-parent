package com.mingkai.mediamanagesysmapper.model.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-05 22:24
 */
@Data
@ApiModel("完整地区VO")
public class AreaCityProvItem {

    private Province province;

    private City city;

    private Area area;


    public AreaCityProvItem() {
    }

    public AreaCityProvItem(Province province, City city, Area area) {
        this.province = province;
        this.city = city;
        this.area = area;
    }
}
