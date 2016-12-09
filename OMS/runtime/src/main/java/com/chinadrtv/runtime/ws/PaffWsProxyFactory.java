package com.chinadrtv.runtime.ws;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.context.ApplicationContextConfig;
import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.common.log.LOG_TYPE;


@SuppressWarnings("unchecked")
public class PaffWsProxyFactory {

    private static Map<String, JaxWsProxyFactoryBean> wsFactoryBeanMap = new HashMap<String, JaxWsProxyFactoryBean>();

    private static Map<String, Integer> countMap = new HashMap<String, Integer>();

    private static Lock WsProxyFactoryLock = new ReentrantLock();

    private static Lock createUrlBeanLock = new ReentrantLock();

    private static final Logger LOG = LoggerFactory
            .getLogger("LOG_TYPE.PAFF_COMMON.val");

    private PaffWsProxyFactory() {
    }

    /**
     * 获取支持平台远程接口
     *
     * @param clazz    接口class
     * @param wsUrlKey 接口地址Key
     * @return 返回接口
     */
    public static <T> T getSysInstance(Class<T> clazz, String wsUrlKey) {
        return getSysRemoteBean(clazz, wsUrlKey);
    }

    /**
     * 获取业务系统远程接口
     *
     * @param clazz    接口class
     * @param wsUrlKey 接口地址Key
     * @return 返回接口
     */
    public static <T> T getBizInstance(Class<T> clazz, String wsUrlKey) {
        return getBizRemoteBean(clazz, wsUrlKey);
    }

    /**
     * 获取支持平台远程接口
     *
     * @param clazz    接口class
     * @param wsUrlKey 接口地址Key
     * @return 返回接口
     */
    private static <T> T getSysRemoteBean(Class<T> clazz, String wsUrlKey) {
        String urls = getSysRemoteAddress(wsUrlKey);
        UrlBean ub = createUrlBean(wsUrlKey, urls);
        return (T) setTimeOut(getJaxWsProxyFactoryBean(clazz, ub).create());
    }

    /**
     * 获取业务系统远程接口
     *
     * @param clazz    接口class
     * @param wsUrlKey 接口地址Key
     * @return 返回接口对象
     */
    private static <T> T getBizRemoteBean(Class<T> clazz, String wsUrlKey) {
        String urls = getBizRemoteAddress(wsUrlKey);
        UrlBean ub = createUrlBean(wsUrlKey, urls);
        return (T) setTimeOut(getJaxWsProxyFactoryBean(clazz, ub).create());
    }

    /**
     * 获取指定的远程接口
     *
     * @param clazz   接口class
     * @param address 接口地址
     * @return
     */
    public static <T> T getAppointRemoteBean(Class<T> clazz, String address) {
        JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
        proxyFactoryBean.setServiceClass(clazz);
        proxyFactoryBean.setAddress(address);
        return (T) setTimeOut(proxyFactoryBean.create());
    }

    /**
     * 设置超时
     *
     * @param o
     * @return
     */
    private static <T> T setTimeOut(Object o) {
        // 设置客户端等待服务端响应时间  
        Client proxy = ClientProxy.getClient(o);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();

        // 连接服务器超时时间 1分钟  
        policy.setConnectionTimeout(60 * 1000);
        // 等待服务器响应超时时间 2分钟  
        policy.setReceiveTimeout(120 * 1000);
        conduit.setClient(policy);
        return (T) o;

    }

    /**
     * 获取远程接口。<br/>如果map中没有，创建远程接口，并put到map中
     *
     * @param clazz 接口class
     * @param ub    url封装对象
     * @return 返回接口对象
     */
    private static <T> JaxWsProxyFactoryBean getJaxWsProxyFactoryBean(Class<T> clazz, UrlBean ub) {
        WsProxyFactoryLock.lock();
        try {
            if (!wsFactoryBeanMap.containsKey(ub.getFactoryBeanId())) {
                JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
                proxyFactoryBean.setServiceClass(clazz);
                proxyFactoryBean.setAddress(ub.getUrl());
                wsFactoryBeanMap.put(ub.getFactoryBeanId(), proxyFactoryBean);

            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            WsProxyFactoryLock.unlock();
        }

        return wsFactoryBeanMap.get(ub.getFactoryBeanId());
    }

    /**
     * 创建url封装对象。
     *
     * @param wsUrlKey
     * @param url
     * @return
     */
    private static UrlBean createUrlBean(String wsUrlKey, String url) {
        if (StringUtils.isBlank(url)) {
            LOG.warn("wsUrlKey[{}] is not exist", wsUrlKey);
            throw new RuntimeException("wsUrlKey is not exist");
        }
        String[] urls = url.split(",");
        int len = urls.length;
        int next = 0;
        UrlBean urlBean = null;
        try {
            createUrlBeanLock.lock();
            Integer curr = countMap.get(wsUrlKey);
            if (curr != null)
                next = curr + 1;
            if (next >= len)
                next = 0;
            countMap.put(wsUrlKey, next);
            urlBean = new UrlBean();
            urlBean.setFactoryBeanId(wsUrlKey + urls[next]);
            urlBean.setUrl(urls[next]);
        } finally {
            createUrlBeanLock.unlock();
        }

        return urlBean;
    }

    private static String getSysRemoteAddress(String wsUrlKey) {
        return SystemContext.get(wsUrlKey);
    }

    private static String getBizRemoteAddress(String wsUrlKey) {
        return ApplicationContextConfig.get(wsUrlKey);
    }

    static class UrlBean {
        private String url;

        private String factoryBeanId;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFactoryBeanId() {
            return factoryBeanId;
        }

        public void setFactoryBeanId(String factoryBeanId) {
            this.factoryBeanId = factoryBeanId;
        }

    }

}
