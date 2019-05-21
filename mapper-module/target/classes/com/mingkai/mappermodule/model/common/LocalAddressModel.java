package com.mingkai.mappermodule.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 20:02
 */
@Data
public class LocalAddressModel implements Serializable {
    private String province;
    private String city;
    private String district;
    private String street;
    private String street_number;
    private Integer city_code;

}
