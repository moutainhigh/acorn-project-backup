package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.MediaPlan;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.uc.dto.*;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.sales.service.CommonService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 公共Controller
 * @author haoleitao
 *
 */
@Controller
@RequestMapping(value="/common")
public class CommonController extends BaseController {
    private static final Logger logger = Logger.getLogger(CommonController.class.getName());
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CountyService countyService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private CampaignApiService campaignApiService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private LeadService leadService;

    @Autowired
    private CommonService commonService;

	/**
	 * 查询所有省份
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/province")
	public void getAllProvince(HttpServletRequest request, HttpServletResponse response){
		List<ProvinceDto> list = provinceService.getAllProvince();
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
	
	/**
	 * 查询所有城市
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/cityAll")
	public void getCityAll(
			HttpServletRequest request, HttpServletResponse response){
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/city")
	public void getCity(
			@RequestParam(required = false, defaultValue = "") String provinceId,
			HttpServletRequest request, HttpServletResponse response){
		List<CityDto> list = cityService.getCityByProvinceId(provinceId);
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
	/**
	 * 获取城市的区县
	 * @param cityId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/county")
	public void getCounty(
			@RequestParam(required = false, defaultValue = "") String cityId,
			HttpServletRequest request, HttpServletResponse response){
		List<CountyDto> list = countyService.getCountryByCityId(Integer.valueOf(cityId));
	 HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
	}
/**
 * 获取乡镇,街道
 * @param countyId
 * @param request
 * @param response
 */
    @RequestMapping(value="/area")
    public void getArea(
            @RequestParam(required = false, defaultValue = "") String countyId,
            HttpServletRequest request, HttpServletResponse response){
        List<AreaDto> list = areaService.getAreaByCountryId(Integer.valueOf(countyId));
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
    }

	@RequestMapping(value="/get400")
	public void get400ByDnis(			
			@RequestParam(required = false, defaultValue = "") String dnis,
			HttpServletRequest request, HttpServletResponse response){
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
      * outbound 挂机		

      * @param isContact
      * @param contactTime
      * @param remark

      * @param h_instId

      * @param request
      * @param response
      */
		@RequestMapping(value="/outbound/hook")
		public void outboundHook(			
				@RequestParam(required = false, defaultValue = "") Boolean isContact,
				@RequestParam(required = false, defaultValue = "") String contactTime,
				@RequestParam(required = false, defaultValue = "") String remark,
				@RequestParam(required = false, defaultValue = "") String h_instId,
				@RequestParam(required = false, defaultValue = "") Boolean isOutbound,
                @RequestParam(required = false, defaultValue = "") String  ani,
                @RequestParam(required = false, defaultValue = "") String  dani,
				@RequestParam(required = false, defaultValue = "") String marketingPlan,
                @RequestParam(required = false, defaultValue = "") String d_reson,
                @RequestParam(required = false, defaultValue = "0") String time_length,
                @RequestParam(required = false, defaultValue = "false") Boolean d_selcontact,
                @RequestParam(required = false, defaultValue = "") String d_dept,
                @RequestParam(required = false, defaultValue = "") String d_group,
                @RequestParam(required = false, defaultValue = "") String d_seat,
                @RequestParam(required = false, defaultValue = "") String ctiedt,

				HttpServletRequest request, HttpServletResponse response){

            Long startTime=System.currentTimeMillis();
            Long endTime=0L;
			Map<String, Object> map=new HashMap();

			logger.info(h_instId);
			if(! h_instId.equals("")){

		    AgentUser user = SecurityHelper.getLoginUser();
		     endTime = System.currentTimeMillis();
            logger.info("hook::::SecurityHelper.getLoginUser()::"+(endTime-startTime));
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
            dto.setCtiedt(DateUtil.parseDate(ctiedt,DateUtil.DATE_TIME_FORMAT_EXAC_SSS));
			try {

				map= commonService.phoneHook(dto);
                endTime = System.currentTimeMillis();
                logger.info("hook::::commonService.phoneHook::"+(endTime-startTime));
                leadService.updateLead(leadInterActionService.getLead2Updated(Long.valueOf(map.get("leadId").toString())));
                endTime = System.currentTimeMillis();
                logger.info("hook::::leadService.updateLead::"+(endTime-startTime));
                //是否转移联系人
                if( d_selcontact){

                    try {
                        leadService.transferLeadAndSubsidiaries(map.get("instId").toString(),d_seat,DateUtil.parseDate(contactTime,DateUtil.FORMAT_DATE_LINE), remark);

                        endTime = System.currentTimeMillis();
                        logger.info("hook::::leadService.transferLeadAndSubsidiaries::"+(endTime-startTime));
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
                logger.info("hook::::leadInterActionService.queryTotalIntradayOutcallTime::"+(endTime-startTime));
				HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
			}
			}else{
				map.put("result", false);
				map.put("msg", "instId为空！");
			}
			
			HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");

		}

		
		@RequestMapping(value="/splicePhone")
		public void splicePhone(
				@RequestParam(required = false, defaultValue = "") String inPhone,				
				HttpServletRequest request, HttpServletResponse response){
			
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
