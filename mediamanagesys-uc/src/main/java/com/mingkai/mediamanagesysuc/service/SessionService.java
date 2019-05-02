package com.mingkai.mediamanagesysuc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @description:  操作session
 * @author: Created by 云风 on 2019-05-02 9:54
 */
@Service
@Slf4j
public class SessionService {

    @Autowired
    private HttpSession session;

    /**
     * 得到session
     * @return
     */
    public HttpSession getSession(){
        return session;
    }


    /**
     * 得到session 属性值
     * @param attrName
     * @return
     */
    public Object getAttribute(String attrName){
        return session.getAttribute(attrName);
    }


    /**
     * 设置属性值
     * @param key
     * @param val
     * @return
     */
    public void setAttribute(String key,Object val){
         session.setAttribute(key,val);
    }
}
