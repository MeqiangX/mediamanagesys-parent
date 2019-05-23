package com.mingkai.mediamanagesyscommonapp.feigns;

import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.common.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 16:54
 */
@FeignClient(value = "common-service-api")
public interface CommonRpcFeign {


    @ApiOperation("定位")
    @RequestMapping(value = API.API_COMMON + "/get-localtion",method = RequestMethod.GET)
    R<LocationModel> getLocation();

    @ApiOperation("模糊搜索省份")
    @RequestMapping(value = API.API_COMMON + "/search-province",method = RequestMethod.GET)
    R<List<Province>> searchProvince(@RequestParam("provinceName") String provinceName);

    @ApiOperation("模糊搜索城市")
    @RequestMapping(value = API.API_COMMON + "/search-city",method = RequestMethod.GET)
    R<List<City>> searchCity(@RequestParam("fatherId") Integer fatherId,
                                    @RequestParam("cityName") String cityName);


    @ApiOperation("模糊搜索区域")
    @RequestMapping(value = API.API_COMMON + "/search-area",method = RequestMethod.GET)
    R<List<Area>> searchArea(@RequestParam("fatherId") Integer fatherId,
                                    @RequestParam("areaName") String areaName);


    @ApiOperation("根据父id查询城市")
    @RequestMapping(value = API.API_COMMON + "/underProvCitys",method = RequestMethod.GET)
    R<List<City>> searchCitysUnderProv(@RequestParam("fatherId") Integer fatherId);

    @ApiOperation("根据父id查询区域")
    @RequestMapping(value = API.API_COMMON + "/underProvAreas",method = RequestMethod.GET)
    R<List<Area>> searchAreasUnderCity(@RequestParam("fatherId") Integer fatherId);

    @ApiOperation("初始化城市下拉框")
    @RequestMapping(value = API.API_COMMON + "/init-select-area",method = RequestMethod.GET)
    R<List<AreaSelectItem>> initSelectArea();


    //通过areaId 来查询 上两级
    @ApiOperation("得到上两级地区")
    @RequestMapping(value = API.API_COMMON + "/get-area-city-prov",method = RequestMethod.GET)
    R<AreaCityProvItem> getAreaCityProv(@RequestParam("areaId") Integer areaId);




    // --- 文件


    @ApiOperation("图片上传")
    @RequestMapping(value = API.API_COMMON_FILE + "/file-upload",method = RequestMethod.POST)
    R<String> fileUpload(@RequestParam("id") String id,
                         @RequestParam("file") MultipartFile file);

}
