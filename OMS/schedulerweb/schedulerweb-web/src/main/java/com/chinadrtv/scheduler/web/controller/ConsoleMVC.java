package com.chinadrtv.scheduler.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.common.facade.code.RspCode;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateResponseLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusResponseLocal;
import com.chinadrtv.scheduler.common.integration.ws.WebServiceUtilClient;
import com.chinadrtv.scheduler.common.util.WebServiceAddressUtil;
import com.chinadrtv.scheduler.service.model.JobHistory;
import com.chinadrtv.util.XmlUtil;



@Controller
@RequestMapping("/console")
public class ConsoleMVC {
    //Logger                       logger = LoggerFactory.getLogger(LOG_TYPE.PAFF_SERVICE.val);

    @Autowired
    private WebServiceUtilClient webServiceUtilClient;

    /**
     * 日志监控主界面
     * 
     * @return
     */
    @RequestMapping(value = "/historyConsole", method = RequestMethod.GET)
    public ModelAndView historyConsole(@RequestParam(value = "id", required = true) int id) {
        ModelAndView mav = new ModelAndView("/console/historyConsole");
        mav.addObject("id", id);
        return mav;
    }

    /**
     * 分页显示job运行日志列表信息
     * 
     * @param model
     * @param page
     * @param jobHistory
     * @return
     */
    @RequestMapping(value = "/listHistoryData", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> listHistoryData(ModelMap model, Page page, JobHistory jobHistory) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("page", page);
        param.put("jobHistory", jobHistory);
        param.put("operate", "history");
        DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
        databaseOperateRequestLocal.setParam(XmlUtil.toXml(param));

        String quartzWebService = "test"; //WebServiceAddressUtil.getRandomWebServiceAddress(); //随机获取Quartz　WebService地址

        if (quartzWebService == null) {
            //logger.warn("没有可用的Quartz　WebService服务，返回空列表");
            resultMap.put("list", new ArrayList());
            resultMap.put("maxCount", 0);
        } else {
            DatabaseOperateResponseLocal databaseOperateResponseLocal = this.webServiceUtilClient
                .doDataInvokeWs(databaseOperateRequestLocal);
            String respCode = databaseOperateResponseLocal.getRespCode();
            if (RspCode.SUCCESS.getCode().equals(respCode)) {
                //logger.info("获取后台数据结果：" + respCode + ":成功");
                String rsp = databaseOperateResponseLocal.getDatabaseOperateRsp();
                PaginationBean<?> pageinatine = XmlUtil.toObjectByAnnotation(rsp,
                    PaginationBean.class);

                resultMap.put("list", pageinatine.getPageList());
                resultMap.put("maxCount", pageinatine.getTotalRecords());
            } else {
                //logger.warn("获取后台数据结果：" + respCode + ":失败" + ReflectionToStringBuilder.toString(param));
                resultMap.put("list", new ArrayList());
                resultMap.put("maxCount", 0);
            }
        }

        return resultMap;
    }

    /**
     * 监控主界面
     */
    @RequestMapping(value = "/mainConsole", method = RequestMethod.GET)
    public String mainConsole() {
        return "/console/mainConsole";
    }

    /**
     * 获取监控主机信息
     * 
     * @return
     */
    @RequestMapping(value = "/listConsoleData", method = RequestMethod.POST)
    public @ResponseBody
    List<Map<String, String>> listConsoleData() {
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        List<String> quartzServiceIpList = WebServiceAddressUtil.getHostNameList();
        for (String quartzServiceIp : quartzServiceIpList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("hostName", quartzServiceIp);
            resultList.add(map);
        }

        return resultList;
    }

    /**
     * Quartz服务器job列表主界面
     * 
     * @return
     */
    @RequestMapping(value = "/searchSchedulerInfo", method = RequestMethod.GET)
    public ModelAndView searchSchedulerInfo(@RequestParam(value = "hostName", required = true) String hostName) {
        ModelAndView mav = new ModelAndView("/console/searchSchedulerInfo");
        mav.addObject("hostName", hostName);
        return mav;
    }

    /**
     * 获取Quartz服务器上Scheduler job信息
     * 
     * @param hostName  Quartz 服务器地址
     * @return
     */
    @RequestMapping(value = "/searchSchedulerData", method = RequestMethod.POST)
    public @ResponseBody
    List<HashMap<String, String>> searchSchedulerData(@RequestParam(value = "hostName", required = true) String hostName) {
        String quartzWebService = "test";//WebServiceAddressUtil.getHostWebServiceAddress(hostName);

        if (quartzWebService == null)
            return new ArrayList<HashMap<String, String>>();
        else {
            QuartzJobStatusRequestLocal quartzJobStatusRequestLocal = new QuartzJobStatusRequestLocal();
            QuartzJobStatusResponseLocal quartzJobStatusResponseLocal = this.webServiceUtilClient
                .doInvokeWs(hostName, quartzJobStatusRequestLocal);
            String respCode = quartzJobStatusResponseLocal.getRespCode();
            if (RspCode.SUCCESS.getCode().equals(respCode)) {
                //logger.info("获取后台数据结果：" + respCode + ":成功");
                String QuartzJobStatusList = quartzJobStatusResponseLocal.getQuartzJobStatusList();
                List<HashMap<String, String>> list = XmlUtil.toObject(QuartzJobStatusList);
                return list;
            } else {
                //logger.warn("获取后台数据结果：" + respCode + ":失败"+hostName);
                return new ArrayList<HashMap<String, String>>();
            }
        }
    }
}
