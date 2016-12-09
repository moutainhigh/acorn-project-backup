package com.chinadrtv.scheduler.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.common.facade.code.RspCode;
import com.chinadrtv.scheduler.common.integration.JmsIntegration;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateResponseLocal;
import com.chinadrtv.scheduler.common.integration.ws.WebServiceUtilClient;
import com.chinadrtv.scheduler.service.model.Scheduler;
import com.chinadrtv.util.XmlUtil;


@Controller
@RequestMapping("/scheduler")
public class SchedulerMVC {
    Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");

    @Autowired
    private JmsIntegration jmsIntegration;

    @Autowired
    private WebServiceUtilClient webServiceUtilClient;

    //TOPIC运行状态码
    private final int TOPIC_STATUS_START = 1;
    private final int TOPIC_STATUS_STOP = 0;
    private final int TOPIC_STATUS_REPEAT = 2;

    /**
     * 列表主页面
     *
     * @return
     */
    @RequestMapping(value = "/listScheduler", method = RequestMethod.GET)
    public String listScheduler() {
        return "/scheduler/listScheduler";
    }

    /**
     * 列表请求数据
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/listSchedulerData", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> listSchedulerData(ModelMap model, Page page, Scheduler scheduler) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("scheduler", scheduler);
        param.put("operate", "list");
        DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
        databaseOperateRequestLocal.setParam(XmlUtil.toXml(param));

        String quartzWebService = "test";//WebServiceAddressUtil.getRandomWebServiceAddress(); //随机获取Quartz　WebService地址

        if (quartzWebService == null) {
            logger.warn("没有可用的Quartz　WebService服务，返回空列表");
            resultMap.put("list", new ArrayList());
            resultMap.put("maxCount", 0);
        } else {
            DatabaseOperateResponseLocal databaseOperateResponseLocal = this.webServiceUtilClient
                .doDataInvokeWs(databaseOperateRequestLocal);
            String respCode = databaseOperateResponseLocal.getRespCode();
            if (RspCode.SUCCESS.getCode().equals(respCode)) {
                logger.info("获取后台数据结果：" + respCode + ":成功");
                String rsp = databaseOperateResponseLocal.getDatabaseOperateRsp();
                PaginationBean<?> pageinatine = XmlUtil.toObjectByAnnotation(rsp,
                    PaginationBean.class);

                resultMap.put("list", pageinatine.getPageList());
                resultMap.put("maxCount", pageinatine.getTotalRecords());
            } else {
                logger.warn("获取后台数据结果：" + respCode + ":失败"
                            + ReflectionToStringBuilder.toString(param));
                resultMap.put("list", new ArrayList());
                resultMap.put("maxCount", 0);
            }
        }

        return resultMap;
    }

    /**
     * TOPIC任务添加页面
     *
     * @return
     */
    @RequestMapping(value = "/addSchedulerData", method = RequestMethod.GET)
    public ModelAndView addSchedulerData() {
        ModelAndView mav = new ModelAndView("/scheduler/addSchedulerData");
        String systemNames = SystemContext.get("systemNames");
        //todo 暂时写死 李宇
        systemNames = "aocrn-iagent,acorn-oms";

        if (systemNames != null) {
            mav.addObject("systemNames", systemNames.split(","));
        } else {
            mav.addObject("systemNames", new ArrayList<String>());
        }
        return mav;
    }

    /**
     * CRON表达式配置页面
     *
     * @return
     */
    @RequestMapping(value = "/cronExpression", method = RequestMethod.GET)
    public String cronExpression() {
        return "/scheduler/cronExpression";
    }

    /**
     * 添加TOPIC任务操作
     *
     * @param jobName
     * @param jobTopic
     * @param jobCronExpression
     * @param jobDescription
     * @return
     */
    @RequestMapping(value = "/saveScheduler", method = RequestMethod.POST)
    public ModelAndView saveScheduler(@RequestParam(value = "jobSystem", required = true) String jobSystem,
                                      @RequestParam(value = "jobName", required = true) String jobName,
                                      @RequestParam(value = "jobTopic", required = true) String jobTopic,
                                      @RequestParam(value = "jobCronExpression", required = true) String jobCronExpression,
                                      @RequestParam(value = "jobDescription") String jobDescription) {

        String quartzWebService = "test"; //WebServiceAddressUtil.getRandomWebServiceAddress(); //随机获取Quartz　WebService地址

        if (quartzWebService == null) {
            logger.warn("没有可用的Quartz　WebService服务，直接返回");
            ModelAndView mav = new ModelAndView(new RedirectView("addSchedulerData"));
            return mav;
        } else {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("jobSystem", jobSystem);
            param.put("jobName", jobName);
            param.put("jobTopic", jobTopic);
            param.put("jobCronExpression", jobCronExpression);
            param.put("jobDescription", jobDescription);
            param.put("jobStatus", TOPIC_STATUS_STOP);
            param.put("operate", "add");

            DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
            databaseOperateRequestLocal.setParam(XmlUtil.toXml(param));
            DatabaseOperateResponseLocal databaseOperateResponseLocal = this.webServiceUtilClient
                    .doDataInvokeWs(databaseOperateRequestLocal);
            String respCode = databaseOperateResponseLocal.getRespCode();
            if (RspCode.SUCCESS.getCode().equals(respCode)) {
                logger.info("操作后台数据结果：" + respCode + ":成功");
            } else {
                logger.warn("操作后台数据结果：" + respCode + ":失败" + ReflectionToStringBuilder.toString(param));
            }

            ModelAndView mav = new ModelAndView(new RedirectView("listScheduler"));
            return mav;
        }
    }

    /**
     * 发送开始定时任务消息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/startScheduler", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> startScheduler(@RequestParam(value = "id", required = true) int id) {
        Map<String, String> result = new HashMap<String, String>();

        try {
            String quartzWebService = "test";//WebServiceAddressUtil.getRandomWebServiceAddress(); //随机获取Quartz　WebService地址

            if (quartzWebService == null) {
                logger.warn("没有可用的Quartz　WebService服务，直接返回");
                result.put("result", "2");
            } else {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", id);
                param.put("jobStatus", TOPIC_STATUS_START);
                param.put("operate", "modify");

                DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
                databaseOperateRequestLocal.setParam(XmlUtil.toXml(param));
                DatabaseOperateResponseLocal databaseOperateResponseLocal = this.webServiceUtilClient
                        .doDataInvokeWs(databaseOperateRequestLocal);
                String respCode = databaseOperateResponseLocal.getRespCode();
                if (RspCode.SUCCESS.getCode().equals(respCode)) {
                    logger.info("操作后台数据结果：" + respCode + ":成功");
                    //触发MQ消息 
                    this.sendTopicMessage(id, TOPIC_STATUS_START);

                    result.put("result", "1");
                } else {
                    logger.warn("操作后台数据结果：" + respCode + ":失败"
                            + ReflectionToStringBuilder.toString(param));
                    result.put("result", "2");
                }
            }
        } catch (Exception e) {
            result.put("result", "2");
            logger.warn("执行开始操作失败,id:" + id, e);
        }
        return result;
    }

    /**
     * 发送停止定时任务消息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/stopScheduler", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> stopScheduler(@RequestParam(value = "id", required = true) int id) {
        Map<String, String> result = new HashMap<String, String>();

        try {
            String quartzWebService = "test";//WebServiceAddressUtil.getRandomWebServiceAddress(); //随机获取Quartz　WebService地址

            if (quartzWebService == null) {
                logger.warn("没有可用的Quartz　WebService服务，直接返回");
                result.put("result", "2");
            } else {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", id);
                param.put("jobStatus", TOPIC_STATUS_STOP);
                param.put("operate", "modify");

                DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
                databaseOperateRequestLocal.setParam(XmlUtil.toXml(param));
                DatabaseOperateResponseLocal databaseOperateResponseLocal = this.webServiceUtilClient
                        .doDataInvokeWs(databaseOperateRequestLocal);
                String respCode = databaseOperateResponseLocal.getRespCode();
                if (RspCode.SUCCESS.getCode().equals(respCode)) {
                    logger.info("操作后台数据结果：" + respCode + ":成功");
                    //触发MQ消息 
                    this.sendTopicMessage(id, TOPIC_STATUS_STOP);

                    result.put("result", "1");
                } else {
                    logger.warn("操作后台数据结果：" + respCode + ":失败"
                            + ReflectionToStringBuilder.toString(param));
                    result.put("result", "2");
                }
            }
        } catch (Exception e) {
            result.put("result", "2");
            logger.warn("执行停止操作失败,id:" + id, e);
        }

        return result;
    }

    /**
     * 手工触发，执行job任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/repeatScheduler", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> repeatScheduler(@RequestParam(value = "id", required = true) int id) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            this.sendTopicMessage(id, TOPIC_STATUS_REPEAT);
            result.put("result", "1");
        } catch (Exception e) {
            logger.warn("手工触发执行job任务失败,id:" + id);
            result.put("result", "1");
        }

        return result;
    }

    /**
     * 发送MQ消息方法
     *
     * @param id     ：TOPIC任务id
     * @param status ：停止0/运行1
     */
    private void sendTopicMessage(final int id, final int status) throws Exception {
        HashMap<String, Object> schedulerMsg = new HashMap<String, Object>();
        schedulerMsg.put("id", id);
        schedulerMsg.put("status", status);

        jmsIntegration.sendJmsMessage(schedulerMsg);
    }
}
