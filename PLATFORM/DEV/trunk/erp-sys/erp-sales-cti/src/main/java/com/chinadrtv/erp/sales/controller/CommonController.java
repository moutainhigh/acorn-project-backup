package com.chinadrtv.erp.sales.controller;

import com.alibaba.druid.util.StringUtils;
import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.*;
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

//	@Autowired
//    private CommonService commonService;


//    @Autowired
//    private SourceConfigure sourceConfigure;






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
                @RequestParam(required = false, defaultValue = "") String acdId,
                HttpServletRequest request
                ,HttpServletResponse response){
            String username = request.getSession().getAttribute("cti_agentId").toString();
            String cti_dn = request.getSession().getAttribute("cti_dn").toString();

           logger.info("<<< "+username+" "+ cti_dn+ " "+ ctiedt+" "+callid+ " "+acdId+" "+ani+" "+dani +""+isOutbound+" "+remark+">>>");
            Long startTime=System.currentTimeMillis();
            Long endTime=0L;
			Map<String, Object> map=new HashMap();

			//logger.info(h_instId);
			if(! h_instId.equals("")){

		    AgentUser user = SecurityHelper.getLoginUser();
		     endTime = System.currentTimeMillis();
            //logger.info("hook_1::::SecurityHelper.getLoginUser()::"+(endTime-startTime));
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
                    //logger.warn("日期转换失败:"+e.getMessage());
                }
                dto.setCallResult(callResult);
            dto.setCallType(callType);
            dto.setConnId(callid);

			try {

				//map= commonService.phoneHook(dto);
                endTime = System.currentTimeMillis();
                //logger.info("hook_2::::commonService.phoneHook::"+(endTime-startTime));
                Lead lead = null;

                endTime = System.currentTimeMillis();

			}finally{
                map.put("totalOutcallTime","");

                endTime = System.currentTimeMillis();
                //logger.info("hook_5::::leadInterActionService.queryTotalIntradayOutcallTime::"+(endTime-startTime));
				HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
			}
			}else{
				map.put("result", false);
				map.put("msg", "instId为空！");
			}
			
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
      //  s_result =commonService.interrupt(dto);
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
           // map.put("totalOutcallTime",leadInterActionService.queryTotalIntradayOutcallTime(SecurityHelper.getLoginUser().getUserId()));
            logger.info("totalOutcallTime:"+SecurityHelper.getLoginUser());
            }catch (Exception e){
                map.put("totalOutcallTime","0");
            }
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }


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
            //orderTypeList=namesService.getAllNames(tid);
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


        HttpUtil.ajaxReturn(response, "", "application/json");
    }





}
