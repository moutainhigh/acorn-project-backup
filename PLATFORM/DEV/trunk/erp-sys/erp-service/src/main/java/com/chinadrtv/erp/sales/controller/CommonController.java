package com.chinadrtv.erp.sales.controller;

import com.alibaba.druid.util.StringUtils;
import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.dto.MediaPlan;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.marketing.*;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;
import com.chinadrtv.erp.sales.service.CommonService;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.uc.dto.*;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;

/**
 * 公共Controller
 * @author haoleitao
 *
 */
@Controller
@RequestMapping(value="/common")
public class CommonController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CommonController.class);
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CountyService countyService;
	@Autowired
	private AreaService areaService;
	@Autowired
    private CommonService commonService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private CampaignApiService campaignApiService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	@Autowired
	private OldContactexService oldContactexService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private LeadService leadService;

    @Autowired
    private LeadTypeService leadTypeService;
    @Autowired
    private CallbackService callbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private CallHistService callHistService;

    @Autowired
    private SourceConfigure sourceConfigure;

    @Autowired
    private PotentialContactService potentialContactService;

    @Autowired
    private NamesService namesService;

	/**
	 * 查询所有省份
     * @param response
     */
	@RequestMapping(value="/province")
	public void getAllProvince(HttpServletResponse response){
		List<ProvinceDto> list = provinceService.getAllProvince();
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
	
	/**
	 * 查询所有城市
     * @param response
     */
	@RequestMapping(value="/cityAll")
	public void getCityAll(
            HttpServletResponse response){
		List<CityDto> list = cityService.getCityAll();
		
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}

	/**
	 * 查询所有区县信息
     * @param response
     */
	@RequestMapping(value="/countyAll")
	public void getCountyAll(
            HttpServletResponse response){
		List<CountyDto> list = countyService.getCountryAll();
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
	/**
	 * 获取省的城市
     * @param provinceId
     * @param response
     */
	@RequestMapping(value="/city")
	public void getCity(
            @RequestParam(required = false, defaultValue = "") String provinceId,
            HttpServletResponse response){
		List<CityDto> list = cityService.getCityByProvinceId(provinceId);
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
	/**
	 * 获取城市的区县
     * @param cityId
     * @param response
     */
	@RequestMapping(value="/county")
	public void getCounty(
            @RequestParam(required = false, defaultValue = "") String cityId,
            HttpServletResponse response){
		List<CountyDto> list = countyService.getCountryByCityId(Integer.valueOf(cityId));
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
/**
 * 获取乡镇,街道
 * @param countyId
 * @param response
 */
    @RequestMapping(value="/area")
    public void getArea(
            @RequestParam(required = false, defaultValue = "") String countyId,
            HttpServletResponse response){
        List<AreaDto> list = areaService.getAreaByCountryId(Integer.valueOf(countyId));
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
    }
	
   /**
    *地址组件测试地址
    *
    *
    * @return
    */
	@RequestMapping(value="/test")
	public ModelAndView test(){
    	ModelAndView mav = new ModelAndView("common/test");
    	
    	return mav;
	}




    /*部门*组*座席*/
    @RequestMapping(value="/getAllDept")
    public void getAllDept(HttpServletResponse response){
        //userService.g
        List<DepartmentInfo> list = null;
        try {
            list = userService.getDepartments();
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates;
            logger.info(e.getMessage());
        }finally {
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(list==null ? "":list),"application/json");
        }

    }

    @RequestMapping(value="/getAllGroup")
    public void getAllGroup(HttpServletResponse response){
        List<GroupInfo> list = new ArrayList<GroupInfo>();
        try {
            list = userService.getAllGroup();
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(list==null ? "":list),"application/json");
        }

    }

    @RequestMapping(value="/getAllSeat")
    public void getAllSeat(HttpServletResponse response){
        List<AgentUser> list = new ArrayList<AgentUser>();

        HttpUtil.ajaxReturn(response,jsonBinder.toJson(list),"application/json");
    }


    @RequestMapping(value="/getGroupByDept")
    public void getGroupByDept(
            @RequestParam(required = false, defaultValue = "") String deptId,
            HttpServletResponse response){
        List<GroupInfo> list = null;
        try {
            list = userService.getGroupList(deptId);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(list==null ? "":list),"application/json");
        }
    }

    @RequestMapping(value="/getSeatByGroup")
    public void getSeatByGroup(
            @RequestParam(required = false, defaultValue = "") String groupId,
            HttpServletResponse response){
        List<AgentUser> list = null;
        try {
            list = userService.getUserList(groupId);
        }catch (ServiceException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.info(e.getMessage());
        }finally{
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(list==null ? "":list),"application/json");
        }


    }


    @RequestMapping(value = "getUserByUid")
    @ResponseBody
    public List<AgentUser> getUserByUid(@RequestParam(required = false, defaultValue = "") String uid){
        try {
            List<AgentUser> agentUser= userService.findUserLikeUid(uid);
             return agentUser;
        }catch (ServiceException e){
            logger.info(e.getMessage());
        }

         return null;
    }


	@RequestMapping(value="/get400")
	public void get400ByDnis(
            @RequestParam(required = false, defaultValue = "") String dnis,
            HttpServletResponse response){
		Map<String, Object> map = new HashMap();
		Boolean result = false;
		String message= "";
		try {
		   //map = campaignApiService.queryMediaByDnis(dnis);
         MediaPlan mediaPlan =  campaignApiService.getMediaPlan(dnis);
         map.put("mediaPlan",mediaPlan);
            result = true;
		}catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 message="没有获取到四零零号码!";

			logger.info("没有获取到四零零号码:"+e.getMessage());
		}finally{
			
			 map.put("result", result);
			 map.put("msg", message);

			HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
		}
		
		
	}
	
	
	   /**
	    *CTI测试地址
	    *
        *
        * @return
	    */
		@RequestMapping(value="/cti")
		public ModelAndView cti(){
	    	ModelAndView mav = new ModelAndView("cti/index");
	    	
	    	return mav;
		}

//=========================================销售线索和任务==================================================================//
		

     /**
      * outbound 挂机
      * @param isContact
      * @param contactTime
      * @param remark
      * @param h_instId
      * @param response
      */
		@RequestMapping(value="/outbound/hook")
		public void outboundHook(
                @RequestParam(required = false, defaultValue = "") Boolean isContact,
                @RequestParam(required = false, defaultValue = "") String contactTime,
                @RequestParam(required = false, defaultValue = "") String remark,
                @RequestParam(required = false, defaultValue = "") String h_instId,
                @RequestParam(required = false, defaultValue = "") Boolean isOutbound,
                @RequestParam(required = false, defaultValue = "") String ani,
                @RequestParam(required = false, defaultValue = "") String dani,
                @RequestParam(required = false, defaultValue = "") String marketingPlan,
                @RequestParam(required = false, defaultValue = "") String d_reson,
                @RequestParam(required = false, defaultValue = "0") String time_length,
                @RequestParam(required = false, defaultValue = "false") Boolean d_selcontact,
                @RequestParam(required = false, defaultValue = "") String d_seat,
                @RequestParam(required = false, defaultValue = "") String ctiedt,
                @RequestParam(required = false, defaultValue = "") String callid,
                @RequestParam(required = false, defaultValue = "") String callResult,
                @RequestParam(required = false, defaultValue = "") String callType,

                HttpServletResponse response){

            Long startTime=System.currentTimeMillis();
            Long endTime=0L;
			Map<String, Object> map=new HashMap();

			logger.info(h_instId);
			if(! h_instId.equals("")){

		    AgentUser user = SecurityHelper.getLoginUser();
		     endTime = System.currentTimeMillis();
            logger.info("hook_1::::SecurityHelper.getLoginUser()::"+(endTime-startTime));
		    PhoneHookDto dto = new PhoneHookDto();
			dto.setContactTime(contactTime);
			dto.setH_instId(h_instId);
			dto.setIsContact(isContact);
			dto.setRemark(remark);
            dto.setDani(dani);
			dto.setUsrId(user.getUserId());
			dto.setIsOutbound(isOutbound);
            dto.setReson(Integer.valueOf(d_reson.equals("")?"0":d_reson));
		    dto.setH_campaignId(Long.valueOf(marketingPlan.equals("")?"0":marketingPlan));
            dto.setAni(ani);
            dto.setTimerLength(Long.valueOf(time_length.equals("")?"0":time_length));
                try {
                    dto.setCtiedt(DateUtil.parse(ctiedt, "yyyy-MM-dd HH:mm:ss.SSS"));
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    logger.warn("日期转换失败:"+e.getMessage());
                }
                dto.setCallResult(callResult);
            dto.setCallType(callType);
            dto.setConnId(callid);

			try {

				map= commonService.phoneHook(dto);
                endTime = System.currentTimeMillis();
                logger.info("hook_2::::commonService.phoneHook::"+(endTime-startTime));
                Lead lead = null;
                if(map.get("leadId").toString() != null) {
                	lead = leadInterActionService.getLead2Updated(Long.valueOf(map.get("leadId").toString()));
                }
                if(lead != null) {
                	leadService.updateLead(lead);
                }
                endTime = System.currentTimeMillis();
                logger.info("hook_3::::leadService.updateLead::"+(endTime-startTime));
                //是否转移联系人
                if( d_selcontact){

                    try {
                        leadService.transferLeadAndSubsidiaries(map.get("instId").toString(),d_seat, DateUtil.parseDate(contactTime,DateUtil.FORMAT_DATETIME_FULL), remark);

                        endTime = System.currentTimeMillis();
                        logger.info("hook_4::::leadService.transferLeadAndSubsidiaries::"+(endTime-startTime));
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        logger.info(e.getMessage());

                    }

                }

			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}finally{
                map.put("totalOutcallTime",leadInterActionService.queryTotalIntradayOutcallTime(user.getUserId()));

                endTime = System.currentTimeMillis();
                logger.info("hook_5::::leadInterActionService.queryTotalIntradayOutcallTime::"+(endTime-startTime));
				HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
			}
			}else{
				map.put("result", false);
				map.put("msg", "instId为空！");
			}
			
			HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");

		}
		
		
		/**
		 * 消费取数数据
         * @param h_instId

         * @param response
         */
		@RequestMapping(value="/fatch/message")
		public void fetchMessage(
                @RequestParam(required = false, defaultValue = "") String h_instId,
                @RequestParam(required = false, defaultValue = "") String ani,
                @RequestParam(required = false, defaultValue = "") String dani,
                @RequestParam(required = false, defaultValue = "") String boundType,
                @RequestParam(required = false, defaultValue = "") String connId,
                HttpServletResponse response){
			
			Map<String, Object> map=new HashMap();
			Boolean result=true;
			String message = "Ok";
			logger.info(h_instId);
			try {
				if(! h_instId.equals("")){
				    PhoneHookDto dto = new PhoneHookDto();
					dto.setH_instId(h_instId);
                    dto.setAni(ani);
                    dto.setDani(dani);
                    dto.setBoundType(Integer.valueOf(boundType));
                    dto.setConnId(connId);
                   // dto.setCtisdt(DateUtil.parseDate(ctisdt,DateUtil.DATE_TIME_FORMAT_EXAC_SSS));
				map= commonService.fetchMessage(dto);
				//result = true;
                map.put("msg","Ok");
				}else{
					result =false;
                    message = "instId为空！";
					//map.put("msg", "instId为空！");
					//map.put("result", result);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
                message=e.getMessage();
                result =false;
			}finally{
                map.put("msg", message);
                map.put("result", result);
				HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
			}

		}

    /**
     * 消费取数数据
     * @param h_instId

     *
     */
    @RequestMapping(value="/fatch/message/callback")
    public void fetchMessageCallback(
            @RequestParam(required = false, defaultValue = "") String h_instId,
            @RequestParam(required = false, defaultValue = "") String connId,
            @RequestParam(required = false, defaultValue = "") String ctisdt,
            HttpServletResponse response){
        Boolean result=true;
        String message = "Ok";
        if(! h_instId.equals("")){
            PhoneHookDto dto = new PhoneHookDto();
            dto.setH_instId(h_instId);
            dto.setConnId(connId);
            try {
                dto.setCtisdt(DateUtil.parse(ctisdt, "yyyy-MM-dd HH:mm:ss.SSS"));
            } catch (ParseException e) {
                logger.warn("日期转换失败:"+e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            commonService.fetchMessageCallBack(dto);
        }else{
            result =false;
            message = "instId为空！";
            //map.put("msg", "instId为空！");
            //map.put("result", result);
        }

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap();
        map.put("result",result);
        map.put("msg",message);

        HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");

    }




		
        @RequestMapping(value="/get/CurrentPlanAndPlanList")
		public void getCurrentPlanAndPlanList(@RequestParam(required = false, defaultValue = "") String h_instId,
                                              @RequestParam(required = false, defaultValue = "") String customerId,
                                              HttpServletResponse response){
    	   //获取
            Gson gson = new Gson();
        	Map map = new HashMap();
    	    AgentUser user = SecurityHelper.getLoginUser();
        	CampaignTaskVO vo= campaignBPMTaskService.queryMarketingTask(h_instId);

            LeadType dto = new LeadType();
            dto.setId(vo.getLtpId());
            dto.setName(vo.getLtpName());
            List<LeadType> leadTypeList = new ArrayList<LeadType>();
     // =
            AgentUser user2 =oldContactexService.queryBindAgentUser(customerId);
            if(user2 == null){
                map.put("boundCustomer", false);
            }else{
                if(user.getUserId().equals(user2.getUserId())){
                    map.put("boundCustomer", false);
                }else{
                    map.put("boundCustomer", true);
                }
            }

            map.put("campaignId", vo.getLtpId());
            //判断任务是否已经完成
//            logger.info("isDone:"+campaignBPMTaskService.queryInstsIsDone(h_instId));
//            if(! campaignBPMTaskService.queryInstsIsDone(h_instId)){
//                leadTypeList.add(dto);
//                map.put("leadTypeList",leadTypeList) ;
//                map.put("isfinished",false);
//                logger.info("getCurrentPlanAndPlanList:"+gson.toJson(map));
//                HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
//
//            }else{

                    map.put("isfinished",true);
                    try {
                        LeadType leadType = new LeadType();
                        leadType.setId(10L);
                        leadType.setName("未拨通电话，重复原任务");
                        leadTypeList.add(leadType);
                        logger.info("leadTypeList" + leadTypeList);
                        logger.info("LEAD_TYPE_CONTACT:" + Constants.LEAD_TYPE_CONTACT);

                    } catch (Exception e) {
                        logger.error("查询任务列表出错。" + e.getMessage(), e);
                    }

//                    boolean  mark = true;
//                    for(LeadType cdto:leadTypeList){
//                        if(cdto.getId()== Long.valueOf(vo.getLtpId())){
//                            mark = false;
//                        }
//                    }
//                    if(mark){
//                        leadTypeList.add(dto);
//                    map.put("leadTypeList", leadTypeList);
//                    }

            map.put("leadTypeList", leadTypeList);
            map.put("hasOldCustomerRole",SecurityHelper.getLoginUser().hasRole(SecurityConstants.ROLE_CREATE_TASK)?"1":"0");
                    logger.info("getCurrentPlanAndPlanList:"+gson.toJson(map));
                    HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
 //           }
		}
		
		
		@RequestMapping(value="/splicePhone")
		public void splicePhone(
                @RequestParam(required = false, defaultValue = "") String inPhone,
                HttpServletResponse response){
			
			Map<String, Object> map = new HashMap();
			Boolean result = false;
			String message= "";
			PhoneAddressDto dto = phoneService.splicePhone(inPhone);
			if(dto != null){
				result=true;
				message="电话解析成功";
				logger.info("已完成电话解析");
			}else{
				message="电话解析失败";
				logger.info("未完成电话解析");
			}
			map.put("dto", dto);
			map.put("msg", message);
			map.put("result", result);
			HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
		}


    /**
     * 电话中断
     * @param h_instId
     * @param response
     */

    @RequestMapping(value="/phone/interrupt")
    public void interrupt(
            @RequestParam(required = false, defaultValue = "") String h_instId,
            @RequestParam(required = false, defaultValue = "0") String time_length,
            @RequestParam(required = false, defaultValue = "") String ctiedt,
            @RequestParam(required = false, defaultValue = "") String connId,
            HttpServletResponse response){
        PhoneHookDto dto = new PhoneHookDto();
        dto.setH_instId(h_instId);
        dto.setTimerLength(Long.valueOf(StringUtils.isEmpty(time_length) ? "0" : time_length));
        try {
            dto.setCtiedt(DateUtil.parse(ctiedt, "yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (ParseException e) {
            logger.warn("日期转换失败:"+e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        dto.setConnId(connId);
        Map<String, Object> map = new HashMap();
        Boolean result = false, s_result =null;
        String message= "";

        try{
        s_result =commonService.interrupt(dto);
        }catch (Exception e){
            logger.info("interruptException:"+e.getMessage());
        }finally {

            if(s_result !=null &&  s_result ){
                result=true;
                message="记录电话中断成功";
            }else{
                result=false;
                message="记录电话中断失败";
            }
            map.put("msg", message);
            map.put("result", result);
            logger.info(jsonBinder.toJson(map));
            try{
            map.put("totalOutcallTime",leadInterActionService.queryTotalIntradayOutcallTime(SecurityHelper.getLoginUser().getUserId()));
            logger.info("totalOutcallTime:"+SecurityHelper.getLoginUser());
            }catch (Exception e){
                map.put("totalOutcallTime","0");
            }
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }


    }

    /**
     *
     * @param h_instId
     * @param response
     */

    @RequestMapping(value="/callBack")
    public void callBack(
            @RequestParam(required = false, defaultValue = "") String h_instId,
            @RequestParam(required = false, defaultValue = "") String leadInterId,
            @RequestParam(required = false, defaultValue = "") String leadId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String sex,
            @RequestParam(required = false, defaultValue = "") String customerId,
            @RequestParam(required = false, defaultValue = "") String customerType,
            @RequestParam(required = false, defaultValue = "") String spellcode,
            @RequestParam(required = false, defaultValue = "") String callbackDt,
            @RequestParam(required = false, defaultValue = "") String callbackRemark,
            @RequestParam(required = false, defaultValue = "") String callbackPriority,
            @RequestParam(required = false, defaultValue = "") String acdgroup,
            @RequestParam(required = false, defaultValue = "") String ani,
            @RequestParam(required = false, defaultValue = "") String dnsi,
            @RequestParam(required = false, defaultValue = "") String phn1,
            @RequestParam(required = false, defaultValue = "") String phn2,
            @RequestParam(required = false, defaultValue = "") String phn3,
            @RequestParam(required = false, defaultValue = "0") Long time_length,
            HttpServletResponse response) throws ServiceException {
            AgentUser user = SecurityHelper.getLoginUser();

            CampaignTaskVO vo=  h_instId.equals("")? null:campaignBPMTaskService.queryMarketingTask(h_instId);
            Lead lead =  leadService.get(Long.valueOf(leadId));
            //临时属性，创建任务时需要指定
            lead.setTaskSourcType(CampaignTaskSourceType.INCOMING.getIndex());
            Map<String, Object> map = new HashMap();
            String msg ="";
            Boolean result = false;
            Callback callBack = new Callback();
            callBack.setAcdGroup(acdgroup);
            callBack.setAni(ani);
            callBack.setLeadInteractionId(Long.valueOf(leadInterId.equals("")?"0":leadInterId));
            callBack.setDnis(dnsi);
            if(customerType.equals(CustomerFrontDto.POTENTIAL_CONTACT)) {
              callBack.setLatentcontactId(customerId);
            }else{
              callBack.setContactId(customerId);
            }
            callBack.setUsrId(user.getUserId());
            callBack.setUsrGrp(user.getWorkGrp());
            callBack.setName(name);
            callBack.setPhn1(phn1);
            callBack.setPhn2(phn2);
            callBack.setPhn3(phn3);
            callBack.setPriority(callbackPriority);
            //进线类型
            callBack.setType(LeadInteractionType.CALLBACK_IN.getIndexString());
            callBack.setRequiredt(DateUtil.parseDate(callbackDt,DateUtil.FORMAT_DATETIME_FULL));
            callBack.setRemark(callbackRemark);
            callBack.setMediaprodId(spellcode);
            callBack.setMediaplanId(lead.getCampaignId().toString());
            callBack.setAllocatedManual(true);
            callBack.setCrdt(new Date());

            if(customerType.equals("2")){
                //修改潜客信息
                PotentialContact potentialContact = potentialContactService.queryById(Long.valueOf(customerId));
                boolean isupdate = false;
                if(! StringUtils.isEmpty(sex)){
                    potentialContact.setGender(sex);
                   isupdate=true;
                }
                if(! StringUtils.isEmpty(name)){
                    potentialContact.setName(name);
                    isupdate=true;
                }
               if(isupdate) potentialContactService.saveOrUpdate(potentialContact);
            }



        try{


            //添加备注
            LeadInteraction leadInteraction  = leadInterActionService.getLatestPhoneInterationByLeadId(vo == null ? Long.valueOf(leadId):vo.getLeadId());

            leadInteraction.setComments(callbackRemark);



            logger.info("=================END==================");
            leadInteraction.setEndDate(new Date());
            leadInteraction.setUpdateUser(user.getUserId());
            //类新
            leadInteraction.setInterActionType(LeadInteractionType.CALLBACK_IN.getIndexString());
            //回呼时间
            leadInteraction.setReserveDate(DateUtil.parseDate(callbackDt,DateUtil.FORMAT_DATETIME_FULL));
            //添加通话时间
           // leadInteraction.setTimeLength(time_length);

            leadInterActionService.saveOrUpdate(leadInteraction);

            //修改任务属性
            /*
            if(vo != null){
                campaignBPMTaskService.updateInstAppointDate(vo.getInstID(),callBack.getRequiredt()) ;
                campaignBPMTaskService.updateTaskStatus(vo.getInstID(),null, callBack.getRemark(),null);
            }
            */
            //有权限的进线接callback，创建任务-gaudi 2013-11-29
            if(user.hasRole(SecurityConstants.ROLE_CALLBACK_AGENT)){
                LeadTask task = leadService.saveLead(lead);
                if(task != null){
                    String instId = task.getBpmInstanceId();
                    if(org.apache.commons.lang.StringUtils.isNotBlank(instId)){
                        //激活任务
                        campaignBPMTaskService.updateInstAppointDate(instId, callBack.getRequiredt()) ;
                        campaignBPMTaskService.updateTaskStatus(instId, String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()), callBack.getRemark(), null);
                        callBack.setTaskId(Long.parseLong(instId));
                        callBack.setUserAssigner(user.getUserId());
                        callBack.setGroupAssigner(user.getUserId());
                        callBack.setDbusrId(user.getUserId());
                        callBack.setDbdt(new Date());
                        callBack.setRdbusrId(user.getUserId());
                        callBack.setAllocatedManual(false);
                        callBack.setAllocateCount(1L);
                        callBack.setOpusr(user.getUserId());
                        callBack.setFirstusrId(user.getUserId());
                        callBack.setFirstdt(new Date());
                        callBack.setBatchId(callbackService.generateBatchId());
                    }
                }
            }
            callbackService.saveOrUpdate(callBack);




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

            result=true;
        }catch (Exception e){
            result=false;
            msg="保存callback信息失败!";
            logger.info(e.getMessage());
        }finally {
            map.put("result",result);
            map.put("msg",msg);
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }

    }

    /**
     * 获取系统版本信息
     * @param response
     */
    @RequestMapping(value="/getVersion")
    public void getVersion(HttpServletResponse response){
        AgentUser user = SecurityHelper.getLoginUser();
        Map map =  new HashMap();
        String seatType =null;
        try {
            seatType =  userService.getGroupType(user.getUserId());
            map.put("totalOutcallTime", leadInterActionService.queryTotalIntradayOutcallTime(user.getUserId()));
        } catch (ServiceException e) {
            map.put("totalOutcallTime",0);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        map.put("analoglines",sourceConfigure.getAnaloglines());
        map.put("version",sourceConfigure.getVersion());
        map.put("csPhone",sourceConfigure.getCustomerServicePhone());
        map.put("workGrp",user.getWorkGrp());
        map.put("role",user.loadAgentRoles() !=null? user.loadAgentRoles().iterator().next():"");
        map.put("employeeType",user.getEmployeeType());
        map.put("displayName",user.getDisplayName());
        map.put("userId",user.getUserId());

        map.put("seatType",seatTypeToUpcase(seatType));
       List<Names> names = namesService.getAllNames("ADDRESSCONTAINRULES");
        List<Names> namesext = namesService.getAllNames("ADDRESSEXRULES");

        String arraysNames[] = new String[names.size()];
        String arraysNamesExt[] = new String[namesext.size()];
        int i=0;
       for(Names name :names){
           arraysNames[i]=name.getDsc();
           ++i;
       }
        int j=0;
        for(Names nameext :namesext){
            arraysNamesExt[j]=nameext.getDsc();
            ++j;
        }
        map.put("names",arraysNames);
        map.put("namesext",arraysNamesExt);
      HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }



    private String seatTypeToUpcase(String seatType){
        String result="";
        try{
           result = seatType.toUpperCase();
        }catch (Exception e){
           logger.warn(e.getMessage());
        }finally {
            return result;
        }

    }














    @RequestMapping(value = "/names/lookup", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, String>> LookupNames(@RequestParam(required = false, defaultValue = "")String tid)
            throws Exception {
        List<Names> orderTypeList=null;
        if("AUDITSTATUS".equals(tid))
        {
            orderTypeList=new ArrayList<Names>();
            List<String> list=AuditTaskStatus.toList();
            for(int index=0;index<list.size();index++)
            {
                Names names=new Names();
                NamesId namesId=new NamesId();
                namesId.setId(String.valueOf(index));
                namesId.setTid("");
                names.setId(namesId);
                names.setDsc(list.get(index));
                names.setTdsc(list.get(index));
                names.setValid("-1");

                orderTypeList.add(names);
            }
        }
        else
        {
            if(StringUtils.isEmpty(tid))
                return null;
            orderTypeList=namesService.getAllNames(tid);
        }
        List<Map<String, String>> mapList=new ArrayList<Map<String, String>>();
        for(Names orderType:orderTypeList)
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", orderType.getId().getId());
            map.put("dsc", orderType.getDsc());

            mapList.add(map);
        }

        return mapList;
    }

    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception ex) {
        if(ex!=null)
        {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }


    @RequestMapping(value="/changeCC",method = RequestMethod.POST)
    public void  changeCC(@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
                          @RequestParam(required = false, defaultValue = "") String customerType,//客户类型
                          @RequestParam(required = false, defaultValue = "") String instId,//客户类型
                          HttpServletResponse response){
                Boolean result=false;
                String message = "切换但前客和失败!";
                int resultNum=leadService.updateContact(instId,customerId,Integer.valueOf(customerType) ==2 ? true:false);
                if(resultNum>0){
                    result = true;
                    message="切换客户成功!";
                }
                Map map = new HashMap();
                map.put("result",result);
                map.put("msg",message);
                HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");

    }

    @RequestMapping(value="/setCtiInfo")
    public void setCtiConnId(
            @RequestParam(required = false, defaultValue = "") String h_instId,
            @RequestParam(required = false, defaultValue = "") String ctiedt,
            @RequestParam(required = false, defaultValue = "") String connId,
            HttpServletResponse response
    ){
        PhoneHookDto dto = new PhoneHookDto();
        dto.setH_instId(h_instId);

        try {
            dto.setCtiedt(DateUtil.parse(ctiedt, "yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (ParseException e) {
            logger.warn("日期转换失败:"+e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        dto.setConnId(connId);

        try {
            commonService.saveCtiInfo(dto);
        } catch (ServiceException e) {
            logger.info("保存CTI_INFO 失败!!");
        }
        HttpUtil.ajaxReturn(response, "", "application/json");
    }


    @RequestMapping(value="/callback/setCtiInfo")
    public void setCallbackCtiConnId(
            @RequestParam(required = false, defaultValue = "") String v_leadInterId,
            @RequestParam(required = false, defaultValue = "") String ctiedt,
            @RequestParam(required = false, defaultValue = "") String connId,
            HttpServletResponse response
    ){
        PhoneHookDto dto = new PhoneHookDto();
        dto.setLeadInterId(v_leadInterId);

        try {
            dto.setCtiedt(DateUtil.parse(ctiedt, "yyyy-MM-dd HH:mm:ss.SSS"));
        } catch (ParseException e) {
            logger.warn("日期转换失败:"+e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        dto.setConnId(connId);

        try {
            commonService.saveCallbackCtiInfo(dto);
        } catch (ServiceException e) {
            logger.info("保存CTI_INFO 失败!!");
        }
        HttpUtil.ajaxReturn(response, "", "application/json");
    }


    /**
     * 获取常规电话
     * @param request
     * @param response
     */
    @RequestMapping(value="/normalPhone")
    public void getNormalPhone(
            HttpServletRequest request, HttpServletResponse response){
       List<BaseConfig> list =  commonService.getNormalPhone();
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
    }


}
