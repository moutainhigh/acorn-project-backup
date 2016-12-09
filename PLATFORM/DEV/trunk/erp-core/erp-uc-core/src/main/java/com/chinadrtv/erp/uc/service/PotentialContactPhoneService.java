package com.chinadrtv.erp.uc.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;

/**
 * 潜客电话
 * User: xieguoqiang
 * Date: 13-5-10
 * Time: 下午1:28
 */
public interface PotentialContactPhoneService extends GenericService<PotentialContactPhone, Long> {
    /**
     * 新增单个潜客电话
     * @param potentialContactPhone
     * @return PotentialContactPhone
     */
    PotentialContactPhone addPotentialContactPhone(PotentialContactPhone potentialContactPhone) throws ServiceTransactionException;

    /**
     * 新增多个潜客电话
     * @param potentialContactPhones
     */
    void addPotentialContactPhoneList(List<PotentialContactPhone> potentialContactPhones) throws ServiceTransactionException;

    /**
     * 设置潜客主电话
     * @param potentialContactId
     * @param potentialContactPhoneId
     */
    void setPrimePotentialContactPhone(String potentialContactId, String potentialContactPhoneId);

    /**
     * 更新潜客一个电话
     * @param potentialContactPhone
     */
    void updatePotentialContactPhone(PotentialContactPhone potentialContactPhone) throws ServiceTransactionException;

    /**
     * 更新潜客多个电话
     * @param potentialContactPhones
     */
    void updatePotentialContactPhoneList(List<PotentialContactPhone> potentialContactPhones) throws ServiceTransactionException;

    /**
     * 查找潜客的所有潜客电话
     * @param potentialContactId
     * @return
     */
    List<PotentialContactPhone> getPotentialContactPhones(Long potentialContactId);

    /**
     * 根据完整电话号码查找
     * @param areaCode 区号
     * @param phoneNum
     * @param childNum 分机号
     */
    List<PotentialContactPhone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum);


    /**
     * 根据完整电话号码及客户ID查找
     * @param areaCode 区号
     * @param phoneNum
     * @param childNum 分机号
     * @param potentialContactId 潜客ID
     */
    PotentialContactPhone findByPhoneFullNumAndPotentialContactId(String areaCode, String phoneNum, String childNum, String potentialContactId);

    /**
     * 删除潜客电话
     * @param phone
     */
    void deletePhone(PotentialContactPhone phone) throws ServiceTransactionException;

    /**
     * 删除潜客多个电话
     *
     * @param phoneList
     */
    void deletePhoneList(List<PotentialContactPhone> phoneList) throws ServiceTransactionException;

    /**
     * 根据potentialContactId查找潜客电话，分页显示
     *
     * @param potentialContactId 潜客ID
     * @param dataGridModel      分页条件
     */
    Map<String, Object> findByPotentialContactId(String potentialContactId, DataGridModel dataGridModel);

    /**
     * 根据potentialPhoneId查找潜客电话
     * @param potentialPhoneId 潜客电话ID 
     * @return PotentialContactPhone 对象
     */
    public PotentialContactPhone findByPotentialPhoneId(Long potentialPhoneId);


    /**
     * 更新潜客上次通话时间
     *
     * @param updateLastCallDateDto
     */
    void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto);

    /**
     * 潜客电话加入黑名单
     * @param phoneId
     */
    public void addToBlackList(Long phoneId);

    /**
     * 退出黑名单
     * @param phoneId
     */
    public void removeFromBlackList(Long phoneId);

    /**
     * 设置潜在客户备选电话
     * @param potentialContactId 客户ID
     * @param potentialContactPhoneId 电话ID
     */
    void setBackupPotentialContactPhone(String potentialContactId, String potentialContactPhoneId);


    /**
     * 取消设置潜在客户备选电话
     * @param potentialContactPhoneId 电话ID
     */
    void unsetBackupPotentialContactPhone(String potentialContactPhoneId);
}
