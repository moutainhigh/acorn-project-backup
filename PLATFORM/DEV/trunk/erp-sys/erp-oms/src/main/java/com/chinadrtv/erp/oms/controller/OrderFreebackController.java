package com.chinadrtv.erp.oms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.model.Orderdet;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.oms.dto.EdiClearDto;
import com.chinadrtv.erp.oms.service.CardtypeService;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.core.service.EmsService;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.oms.service.OrderFeedbackService;
import com.chinadrtv.erp.oms.service.OrderdetService;
import com.chinadrtv.erp.oms.service.OrderhistService;
import com.chinadrtv.erp.oms.service.PreTradeDetailService;
import com.chinadrtv.erp.oms.service.SourceConfigure;
import com.chinadrtv.erp.oms.service.SystemConfigure;
import com.chinadrtv.erp.oms.util.HttpUtil;
import com.chinadrtv.erp.oms.util.StringUtil;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 订单反馈
 * @author haoleitao
 * @date 2013-1-9 下午6:42:14
 *
 */
@Controller
public class OrderFreebackController {
	private static Logger log = LoggerFactory.getLogger(OrderFreebackController.class);
	
    @Autowired
	private OrderhistService orderhistService;
    
    @Autowired
    private OrderdetService orderdetService ;
    
    @Autowired
    private NamesService namesService ;
    @Autowired
    private CompanyService companyService ;
    @Autowired
    private EmsService emsService;
    
    @Autowired
    private SystemConfigure systemConfigure;
    
    @Autowired
    private OrderFeedbackService orderFeedbackService;
    
    @Autowired
    private CardtypeService cardtypeService;
    
    @Autowired
    private SourceConfigure sourceConfigure;
    
    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
    
    
    
    
     /**
      * 
      * 订单手动反馈页面
      * 
      * @param request
      * @param response
      * @param model
      * @return
      * @throws IOException
      * @author haoleitao
      * @date 2013-1-15 下午2:40:54
      */
	 @RequestMapping(value = "orderFreeback/index", method = RequestMethod.GET)
	    public ModelAndView index(HttpServletRequest request, HttpServletResponse response,
	    		//@RequestParam(required = false, defaultValue = "") String from, 
	    		Model model) throws IOException {
	        ModelAndView mav = new ModelAndView("orderFreeback/index");
	   	 request.setAttribute("company_req",companyService.getAllCompanies());
	   //	mav.addObject("company_req",companyService.getAllCompany());
	        return mav;
	    }
	 @RequestMapping(value = "orderFreeback/index", method = RequestMethod.POST)
	    public ModelAndView index_post(HttpServletRequest request, HttpServletResponse response,
	    		//@RequestParam(required = false, defaultValue = "") String from, 
	    		Model model) throws IOException {
	        ModelAndView mav = new ModelAndView("orderFreeback/index");
	   	    request.setAttribute("company_req",companyService.getAllCompanies());
	        return mav;
	    }
	 
	 
	    @RequestMapping(value = "orderFreeback/fblist", method = RequestMethod.POST)
	    public void feebackList(
	    		@RequestParam(required = false, defaultValue = "0") int page,
                @RequestParam Integer rows,
	            @RequestParam(required = false, defaultValue = "0") int state,
	            @RequestParam(required = false, defaultValue = "0" ) int settleType,
                @RequestParam(required = false, defaultValue="") Boolean remark,
	            @RequestParam(required = false, defaultValue = "" ) String companyid ,
	    		HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, ParseException {
			String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
            String sBeginDate = request.getParameter("beginDate");
			Date beginDate = null;
			if (sBeginDate != null && !sBeginDate.isEmpty()) {
				sBeginDate += " 00:00:00";
				beginDate = dateFormat.parse(sBeginDate);
			}
			String sEndDate = request.getParameter("endDate");
			
			Date endDate = null;
			if (sEndDate != null && !sEndDate.isEmpty()) {
				sEndDate += " 23:59:59";
				endDate = dateFormat.parse(sEndDate);
			}

			int totalRecords = orderFeedbackService.getOrderFeedbackCount(companyid,state,settleType,beginDate,endDate,remark);
			List<EdiClearDto> ohList = orderFeedbackService.getAllOrderFeedback(companyid,state,settleType,beginDate,endDate,page-1, rows,remark);
		

            jsonBinder.setDateFormat(dateFormatStr);

			String jsonData = "{\"rows\":" + jsonBinder.toJson(ohList) + ",\"total\":" + totalRecords
					+ " }";
			log.info(jsonData);
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter pt;
			try {
				pt = response.getWriter();
				pt.write(jsonData);
				pt.flush();
				pt.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	   
	    @RequestMapping(value = "orderFreeback/settle/settle", method = RequestMethod.POST)
	    public void settle(
	            @RequestParam(required = false, defaultValue = "")  String rows,
	            @RequestParam(required = false, defaultValue = "")  String companyid,
	    		HttpServletRequest request, HttpServletResponse response) {
  
	    	//获取 参数
            String url = sourceConfigure.getSettle();
            
            String userId = SecurityHelper.getLoginUser().getUserId();
            List result = null;
            try {
					log.debug("结账订单："+rows);
					log.debug("送货公司："+companyid);
//					result = HttpUtil.postUrl(url, params);
                    result = orderFeedbackService.accountShipment(url, rows, companyid, userId);
					log.info("结账结果："+ result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info(e.getMessage());
					result = new ArrayList();
					result.add("结账操作失败");
					
				} 
				if(result == null) result.add("结账操作失败");
				HttpUtil.ajaxReturn(response, StringUtil.nullToBlank(jsonBinder.toJson(result)), "application/text");
	    }
	   
	   /**
	    * 导入结果查询
	    * @param page
	    * @param rows
	    * @param state
	    * @param paytype
	    * @param mailtype
	    * @param ordertype
	    * @param result
	    * @param companyid
	    * @param cardtype
	    * @param cityid
	    * @param request
	    * @param response
	    * @throws IOException
	    * @throws JSONException
	    * @throws ParseException
	    * @author haoleitao
	    * @date 2013-1-15 下午2:40:24
	    */
	    @RequestMapping(value = "orderFreeback/list", method = RequestMethod.POST)
	    public void list(@RequestParam(required = false, defaultValue = "0") int page,
	            @RequestParam Integer rows,
	            @RequestParam(required = false, defaultValue = "0") int state,
	            @RequestParam(required = false, defaultValue = "") String orderid,
	            @RequestParam(required = false, defaultValue = "") String mailid,
	            @RequestParam(required = false, defaultValue = "") String status,
	            @RequestParam(required = false, defaultValue = "") String paytype,
	            @RequestParam(required = false, defaultValue = "") String mailtype,
	            @RequestParam(required = false, defaultValue = "") String ordertype,
	            @RequestParam(required = false, defaultValue = "") String result,
	            @RequestParam(required = false, defaultValue = "") String companyid,
	            @RequestParam(required = false, defaultValue = "") String cardtype,
	            @RequestParam(required = false, defaultValue = "") String cityid,
	    		HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, ParseException {
			String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			String sBeginDate = request.getParameter("beginDate");
			Date beginDate = null;
			if (sBeginDate != null && !sBeginDate.isEmpty()) {
				sBeginDate += " 00:00:00";
				beginDate = dateFormat.parse(sBeginDate);
			}else{
				beginDate =dateFormat.parse(dateFormat2.format(new Date())+" 00:00:00");
			}
			String sEndDate = request.getParameter("endDate");
			
			Date endDate = null;
			if (sEndDate != null && !sEndDate.isEmpty()) {
				sEndDate += " 23:59:59";
				endDate = dateFormat.parse(sEndDate);
			}else{
				endDate =dateFormat.parse(dateFormat2.format(new Date())+" 23:59:59");
			}
			List<Orderhist> ohList = orderhistService.getAllOrderhist(state,beginDate,endDate, orderid, mailid,status,paytype, mailtype, ordertype, result, companyid, cardtype, cityid,page - 1, rows);
			int totalRecords = orderhistService.getOrderhistCount(state,beginDate,endDate,orderid,mailid,status,paytype, mailtype, ordertype, result, companyid, cardtype, cityid);
			
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd").setExclusionStrategies(
							new ExcludeUnionResource(new String[] {"address","contact","company","getcontact","paycontact"})).create();
			String tmpStr= "";
			StringBuffer sb = new StringBuffer("[");
			for(Orderhist oh:ohList){
				tmpStr = gson.toJson(oh);
				tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
				if(oh.getContact() == null){
					tmpStr += ",\"c_name\":\" \"";
				}else{
					tmpStr += ",\"c_name\":\"" + StringUtil.nullToBlank(oh.getContact().getName()) + "\"";	
				}
				
				tmpStr += ",\"c_city\":\"" +StringUtil.nullToBlank(emsService.getCityNameById(oh.getCityid()))+ "\"";
				tmpStr += "}";
				sb.append(tmpStr);
				sb.append(",");
				tmpStr= "";
			}
			
			 tmpStr = sb.toString();
			if (tmpStr.length() > 1)
				tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
			tmpStr += "]";
			
			String jsonData = "{\"rows\":" + tmpStr  + ",\"total\":" + totalRecords 
					+ " }";
			log.info(jsonData);
			
			HttpUtil.ajaxReturn(response, jsonData, "application/json");

	    }
	   
	 
	    
	    

	 
	 
   /**
	* 
	* 获取订单产品信息
	* 
	* @param request
	* @param response
	* @param orderid
	* @throws IOException
	* @author haoleitao
	* @date 2013-1-15 下午2:41:46
	*/
	 @RequestMapping(value = "orderFreeback/getOrderdet", method = RequestMethod.POST)
	public void getAllOrderdet(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam(required = false, defaultValue = "") String orderid
			) throws IOException {
	        
		List<Orderdet> list = orderdetService.getAllOrderdet(orderid);
			Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd").setExclusionStrategies(
					new ExcludeUnionResource(new String[] { "contact" }))			
					.create();
		StringBuffer sb = new StringBuffer("[");
		String tmpStr= "";
		for(Orderdet od:list){
			tmpStr = gson.toJson(od);
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
			tmpStr += ",\"c_name\":\"" + StringUtil.nullToBlank(od.getContact().getName()) + "\"";
			//tmpStr += ",\"c_city\":\"" +StringUtil.nullToBlank(emsService.getCityNameById(oh.getCityid()))+ "\"";
			tmpStr += "}";
			sb.append(tmpStr);
			sb.append(",");
			tmpStr= "";
		}
		
		 tmpStr = sb.toString();
		if (tmpStr.length() > 1)
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
		tmpStr += "]";
		
			
	String jsonData = "{\"rows\":" + tmpStr+ ",\"total\":" +list.size()+" }";
	log.info(jsonData);
	HttpUtil.ajaxReturn(response, jsonData, "application/json");
	}


			@RequestMapping(value = "orderFreeback/fileupload", method = RequestMethod.POST)
		    public ModelAndView fileupload(MultipartHttpServletRequest request, HttpServletResponse response,
		    		@RequestParam(required = false, defaultValue = "") String v_settlType, 
		    		Model model) throws IOException {
				log.info(v_settlType);
				Map map = new HashMap();
			   MultipartFile multipartFile = request.getFile("uploadfile");
			   request.setAttribute("company_req",companyService.getAllCompanies());
               map.put("settleType",v_settlType);
		        if(multipartFile!=null)
		        {
		            try {

		                    String fileName = multipartFile.getOriginalFilename();
		                    //保存文件
		                    
		                    String realPathDir= systemConfigure.getFreebackfilePathDir()+getFilePath(Integer.valueOf(v_settlType));
		                    File savedir = new File(realPathDir);
		                    if (!savedir.exists()) {
		                        savedir.mkdirs();
		                    }
		                    
		                    log.info("保存的文件路径为:"+savedir.getPath() );
		                    
		                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		                    String savepath = realPathDir + (realPathDir.endsWith(File.separator)?"":File.separator);
		                    savepath += sdf.format(new Date()) + "_" + fileName;
		                    File file = new File(savepath);
		                    FileOutputStream fops = new FileOutputStream(file);
		                    fops.write(multipartFile.getBytes());
		                    fops.flush();
		                    fops.close();
		                    map.put("msg", "文件上传成功:"+fileName);
		   
		                    return new ModelAndView("orderFreeback/index",map);
		             
		            } catch (IOException e) {
		                map.put("msg", e.getMessage());
		                e.printStackTrace();
		                return new ModelAndView("orderFreeback/index",map);
		            }
		        }
		        else
		        {
		            return new ModelAndView("orderFreeback/index",map);
		        }
			 
			 
		    }

	 
	 @RequestMapping(value = "orderFreeback/findResult", method = RequestMethod.GET)
	    public ModelAndView findResult(HttpServletRequest request, HttpServletResponse response,
	    		//@RequestParam(required = false, defaultValue = "") String from, 
	    		Model model) throws IOException {
	        ModelAndView mav = new ModelAndView("orderFreeback/findResult");
	        
		    initFreeback(request);
	        return mav;
	    }
	 
	 public void initFreeback(HttpServletRequest request){
		 
		 Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd")			
					.create();
		 //初始化付款方式
		 request.setAttribute("paytype_req",namesService.getAllNames("PAYTYPE"));
		 request.setAttribute("paytype_json","{\"rows\":"+gson.toJson(namesService.getAllNames("PAYTYPE"))+" }");
		//初始订购方式
		 request.setAttribute("buytype_req",namesService.getAllNames("BUYTYPE"));
		 request.setAttribute("buytype_json","{\"rows\":"+gson.toJson(namesService.getAllNames("BUYTYPE"))+" }");
		//初始订单类型
		 request.setAttribute("ordertype_req",namesService.getAllNames("ORDERTYPE"));
		 request.setAttribute("ordertype_json","{\"rows\":"+gson.toJson(namesService.getAllNames("ORDERTYPE"))+" }");
		//初始反馈结果
		 request.setAttribute("orderfeedback_req",namesService.getAllNames("ORDERFEEDBACK"));
		 request.setAttribute("orderfeedback_json","{\"rows\":"+gson.toJson(namesService.getAllNames("ORDERFEEDBACK"))+" }");
		//送货公司
		 request.setAttribute("company_req",companyService.getAllCompanies());
		 request.setAttribute("company_json","{\"rows\":"+gson.toJson(companyService.getAllCompanies())+" }");
		//信用卡
		 log.info(gson.toJson(cardtypeService.getAllCardtype()));
		 request.setAttribute("cartdype_req",cardtypeService.getAllCardtype());
		 request.setAttribute("cardtype_json","{\"rows\":"+gson.toJson(cardtypeService.getAllCardtype())+" }");
		//城市
		 request.setAttribute("city_req",emsService.getAllEms());
		//FR状态 
		 request.setAttribute("frstate_req",namesService.getAllNames("CUSTOMIZESTATUS"));
		 request.setAttribute("frstate_json","{\"rows\":"+gson.toJson(namesService.getAllNames("CUSTOMIZESTATUS"))+" }");
		//销售方式
		 //request.setAttribute("saletype_req",namesService.getAllNames("SALETYPE"));
		 request.setAttribute("saletype_json","{\"rows\":"+gson.toJson(namesService.getAllNames("SALETYPE"))+" }");
		 
		 //产品状态
		 //request.setAttribute("prodstate_req",namesService.getAllNames("PRODSTATUS"));
		 request.setAttribute("prodstate_json","{\"rows\":"+gson.toJson(namesService.getAllNames("PRODSTATUS"))+" }");
		

	 }
	 
	 
		@RequestMapping(value="orderFreeback/company")
		public void getCityAll(
				HttpServletRequest request, HttpServletResponse response){
			
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(companyService.getAllCompanies()), "application/json");
		}
	 
	 public String getFilePath(Integer stype){
		 
		 if(stype == 1) return File.separator+"feedback_ems"+File.separator+"input";
		 if(stype == 2) return File.separator+"feedback_teneng"+File.separator+"input";
		 if(stype == 3) return File.separator+"feedback_songhuo"+File.separator+"input";
		 if(stype == 4) return File.separator+"feedback_sichuanems"+File.separator+"input";
		 if(stype == 5) return File.separator+"feedback_creditCard"+File.separator+"input";
		 if(stype == 6) return File.separator+"feedback_jiangxiems"+File.separator+"input";
		 
        return "";
		 
	 }
	 
	 private List<EdiClearDto> po2vo(List<EdiClear> list){
		 List<EdiClearDto> liste = new ArrayList();
		 for(EdiClear clear : list){
			 EdiClearDto edto = new EdiClearDto();
			 
			// edto.setCompany(orderhistService.getCompanyNameByOrderId(clear.getOrderid()));
			 edto.setClearid(clear.getClearid().toString());
			 edto.setImpdt(clear.getImpdt());
			 edto.setImpusr(clear.getImpusr());
			 edto.setMailid(clear.getMailid());
			 edto.setNowmoney(clear.getNowmoney());
			 edto.setOrderid(clear.getOrderid());
			 edto.setPostFee(clear.getPostFee());
			 edto.setReason(clear.getReason());
			 edto.setRemark(clear.getRemark());
			 edto.setSenddt(clear.getSenddt());
			 edto.setStatus(clear.getStatus());
			 edto.setTotalprice(clear.getTotalprice());
			 edto.setType(clear.getType());
			 liste.add(edto);
		 }
		 return liste;
		 
	 }
	 
	 
	
}
