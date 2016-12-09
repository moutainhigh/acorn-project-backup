package com.chinadrtv.taobao.common.integration.ws;

public interface CaptchaCenterWSClient {
    /**
     * 返回验证码
     * 
     * @param id
     * @return
     */
    public byte[] getCaptcha(String id);

    /**
     * 返回检查结果
     * 
     * @param id
     * @param submitCode
     * @return
     */
    public String checkCaptcha(String id, String submitCode);
}
