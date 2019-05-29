package com.mingkai.mediamanagesysmapper.common;

/**
 * @description: 省份简写
 * @author: Created by 云风 on 2019-05-27 19:19
 */
public enum ProvinceShortEnum {

    BEIJING("北京市","北京"),
    TIANJIN("天津市","天津"),
    HEBEI("河北省","河北"),
    SHANXI("山西省","山西"),
    NEIMENGGU("内蒙古自治区","内蒙古"),
    LIAONING("辽宁省","辽宁"),
    JILIN("吉林省","吉林"),
    HEILONGJIANG("黑龙江省","黑龙江"),
    SHANGHAI("上海市","上海"),
    JIANGSU("江苏省","江苏"),
    ZHEJIANG("浙江省","浙江"),
    ANHUI("安徽省","安徽"),
    FUJIAN("福建省","福建"),
    JIANGXI("江西省","江西"),
    SHANDONG("山东省","山东"),
    HENAN("河南省","河南"),
    HUBEI("湖北省","湖北"),
    HUNAN("湖南省","湖南"),
    GUANGDONG("广东省","广东"),
    GUANGXI("广西壮族自治区","广西"),
    HAINAN("海南省","海南"),
    CHONGQING("重庆市","重庆"),
    SICHUAN("四川省","四川"),
    GUIZHOU("贵州省","贵州"),
    YUNNAN("云南省","云南"),
    XIZANG("西藏自治区","西藏"),
    SX("陕西省","陕西"),
    GANSU("甘肃省","甘肃"),
    QINGHAI("青海省","青海"),
    NINGXIA("宁夏回族自治区","宁夏"),
    XINJIANG("新疆维吾尔自治区","新疆"),
    TAIWAN("台湾省","台湾"),
    XIANGGNAG("香港特别行政区","香港"),
    AOMEN("澳门特别行政区","澳门");

    private String fullname;

    private String shortname;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    ProvinceShortEnum(String fullname, String shortname) {
        this.fullname = fullname;
        this.shortname = shortname;
    }


    public static ProvinceShortEnum getProvinceEnum(String fullname){
        ProvinceShortEnum[] values = ProvinceShortEnum.values();
        for (ProvinceShortEnum value : values) {
            if (fullname.equals(value.getFullname())){
                return value;
            }
        }
        return null;
    }


}
