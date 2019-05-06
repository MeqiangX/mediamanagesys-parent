package com.mingkai.mediamanagesyscommon.service;

import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mingkai.mediamanagesyscommon.mapper.common.AreaMapper;
import com.mingkai.mediamanagesyscommon.model.common.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 16:26
 */
@Service
@Slf4j
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


    /**
     *  地域下拉框初始化
     * @return
     */
    public List<AreaSelectItem> initSelectArea(){

        // 查找所有地域的信息 除了 县、市辖区
        List<Area> areas = areaMapper.selectAreas();

        // 分组成 26个字母

        Map<String,List<Area>> areaInitialMap = Maps.newHashMap();

        for (Area area:areas){

            try {
                String shortPinyin = PinyinHelper.getShortPinyin(area.getArea().substring(0, 1));

                if (Objects.isNull(areaInitialMap.get(shortPinyin.toUpperCase()))){
                    areaInitialMap.put(shortPinyin.toUpperCase(), Lists.newArrayList());
                }
                areaInitialMap.get(shortPinyin.toUpperCase()).add(area);
            } catch (PinyinException e) {
                log.info("转化拼音失败： " + e);
            }

        }


        List<AreaSelectItem> result = Lists.newArrayList();
        for (int i = 65; i <= 90;++i){
            AreaSelectItem areaSelectItem = new AreaSelectItem();
            areaSelectItem.setInitials(""+(char)i);
            areaSelectItem.setAreas(areaInitialMap.get(""+(char)i));
            result.add(areaSelectItem);
        }
        // 返回

        return result;

    }


    /**
     * 根据fatherId 查询  城市
     * @param fatherId
     * @return
     */
    public List<City> searchCitysByFatherId(Integer fatherId){
        return areaMapper.selectUnderProvinceCitys(fatherId);
    }


    public List<Area> searchAreasByFatherId(Integer fatherId){
        return areaMapper.selectUnderCityAreas(fatherId);
    }


    /**
     * 根据areaId来得到三级区域
     * @param areaId
     * @return
     */
    public AreaCityProvItem getAreaCityProv(Integer areaId){
        Area area = areaMapper.selectByAreaId(areaId);

        City city = areaMapper.selectByCityId(area.getFatherId());

        Province province = areaMapper.selectByProvinceId(city.getFatherId());


        AreaCityProvItem areaCityProvItem = new AreaCityProvItem(province,city,area);

        return areaCityProvItem;
    }
}
