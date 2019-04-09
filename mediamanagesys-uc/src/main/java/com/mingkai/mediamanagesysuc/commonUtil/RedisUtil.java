package com.mingkai.mediamanagesysuc.commonUtil;

import com.dtstack.plat.lang.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @description: RedisUtil 工具类
 * @author: Created by 云风 on 2019-03-31 19:17
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 写入redis String
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,Object value){

        boolean result = false;

        try{
            ValueOperations<String,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            result = true;
        } catch (Exception e){
            throw new BizException("写入缓存失败",e);
        }
        return result;

    }

    /**
     * 写入缓存 有过期时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key,Object value,Long expireTime){

        boolean result = false;

        try{
            ValueOperations<String,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            throw new BizException("写入缓存失败",e);
        }

        return result;

    }

    /**
     * 是否存在Key
     * @param key
     * @return
     */
    public boolean exist(final String key){
        return redisTemplate.hasKey(key);
    }


    /**
     * 读取key
     * @param key
     * @return
     */
    public Object get(final String key){

        ValueOperations<String,Object> operations = redisTemplate.opsForValue();
        Object result = operations.get(key);

        return result;
    }

    /**
     * 删除对应的Key
     * @param key
     */
    public void del(final String key){
        if (exist(key)){
            redisTemplate.delete(key);
        }
    }
}
