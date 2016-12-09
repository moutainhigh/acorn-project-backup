package com.chinadrtv.erp.uc.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;

/**
 * 电话相关操作
 * 查找电话，更新电话，增加电话，设置客户主电话
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 上午10:32
 */
public interface PhoneService extends GenericService<Phone, Long> {
    /**
     * 设置客户主电话
     * @param contactId 客户ID
     * @param phoneId 电话ID
     */
    void setPrimePhone(String contactId, String phoneId);

    /**
     * 设置客户备选电话
     * @param contactId 客户ID
     * @param phoneId 电话ID
     */
    void setBackupPhone(String contactId, String phoneId);

    /**
     * 取消设置客户备选电话
     * @param phoneId 电话ID
     */
    void unsetBackupPhone(String phoneId);

    /**
     * 根据id查找电话
     * @param phoneId
     * @return Phone
     * @throws Exception
     */
    Phone get(Long phoneId);

    /**
     * 增加单个电话
     * @param phone
     * @return Phone
     */
    Phone addPhone(Phone phone) throws ServiceTransactionException;

    /**
     * 增加单个电话，检查该客户是否已经存在该号码
     * @param phone
     * @return Phone
     */
    Phone addPhoneWithCkeckSelfExist(Phone phone) throws ServiceTransactionException;

    /**
     * 增加单个电话，处理主备号码
     * @param phone
     * @return Phone
     */
    Phone addPhoneWithProcessPrm(Phone phone) throws ServiceTransactionException;

    /**
     * 增加多条电话
     * @param phones
     * @return Phone
     */
    void addPhoneList(List<Phone> phones) throws ServiceTransactionException;

    /**
     * 更新一条客户电话
     * @param phone
     */
    void updatePhone(Phone phone) throws ServiceTransactionException;

    /**
     * 更新多条客户电话
     * @param phones
     */
    void updatePhoneList(List<Phone> phones) throws ServiceTransactionException;

    /**
     * 根据完整电话号码查找
     * @param areaCode 区号
     * @param phoneNum
     * @param childNum 分机号
     */
    List<Phone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum);

    /**
     * 根据完整电话号码及客户ID查找
     * @param areaCode 区号
     * @param phoneNum
     * @param childNum 分机号
     * @param contactId 客户Id
     */
    Phone findByPhoneFullNumAndContactId(String areaCode, String phoneNum, String childNum, String contactId);


    /**
     * 删除客户电话
     * @param phone
     */
    void deletePhone(Phone phone) throws ServiceTransactionException;

    /**
     * 删除客户多个电话
     * @param phoneList
     */
    void deletePhoneList(List<Phone> phoneList) throws ServiceTransactionException;

    /**
     * 根据contactID查找电话
     * @param contactId 客户ID
     * @param dataGridModel 分页条件
     */
    Map<String, Object> findByContactId(String contactId, DataGridModel dataGridModel);
    
   /**
    * <p>新增地址审批流程</p>
    * @param phone
    * @param remark
    * @param userId
    * @param deptId
    * @param batchId
    * @return phoneId
    */
    void applyAddRequest(Phone phone, String remark, String userId, String deptId, String batchId);
    
    
    /**
     * <p>新增地址审批流程结束</p>
     * @param phoneId
     * @param remark
     * @param userId
     * @param instId
     * @throws MarketingException
     */
    void finishAddRequest(Long phoneId, String remark, String applyUser, String approveUser, String instId) throws Exception;
    
	/**
	 * <p>电话修改调用修改审批流程 </p>
	 * @param phone
	 * @param remark	审批建议
	 * @param userId	当前用户ID
	 * @param roleId	当前用户角色ID
	 * @param batchId	批号
	 */
    @Deprecated
	void applyUpdateRequest(Phone phone, String remark, String userId, String deptId, String batchId);

    /**
     * <p>结束审批流程</p>
     * @param phoneId
     * @param remark
     * @param userId
     * @param instId
     */
    @Deprecated
    void finishUpdateRequest(Long phoneId, String remark, String applyUser, String approveUser, String instId) throws Exception;

    /**
     * 更新客户上次通话时间
     * @param updateLastCallDateDto
     */
    void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto);

    /**
     * 查询一个手机是否重复
     * @param phone
     */
    boolean checkExistSameNameAndPhone(Phone phone);

    /**
     * 解析电话号码，指定省市地址
     * 支持格式：
     * 手机号码：1XXXXXXXXXX 861XXXXXXXXXX +861XXXXXXXXXX
     * 固话: 0XXXXXXXXXXX 0XXXXXXXXXX
     * @param phone
     */
    PhoneAddressDto splicePhone(String phone);

	/**
	 * <p>查询客户手机号</p>
	 * @param contactId
	 * @return List<Phone>
	 */
	List<Phone> queryMobilePhoneByContact(String contactId);

    /**
     * 电话加入黑名单
     * @param phoneId
     */
    public void addToBlackList(Long phoneId);

    /**
     * 退出黑名单
     * @param phoneId
     */
    public void removeFromBlackList(Long phoneId);


    /**
     * 增加或者更新电话，包括内部的主电话以及备选电话的处理逻辑
     * @param phone
     */
    public void addOrUpdateNewPhone(Phone phone) throws ServiceTransactionException;
    
    /**
     * 
    * @Description: 查询用户电话号码
    * @param contactId
    * @return
    * @return List<Phone>
    * @throws
     */
    public List<Phone> getPhonesByContactId(String contactId);


    public void updatePhoneDirect(Phone phone);


    /**
     *
     * @Description: 查询用户是否已经存在该号码
     * @param phone
     * @return
     * @return true代表该客户已经存在该号码，false代表不存在
     * contactId必填，phn2必填，手机号phonetypid必须填4， phn1、phn3选填
     */
    boolean checkExistPhone(Phone phone);

    /**
     * 增加单个电话，检查该客户是否已经存在该号码
     * @param phone
     * @return Phone
     */
    Phone addPhoneWithCkeckSelfExistForService(Phone phone);
}
