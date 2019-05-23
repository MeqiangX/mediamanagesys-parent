package com.mingkai.mediamanagesysportal.feigns;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:  内部服务调用如果通过网关 只需将Feign的请求写成网关的serviceId 即可 就可以去请求zuul网关，通过网关来适配url 寻找对应的服务
 * @author: Created by 云风 on 2019-05-21 19:33
 */
@FeignClient(value = "service-zuul")
public interface ServiceZuulFeign {
}
