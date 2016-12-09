/*
 * @(#)AddressServiceTest.java 1.0 2013-5-3下午2:02:03
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.dao.AreaDao;
import com.chinadrtv.erp.uc.dao.CityDao;
import com.chinadrtv.erp.uc.dao.CountyAllDao;
import com.chinadrtv.erp.uc.dao.ProvinceDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.test.AppTest;

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
 * @since 2013-5-3 下午2:02:03 
 * 
 */
public class AddressServiceTest extends AppTest {

	@Autowired
	private AddressService addressService;
    @Autowired
	private ChangeRequestService changeRequestService;
	@Autowired
	private AddressExtDao addressExtDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private CountyAllDao countyAllDao;
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(addressService);
	}
	
	//@Test
	public void testQuery(){
		String id = "28096312";
		AddressDto address = addressService.queryAddress(id);
		Assert.assertNotNull(address);
	}
	
	@Test
//	@Rollback(false)
	public void insertTest(){
		AddressDto address = new AddressDto();
		
		address.setAddress("田林路879号");
		address.setState("03");
		address.setCityId(5);
		address.setAreaid(44211);
		address.setZip("201909");
		address.setContactid("956918248");
		address.setAddrtypid("2");
		address.setAddconfirmation("");
		address.setAddrconfirm("-1");
		address.setFlag("");
		address.setCountyId(3132);
		address.setIsdefault("0");
		
		Address returnAddress = addressService.addAddress(address);
		
		Assert.assertNotNull(returnAddress);
		Assert.assertEquals("CD", returnAddress.getCity());
	}
	
	
	@Test
//	@Rollback(false)
	public void updateTest(){
		String addressId = "28208819";
		
		AddressDto addressDto = addressService.queryAddress(addressId);
		
		addressDto.setAddress("真大路210号");
		addressDto.setIsdefault("0");
		addressDto.setCountyId(3132);
		addressDto.setCityId(5);
		addressDto.setAreaid(null);
		Address returnedUpdateAdd = addressService.updateAddress(addressDto);
		Assert.assertNotNull(returnedUpdateAdd);
		
	}
	
	
	@Test
//	@Rollback(false)
	public void updateContactMainAddress(){
		String contactId = "910298214";
		AddressDto address = new AddressDto();
		
		address.setAddress("北京西单");
		address.setState("03");
		address.setCityId(5);
		address.setAreaid(44211);
		address.setZip("201909");
		address.setContactid("910298214");
		address.setAddrtypid("2");
		address.setAddconfirmation("");
		address.setAddrconfirm("-1");
		address.setFlag("");
		address.setCountyId(3132);
		address.setIsdefault("0");
		
		Address returnAddress = addressService.addAddress(address);
		
		addressService.updateContactMainAddress(contactId, returnAddress.getAddressid());
	}
	

	@Test
	@SuppressWarnings("unchecked")
	public void queryAddressPageList(){
		String contactId = "910298214";
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setRows(1);
		Map<String, Object> pageMap= addressService.queryAddressPageList(dataModel, contactId);
		
		Integer count = (Integer) pageMap.get("total");
		List<AddressDto> addressDtoList = (List<AddressDto>) pageMap.get("rows");
		
		Assert.assertTrue(count>=0);
		Assert.assertNotNull(addressDtoList);
	}
	
	@Test
	//@Rollback(false)
	public void applyAddRequest(){
		String contactId = "910698284";
    	String userId = "17353";
    	String remark = "创建流程";
    	String deptId= "ERP";
    	
    	UserBpm userBpm = new UserBpm();
    	userBpm.setContactID(contactId);
    	userBpm.setCreateDate(new Date());
    	userBpm.setCreateUser(userId);
    	userBpm.setDepartment(deptId);
    	
    	String batchId = changeRequestService.createChangeRequest(userBpm);
    	
    	String addressDesc = "河南省许昌市魏都区建设路11号";
    	Address address = new Address();
    	address.setAddress(addressDesc);
    	address.setState("20");
    	address.setCity("HYWD");
    	address.setArea("3277");
    	address.setZip("461000");
    	address.setAddrtypid("1");
    	address.setAdditionalinfo("0");
    	address.setAddrconfirm("0");
    	address.setIsdefault("-1");
    	
    	AddressExt addressExt = new AddressExt();
    	addressExt.setAddressDesc(addressDesc);
    	Province province = provinceDao.get("20");
    	addressExt.setProvince(province);
    	CityAll city = cityDao.get(208);
    	addressExt.setCity(city);
    	CountyAll county = countyAllDao.get(1731);
    	addressExt.setCounty(county);
    	AreaAll area = areaDao.get(41986);
    	addressExt.setArea(area);
    	addressExt.setAddressType("0");
    	addressExt.setAreaName(area.getAreaname());
    	
		addressService.applyAddRequest(contactId, address, addressExt, remark, userId, deptId, batchId);
	}
	
	
	/**
	 * <p>修改流程</p>
	 */
	@Test
	//@Rollback(false)
	public void applyUpdateRequest(){
		String contactId = "910698284";
		String addressId = "28096312";
    	String userId = "27427";
    	String remark = "创建流程";
    	String deptId= "22";
    	
    	UserBpm userBpm = new UserBpm();
    	userBpm.setContactID(contactId);
    	userBpm.setCreateDate(new Date());
    	userBpm.setCreateUser(userId);
    	userBpm.setDepartment(deptId);
    	
    	String batchId = changeRequestService.createChangeRequest(userBpm);
    	
    	AddressExt addressExt = addressExtDao.get(addressId);
    	Address address = addressDao.get(addressId);
    	address.setZip("200001");
    	CityAll city = cityDao.get(157);
    	addressExt.setCity(city);
    	addressService.applyUpdateRequest(address, addressExt, remark, userId, deptId, batchId);
	}

//    @Test
//    @Rollback(false)
//    public void testAddToBlackList(){
//        addressService.addToBlackList("28096312");
//    }

//    @Test
//    @Rollback(false)
//    public void testRemoveFromBlackList(){
//        addressService.removeFromBlackList("28096312");
//    }
}
