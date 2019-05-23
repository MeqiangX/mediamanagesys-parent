package com.mingkai.mediamanagesysportal.feigns;


import com.mingkai.mappermodule.model.AliPay;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:58
 */
@FeignClient(value = "pay-service-api")
@Repository
public interface ApiPayRpcFeign {


    @ApiOperation("支付测试")
    @PostMapping("test-pay")
    void payTest(@RequestBody AliPay aliPay, @RequestParam("response") HttpServletResponse response, @RequestParam("request") HttpServletRequest request);



    /**
     * @Description: 前往支付宝第三方网关进行支付
     */
    @RequestMapping(value = "goAlipay", produces = "text/html; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    String goAlipay(String orderId);

    /****
     *   支付宝的同步回调  请求到这
     * @param request  请求参数获取对象
     * @param response
     * @return
     */
    @GetMapping("/returnUrl")
    String synCallBack(@RequestParam("request") HttpServletRequest request, @RequestParam("response") HttpServletResponse response);


    /**
     * 支付宝异步回调
     * @param request
     * @param response
     */
    @PostMapping("/notifyUrl")
    void asynCallBack(@RequestParam("request") HttpServletRequest request, @RequestParam("response") HttpServletResponse response);


}
