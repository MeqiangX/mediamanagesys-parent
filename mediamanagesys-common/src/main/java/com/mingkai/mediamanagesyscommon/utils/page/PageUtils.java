package com.mingkai.mediamanagesyscommon.utils.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @description: 分页工具类
 * @author: Created by 云风 on 2019-04-08 16:11
 */
@Slf4j
public class PageUtils {

    /**
     *  对于要先查询中间表 然后再查询详情表的 分页方法
     * @param records
     * @param page
     * @param <T>
     * @return
     */
    public static <T> Page<T> page(List<T> records, Page page){

        if (Objects.isNull(records)){
            throw new BizException("list 为空");
        }

        if (page.getCurrent() < 1L){
            page.setCurrent(1L);
            log.info("当前页不能小于1，设置为默认1");
        }
        if (page.getSize() < 1L){
            page.setSize(10L);
            log.info("分页参数不能小于1！设置为默认10");
        }

        Page<T> returnPage = new Page<T>();
        returnPage.setCurrent(page.getCurrent());
        returnPage.setSize(page.getSize());
        returnPage.setRecords(records);
        returnPage.setTotal(page.getTotal());
        return returnPage;
    }

}
