package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;

import java.util.List;

/**
 * 潜客电话相关操作
 * User: xieguoqiang
 * Date: 13-5-10
 * Time: 下午1:36
 */
public interface PotentialContactPhoneDao extends GenericDao<PotentialContactPhone, Long> {
    /**
     * 擦除潜客之前的主电话设置
     * @param potentialContactId
     */
    void clearPrimePotentialContactPhone(String potentialContactId);

    /**
     * 设置潜客主电话
     * @param potentialContactId
     * @param potentialContactPhoneId
     */
    void setPrimePotentialContactPhone(String potentialContactId, String potentialContactPhoneId);

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
     * 根据电话号码及客户Id查找电话
     * @param areaCode
     * @param phoneNum
     * @param childNum
     * @param potentialContactId
     */
    List<PotentialContactPhone> findByPhoneFullNumAndPotentialContactId(String areaCode, String phoneNum, String childNum, String potentialContactId);


    /**
     * 查找潜客主电话
     * @param customerId
     * @return PotentialContactPhone
     */
    PotentialContactPhone findPrimePhone(String customerId);

    /**
     * 根据potentialContactId查找符合条件的电话个数
     * @param potentialContactId 潜客ID
     */
    int findCountByPotentialContactId(String potentialContactId);

    /**
     * 根据potentialContactId查找电话
     * @param potentialContactId 潜客ID
     * @param startRow 首条索引
     * @param rows 每页数
     */
    List<PotentialContactPhone> findByPotentialContactId(String potentialContactId, int startRow, int rows);

    /**
     * 更新潜客上次通话时间
     * @param updateLastCallDateDto
     */
    void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto);

    /**
     * 电话加入黑名单
     * @param phoneId
     */
    void addToBlackList(Long phoneId);

    /**
     * 退出黑名单
     * @param phoneId
     */
    void removeFromBlackList(Long phoneId);

    /**
     * 查找潜客备选电话
     * @param potentialContactId
     */
    List<PotentialContactPhone> findBackupPCPhoneByPContactId(String potentialContactId, String phoneId);

    void unsetBackupPCPhone(String potentialContactPhoneId);

    void setBackupPCPhone(String potentialContactPhoneId);
}
