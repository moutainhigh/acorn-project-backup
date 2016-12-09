package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品相关信息(推荐商品/话术)
 * User: gdj
 * Date: 13-5-29
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RecommendController extends BaseController {

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
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/product/recommend/lead",method= RequestMethod.POST)
    public String recommendLead(
            @RequestParam(required = true) String tollFreeNum,
            @RequestParam(required = true) String telephone,
            @RequestParam(required = true) String contactId,
            @RequestParam(required = false) String contactType,
            @RequestParam(required = false) Long incomingType,
            @RequestParam(required = false) String cticonnid,
            @RequestParam(required = false) String begindate
            )
    {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            try
            {
                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                CampaignDto dto = campaignApiService.queryInboundCampaign(tollFreeNum, dateformat1.format(new Date()));
                if(dto != null){
                    String groupType = userService.getGroupType(agentUser.getUserId());

                    Lead lead = new Lead();
                    if("IN".equalsIgnoreCase(groupType)){
                        lead.setOutbound(false);
                    }
                    else if("OUT".equalsIgnoreCase(groupType)){
                        lead.setOutbound(true);
                    }
                    lead.setCampaignId(dto.getId());
                    lead.setOwner(agentUser.getUserId());
                    lead.setAni(telephone);
                    //不需要再把400转化为落地号
                    //lead.setDnis(dto.getDnis());
                    lead.setDnis(tollFreeNum);
                    if(incomingType != null){
                        lead.setTaskSourcType(incomingType);
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
                    lead.setBeginDate(new Date());
                    lead.setCreateUser(agentUser.getUserId());
                    lead.setCreateDate(new Date());
                    lead.setUpdateUser(agentUser.getUserId());
                    lead.setUpdateDate(new Date());

                    LeadTask task = leadService.saveLead(lead);
                    if(task != null){
                        String instId = task.getBpmInstanceId();
                        if(StringUtils.isNotBlank(instId)){
                            //激活任务
                            campaignBPMTaskService.updateTaskStatus(instId, String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()), null, null);
                            //添加交互
                            LeadInteraction leadInteraction = new LeadInteraction();
                            leadInteraction.setCreateUser(agentUser.getUserId());
                            leadInteraction.setCreateDate(new Date());
                            leadInteraction.setBeginDate(new Date());

                            SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            try {
                                leadInteraction.setCtiStartDate(df.parse(begindate));
                            }catch (Exception ex){
                                leadInteraction.setCtiStartDate(null);
                            }
                            leadInteraction.setCallId(cticonnid);

                            if("IN".equalsIgnoreCase(groupType)){
                                leadInteraction.setInterActionType(LeadInteractionType.INBOUND_IN.getIndexString());
                            }
                            else if("OUT".equalsIgnoreCase(groupType)){
                                leadInteraction.setInterActionType(LeadInteractionType.OUTBOUND_IN.getIndexString());
                            }


                            leadInteraction.setAni(telephone);
                            //不需要再把400转化为落地号
                            //leadInteraction.setDnis(dto.getDnis());
                            leadInteraction.setDnis(tollFreeNum);
                            leadInteraction.setContactId(contactId);
                            leadInteraction.setLeadId(task.getLeadId());
                            leadInteraction.setStatus("0");
                            leadInteraction.setResultCode("-1"); //新建时设置为-1
                            leadInteraction.setOperation("电话呼入");
                            leadInteraction.setComments("电话呼入创建任务");
                            leadInterActionService.insertLeadInterAction(leadInteraction);

                            return instId;
                        }
                    }
                    return String.format("创建任务失败：不能创建活动%s的线索", dto.getName());
                }
                else
                {
                    return String.format("创建任务失败：没有找到%s对应的活动", tollFreeNum);
                }
            }
            catch (Exception ex)
            {
                return String.format("创建任务失败：%s", ex.getMessage());
            }
        }
        else {
            return String.format("创建任务失败：用户没有登陆或已经超时！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/product/recommend/incoming",method= RequestMethod.POST)
    public List<NcPlubasInfo> incoming(@RequestParam(required = true) String tollFreeNum)  throws Exception {
        List<NcPlubasInfo> ncPlubasInfos = new ArrayList<NcPlubasInfo>();
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CampaignDto dto = campaignApiService.queryInboundCampaign(tollFreeNum, dateformat1.format(new Date()));
        if(dto != null){
            List<TaskFormItem> items = campaignBPMTaskService.getTaskForm(String.valueOf(dto.getId()));
            for(TaskFormItem item : items){
                NcPlubasInfo info = plubasInfoService.getNcPlubasInfo(item.getValue());
                if(info != null){
                    ncPlubasInfos.add(info);
                }
            }
        }
        return ncPlubasInfos;
    }
}
