package com.chinadrtv.scheduler.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.util.JsonUtil;

public class WebServiceAddressUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(WebServiceAddressUtil.class);

    /**
     * 随机获取Quartz　WebService地址
     * 
     * @return
     */
    public static String getRandomWebServiceAddress() {
        String quartzWebServiceString = SystemContext.get("quartzWebService");
        
        logger.info("quartzWebServiceString", quartzWebServiceString);

        //quartzWebServiceString = "http://localhost:8081/scheduler/services/webServiceUtilRemote";

       /* if (quartzWebServiceString != null) {
            JsonUtil jsonUtil = new JsonUtil();
            ArrayList<Map<String, String>> list = jsonUtil.toObject(quartzWebServiceString,
                ArrayList.class);
            if (list.size() > 0) {
                Random random = new Random();
                int i = random.nextInt(list.size());
                Map<String, String> map = list.get(i);
                return map.get("value");
            }
        }*/
        return quartzWebServiceString;
    }

    /**
     * 获取指定地址Quartz WebService地址
     * 
     * @param hostName
     * @return
     */
    public static String getHostWebServiceAddress(String hostName) {
        String quartzWebServiceString = SystemContext.get("quartzWebService");
        /*if (quartzWebServiceString != null) {
            JsonUtil jsonUtil = new JsonUtil();
            ArrayList<Map<String, String>> list = jsonUtil.toObject(quartzWebServiceString,
                ArrayList.class);
            for (Map<String, String> map : list) {
                String key = map.get("key");
                if (hostName.equals(key)) {
                    return map.get("value");
                }
            }
        }*/
        return quartzWebServiceString;
    }

    /**
     * 获取Quartz服务器IP地址
     * 
     * @return
     */
    public static List<String> getHostNameList() {
        String quartzServiceIpString = SystemContext.get("quartzServerIP");
        quartzServiceIpString = "调度服务器一";
        List<String> quartzServiceIpList = new ArrayList<String>();
        if (quartzServiceIpString != null) {
            String[] quartzServiceIps = quartzServiceIpString.split(",");
            for (String quartzServiceIp : quartzServiceIps) {
                quartzServiceIpList.add(quartzServiceIp);
            }
        }
        return quartzServiceIpList;
    }

}
