package com.chinadrtv.erp.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.marketing.core.dto.GrpTaskNumExt;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.marketing.core.service.CustomerBatchCoreService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 *
 * 任务分配
 *
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * User: youngphy.fei
 * Date: 13-8-28
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 *
 *
 *
 */
@Controller
@RequestMapping(value = "/task/allocate")
public class TaskAllocateController extends BaseController {

    private static final Long CampaignAllCateType = 6L;    //可分配营销计划类型;

    private static final Logger logger = LoggerFactory.getLogger(TaskAllocateController.class);

    @Autowired
    private CampaignApiService  campaignApiService;

    @Autowired
    private JobRelationexService jobRelationexService;

    @Autowired
    private CustomerBatchCoreService customerBatchCoreService;


    @Autowired
    private CampaignReceiverService campaignReceiverService;

    @RequestMapping(value="/getAllCampaign")
    public void getAllCampaign(@RequestParam(required = true, defaultValue = "") String jobId,
            HttpServletRequest request, HttpServletResponse response){
        List<Campaign> list =campaignApiService.queryList(jobId);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list==null?"":list), "application/json");
    }

    @RequestMapping(value="/getJobNum")
    public void getJobNum(
            HttpServletRequest request, HttpServletResponse response){
//    	String deptNum = SecurityHelper.getLoginUser().getDepartment();
    	
    	//modify by zhaizy
    	String agentId = SecurityHelper.getLoginUser().getUserId();
        List<AgentJobs>list = jobRelationexService.queryJob(agentId);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list==null?"":list), "application/json");
    }

    @RequestMapping(value="/customerBatchByGroupCode")
    public void customerBatchByGroupCode(
            @RequestParam(required = false, defaultValue = "") String groupCode,
            HttpServletRequest request, HttpServletResponse response){
        List<CustomerBatch>list = customerBatchCoreService.querBatchBygroupCode(groupCode);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list==null?"":list), "application/json");
    }
    
    @RequestMapping(value="/queryGroupList")
    public void queryGroupList(@RequestParam(required = true, defaultValue = "") String jobId,
    		@RequestParam(required = false, defaultValue = "") String groupcode,
            @RequestParam(required = false, defaultValue = "") String campaignId,
//            @RequestParam(required = false, defaultValue = "") String jobNum,
            @RequestParam(required = false, defaultValue = "") String customerBatch,
            @RequestParam(required = false, defaultValue = "") String dataState,
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	//modify by zhaizy
    	String deptNum = SecurityHelper.getLoginUser().getDepartment();
    	List<Grp> grps = jobRelationexService.queryJobGroup(deptNum,jobId);
    	if(StringUtils.isNotBlank(groupcode)) {
    		Iterator<Grp> iteGrp = grps.iterator();
    		while(iteGrp.hasNext()){
	    		Grp grp = iteGrp.next();
	    		if(StringUtils.isNotBlank(grp.getGrpname()) && grp.getGrpname().indexOf(groupcode)>=0) {
	    			continue;
	    		}
	    		if(StringUtils.isNotBlank(grp.getGrpid()) && grp.getGrpid().indexOf(groupcode)>=0) {
	    			continue;
	    		}
	    		iteGrp.remove();
	    	}
    	}
    	//基于效率, 查询剩余数量不能次数太多
    	if(StringUtils.isNotBlank(customerBatch)) {
	    	List<Grp> grpexts = new ArrayList<Grp>();
	        CampaignReceiverDto campaignReceiverDto = new CampaignReceiverDto();
	        if(! StringUtil.isNullOrBank(campaignId)) campaignReceiverDto.setCampaignId(Long.valueOf(campaignId));
	        if(! StringUtil.isNullOrBank(jobId)) campaignReceiverDto.setJobid(jobId);
	        if(! StringUtil.isNullOrBank(customerBatch)) campaignReceiverDto.setBatchCode(customerBatch);

	    	for(Grp grp : grps) {
	    		GrpTaskNumExt grpext = new GrpTaskNumExt();
	    		grpext.setGrpid(grp.getGrpid());
	    		grpext.setGrpname(grp.getGrpname());
	    		if(StringUtils.isNotBlank(grp.getGrpid())) {
	    			campaignReceiverDto.setGroupId(grp.getGrpid());
		    		//查询剩余数量
	    	        campaignReceiverDto.setStatus("1");
					int count = campaignReceiverService.getAssigntotle(campaignReceiverDto);
					grpext.setOverplusNum(count);
					grpext.setObtainQty(count);
					//查询已分配
					campaignReceiverDto.setStatus(null);
					List<String> statuses = new ArrayList<String>();
					statuses.add("2");
					statuses.add("3");
					campaignReceiverDto.setStatuses(statuses);
					int obtainQty = campaignReceiverService.getAssigntotle(campaignReceiverDto);
					grpext.setObtainQty4use(obtainQty);

	    		}
	    		grpexts.add(grpext);
	    	}
	    	HttpUtil.ajaxReturn(response, jsonBinder.toJson(grpexts), "application/json");
    	} else {
    		HttpUtil.ajaxReturn(response, jsonBinder.toJson(grps), "application/json");
    	}

    }

    //获取可分配数量
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/findAssigenableNum")
    public void findAssigenableNum(
            @RequestParam(required = false, defaultValue = "") String campaignId,//
            @RequestParam(required = false, defaultValue = "") String jobNum,
            @RequestParam(required = false, defaultValue = "") String customerBatch,
            @RequestParam(required = false, defaultValue = "") String dataState,
            HttpServletRequest request, HttpServletResponse response){
        CampaignReceiverDto campaignReceiverDto = new CampaignReceiverDto();
        if(! StringUtil.isNullOrBank(campaignId)) campaignReceiverDto.setCampaignId(Long.valueOf(campaignId));
        if(! StringUtil.isNullOrBank(jobNum)) campaignReceiverDto.setJobid(jobNum);
        if(! StringUtil.isNullOrBank(customerBatch)) campaignReceiverDto.setBatchCode(customerBatch);
        if(! StringUtil.isNullOrBank(dataState)) campaignReceiverDto.setStatus(dataState);

        int count = campaignReceiverService.getAssigntotle(campaignReceiverDto);
        Map map = new HashMap();
        map.put("count",count);
        map.put("result",true);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

       //分配话务到组
    @RequestMapping(value="/allotToGroup")
    public void allotToGroup(
            @RequestParam(required = false, defaultValue = "") String groupIds,
            @RequestParam(required = false, defaultValue = "") String groupNums,
            @RequestParam(required = false, defaultValue = "") String campaignId,//
            @RequestParam(required = false, defaultValue = "") String jobNum,
            @RequestParam(required = false, defaultValue = "") String customerBatch,
            @RequestParam(required = false, defaultValue = "") String dataState,
            @RequestParam(required = false, defaultValue = Constants.SEQUENCIAL_ALLOCATION) String alloStrategy,
            HttpServletRequest request, HttpServletResponse response){
        AgentUser user = SecurityHelper.getLoginUser();
        Map map =  new HashMap();
        Boolean result = false;
        String message="分配到坐席组失败";


        CampaignReceiverDto campaignReceiverDto = new CampaignReceiverDto();
        campaignReceiverDto.setCampaignId(Long.valueOf(campaignId));
        campaignReceiverDto.setJobid(jobNum);
        campaignReceiverDto.setBatchCode(customerBatch);
        campaignReceiverDto.setStatus(dataState);


        try {
            String arrayGnums[] = groupNums.split(",");
            int totleNum = 0;
            for(String num : arrayGnums){
                if (!StringUtil.isNullOrBank(num)) {
                    totleNum+=Integer.parseInt(num);
                }
            }

             campaignReceiverService.assignToGroup(campaignReceiverDto,groupIds,groupNums,user, alloStrategy);

                 result =true;
                 message = "已成功分配"+totleNum+"条数据到坐席组";
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);  //To change body of catch statement use File | Settings | File Templates.
            message+=e.getMessage();
            logger.error(message+e.getMessage());

        }finally {


        map.put("result",result);
        map.put("msg",message);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }
    }




}
