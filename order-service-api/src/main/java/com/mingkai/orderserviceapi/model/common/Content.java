package com.mingkai.orderserviceapi.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 20:06
 */
@Data
public class Content implements Serializable {

    private LocalAddressModel address_detail;

    private String address;

    private Point point;

}
