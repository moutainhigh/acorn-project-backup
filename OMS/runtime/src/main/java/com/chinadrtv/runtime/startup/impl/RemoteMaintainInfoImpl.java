package com.chinadrtv.runtime.startup.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.common.log.LOG_TYPE;
//import com.chinadrtv.maintenance.common.facade.ConfigurationQueryFacade;
//import com.chinadrtv.maintenance.common.facade.bean.ConfigCenter;
//import com.chinadrtv.maintenance.common.facade.bean.RspObj;
//import com.chinadrtv.maintenance.common.facade.code.RspCode;
import com.chinadrtv.runtime.startup.StartUpExecuter;
import com.chinadrtv.runtime.ws.PaffWsProxyFactory;
import com.chinadrtv.util.PropertiesUtil;
import com.chinadrtv.util.XmlUtil;

public class RemoteMaintainInfoImpl implements StartUpExecuter {

    Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    //项目名称(从属性文件读取)
    private final String PROJECT_NAME = "maintainConfigCenter.project.name";
    //环境参数(从属性文件读取)
    private final String PROJECT_ENVIRONMENT = "maintainConfigCenter.project.env";
    //运维中心WebService地址(从属性文件读取)
    private final String CONFIG_CENTER_ADDRESS = "maintainConfigCenter.address";

    @SuppressWarnings("unchecked")
    @Override
    public void execute() {
//        //从属性文件初始化相应值
//        String configCenterIp = PropertiesUtil.getValue(CONFIG_CENTER_ADDRESS);
//        SystemContext.put(CONFIG_CENTER_ADDRESS, configCenterIp);
//        String system = PropertiesUtil.getValue(PROJECT_NAME);
//        String env = PropertiesUtil.getValue(PROJECT_ENVIRONMENT);
//        //远程服务对象
//        //ConfigurationQueryFacade server = null;
//        //获取结果为XML文件
//        String configInfoXml = null;
//        try {
//            //server = PaffWsProxyFactory.getAppointRemoteBean(ConfigurationQueryFacade.class, configCenterIp);
////            server = PaffWsProxyFactory.getSysInstance(ConfigurationQueryFacade.class, CONFIG_CENTER_ADDRESS);
////            configInfoXml = server.getConfiguration(system, env);
//            logger.debug(configInfoXml);
//        } catch (Exception e) {
//            StringBuffer sb = new StringBuffer();
//            sb.append(PROJECT_NAME).append("=").append(system).append(";");
//            sb.append(PROJECT_ENVIRONMENT).append("=").append(env).append(";");
//            sb.append(CONFIG_CENTER_ADDRESS).append("=").append(configCenterIp).append(";");
//            logger.error("运维中心远程访问错误!");
//            logger.error(sb.toString());
//        }
        //解析XML
//        RspObj rspObj = XmlUtil.toObject(configInfoXml);
//        //判断返回码并加载远程配置
//        if (RspCode.SUCCESS.getCode().equals(rspObj.getCode())) {
//            List<ConfigCenter> cList = (List<ConfigCenter>) rspObj.getObj();
//            if (cList != null) {
//                for (ConfigCenter c : cList) {
//                    SystemContext.put(c.getConfigKey(), c.getConfigValue());
//                }
//            }
//        } else {
//            StringBuffer sb = new StringBuffer();
//            sb.append(PROJECT_NAME).append("=").append(system).append(";");
//            sb.append(PROJECT_ENVIRONMENT).append("=").append(env).append(";");
//            sb.append(CONFIG_CENTER_ADDRESS).append("=").append(configCenterIp).append(";");
//            logger.warn("运维中心返回码错误!");
//            logger.error(sb.toString());
//        }
    }

}
