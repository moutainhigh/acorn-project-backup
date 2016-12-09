package com.chinadrtv.util;

import java.util.UUID;


public class StringUtil {

    /**
     * 生成图片验证唯一标识码 
     */
    public String generateCaptchaId() {
        return this.urlFilter(this.base64Encode(UUID.randomUUID().toString()));
    }

    @SuppressWarnings("restriction")
    private String base64Encode(String s) {
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    /**
     * 过滤掉url中的特殊字符
     */
    private String urlFilter(String s) {
        return s.replaceAll("[+? /%#&=]", "");
    }

}