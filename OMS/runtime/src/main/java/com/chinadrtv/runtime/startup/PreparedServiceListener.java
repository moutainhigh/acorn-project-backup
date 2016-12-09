package com.chinadrtv.runtime.startup;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.startup.impl.RemoteConfigInfoImpl;
import com.chinadrtv.runtime.startup.impl.RemoteMaintainInfoImpl;


public class PreparedServiceListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");
    
    /** 启动参数名称 */
    private static final String PARAM = "preparedServiceListener_param";
    private static final String CONF = "configuration";
    private static final String MATN = "maintainance";

    /**
     * 启动待执行的列表
     */
    private volatile List<StartUpExecuter> executers;

    private volatile boolean started = false;

    /**
     * 注册所有启动执行项到executers按从上下顺序排列，先加入的先执行
     */
    private synchronized void initExecuters(String initParam) {
        if (initParam != null && initParam.length() > 0) {
            if (initParam.indexOf(CONF) > -1) {
                executers.add(new RemoteConfigInfoImpl());
            }
            if (initParam.indexOf(MATN) > -1) {
                executers.add(new RemoteMaintainInfoImpl());
            }
        } else {
            executers.add(new RemoteConfigInfoImpl());
            executers.add(new RemoteMaintainInfoImpl());
        }
    }

    /**
     * 保证该方法的内容只被执行一次
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("系统初始化开始");
        if (executers == null) {
            executers = new LinkedList<StartUpExecuter>();
            synchronized (executers) {
                if (!started) {
                    String initParam = servletContextEvent.getServletContext().getInitParameter(PARAM);
                    initExecuters(initParam);
                    executeAll();
                    started = true;
                }
            }
        }
    }

    /**
     * 按数组坐标顺序执行execute方法
     */
    private synchronized void executeAll() {
        for (StartUpExecuter executer : executers) {
            executer.execute();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        executers = null;
        started = false;
    }

}
