package com.mingkai.mediamanagesysschdule.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: MybatisPlus 自动填充
 * @author: Created by 云风 on 2019-04-02 15:29
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("creator","yunfeng",metaObject);
        this.setFieldValByName("updator", "yunfeng",metaObject);
        this.setFieldValByName("isDeleted",0,metaObject);
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updator","yunfeng-update",metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
