package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.BaseConfigDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.LeadTypeService;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;
import com.chinadrtv.erp.sales.service.CommonService;
import com.chinadrtv.erp.uc.service.CallHistService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service 
public class CommonServiceImpl implements CommonService{

    private static final Logger logger = Logger.getLogger(CommonServiceImpl.class.getName());
	
	@Autowired
	private	LeadService leadService; 
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	@Autowired
	private LeadInterActionService 	leadInterActionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CallHistService callHistService;

    @Autowired
    private LeadTypeService leadTypeService;

    @Autowired
    private BaseConfigDao baseConfigDao;
	/**
	 * 客户挂机
	 * 1.更新线索的状态
	 * 2.
	 * 
	 */
	@Override
	public Map phoneHook(PhoneHookDto dto) throws ServiceException {
        Long startTime=System.currentTimeMillis();
        Long endTime=0L;
		Map<String, Object> map = new HashMap();
		Boolean result = false;
		String message= "";
        String instId = dto.getH_instId();
		logger.info("instId:"+dto.getH_instId());
        CampaignTaskVO vo= campaignBPMTaskService.queryMarketingTask(dto.getH_instId());
        endTime = System.currentTimeMillis();
        logger.info("hook::::commonService.phoneHook::campaignBPMTaskService.queryMarketingTask::"+(endTime-startTime));
			try {
			    AgentUser user = SecurityHelper.getLoginUser();
					CampaignTaskDto mTaskDto = new CampaignTaskDto();
					mTaskDto.setInstId(dto.getH_instId());

					if(vo != null){
						dto.setH_contactId(vo.getConid());
						dto.setH_leadId(vo.getLeadId());
						dto.setH_pdCustomerId(vo.getPdCustomerId());
						dto.setH_ispotential(vo.getIsPotential());
					}else{
						message="根据InstId查询CampaignTaskVO 失败!";
						map.put("msg", message);
						map.put("result", result);
						logger.info(message);
						return map;
					}


				
				//是否再联系
				if(dto.getIsContact()){
                    logger.info("contact ....");

                    Lead lead = new Lead();

                    //boolean  isOldTask = dto.getH_campaignId() == vo.getLtpId()? true : false;
                    LeadType leadType= leadTypeService.getLeadTypeById(dto.getH_campaignId()) ;
					if(leadType.getType().equals(Constants.LEAD_TYPE_REPEAT) ){
                    //延长任务时间
                    lead.setId(vo.getLeadId());
                    lead.setCallbackTask(true);
                    lead.setAppointDate(dto.getContactTime().equals("")?null:DateUtil.stringToDate(dto.getContactTime()));
                    lead.setCampaignId(Long.valueOf(vo.getCaID()));
                    leadService.saveLead(lead);
                    //设置任务的结束时间
                        campaignBPMTaskService.updateInstAppointDate(vo.getInstID(),dto.getContactTime().equals("")?null:DateUtil.stringToDate(dto.getContactTime()));
                        //添加备注
                        campaignBPMTaskService.updateTaskStatus(dto.getH_instId(),null,leadType.getName()+","+dto.getRemark(),null);
                        message="延长任务时间成功";
                    //关闭老任务,建立新任务
                        endTime = System.currentTimeMillis();
                        logger.info("hook::::commonService.phoneHook::延长任务时间::"+(endTime-startTime));
                    }else{
                        //关闭老任务
                        campaignBPMTaskService.completeTaskAndUpdateStatus("",dto.getH_instId(),null,dto.getUsrId());
                        //延长lead时间
                        lead.setId(vo.getLeadId());
                        lead.setCallbackTask(true);
                        lead.setAppointDate(dto.getContactTime().equals("") ? null : DateUtil.stringToDate(dto.getContactTime()));
                        lead.setCampaignId(Long.valueOf(vo.getCaID()));
                        leadService.saveLead(lead);
                        Lead mylead = leadService.get(vo.getLeadId()) ;
                        //创建任务
                        instId= campaignBPMTaskService.createMarketingTask(
                              vo.getLeadId()+"", vo.getCaID(), vo.getConid(),dto.getH_ispotential(),vo.getPdCustomerId(),
                   user.getUserId(), mylead.getEndDate(),
                              dto.getContactTime().equals("") ? null : DateUtil.stringToDate(dto.getContactTime()),
                              CampaignTaskSourceType.CALLBACK.getIndex(), leadType.getName()+","+dto.getRemark());
                        message="新建任务成功";
                        endTime = System.currentTimeMillis();
                        logger.info("hook::::commonService.phoneHook::新建任务::"+(endTime-startTime));
                    }

					//message="新建任务成功";
                    leadInterActionService.updateLeadInteraction(vo.getLeadId(),"7");

				}else{
				//关闭任务
                    logger.info(" not contact ....");
					campaignBPMTaskService.completeTaskAndUpdateStatus(dto.getRemark(), dto.getH_instId(), null, user.getUserId());
					message="关闭销售线索成功";
                    leadInterActionService.updateLeadInteraction(vo.getLeadId(),"-1");

                    endTime = System.currentTimeMillis();
                    logger.info("hook::::commonService.phoneHook::关闭任务::"+(endTime-startTime));

				}


                //添加备注
                LeadInteraction leadInteraction  = leadInterActionService.getLatestPhoneInterationByLeadId(vo.getLeadId());
                if(!dto.getRemark().equals("")){
                    leadInteraction.setComments(dto.getRemark());
                }
                if(!dto.getContactTime().equals("")){
                    leadInteraction.setReserveDate(DateUtil.string2Date(dto.getContactTime(),"yyyy-MM-dd"));
                }


                logger.info("=================END==================");
                leadInteraction.setEndDate(new Date());
                leadInteraction.setCtiEndDate(dto.getCtiedt());
                leadInteraction.setUpdateUser(user.getUserId());
                if(dto.getH_campaignId() ==10){
                  leadInteraction.setReson(dto.getReson());
                }
                //添加通话时间
                leadInteraction.setTimeLength(DateUtil.getDateDeviations_second(leadInteraction.getCtiStartDate(),dto.getCtiedt()));
                leadInterActionService.saveOrUpdate(leadInteraction);
                logger.info("contact{SAVE} ....");
                this.saveCallHist(leadInteraction);
				result=true;

                endTime = System.currentTimeMillis();
                logger.info("hook::::commonService.phoneHook::添加备注::"+(endTime-startTime));
			} catch (ServiceException e) {

                logger.info("=================e=================="+e.getMessage());
				result=false;
				message="挂机失败";
				throw new ServiceException(message);
			}finally{


				map.put("result", result);
				map.put("msg", message);
                map.put("leadId",vo.getLeadId());
                map.put("instId",instId);
				return map;
			}
			
	}

    public Boolean interrupt(PhoneHookDto dto) throws ServiceException {

        CampaignTaskVO vo = campaignBPMTaskService.queryMarketingTask(dto.getH_instId());
        LeadInteraction leadInteraction  = leadInterActionService.getLatestPhoneInterationByLeadId(vo.getLeadId());
        leadInteraction.setUpdateDate(new Date());
        leadInteraction.setEndDate(new Date());
        leadInteraction.setCtiEndDate(dto.getCtiedt());
        //添加通话时间
        leadInteraction.setTimeLength(DateUtil.getDateDeviations_second(leadInteraction.getCtiStartDate(),dto.getCtiedt()));
        leadInterActionService.saveOrUpdate(leadInteraction);
        this.saveCallHist(leadInteraction);
       if(DateUtil.getDateDeviations_second( vo.getLtCreateDate(),new Date()) <=20 ){
           return leadInterActionService.updateLeadInteraction(vo.getLeadId(),"1");
        }else{
           return leadInterActionService.updateLeadInteraction(vo.getLeadId(),"2");
        }


    }

    private  void  saveCallHist(LeadInteraction leadInteraction){
        AgentUser user = SecurityHelper.getLoginUser();
        //更新callhist
        try{
            CallHist callHist = new CallHist();
            callHist.setSubType("sales");
            callHist.setCallDate(leadInteraction.getCreateDate());
            callHist.setCallNote(leadInteraction.getComments());
            callHist.setStTm(leadInteraction.getCreateDate());
            callHist.setCallType(userService.getGroupType(user.getName()));
            callHist.setResult(leadInteraction.getResultCode());
            callHist.setUserId(user.getUserId());
            callHist.setEndTm(new Date());
            callHist.setGrpId(user.getWorkGrp());
            callHist.setAni(leadInteraction.getAni());
            callHist.setDnis(leadInteraction.getDnis());
            callHist.setContactId(leadInteraction.getContactId());
            callHistService.saveCallHist(callHist);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BaseConfig> getNormalPhone(){
        return  baseConfigDao.query("SHORTCUT_PHONE");
    }

}
