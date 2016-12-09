package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.service.impl.CampaignBPMTaskServiceImpl;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品相关信息(推荐商品/话术)
 * User: gdj
 * Date: 13-5-29
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RecommendController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);
    @Autowired
    private PlubasInfoService plubasInfoService;

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Autowired
    private LeadService leadService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private JobRelationexService jobRelationexService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/product/recommend/lead",method= RequestMethod.POST)
    public String recommendLead(
            @RequestParam(required = true) String tollFreeNum,
            @RequestParam(required = true) String telephone,
            @RequestParam(required = true) String contactId,
            @RequestParam(required = false) String campainId,
            @RequestParam(required = false) String leadId,
            @RequestParam(required = false) String contactType,
            @RequestParam(required = false) Long sourceType,
            @RequestParam(required = false) String cticonnid,
            @RequestParam(required = false) String acdgroup,
            @RequestParam(required = false) String begindate,
            @RequestParam(required = false) Boolean callback
            )
    {
/*    	long initStart = System.currentTimeMillis();
		if(logger.isDebugEnabled()) {
			logger.debug("recommendLead starting at the time :"+System.currentTimeMillis());
		}
		long startTime = System.currentTimeMillis();*/
        AgentUser agentUser = SecurityHelper.getLoginUser();
//        logger.debug("GetLoginUser cost :"+(System.currentTimeMillis()-startTime));
        if(agentUser != null) {
            try
            {
                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                startTime = System.currentTimeMillis();
                CampaignDto dto;
                if(StringUtils.isNotBlank(campainId) && Long.parseLong(campainId) > 0){
                    dto = campaignApiService.getCampaign(Long.parseLong(campainId));
                } else {
                    dto = campaignApiService.queryInboundCampaign(tollFreeNum, dateformat1.format(new Date()));
                }
//        		logger.debug("QueryInboundCampaign cost :"+(System.currentTimeMillis()-startTime));
        		
                if(dto != null){
//                    startTime = System.currentTimeMillis();
                    String groupType = userService.getGroupType(agentUser.getUserId());
//            		logger.debug("GetGroupType cost :"+(System.currentTimeMillis()-startTime));

                    Lead lead = null;
                    if(StringUtils.isNotBlank(leadId)){
                        try{
                            lead = leadService.get(Long.parseLong(leadId));
                            //转接用户,重新创建lead
                            if(!agentUser.getUserId().equalsIgnoreCase(lead.getOwner())){
                                lead = new Lead();
                                lead.setPreviousLeadId(Long.parseLong(leadId));
                                lead.setOwner(agentUser.getUserId());
                                lead.setCreateUser(agentUser.getUserId());
                                lead.setCreateDate(new Date());
                                lead.setBeginDate(new Date());
                            }
                        } catch (Exception ex){
                            lead = new Lead();
                            lead.setOwner(agentUser.getUserId());
                            lead.setCreateUser(agentUser.getUserId());
                            lead.setCreateDate(new Date());
                            lead.setBeginDate(new Date());
                        }
                    } else {
                        lead = new Lead();
                        lead.setOwner(agentUser.getUserId());
                        lead.setCreateUser(agentUser.getUserId());
                        lead.setCreateDate(new Date());
                        lead.setBeginDate(new Date());
                    }

//                    if("IN".equalsIgnoreCase(groupType)){
//                        lead.setOutbound(false);
//                    }
//                    else if("OUT".equalsIgnoreCase(groupType)){
//                        lead.setOutbound(true);
//                    }

                    if("OUT".equalsIgnoreCase(groupType)){
                        lead.setOutbound(true);
                    } else {
                        lead.setOutbound(false);
                    }

                    lead.setCampaignId(dto.getId());
                    lead.setAni(telephone);
                    //不需要再把400转化为落地号
                    //lead.setDnis(dto.getDnis());
                    lead.setDnis(tollFreeNum);
                    if(callback != null && callback){
                        lead.setTaskSourcType(CampaignTaskSourceType.INCOMING.getIndex());
                    }
                    else if(sourceType != null){
                        lead.setTaskSourcType(sourceType);
                    } else {
                        lead.setTaskSourcType(CampaignTaskSourceType.SELFCREATE.getIndex());
                    }
                    //0:呼入;1:呼出
                    lead.setCallDirection(0L);
                    //0：开始，1：正常结束，2：系统关闭，3：客户拒绝，4：其他原因结束
                    lead.setStatus(0L);

                    if("2".equals(contactType)){
                        lead.setPotentialContactId(contactId);
                    }
                    else
                    {
                        lead.setContactId(contactId);
                    }

                    lead.setUserGroup(agentUser.getWorkGrp());
                    lead.setUpdateUser(agentUser.getUserId());
                    lead.setUpdateDate(new Date());

                    //添加交互
                    LeadInteraction leadInteraction = new LeadInteraction();
                    leadInteraction.setCreateUser(agentUser.getUserId());
                    leadInteraction.setCreateDate(new Date());
                    leadInteraction.setBeginDate(new Date());
                    leadInteraction.setGroupCode(agentUser.getWorkGrp());
                    leadInteraction.setAcdGroup(acdgroup);

                    SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    try {
                        leadInteraction.setCtiStartDate(df.parse(begindate));
                    }catch (Exception ex){
                        leadInteraction.setCtiStartDate(null);
                    }
                    if(StringUtils.isNotBlank(cticonnid)){
                        leadInteraction.setCallId(cticonnid);
                    }


                    if("IN".equalsIgnoreCase(groupType)){
                        leadInteraction.setInterActionType(LeadInteractionType.INBOUND_IN.getIndexString());
                    }
                    else if("OUT".equalsIgnoreCase(groupType)){
                        leadInteraction.setInterActionType(LeadInteractionType.OUTBOUND_IN.getIndexString());
                    } else if("CPN".equalsIgnoreCase(groupType)){
                        leadInteraction.setInterActionType(LeadInteractionType.CPN_IN.getIndexString());
                    }

                    leadInteraction.setAni(telephone);
                    //不需要再把400转化为落地号
                    //leadInteraction.setDnis(dto.getDnis());
                    leadInteraction.setDnis(tollFreeNum);
                    leadInteraction.setContactId(contactId);
                    leadInteraction.setIsValid(1l);
                    leadInteraction.setAllocateCount(0l);

                    leadInteraction.setStatus("0");
                    leadInteraction.setResultCode("-1"); //新建时设置为-1
                    leadInteraction.setOperation("电话呼入");
                    leadInteraction.setComments("电话呼入创建任务");

                    //判断是否有CallBack权限,如果有直接创建任务,否则创建线索
                    // && !agentUser.hasRole(SecurityConstants.ROLE_CALLBACK_AGENT)
                    // Callback时创建任务,在保存Callback单时创建
                    if(callback != null && callback) {
                        leadService.insertLead(lead);
                        leadInteraction.setLeadId(lead.getId());
                        leadInterActionService.insertLeadInterAction(leadInteraction);
                        return String.format("{\"instId\":\"\",\"leadId\":\"%d\",\"leadInterId\":\"%d\"}", lead.getId(), leadInteraction.getId());
                    } else {
//                        startTime = System.currentTimeMillis();
                        LeadTask task = leadService.saveLead(lead);
//                		logger.debug("SaveLead cost :"+(System.currentTimeMillis()-startTime));

                        if(task != null){
                            String instId = task.getBpmInstanceId();
                            if(StringUtils.isNotBlank(instId)){
                                //激活任务
//                                startTime = System.currentTimeMillis();
                                campaignBPMTaskService.updateTaskStatus(instId, String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()), null, null);
//                        		logger.debug("UpdateTaskStatus cost :"+(System.currentTimeMillis()-startTime));
                                
                                leadInteraction.setLeadId(task.getLeadId());
                                
//                                startTime = System.currentTimeMillis();
                                leadInterActionService.insertLeadInterAction(leadInteraction);
//                        		logger.debug("InsertLeadInterAction cost :"+(System.currentTimeMillis()-startTime));
                                
//                        		logger.debug("Total cost :"+(System.currentTimeMillis()-initStart));
                        		
                                return String.format("{\"instId\":\"%s\",\"leadId\":\"%d\",\"leadInterId\":\"%d\"}", instId, task.getLeadId(), leadInteraction.getId());
                            }

                        }
                    }
                    return String.format("{\"errorMsg\":\"创建任务失败：不能创建活动%s的线索\"}", dto.getName());
                }
                else
                {
                    return String.format("{\"errorMsg\":\"创建任务失败：没有找到%s对应的活动\"}", tollFreeNum);
                }
            }
            catch (Exception ex)
            {
                logger.error("创建任务失败", ex);
                return String.format("{\"errorMsg\":\"创建任务失败：%s\"}", ex.getMessage());
            }
        }
        else {
            return String.format("{\"errorMsg\":\"创建任务失败：用户没有登陆或已经超时！\"}");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/product/recommend/outgoing",method= RequestMethod.POST)
    public Map<String, Object> outgoing(
            @RequestParam(required = false) String instId,
            @RequestParam(required = false) String campainId)  throws Exception {
        List<NcPlubasInfo> ncPlubasInfos = new ArrayList<NcPlubasInfo>();
        if(StringUtils.isBlank(campainId)) {
            CampaignTaskVO vo = campaignBPMTaskService.queryMarketingTask(instId);
            if(vo != null){
                campainId = vo.getCaID();
            }
        }
        String camId = campainId;
        String camName = "";
        if(StringUtils.isNotBlank(campainId)) {
            List<TaskFormItem> items = campaignBPMTaskService.getTaskForm(String.valueOf(campainId));
            for(TaskFormItem item : items){
                NcPlubasInfo info = plubasInfoService.getNcPlubasInfo(item.getValue());
                if(info != null){
                    ncPlubasInfos.add(info);
                }
                camName = item.getCamName();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", camId);
        map.put("name", camName);
        map.put("details", ncPlubasInfos);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/product/recommend/incoming",method= RequestMethod.POST)
    public Map<String, Object> incoming(
            @RequestParam(required = true) String tollFreeNum,
            @RequestParam(required = false) String campainId)  throws Exception {

        List<NcPlubasInfo> ncPlubasInfos = new ArrayList<NcPlubasInfo>();
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String camId = "";
        String camName = "";
        CampaignDto dto;

        if(StringUtils.isNotBlank(campainId)){
            logger.info("/product/recommend/incoming{campainId}:"+campainId);
            dto = campaignApiService.getCampaign(Long.parseLong(campainId));
        } else {
            dto = campaignApiService.queryInboundCampaign(tollFreeNum, dateformat1.format(new Date()));
        }
        if(dto != null){
            camId = String.valueOf(dto.getId());
            List<TaskFormItem> items = campaignBPMTaskService.getTaskForm(String.valueOf(dto.getId()));
            for(TaskFormItem item : items){
                NcPlubasInfo info = plubasInfoService.getNcPlubasInfo(item.getValue());
                if(info != null){
                    ncPlubasInfos.add(info);
                }
                camName = item.getCamName();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", camId);
        map.put("name", camName);
        map.put("details", ncPlubasInfos);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/product/recommend/submit",method= RequestMethod.POST)
    public String submit(
            @RequestParam(required = true) String instId,
            @RequestParam(required = true) String nccodes,
            @RequestParam(required = false) String campainId,
            @RequestParam(required = false) String tollFreeNum
            )  {
        try
        {
            if(StringUtils.isBlank(campainId)){
                if(StringUtils.isNotBlank(instId)){
                    CampaignTaskVO vo = campaignBPMTaskService.queryMarketingTask(instId);
                    if(vo != null){
                        campainId = vo.getCaID();
                    }
                }  else {
                    SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    CampaignDto dto = campaignApiService.queryInboundCampaign(tollFreeNum, dateformat1.format(new Date()));
                    if(dto != null){
                        campainId = String.valueOf(dto.getId());
                    }
                }
            }
            if(StringUtils.isNotBlank(campainId)){
                List<String> nccodeList =  new ArrayList<String>(Arrays.asList(nccodes.split(",")));
                List<TaskFormItem> items = campaignBPMTaskService.getTaskForm(campainId);
                for(TaskFormItem item : items){
                    if(nccodeList.remove(item.getValue())){
                        item.setChecked(true);
                    }  else {
                        item.setChecked(false);
                    }
                }
                //新增自定义商品
                for(String nccode : nccodeList){
                    TaskFormItem item = new TaskFormItem();
                    item.setName("推荐商品四");
                    item.setValue(nccode);
                    item.setChecked(true);
                    item.setType("input-read");
                    items.add(item);
                }
                campaignBPMTaskService.submitTaskForm(instId, "", items);
                /*
                //变更取数
                CampaignTaskVO taskVo = campaignBPMTaskService.queryMarketingTask(instId);
                if(taskVo != null && taskVo.getPdCustomerId() != null){
                    jobRelationexService.dialContact(taskVo.getPdCustomerId(), null);
                }

                AgentUser agentUser = SecurityHelper.getLoginUser();
                if(agentUser != null) {
                    campaignBPMTaskService.completeTaskAndUpdateStatus("",instId,"", agentUser.getUserId());
                } else {
                    campaignBPMTaskService.completeTaskAndUpdateStatus("", instId, "", "");
                }
                */

            }
            return "已推荐！";
        }
        catch (Exception ex){
            return String.format("产品推荐失败：%s", ex.getMessage());
        }
    }
}
