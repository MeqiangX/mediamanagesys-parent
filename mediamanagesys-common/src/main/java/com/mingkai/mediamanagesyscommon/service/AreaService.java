package com.mingkai.mediamanagesyscommon.service;

import com.alibaba.fastjson.JSONObject;
import com.mingkai.mediamanagesyscommon.mapper.common.AreaMapper;
import com.mingkai.mediamanagesyscommon.model.common.Area;
import com.mingkai.mediamanagesyscommon.model.common.City;
import com.mingkai.mediamanagesyscommon.model.common.LocationModel;
import com.mingkai.mediamanagesyscommon.model.common.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 16:26
 */
@Service
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    public static final String uri = "https://api.map.baidu.com/location/ip?ak=pN5ZEQWj4uc6QDVz4vSkBkZqzNZjlSOV";

    /**
     * 定位
     * @return
     */
    public LocationModel getLocation(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        // 解决中文乱码 在header 上加上 字符格式
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        //要将json 转化回来

        LocationModel s1 = JSONObject.parseObject(exchange.getBody(), LocationModel.class);

        return s1;

    }


    /**
     * 模糊搜索省份
     * @param provinceName
     * @return
     */
    public List<Province> searchProvince(String provinceName){
        return areaMapper.selectProvincesByName(provinceName);
    }

    /**
     * 模糊搜索城市
     * @param fatherId
     * @param cityName
     * @return
     */
    public List<City> searchCity(Integer fatherId,String cityName){
        return areaMapper.selectCitysByName(fatherId,cityName);
    }

    /**
     * 模糊搜索区域
     * @param fatherId
     * @param areaName
     * @return
     */
    public List<Area> searchArea(Integer fatherId,String areaName){
        return areaMapper.selectAreasByName(fatherId,areaName);
    }
}
