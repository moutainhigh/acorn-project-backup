/*
 * @(#)CustomerController.java 1.0 2013-5-22下午2:20:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.agent.MemberLevel;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.service.MemberLevelService;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-22 下午2:20:53 
 * 
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
	
	@Autowired
	private ContactService  contactService;
    @Autowired
	private MemberLevelService memberLevelService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private PotentialContactService potentialContactService;

    @InitBinder
    public void InitBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    /**
	 * 查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/find",method = RequestMethod.POST)
	public void mycustomer_find(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "") String province,
            @RequestParam(required = false, defaultValue = "") String cityId,
            @RequestParam(required = false, defaultValue = "") String countyId,
            @RequestParam(required = false, defaultValue = "") String areaId,
            HttpServletResponse response){
		 Map map = new HashMap();
		 CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
		 customerBaseSearchDto.setName(name);
		 customerBaseSearchDto.setPhone(phone);
		 
		 customerBaseSearchDto.setAreaId(areaId.toString());
		 customerBaseSearchDto.setCityId(cityId.toString());
		 customerBaseSearchDto.setCountyId(countyId.toString());
		 customerBaseSearchDto.setProvinceId(province);
		 DataGridModel dataGridModel = new DataGridModel();
		 dataGridModel.setPage(page);
		 dataGridModel.setRows(rows);
	   map = contactService.findByBaseCondition(customerBaseSearchDto, dataGridModel);
		logger.info("map:"+map);
		jsonBinder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(po2vo(map)), "application/json");
	}

	/**
	 * 查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/phone/find",method = RequestMethod.POST)
	public void mycustomer_findByPhone(
            @RequestParam(required = false, defaultValue = "") String phone,
            HttpServletResponse response){
		 Map map = new HashMap();
		 Boolean result=false;
		 CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
		 customerBaseSearchDto.setPhone(phone);
		 DataGridModel dataGridModel = new DataGridModel();
		 dataGridModel.setPage(1);
		 dataGridModel.setRows(5);
	    map =contactService.findByBaseCondition(customerBaseSearchDto, dataGridModel);
		logger.info("map:"+map);
	    if(map != null){
	    	if(Integer.valueOf(map.get("total").toString()) > 0) result=true;
	    }
	    
        map.put("result", result);
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}
	
	/**
	 * 根据客户编号,运单号,订单号 ,查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/more/find",method = RequestMethod.POST)
	public void mycustomer_more_find(
            @RequestParam(required = false, defaultValue = "") String typeValue, //客户编号
            @RequestParam(required = false, defaultValue = "") String findValue,//运单号
            HttpServletResponse response){
		 Map map = new HashMap();
        Contact contact = null;
	     CustomerDto dto = null;
	     String customerId="",shipmentId="",orderId="";
	     if(typeValue.equals("customerId")) customerId=findValue;
	     if(typeValue.equals("shipmentId")) shipmentId=findValue;
	     if(typeValue.equals("orderId")) orderId=findValue;
	     if(!customerId.equals("")){
	    	  //正式客户
		     try {
		    	 dto= new CustomerDto();
		    	 contact = contactService.get(customerId);
				if(contact == null){
					PotentialContact pcontact  = potentialContactService.queryById(Long.valueOf(customerId));
					if(pcontact != null){
					dto.setPotentialContactId(pcontact.getContactId().toString());
					dto.setName(pcontact.getName());
					dto.setCrdt(DateUtil.dateToString(pcontact.getCrdt()));
					dto.setCrusr(pcontact.getCrusr());
                    dto.setContactType(CustomerFrontDto.POTENTIAL_CONTACT);
					}
					
				}else{
					   dto.setContactId(contact.getContactid());
					   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
					   dto.setAddressDto(addressdto);
					   dto.setName(contact.getName());
					   dto.setLevel(contactService.findLevelByContactId(customerId));
					   dto.setCrdt(contact.getCrdtString());
					   dto.setCrusr(contact.getCrusr());
                       dto.setContactType(CustomerFrontDto.CONTACT);
				}
			} catch (Exception e) {
			     logger.info("查询客户信息失败,"+e.getMessage());
			}
	     }else if(! shipmentId.equals("")){
	    	 contact = contactService.findByMailId(shipmentId);
	       	 dto= new CustomerDto();
	    	 if(contact != null){
			   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
			   dto.setContactId(contact.getContactid());
			   dto.setAddressDto(addressdto);
			   dto.setName(contact.getName());
			   dto.setLevel(contactService.findLevelByContactId(customerId));
			   dto.setCrdt(contact.getCrdtString());
			   dto.setCrusr(contact.getCrusr());
               dto.setContactType(CustomerFrontDto.CONTACT);
	    	 }
	     }else if(! orderId.equals("")){
	       	 dto= new CustomerDto();
		     contact = contactService.findByOrderId(orderId);
		      if(contact != null){
			   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
			   dto.setContactId(contact.getContactid());
			   dto.setAddressDto(addressdto);
			   dto.setName(contact.getName());
			   dto.setLevel(contactService.findLevelByContactId(customerId));
			   dto.setCrdt(contact.getCrdtString());
			   dto.setCrusr(contact.getCrusr());
               dto.setContactType(CustomerFrontDto.CONTACT);
		      }
	     }
	     
		 //潜客

        List<CustomerDto> list = new ArrayList();
        if (StringUtils.isNotBlank(dto.getContactId()) || StringUtils.isNotBlank(dto.getPotentialContactId())) {
            list.add(dto);
            map.put("total", 1);
            map.put("rows", list);
        } else {
            map.put("total", 0);
            map.put("rows", list);
        }

        logger.info("map:"+map);
        jsonBinder.setDateFormat("yyyy-MM-dd HH:mm:ss");
         HttpUtil.ajaxReturn(response, jsonBinder.toJson(po2vo(map)), "application/json");
	}

    public Map po2vo(Map map){
        List<CustomerDto> list = (List<CustomerDto>) map.get("rows");
        List<CustomerFrontDto> result = new ArrayList();
        Map<String,MemberLevel> mapLevel= memberLevelService.queryAlltoMap();

        System.out.println(mapLevel);
        for(CustomerDto po : list  ){
            CustomerFrontDto vo = new  CustomerFrontDto();
            vo.setCustomerType(po.getContactType());
            if(po.getContactId() == null){
                vo.setCustomerId(po.getPotentialContactId());
            }else{
                vo.setCustomerId(po.getContactId());
                AddressDto addressDto= addressService.getContactMainAddress(po.getContactId());
                vo.setDetailedAddress(addressDto==null ? "":addressDto.getAddressDesc());
                vo.setAddress(addressDto==null ? "":addressService.addressConcordancyNoAddressInfo(addressDto));
                vo.setAddressid(addressDto==null ? null:addressDto.getAddressid());
            }
            vo.setName(po.getName());
            vo.setLevel(po.getLevel());
            vo.setCrusr(po.getCrusr());
            vo.setCrdt(po.getCrdtDate());
            vo.setSex(po.getSex());
            result.add(vo);
        }
        map.put("rows", result);

        return map;
    }
}
