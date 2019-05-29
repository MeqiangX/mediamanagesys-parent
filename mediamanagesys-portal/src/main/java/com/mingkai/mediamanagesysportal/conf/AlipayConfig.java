package com.mingkai.mediamanagesysportal.conf;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-09 14:49
 */
@Slf4j
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092600597298";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWlB31MQTUI3NfuHYVa2XKDlu08U+mEkj7QetMCa7SuK7O2PQHMwlf32xYRzDqohv9aQXDM+C++Y1+OrpE96ixGi8YsCf4gA0r8CwFKmDyDsNLqJY5x//8oIKZwF6me+eLTt8gOLMaNFqM74k56Ia/7EEZB65IM+P7GdeFWjAGF+hVh1RS4J3PCLL9J37M9KxmUFaAAiQkCJM1d4PlRjJvtfh65oeospBeSaNkJTlgVfaQcotpVakAfiw1yOztzyj79u5ebEMzp5eI29Y5WputOAD0PtT1dobR+OgElLeofM6kly14XatOwv3XHPUI4E3GO9zLm/ieRUf7xxQCz+iVAgMBAAECggEAM3pdp4ZjlTatacsBK590xBgWiAx7Y1Ya9zL5AxzIVKfoszR+cLuk987FFoT4Fy7mjMLLwolIy2E7Wy0uowdb93fxI+qNL6pvVlccZYOwcvBTGdl/yi8yt6YUIAYhFZ+EcB69ryFTMaE2V7pIWrDA6HoXYzw5+9splJ2oDaHgiyQ1MJz75ZWXVxScqbey6UfMRde3FT0sR/WOntl6CkAxr7iqlxMbSV9ZVQxoEhzL40r1EkhTkc300sdZkS6Hse15XyHbxZ3sUg057MwYAhqsCsom5yREe5zCp+mLFYk4wsZUcf3+EGL9axGV9HFPVMlkAii05veGOTanJL5RNbID4QKBgQD49LFevZdyJziO82JlGE9WVCVXSLQUGfQpAZ45wEDkl1Qt6wlARLwgHGXYww3dU4DpLT5bpG2WJwV0yuDOfZUlDuBdxlCTKILVVI/mcwZgZzs3xygLz0hQiuXvUeH/AEwUUs4qkuW0B1bHxzS+phKGIu6PMfoYD76cfDBI9yjoSQKBgQCa1tSPO9lJrOjbsj4SaoRjgCqZDnclCfS2Yvf1VRZaOHiGheGpjrBPGDMBuyTjUl9tvYt7q7TGaZYCQ22/ZDo0xuB8jxXH+Eu7qCrt12oiMCCTXh25wnlqPzXyUkgoqpPM4hFn68CXiil7ooXTWBbix6PU6Sn7VLY4jzzHjIv17QKBgQDrItnf/Lvs0oSF5/S4PVS52fIG1aFYFwdYL7INI7tm7FNSp1pufD0fKxM6v0f6EiOAmP37unrSxfpOTEw2W8ChZsKdb3Cs/SdNbHgZleyg4v+4d/8n4FiR0g7IkhMdMV+qFmepKGOSPRhGyqzToLGARcACOZbYYQ6MaHOGhwL5GQKBgFoeFMJ0g8eEklZjjArt4bWDf8drCt8Mx1drjINGDWdfHI621xr3SBgQrocgY5zGcXIOcmL9EY61HzCcDADrlZDn+vmj0Xp8QZ+rhai9yjCxsBP1H/ngfQfgWEZ7qzG37aO+I4FHTeUk7XXepmLWDyPYK7scJWn46MFMZISDgWYVAoGBANC/Hz8GzpvIviRXfi/B4uHoZEApvFfadFlQFCdt+E4pvOCQ7lHSXIU2ULQmwmW14s63goPX0FA3W+8E4thQi80OmH/UdFOeU5e/g6qDZnyHM2zqCeyz4mfcgskdrDPlL35LAPcmP1jUl+CWJy1Bn6FmJrFxeYj/ZL1FiTDvAswt";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzGWp8094FIPvTRlgxv7P7Xg1iZb3lK+8JlNMVdAdukqYPkeq3HA6Q4kmiIKSlyKUgfY5zI8ZZXoA4WK1ea1ZB6ToasC9B+QauaXdFiSA0C0ku/oKEtgly30+KAMMgzP0zu5X1A2X8zXA484m4Ff4AkD6auVDOiv0058EhjzHAWtiAbhSmTwY0h6FzaGCVTpFQ4PSix2lu23VA5BSPadkx8CqAw5Q6PBZkNChLBECsOSlkZAnz38hypFutyXLI/8Qe5xGc9ku90eA8yo/KElVCJZ1q/QVdEeUtwmBYBKWftQj6UJH0CEQAUA0odS95BMr6yB0pSPjF+vKDnnm+97sAwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://9qqyr7.natappfree.cc/api/protal/alipay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://9qqyr7.natappfree.cc/api/protal/alipay/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";



//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {

        log.info(sWord);

    }


}
