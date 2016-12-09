package com.chinadrtv.runtime.jms.receive;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.chinadrtv.common.context.ApplicationContextConfig;
//import com.chinadrtv.configcenter.common.facade.bean.ConfigCenter;
//import com.chinadrtv.configcenter.common.facade.bean.RspObj;
//import com.chinadrtv.configcenter.common.facade.code.RspCode;
import com.chinadrtv.util.XmlUtil;


public class JmsConfigCenterListener extends JmsListener<String> implements ApplicationContextAware {
    private ApplicationContext  applicationContext;
    private static final String METHOD_ADD    = "add";
    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_REMOVE = "remove";

    // private JmsConfigCenterHook configCenterHook;


    @Override
    public void messageHandler(String msg) throws Exception {

        String otpType = "";
        String key = "";
        String value = "";
        System.out.print(msg);
//        RspObj rsp = XmlUtil.toObject(msg);
//        if (rsp.getCode().equals(RspCode.SUCCESS.getCode())) {
//
//            //转换成对象
//            ConfigCenter configCenter = (ConfigCenter) rsp.getObj();
//
//            otpType = configCenter.getOperate();
//            key = configCenter.getConfigKey();
//            value = configCenter.getConfigValue();
//
//            logger.info("===>接收到配置中心修改信息");
//            logger.info("optType:{} key:{} value:{}", new String[] { otpType, key, value });
//
//            //刷新applicationContextConfigCenter的数据
//            applicationRefresh(otpType, key, value);
//
//            ConfigBean cb = new ConfigBean(key, value);
//
//            Map<String, ReloadConfigcenterHook> hookMap = applicationContext
//                .getBeansOfType(ReloadConfigcenterHook.class);
//
//            for (ReloadConfigcenterHook hook : hookMap.values()) {
//                if (hook != null) {
//                    if (StringUtils.equalsIgnoreCase(METHOD_ADD, otpType)) {
//                        hook.addConfig(cb);
//                    } else if (StringUtils.equalsIgnoreCase(METHOD_UPDATE, otpType)) {
//                        hook.updateConfig(cb);
//                    } else if (StringUtils.equalsIgnoreCase(METHOD_REMOVE, otpType)) {
//                        hook.removeConfig(cb);
//                    }
//
//                }
//
//            }
//        }
    }

    //更新applicationContextConfig
    public void applicationRefresh(String otpType, String key, String value) {

        if (StringUtils.equalsIgnoreCase(METHOD_ADD, otpType)
            || StringUtils.equalsIgnoreCase(METHOD_UPDATE, otpType)) {

            //ApplicationContextConfig.put(key, value);

        } else if (StringUtils.equalsIgnoreCase(METHOD_REMOVE, otpType)) {

            //ApplicationContextConfig.remove(key);

        }
    }

    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }

}
