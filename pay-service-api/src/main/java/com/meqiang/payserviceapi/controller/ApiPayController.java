package com.meqiang.payserviceapi.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.meqiang.payserviceapi.config.AlipayConfig;
import com.meqiang.payserviceapi.service.ApiPayService;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.AliPay;
import com.mingkai.mappermodule.model.Do.order.TicketDetailDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private ApiPayService apiPayService;

    @ApiOperation("支付测试")
    @PostMapping("test-pay")
    public void payTest(@RequestBody AliPay aliPay, HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {

            //设置编码
            response.setContentType("text/html;charset=utf-8");

            PrintWriter out = response.getWriter();
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            //设置请求参数
            AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
            aliPayRequest.setReturnUrl(AlipayConfig.return_url);
            aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

            //商户订单号，后台可以写一个工具类生成一个订单号，必填
            TicketDetailDo order = apiPayService.getOrderByOrderId(aliPay.getOrderId());
            String order_number = new String(aliPay.getOrderId());
            //付款金额，从前台获取，必填
            BigDecimal total_amount = order.getPrice();
            //订单名称，必填
            String subject = new String(aliPay.getOrderId());  // 订单号
            aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            String result = alipayClient.pageExecute(aliPayRequest).getBody();

            out.println(result);
        }

    /**
     * @Description: 前往支付宝第三方网关进行支付
     */
    @RequestMapping(value = "goAlipay", produces = "text/html; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public String goAlipay(String orderId) throws Exception {

        //生成订单 --> 将订单号传入 --> 通过订单号 查询 总额

        TicketDetailDo order = apiPayService.getOrderByOrderId(orderId);

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId;
        //付款金额，必填
        BigDecimal total_amount = order.getPrice();
        //订单名称，必填
        String subject = out_trade_no;
        //商品描述，可空
        String body = "描述";
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "15m";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
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
        log.info("同步界面");
        response.setContentType("text/html;charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用 实际无效
            //  valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款时间
            String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");

            log.info("验证进来了");
            //更新订单

            Boolean update = apiPayService.paySuccess(out_trade_no);


            return "index";

        }else {
            System.out.println("验签失败");
        }
        return null;
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
        log.info("进入异步通知~~");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款时间
            //String datePay = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");

            log.info("进入异步通知~~");

            //更新订单
            Boolean aBoolean = apiPayService.paySuccess(out_trade_no);
            writer.write("success");
        } else {
            writer.write("fail");
            System.out.println("验签失败");
        }


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
