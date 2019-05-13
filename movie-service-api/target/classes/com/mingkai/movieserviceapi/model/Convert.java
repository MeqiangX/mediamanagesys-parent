package com.mingkai.movieserviceapi.model;

/**
 * @program: distr-backend
 * @description: 对象转化接口
 * @author: terry.zhu
 * @create: 2018-10-06 23:22
 **/

@FunctionalInterface
public interface Convert<F,T> {

     T convert(F f, T t);
}
