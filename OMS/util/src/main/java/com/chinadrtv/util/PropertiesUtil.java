package com.chinadrtv.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertiesUtil {

    private static Logger              logger            = LoggerFactory.getLogger("paff.common");
    private static Map<String, String> cachedProp        = new HashMap<String, String>();
    private static List<String>        fileNames         = new ArrayList<String>();
    private static final String        DEFAULT_CONF_FILE = "env.properties";
    private static final String        IS_DEV            = "isDev";
    private static final String        DEFAULT_DEV_VAL   = "${isDev}";
    private static final String        RUNENV            = "run.env";

    static {
        loadFile(DEFAULT_CONF_FILE);//加载env.properties
        String isDev = PropertiesUtil.getValue(IS_DEV);//获取isDev属性值
        if (DEFAULT_DEV_VAL.equals(isDev)) {//判断是否打包
            //未打包环境加载属性文件
            String runEnv = PropertiesUtil.getValue(RUNENV);//加载属性文件名
            PropertiesUtil.loadFile(ConfFileEnum.getFileName(runEnv));
        }
        for (String key : cachedProp.keySet()) {
            System.setProperty(key, cachedProp.get(key));
            logger.debug(key + "=" + cachedProp.get(key));
        }
    }

    /**
     * 加载属性文件
     */
    public static void loadFile(String fileName) {
        for (String fname : fileNames) {
            if (fname.equals(fileName)) {
                return;
            }
        }
        //获取Classpath下的配置文件
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        Properties fileProp = new Properties();
        try {
            fileProp.load(input);
            logger.info("load " + fileName + " success !");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (fileProp.get(IS_DEV) == null) {
            logger.warn("开发环境下的配置文件" + fileName + "没有找到！");
        } else {
            for (Object key : fileProp.keySet()) {
                cachedProp.put((String) key, (String) fileProp.get(key));
            }
        }
    }

    /**
     * 获取属性值
     */
    public static String getValue(String key) {
        if (cachedProp.containsKey(key)) {
            return cachedProp.get(key);
        }
        return null;
    }

}
