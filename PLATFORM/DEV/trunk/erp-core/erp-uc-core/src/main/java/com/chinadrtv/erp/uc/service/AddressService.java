package com.chinadrtv.erp.uc.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.uc.dto.AddressDto;

/**
 * 
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
 * @since 2013-5-3 下午1:55:06 
 *
 */
public interface AddressService extends GenericService<Address, String> {
	

	Address get(String id);
	
	/**
	 * <p>查询地址</p>
	 * @param id
	 * @return AddressDto
	 */
	AddressDto queryAddress(String addressId);
	
	/**
	 * <p>API-UC-11.新增客户地址</p>
	 * @param addressDto
	 * @return Address
	 */
	Address addAddress(AddressDto addressDto);
	
	/**
	 * <p>批量添加地址</p>
	 * @param addressDtoList
	 * @return Integer
	 */
	int addAddress(List<AddressDto> addressDtoList);
	
	/**
	 * <p>API-UC-13.更新客户地址</p>
	 * @param addressDto
	 * @return Address
	 */
	Address updateAddress(AddressDto addressDto);
	
	/**
	 * <p>添加客户地址审批流程 </p>
	 * @param address
	 * @param addressExt
	 * @param remark	审批建议
	 * @param userId	当前用户ID
	 * @param roleId	当前用户角色ID
	 * @param batchId	批号
	 * @return addressId
	 */
	void applyAddRequest(String contactId, Address address, AddressExt addressExt, String remark, String userId, String deptId, String batchId);
	
	
	/**
	 * <p>添加客户地址结束审批流程</p>
	 * @param addressId
	 * @param remark
	 * @param userId
	 * @param instId
	 * @throws MarketingException
	 */
	void finishAddRequest(String addressId, String remark, String applyUser, String approveUser, String instId) throws MarketingException;
	
	/**
	 * <p>SR1：客户地址修改调用修改审批流程 </p>
	 * @param addressDto
	 * @param remark	审批建议
	 * @param userId	当前用户ID
	 * @param roleId	当前用户角色ID
	 * @param batchId	批号
	 */
	void applyUpdateRequest(Address address, AddressExt addressExt, String remark, String userId, String deptId, String batchId);
	
	/**
	 * <p>结束修改审批流程</p>
	 * @param addressId
	 * @param remark
	 * @param userId
	 * @param taskId
	 */
	void finishUpdateRequest(String addressId, String remark, String applyUser, String approveUser, String instId) throws MarketingException;
	
	/**
	 * <p>API-UC-14.设置客户主地址</p>
	 * @param contactId
	 * @param addressId
	 * @return boolean
	 */
	void updateContactMainAddress(String contactId, String addressId);
	
	/**
	 * <p>API-UC-15.查询客户地址</p>
	 * @param contactId
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAddressPageList(DataGridModel dataGridModel, String contactId);
	
	/**
	 * <p>API-UC-42.查询客户主地址</p>
	 * @param contactId
	 * @return Address
	 */
	public AddressDto getContactMainAddress(String contactId);
	/**
	 * <p>API-UC-46 地址拼接</p>
	 *  @param addressDto
	 *  @return  address 带详细地址;
	 */
	public String addressConcordancy(AddressDto addressDto); 
	
	/**
	 * <p>API-UC-46 地址拼接</p>
	 *  @param addressDto
	 *  @return  address 不带详细地址;
	 */
	public String addressConcordancyNoAddressInfo(AddressDto addressDto);

    /**
     * 更新第四级地址
     * @param addressid
     * @param areaId
     * @return
     */
    Address updateAddressArea(String addressid, Integer areaId);

    boolean checkEffectAddress(AddressDto addressDto);

    /**
     * 地址加入黑名单
     * @param addressId
     */
//    public void addToBlackList(String addressId);

    /**
     * 退出黑名单
     * @param addressId
     */
//    public void removeFromBlackList(String addressId);

    /**
     * 检查订单地址是否为黑名单地址并且使用货到付款的支付方式
     * @param addressId
     */
//    void checkBlackListContact(String paytype, String addressId) throws ServiceException;
}
