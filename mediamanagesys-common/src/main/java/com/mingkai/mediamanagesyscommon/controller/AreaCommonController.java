package com.mingkai.mediamanagesyscommon.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.service.AreaService;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 16:34
 */
@RestController
@RequestMapping(API.API_COMMON)
@Api(tags = API.COMMON)
public class AreaCommonController {

    @Autowired
    private AreaService areaService;

    @ApiOperation("定位")
    @GetMapping("get-localtion")
    public R<LocationModel> getLocation(){
        return new APITemplate<LocationModel>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected LocationModel process() throws BizException {
                return areaService.getLocation();
            }
        }.execute();
    }

    @ApiOperation("模糊搜索省份")
    @GetMapping("search-province")
    public R<List<Province>> searchProvince(String provinceName){
        return new APITemplate<List<Province>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<Province> process() throws BizException {
                return areaService.searchProvince(provinceName);
            }
        }.execute();
    }

    @ApiOperation("模糊搜索城市")
    @GetMapping("search-city")
    public R<List<City>> searchCity(Integer fatherId, String cityName){
        return new APITemplate<List<City>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<City> process() throws BizException {
                return areaService.searchCity(fatherId,cityName);
            }
        }.execute();
    }


    @ApiOperation("模糊搜索区域")
    @GetMapping("search-area")
    public R<List<Area>> searchArea(Integer fatherId,String areaName){
        return new APITemplate<List<Area>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<Area> process() throws BizException {
                return areaService.searchArea(fatherId,areaName);
            }
        }.execute();
    }


    @ApiOperation("根据父id查询城市")
    @GetMapping("underProvCitys")
    public R<List<City>> searchCitysUnderProv(Integer fatherId){
        return new APITemplate<List<City>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<City> process() throws BizException {
                return areaService.searchCitysByFatherId(fatherId);
            }
        }.execute();
    }

    @ApiOperation("根据父id查询区域")
    @GetMapping("underProvAreas")
    public R<List<Area>> searchAreasUnderCity(Integer fatherId){
        return new APITemplate<List<Area>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<Area> process() throws BizException {
                return areaService.searchAreasByFatherId(fatherId);
            }
        }.execute();
    }

    @ApiOperation("初始化城市下拉框")
    @GetMapping("init-select-area")
    public R<List<AreaSelectItem>> initSelectArea(){
        return new APITemplate<List<AreaSelectItem>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<AreaSelectItem> process() throws BizException {
                return areaService.initSelectArea();
            }
        }.execute();
    }


    //通过areaId 来查询 上两级
    @ApiOperation("得到上两级地区")
    @GetMapping("/get-area-city-prov")
    public R<AreaCityProvItem> getAreaCityProv(Integer areaId){
        return new APITemplate<AreaCityProvItem>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected AreaCityProvItem process() throws BizException {
                return areaService.getAreaCityProv(areaId);
            }
        }.execute();
    }

}
