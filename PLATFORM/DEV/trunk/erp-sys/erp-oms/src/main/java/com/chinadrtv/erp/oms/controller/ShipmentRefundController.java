package com.chinadrtv.erp.oms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;
import com.chinadrtv.erp.oms.dto.OrderRefundDto;
import com.chinadrtv.erp.oms.dto.OrderdetRefundDto;
import com.chinadrtv.erp.oms.dto.OrderhistRefundDto;
import com.chinadrtv.erp.oms.dto.RefundSearchDto;
import com.chinadrtv.erp.oms.dto.ShipmentRefundDto;
import com.chinadrtv.erp.oms.exception.OmsException;
import com.chinadrtv.erp.oms.exception.ShipmentRefundErrorCode;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.ShipmentRefundDetailService;
import com.chinadrtv.erp.oms.service.ShipmentRefundService;
import com.chinadrtv.erp.oms.service.WarehouseService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 运单半拒收.
 * User: 徐志凯
 * Date: 13-3-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
public class ShipmentRefundController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentRefundController.class);
    
	private static final String REJECT_CODE = "REJECT_CODE";

    @Autowired
    private ShipmentRefundService shipmentRefundService;
    @Autowired
    private NamesService namesService;
    @Autowired
	private CompanyService companyService;
    @Autowired
    private ShipmentRefundDetailService shipmentRefundDetailService;
    @Autowired
    private WarehouseService warehouseService;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/shipment/refund/checkIn")
    public ModelAndView auth()  {
        ModelAndView modelAndView = new ModelAndView("shipment/refund");
        modelAndView.addObject("url", "/shipment/refund/loadOrderhist");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        modelAndView.addObject("postUrl", "/shipment/refund/upload");
        return modelAndView;
    }

    /**
     * 查询客户的订单
     * @param page
     * @param rows
     * @param orderId
     * @param contactId
     * @param mailId
     * @param phone
     * @param entityId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/shipment/refund/loadOrderhist", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderhistRefundDto> orderhist_load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String orderId,
            @RequestParam(required = false, defaultValue="") String contactId,
            @RequestParam(required = false, defaultValue="") String mailId,
            @RequestParam(required = false, defaultValue="") String phone,
            @RequestParam(required = false, defaultValue="") String entityId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        System.out.println("start search ****");
        RefundSearchDto refundSearchDto = new RefundSearchDto(page,rows, orderId,contactId,mailId,phone,entityId);
        /*List<OrderhistRefundDto> orderhistRefundDtoList=new ArrayList<OrderhistRefundDto>();//shipmentRefundService.findContactOrderhist(refundSearchDto);
        OrderhistRefundDto orderhistRefundDto=new OrderhistRefundDto();
        orderhistRefundDto.setAddress("test");
        orderhistRefundDto.setRfOutDate(new Date());
        orderhistRefundDto.setOrderId("1");
        orderhistRefundDto.setMailId("xxx");
        orderhistRefundDto.setMailPrice("11.0");
        orderhistRefundDto.setPhone("123");
        orderhistRefundDto.setTotalPrice("22.2");
        orderhistRefundDto.setOrderTypeId("1");
        orderhistRefundDtoList.add(orderhistRefundDto);

        OrderhistRefundDto orderhistRefundDto2=new OrderhistRefundDto();
        orderhistRefundDto2.setAddress("test2");
        orderhistRefundDto2.setRfOutDate(new Date());
        orderhistRefundDto2.setOrderId("2");
        orderhistRefundDto2.setMailId("xxx");
        orderhistRefundDto2.setMailPrice("22.0");
        orderhistRefundDto2.setPhone("1234");
        orderhistRefundDto2.setTotalPrice("44.4");
        orderhistRefundDto2.setOrderTypeId("2");
        orderhistRefundDtoList.add(orderhistRefundDto2);

        return orderhistRefundDtoList;*/

        return shipmentRefundService.findContactOrderhist(refundSearchDto);
    }

    @RequestMapping(value = "/shipment/refund/submitRefund", method = RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String refund_save(@RequestBody OrderRefundDto orderRefundDto){
        if(orderRefundDto!=null){
            if(SecurityHelper.getLoginUser()==null){
                return "无法获取登录用户！";
            }
            if(StringUtils.isEmpty(SecurityHelper.getLoginUser().getUserId())){
                return "无法获取登录用户！";
            }
            orderRefundDto.setUserId(SecurityHelper.getLoginUser().getUserId());
            shipmentRefundService.addShipmentRefund(orderRefundDto);
        }
        return "succ";

        //orderRefundDto.setUserId("1");
        /*
        if(orderRefundDto.getOrderdetRefundDtoSet()!=null)
        {
            for(OrderdetRefundDto orderdetRefundDto: orderRefundDto.getOrderdetRefundDtoSet())
            {
                System.out.println(orderdetRefundDto.getId());
            }
        }*/
    }

    @RequestMapping(value = "/shipment/refund/loadOrderdet", method = RequestMethod.POST)
    @ResponseBody
    public List<OrderdetRefundDto> orderdet_load(String orderId)
    {
        if(StringUtils.isEmpty(orderId))
        {
            return null;
        }

        return shipmentRefundService.findRefundOrderdet(orderId);

        /*List<OrderdetRefundDto> orderdetRefundDtoList=new ArrayList<OrderdetRefundDto>();
       OrderdetRefundDto orderdetRefundDto=new OrderdetRefundDto();
       orderdetRefundDto.setProdPrice(new BigDecimal("11.1"));
       orderdetRefundDto.setProdQuantity(1L);
       orderdetRefundDto.setProdName("xx:"+orderId);
       orderdetRefundDtoList.add(orderdetRefundDto) ;

       OrderdetRefundDto orderdetRefundDto1=new OrderdetRefundDto();
       orderdetRefundDto1.setProdPrice(new BigDecimal("22.2"));
       orderdetRefundDto1.setProdQuantity(2L);
       orderdetRefundDto1.setProdName("yy:"+orderId);
       orderdetRefundDtoList.add(orderdetRefundDto1) ;

       return orderdetRefundDtoList;*/
    }

    @RequestMapping(value = "/shipment/refund/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new ModelAndView("shipment/refund");
    }

    @RequestMapping(value = "/shipment/refund/lookup/orderType", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, String>> LookupOrderType()
            throws Exception {
        /*List<Names> orderTypeList=new ArrayList<Names>();
        Names names1=new Names();
        names1.setId(new NamesId("1","1"));
        names1.setDsc("正常");
        orderTypeList.add(names1);

        Names names2=new Names();
        names2.setId(new NamesId("2","2"));
        names2.setDsc("特殊");
        orderTypeList.add(names2);*/

        List<Names> orderTypeList=namesService.getAllNames("ORDERTYPE");
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
    public Object handleException(Exception ex, HttpServletRequest request) {
        String strPath=request.getServletPath();
        String strError="";
        if(strPath.endsWith("/shipment/refund/loadOrderhist"))
        {
            String token="";
            if(ex instanceof OmsException)
            {
                token=getCodeDesc(((OmsException) ex).getCode());
            }
            else
            {
                token="系统异常："+ex.getMessage();
            }

            strError=String.format("{\"total\":0,\"rows\":[],\"err\":\"%s\"}",token);
            return strError;
            //return "{\"total\":0,\"rows\":[],\"err\":\"错误\"}";
        }
        else if(strPath.endsWith("/shipment/refund/loadOrderdet"))
        {

        }
        else if(strPath.endsWith("/shipment/refund/submitRefund"))
        {
            String token="";
            if(ex instanceof OmsException)
            {
                token=getCodeDesc(((OmsException) ex).getCode());
            }
            else
            {
                token="系统异常："+ex.getMessage();
            }
            strError=token;
        }

        logger.error(strPath,ex);

        Map<String,String> map=new HashMap<String, String>();
        map.put("err",strError);
        return map;
    }

    /**
     * 获取错误描述
     * 暂时放置在程序代码中
     * @param code
     * @return
     */
    private String getCodeDesc(String code)
    {
        if(StringUtils.isNotEmpty(code))
        {
            if(ShipmentRefundErrorCode.codeMap.containsKey(code))
            {
                return ShipmentRefundErrorCode.codeMap.get(code);
            }
        }
        return "";
    }
    
    
    
    
    
    
    
    

	/*================================== SR3.1_1.2仓库半拒收登记包裹  ================================*/
	
    
    
    
	
	/**
	 * 首页
	* @return ModelAndView
	 */
	@RequestMapping(value = "/shipment/refundRegister")
	public ModelAndView refundRegister(){
		ModelAndView mav = new ModelAndView("shipment/refundRegister");
		
		List<Company> compnayList = companyService.getAllCompanies();
		mav.addObject("compnayList", compnayList);
		
		List<Names> rejectList = namesService.getAllNames(REJECT_CODE);
		mav.addObject("rejectList", rejectList);
		
		return mav;
	}
	
	/**
	 * 查询本人已登记的数量
	* @Description: 
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/shipment/queryRegisterCount")
	@ResponseBody
	public Map<String, Object> queryRegisterCount(String entityId){
		Map<String, Object> checkedMap = new HashMap<String, Object>();
		boolean success = false;
		try {
			checkedMap = shipmentRefundService.queryCheckCount(entityId);
			success = true;
		} catch (Exception e) {
			logger.error("查询登记数量错误", e);
			e.printStackTrace();
		}
		
		checkedMap.put("success", success);
		
		return checkedMap;
	}
	
	@RequestMapping(value = "/shipment/queryRefundList")
	@ResponseBody
	public Map<String, Object> queryRefundList(String mailId, String shipmentId, String entityId){
		return shipmentRefundService.queryRefundList(mailId, shipmentId, entityId);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/shipment/saveRefundList")
	@ResponseBody
	public Map<String, Object> saveRefundList(String jsonList, String rejectCode, String shipmentRefund){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			JSONObject jsonObject = JSONObject.fromObject(shipmentRefund);
			jsonObject.remove("shipmentRefundDetails");
			ShipmentRefund sr = (ShipmentRefund) JSONObject.toBean(jsonObject, ShipmentRefund.class);
			JSONArray jsonArr = JSONArray.fromObject(jsonList);
			List<ShipmentRefundDetail> dataList = JSONArray.toList(jsonArr, ShipmentRefundDetail.class);
			shipmentRefundDetailService.saveBatch(dataList, rejectCode, sr);
			success = true;
		} catch (Exception e) {
			logger.error("批量保存失败", e);
			message = "批量保存失败" + e.getMessage();
			e.printStackTrace();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	/**
	 * 删除退包
	* @Description: 
	* @param shipmentRefund
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/shipment/deleteRefund")
	@ResponseBody
	public Map<String, Object> deleteRefund(String shipmentRefund){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(shipmentRefund);
		ShipmentRefund sr = (ShipmentRefund) JSONObject.toBean(jsonObject, ShipmentRefund.class);
		boolean success = false;
		String message = "";
		try {
			if(sr.getStatus()==null || sr.getStatus()!=3){
				shipmentRefundDetailService.deleteRefund(sr);
				success = true;
			}else{
				throw new BizException("半拒收退货确认已完成,不允许删除！");
			}
		} catch (Exception e) {
			message = "删除退包失败" + e.getMessage();
			logger.error("删除退包失败", e);
			e.printStackTrace();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}
	
	
	
	/*================================== 半拒收退货确认  ================================*/
	
	
	/**
	 * <p>index</p>
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/shipment/refundSend")
	public ModelAndView refundSend(){
		ModelAndView mav = new ModelAndView("shipment/refundSend");
		
		List<Company> compnayList = companyService.getAllCompanies();
		mav.addObject("compnayList", compnayList);
		
		List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
		mav.addObject("warehouseList", warehouseList);
		return mav;
	}
	
	/**
	 * <p>列表</p>
	 * @param srDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/shipment/queryRefundSendList")
	@ResponseBody
	public Map<String, Object> queryRefundSendList(ShipmentRefundDto srDto){
		Map<String, Object> pageMap = shipmentRefundService.queryRefundSendList(srDto);
		return pageMap;
	}
	
	
	/**
	 * <p>导出</p>
	 * @param srDto
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/shipment/exportRefundSendList")
	public void exportRefundSendList(String ids, HttpServletResponse response) throws IOException {
		HSSFWorkbook wb = null;
		try {
			wb = shipmentRefundService.exportRefundSendList(ids);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (wb != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "Report" +sdf.format(new Date()) + ".xls";
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);

			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
		}
	}
	
	/**
	 * <p>生成退包入库单</p>
	 * @param ids
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/shipment/generateRefundList")
	@ResponseBody
	public Map<String, Object> generateRefundList(ShipmentRefundDto srDto){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			message = shipmentRefundService.generateRefundList(srDto);
			if(message!=null && !"".equals(message)){
				success = false;
			}else{
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成退包入库单失败： " + e.getMessage();
			logger.error("生成退包入库单失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
}
