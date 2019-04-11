package com.mingkai.mediamanagesyscommon.mapper.common;

import com.mingkai.mediamanagesyscommon.model.common.Area;
import com.mingkai.mediamanagesyscommon.model.common.City;
import com.mingkai.mediamanagesyscommon.model.common.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 15:50
 */
@Repository
@Mapper
public interface AreaMapper {


    // ---------------- province

    /**
     * 根据 省份id 查找 省份
     * @param provinceId
     * @return
     */
    @Select("select * from province where province_id = #{provinceId}")
    Province selectByProvinceId(@Param("provinceId") Integer provinceId);

    /**
     * 模糊搜索 省份
     * @param provinceName
     * @return
     */
    @Select("<script>" +
            "select * from province " +
            "<if test='provinceName != null and provinceName != &quot;&quot;'>" +
            "where province like " +
            "CONCAT('%',#{provinceName},'%')" +
            "</if>" +
            "</script>")
    List<Province> selectProvincesByName(@Param("provinceName") String provinceName);

    // --------------city

    /**
     *
     * 根据 市区的id 查找 市
     * @param cityId
     * @return
     */
    @Select("select * from city where city_id = #{cityId}")
    City selectByCityId(@Param("cityId") Integer cityId);

    /**
     * 查找省份下的城市
     * @param fatherId
     * @return
     */
    @Select("select * from city where father_id = #{fatherId}")
    List<City> selectUnderProvinceCitys(@Param("fatherId") Integer fatherId);

    /**
     * 模糊搜索城市
     * @param cityName
     * @return
     */
    @Select("<script>" +
            "select * from city where father_id = #{fatherId} " +
            "<if test='cityName != null and cityName != &quot;&quot;'>" +
            "and city like " +
            "CONCAT('%',#{cityName},'%')" +
            "</if>" +
            "</script>")
    List<City> selectCitysByName(@Param("fatherId") Integer fatherId,@Param("cityName") String cityName);


    // ---------------area

    /**
     * 根据区id 查找具体的区域
     * @param areaId
     * @return
     */
    @Select("select * from area where area_id = #{areaId}")
    Area selectByAreaId(@Param("areaId") Integer areaId);

    /**
     * 查找城市下的区域
     * @param fatherId
     * @return
     */
    @Select("select * from area where father_id = #{fatherId}")
    List<Area> selectUnderCityAreas(@Param("fatherId") Integer fatherId);

    /**
     * 模糊搜索 区域
     * @param areaName
     * @return
     */
    @Select("<script>" +
            "select * from area where father_id = #{fatherId} " +
            "<if test='areaName != null and areaName != &quot;&quot;'>" +
            "and area like " +
            "CONCAT('%',#{areaName},'%')" +
            "</if>" +
            "</script>")
    List<Area> selectAreasByName(@Param("fatherId") Integer fatherId,@Param("areaName") String areaName);

}
