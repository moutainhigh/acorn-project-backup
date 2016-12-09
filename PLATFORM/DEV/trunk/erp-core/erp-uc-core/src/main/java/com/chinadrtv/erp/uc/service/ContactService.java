package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.*;
import com.chinadrtv.erp.user.model.AgentUser;

import java.util.List;
import java.util.Map;

/**
 * 客户相关接口
 * 各种条件查询客户，新增客户，更新客户
 * User: xieguoqiang
 * Date: 13-5-3
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
public interface ContactService extends GenericService<Contact, String> {
    /**
     * 根据ID查找正式客户
     * @param contactId
     * @return Contact
     * @throws Exception
     */
    Contact get(String contactId);
    
    /**
     * 根据ID查找正式客户
     * @param contactId
     * @return Contact
     * @return
     */
    Contact findById(String contactId);

    /**
     * 根据订单号查找对应正式客户
     * @param orderId
     * @return Contact
     */
    Contact findByOrderId(String orderId);

    /**
     * 根据运单ID查找对应正式客户
     * @param shipmentId
     * @return Contact
     */
    Contact findByShipmentId(String shipmentId);

    /**
     * 根据ID查找客户级别
     * @param contactId
     * @return String
     */
    String findLevelByContactId(String contactId);

    /**
     * 根据面单号查找对应正式客户
     * @param mailId
     * @return Contact
     */
    Contact findByMailId(String mailId);

    /**
     * 根据基本条件查找正式客户或者潜客，分页输出
     * @param customerBaseSearchDto
     * @param dataGridModel
     * @return
     */
    Map<String, Object> findByBaseCondition(CustomerBaseSearchDto customerBaseSearchDto, DataGridModel dataGridModel);

    /**
     * 根据基本条件查找正式客户或者潜客，分页输出 -- 话务分配专用
     * @param customerBaseSearchDto
     * @param dataGridModel
     * @return
     */
    Map<String, Object> findByBaseConditionForDistribution(CustomerBaseSearchDto customerBaseSearchDto, DataGridModel dataGridModel);

    /**
     * 获取客户积分
     * @param contactId 客户编号
     * @return
     */
    Double findJiFenByContactId(String contactId) throws ServiceException;

    /**
     * 保存客户（正式客户 或 潜客）
     * @param customerDto
     * @return 正式客户ID 或者 潜客ID
     */
    Map<String, Object> saveCustomer(CustomerDto customerDto) throws ServiceTransactionException;

    /**
     * 保存正式客户已经地址 电话
     * @param customer
     * @return 正式客户ID
     */
    Map<String, Object> saveCustomer(CustomerDto customer, List<AddressDto> addressDtos, List<Phone> phones) throws ServiceTransactionException;

    /**
     * 更新客户（正式客户 或 潜客）
     * @param customerDto
     */
    void updateCustomer(CustomerDto customerDto);
    
    /**
     * <p>创建新增审批流程</p>
     * @param contactDto
     * @param remark
     * @param userId
     * @param deptId
     * @param batchId
     * @return contactId
     * @throws Exception
     */
	Map<String, Object> applyAddRequest(ContactDto contactDto, String remark, String userId, String deptId)
			throws Exception;
    
    
    /**
     * <p>结束新增审批流程</p>
     * @param contactId
     * @param remark
     * @param applyUser
     * @param approveUser
     * @param instId
     * @throws Exception
     */
	void finishAddRequest(String contactId, String remark, String applyUser, String approveUser, String instId)
			throws Exception;
	
	/**
	 * <p>新增客户流程后更新地址与电话的state标志位</p>
	 * @param contactId
	 * @param state 状态标记
	 * @throws Exception
	 */
	void finishAddRequestState(String contactId, int state) throws Exception;

    /**
     * <p>创建修改审批流程</p>
     * @param contactDto
     * @param remark	审批建议
     * @param userId	用户ID
     * @param deptId	用户角色ID
     */
	void applyUpdateRequest(ContactDto contactDto, String remark, String userId, String deptId, String batchId)
			throws MarketingException;

    /**
     * 结束审批流程
     * @param contactId
     * @param remark
     * @param applyUser
     * @param approveUser
     * @param instId
     * @throws MarketingException
     */
	void finishUpdateRequest(String contactId, String remark, String applyUser, String approveUser, String instId)
			throws MarketingException;
    
    
    /**
     * <p>查询联系人审批列表</p>
     * @param customerDto
     * @return Map<String, Object> 
     */
	Map<String, Object> queryAuditPageList(DataGridModel dataGrid, ObCustomerDto customerDto) throws MarketingException;

    /**
     * 创建任务
     * @param contactid
     * @param campaignDto
     * @return
     * @throws ServiceException
     */
	String createTask(String contactid, CampaignDto campaignDto) throws ServiceException;
    
    /**
     * <p>坐席对联系人是否有创建任务的权限</p>
     * @param userId
     * @param contactid
     * @return Boolean
     */
    Boolean havePermissionCreateTask(String userId, String contactid);

    
    public List<Contact> getContactFromIds(List<String> contactIdList);

    /**
     * 根据手机查找正式客户，分页输出
     * @param phoneNum
     * @param dataGridModel
     * @return
     */
    Map<String, Object> findContactByPhone(String phoneNum, DataGridModel dataGridModel);


    public Integer findContactCountByPhone(String phoneNum);
    /**
     *
     * @param phoneNumList
     * @return
     */
     Integer findContactCountByPhoneList(List<String> phoneNumList);
    /**
     * 根据手机查找正式客户，分页输出
     * @param phoneNumList
     * @param dataGridModel
     * @return
     */
    Map<String, Object> findContactByPhoneList(List<String> phoneNumList, DataGridModel dataGridModel);



    /**
     * 新增客户走流程
     */
    Map<String, Object> addFormalCustomerWithFlow(CustomerDto customerDto, List<AddressDto> listAddresses, List<Phone> listphone, AgentUser user, String source) throws ServiceException;

    Integer findContactCountByFixPhone(String phoneNum);

    Map<String, Object> findContactByFixPhone(String phoneNum, DataGridModel dataGridModel);
}
