package com.mingkai.mediamanagesysportal.controller;

import com.alipay.api.AlipayApiException;
import com.mingkai.mediamanagesysportal.service.ApiPayRpcService;
import com.mingkai.systemcommon.common.API;
import com.mingkai.systemcommon.model.AliPay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 17:59
 */
@RequestMapping(API.API_ALIPAY)
@Api(tags = API.PROTAL_ALIPAY)
@Slf4j
@Controller
public class ApiPayController {

    @Autowired
    private ApiPayRpcService apiPayRpcService;

    @ApiOperation("支付测试")
    @PostMapping("test-pay")
    public void payTest(@RequestBody AliPay aliPay, HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        apiPayRpcService.payTest(aliPay,response,request);
    }

    /**
     * @Description: 前往支付宝第三方网关进行支付
     */
    @RequestMapping(value = "goAlipay", produces = "text/html; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public String goAlipay(String orderId) throws Exception {
        return apiPayRpcService.goAlipay(orderId);
    }

    /****
     *   支付宝的同步回调  请求到这
     * @param request  请求参数获取对象
     * @param response
     * @return
     * @throws IOException
     * @throws AlipayApiException
     */
    @GetMapping("/returnUrl")
    public String synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        return apiPayRpcService.synCallBack(request,response);
    }

    /**
     * 支付宝异步回调
     * @param request
     * @param response
     * @throws IOException
     * @throws AlipayApiException
     */
    @PostMapping("/notifyUrl")
    public void asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        apiPayRpcService.asynCallBack(request,response);
    }


  /*  //退款
    @Override
    public void refund(HttpServletResponse response,HttpSession session) throws IOException, AlipayApiException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String(订单号);
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(退款金额);
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(UUID.randomUUID().toString());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        out.println(result);以下写自己的订单退款代码

    }*/



}
