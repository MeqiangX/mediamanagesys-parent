package com.mingkai.mediamanagesyscommonapp.service;

import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.model.common.*;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesyscommonapp.feigns.CommonRpcFeign;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 16:56
 */
@Service
public class AreaCommonRpcService {

    @Autowired
    private CommonRpcFeign commonRpcFeign;



    public LocationModel getLocation(){
        return CheckOfR.check(commonRpcFeign.getLocation());
    }

    public List<Province> searchProvince(String provinceName){
        return CheckOfR.check(commonRpcFeign.searchProvince(provinceName));
    }


    public List<City> searchCity(Integer fatherId,String cityName){
        return CheckOfR.check(commonRpcFeign.searchCity(fatherId,cityName));
    }



    public List<Area> searchArea(Integer fatherId, String areaName){
        return CheckOfR.check(commonRpcFeign.searchArea(fatherId,areaName));
    }


    public List<City> searchCitysUnderProv(Integer fatherId){
        return CheckOfR.check(commonRpcFeign.searchCitysUnderProv(fatherId));
    }


    public List<Area> searchAreasUnderCity(Integer fatherId){
        return CheckOfR.check(commonRpcFeign.searchAreasUnderCity(fatherId));
    }


    public List<AreaSelectItem> initSelectArea(){
        return CheckOfR.check(commonRpcFeign.initSelectArea());
    }



    public AreaCityProvItem getAreaCityProv(Integer areaId){
        return CheckOfR.check(commonRpcFeign.getAreaCityProv(areaId));
    }


}
