package com.mingkai.mappermodule.utils.convert;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.utils.convert.convertInterfaces.Convert;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 20:46
 */
public class ConvertUtil {


    /**
     * 对象转化
     * @param f 来源对象
     * @param tClass 目标对象
     * @param ignoreProperties 忽略属性
     * @param <F>
     * @param <T>
     * @return
     */
    public  static <F,T> T convert(F f, Class<T> tClass, String ... ignoreProperties){
        return convert(f,tClass,null,ignoreProperties);
    }


    /**
     * 对象转化，可以自定义转化类
     * @param f 来源对象
     * @param tClass 目标对象
     * @param convert 自定义转化类
     * @param ignoreProperties 忽略属性
     * @param <F>
     * @param <T>
     * @return
     */
    public  static <F,T> T convert(F f, Class<T> tClass, Convert<F,T> convert, String ... ignoreProperties){
        try {
            T t=tClass.newInstance();
            BeanUtils.copyProperties(f,t,ignoreProperties);
            if(convert!=null){
                t=convert.convert(f,t);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分页转化
     * @param f  来源对象
     * @param tClass 目标对象
     * @param <F>
     * @param <T>
     * @return
     */
    public  static <F,T> Page<T> pageConvert(Page<F> f, Class<T> tClass){
        Page<T> t=new Page<T>();
        t.setCurrent(f.getCurrent());
        t.setSize(f.getSize());
        t.setTotal(f.getTotal());
        List<T> records=f.getRecords().stream().map(a -> {
            return convert(a,tClass);
        }).collect(Collectors.toList());
        t.setRecords(records);
        return t;
    }


    public static <F,T> List<T> listConvert(Collection<F> fs, Class<T> tClass){
        List<T> tlist=new ArrayList<>();
        for(F f : fs){
            T t=convert(f,tClass);
            tlist.add(t);
        }
        return tlist;
    }

    /**  转换DO列表为链式VO列表 */
    public static <F,T> LinkedList<T> linkedListConvert(Collection<F> fs, Class<T> tClass){
        LinkedList<T> tlist=new LinkedList<>();
        for(F f : fs){
            T t=convert(f,tClass);
            tlist.add(t);
        }
        return tlist;
    }

    public static <F,T> Set<T> setConvert(Collection<F> fs, Class<T> tClass){
        Set<T> tset=new HashSet<>();
        for(F f : fs){
            T t=convert(f,tClass);
            tset.add(t);
        }
        return tset;
    }


}
