package com.chinadrtv.runtime.jms.receive;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.chinadrtv.common.context.SystemContext;
//import com.chinadrtv.maintenance.common.facade.bean.ConfigCenter;
//import com.chinadrtv.maintenance.common.facade.bean.RspObj;
//import com.chinadrtv.maintenance.common.facade.code.RspCode;
import com.chinadrtv.util.XmlUtil;


public class JmsMaintainListener extends JmsListener<String> implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private static final String METHOD_ADD    = "add";
    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_REMOVE = "remove";

    @Override
    public void messageHandler(String msg) throws Exception {

        String otpType = "";
        String key = "";
        String value = "";
//        RspObj rsp = XmlUtil.toObject(msg);
//        if (rsp.getCode().equals(RspCode.SUCCESS.getCode())) {
//
//            //转换成对象
//            ConfigCenter maintenance = (ConfigCenter) rsp.getObj();
//
//            otpType = maintenance.getOperate();
//            key = maintenance.getConfigKey();
//            value = maintenance.getConfigValue();
//            logger.info("===>接收到运维中心修改信息");
//            logger.info("===>optType:{} key:{} value:{}", new String[] { otpType, key, value });
//            //刷新systemContext的数据
//            systemContextRefresh(otpType, key, value);
//
//            ConfigBean cb = new ConfigBean(key, value);
//
//            Map<String, ReloadMaintainHook> hookMap = applicationContext
//                .getBeansOfType(ReloadMaintainHook.class);
//
//            for (ReloadMaintainHook hook : hookMap.values()) {
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

    //更新SystemContext
    public void systemContextRefresh(String otpType, String key, String value) {

        if (StringUtils.equalsIgnoreCase(METHOD_ADD, otpType)
            || StringUtils.equalsIgnoreCase(METHOD_UPDATE, otpType)) {

            //SystemContext.put(key, value);

        } else if (StringUtils.equalsIgnoreCase(METHOD_REMOVE, otpType)) {

            //SystemContext.remove(key);

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }

}
