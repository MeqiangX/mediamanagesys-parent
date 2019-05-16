package com.mingkai.mediamanagesysportal.service;

import com.alipay.api.AlipayApiException;
import com.mingkai.mappermodule.model.AliPay;
import com.mingkai.mediamanagesysportal.feigns.ApiPayRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:59
 */
@Service
public class ApiPayRpcService {

    @Autowired
    private ApiPayRpcFeign apiPayRpcFeign;

    public void payTest(AliPay aliPay, HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException{
        apiPayRpcFeign.payTest(aliPay,response,request);
    }


    public String goAlipay(String orderId) throws Exception{
        return apiPayRpcFeign.goAlipay(orderId);
    }


    public String synCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException{
        return apiPayRpcFeign.synCallBack(request,response);
    }



    public void asynCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException{
        apiPayRpcFeign.asynCallBack(request,response);
    }


}
