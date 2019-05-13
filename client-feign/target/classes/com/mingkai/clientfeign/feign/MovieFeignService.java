package com.mingkai.clientfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-12 13:09
 */
@FeignClient(value = "movie-service")
@Repository
public interface MovieFeignService {

    @GetMapping("movie-test")
    String getMovieTestFromService(@RequestParam("id") String id);



}
