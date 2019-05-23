package com.mingkai.clientfeign.feign;

import com.dtstack.plat.lang.web.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-12 21:10
 */
@Repository
@FeignClient(value = "movie-service-api")
public interface MovieApiRpcService {

    @GetMapping("portal-test")
    R<Boolean> portalTest();

}
