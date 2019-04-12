package com.mingkai.mediamanagesyscommon.common;

/**
 * @description: swagger & requestMapping
 * @author: Created by 云风 on 2019-03-31 11:16
 */
public interface API {

    /**
     * mapping
     */

    // ------- common
    String API_COMMON = "api/common";


    // ------- uc


            // user
    String API_USER = "api/uc/user";
    String API_COMMOM_CODE = "api/uc/code";

            // role

    String API_ROLE = "api/uc/role";

            // permision

    String API_PERMISION = "api/uc/permision";

    // ------- backend

    String API_TEST = "api/backend/test";

    String API_SCREEN = "api/backend/screen";

    String API_CINEMA = "api/backend/cinema";



    // -------- portal

        // movie
    String API_MOVIE = "api/portal/movie";

    String API_ORDER = "api/portal/order";

    String API_ALIPAY = "api/protal/alipay";

    /**
     * swagger2 tags
     */

    // ---------test

    String TEST = "接口测试";


    // ----------common
    String COMMON = "通用组";

    // ---------- uc

    String USER = "用户中心";
    String COMMON_CODE = "验证码";
    String HTTP_CEND = "发送HTTP请求";

    String ROLE = "角色中心";
    String PERMISION = "权限中心";

    // ---------- backend

    String BACKEND_SCREEN = "后台-放映厅相关";
    String BACKEND_CINEMA = "后台-影院相关";

    // ---------- portal

    String PROTAL = "前台";
    String PROTAL_MOVIE = "前台-电影相关";
    String PROTAL_ORDER = "前台-订单相关";
    String PROTAL_ALIPAY = "前台-支付";


}
