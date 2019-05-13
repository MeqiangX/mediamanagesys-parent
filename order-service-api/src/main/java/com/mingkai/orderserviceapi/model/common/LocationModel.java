package com.mingkai.orderserviceapi.model.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 20:01
 */
@Data
@ApiModel("定位Model")
public class LocationModel implements Serializable {

    private String address;

    private Content content;

    private Integer status;

}
