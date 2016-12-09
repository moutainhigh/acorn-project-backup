package com.chinadrtv.runtime.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.chinadrtv.common.log.LOG_TYPE;


public class PaffWsProxyDynamicProxyFactory {
    private static final Logger                 logger             = LoggerFactory
                                                                       .getLogger("LOG_TYPE.PAFF_COMMON.val");

    //查询jar包中可用的facade接口服务信息列表
    private static Set<Class>                   enableFacadeSet    = new HashSet<Class>();
    //每一接口服务所以集群的url
    private static Map<Class, WsClientProxyMgr> serviceEndPointMap = new HashMap<Class, WsClientProxyMgr>();

    // 1.根据引入的接口获取服务接口  2.通过webservice从运维中心读取所有服务信息
    static {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
        try {
            Resource[] resources = null;
            resources = resourcePatternResolver
                .getResources("classpath*:com/chinadrtv/**/common/facade/*.class");
            if (resources != null && resources.length > 0) {
                logger.info("facade引入接口如下:---------->>>>>>>>>>");
                for (Resource r : resources) {
                    MetadataReader reader = readerFactory.getMetadataReader(r);
                    // 读取接口信息类
                    ClassMetadata classMetadata = reader.getClassMetadata();
                    if (classMetadata.isInterface()) {
                        Class clazz = null;
                        try {
                            clazz = Class.forName(classMetadata.getClassName());
                            enableFacadeSet.add(clazz);
                        } catch (ClassNotFoundException e) {
                            logger.error(e.getMessage(), e);
                        }
                        logger.info("加载接口信息" + clazz);
                    }
                }
                logger.info("------------------------------");
            }

        } catch (IOException e) {
            logger.error("读取facade接口信息异常!");
            logger.error(e.getMessage(), e);
        }
    }

    // 初始化地址
    public static void addWsAddressBean(Class clazz, String url, int status, int priority) {
        if (enableFacadeSet.contains(clazz)) {
            if (serviceEndPointMap.containsKey(clazz)) {
                WsClientProxyMgr wpm = serviceEndPointMap.get(clazz);
                wpm.addWsAddressBean(url, status, priority);
            } else {
                WsClientProxyMgr wpm = new WsClientProxyMgr(clazz);
                wpm.addWsAddressBean(url, status, priority);
                serviceEndPointMap.put(clazz, wpm);
            }
        } else {
            Log.warn("接口:" + clazz + "未在系统中引入");
        }
    }

    //失效某个服务地址
    public static void disableWsAddressBean(Class clazz, String url) {
        if (enableFacadeSet.contains(clazz)) {
            WsClientProxyMgr wpm = serviceEndPointMap.get(clazz);
            wpm.disableCurrentWsAddressBeanByUrl(url);
        }
    }

    /**
     * 获取支持平台远程接口 服务address由运维读取
     * 
     * @param clazz
     * @return
     */
    public static <T> T getWsService(Class<T> clazz) {
        Object obj = serviceEndPointMap.get(clazz).getRemoteService();
        return (T) obj;
    }

    public static void main(String[] args) throws Exception {
        Class c1 = Class.forName("java.lang.reflect.InvocationHandler");
        Class c2 = Class.forName("java.lang.reflect.InvocationHandler");
        System.out.println(c1.hashCode() == c2.hashCode());
        Map<Class, String> map = new HashMap<Class, String>();
        map.put(c1, "sssxx");
        String str = map.get(java.lang.reflect.InvocationHandler.class);
        System.out.println(str);
    }

}
