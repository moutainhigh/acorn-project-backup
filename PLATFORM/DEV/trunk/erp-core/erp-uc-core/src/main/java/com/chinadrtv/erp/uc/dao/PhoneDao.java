package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;

import java.util.List;

/**
 * 电话相关操作
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 上午10:36
 */
public interface PhoneDao extends GenericDao<Phone, Long> {
    /**
     * 擦除客户的主电话设置
     * @param contactId
     */
    void clearPrimePhone(String contactId);

    /**
     * 设置客户的主电话
     * @param contactId
     * @param phoneId
     */
    void setPrimePhone(String contactId, String phoneId);

    /**
     * 根据电话号码查找电话
     * @param areaCode
     * @param phoneNum
     * @param childNum
     */
    List<Phone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum);

    /**
     * 根据电话号码及客户Id查找电话
     * @param areaCode
     * @param phoneNum
     * @param childNum
     * @param contactId
     */
    List<Phone> findByPhoneFullNumAndContactId(String areaCode, String phoneNum, String childNum, String contactId);

    /**
     * 查找客户的主电话
     * @param contactId
     * @return
     */
    Phone findPrimePhone(String contactId);


    /**
     * 根据contactID查找电话
     * @param contactId 客户ID
     * @param startRow 首条索引
     * @param rows 每页数
     */
    List<Phone> findByContactId(String contactId, int startRow, int rows);

    /**
     * 根据contactID查找符合条件的电话个数
     * @param contactId 客户ID
     */
    int findCountByContactId(String contactId);

	/**
	 * <p>手动获取Sequence</p>
	 * @return Long
	 */
	Long getSequence();

    /**
     * 更新客户上次通话时间
     * @param updateLastCallDateDto
     */
    void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto);
    
    Phone findByPhoneId(long phoneId);

    /**
     * 根据客户名字加手机号查找是否重复
     * @param name
     */
    boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn1, String phn2, String phn3);

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
    void addToBlackList(Long phoneId);

    /**
     * 退出黑名单
     * @param phoneId
     */
    void removeFromBlackList(Long phoneId);
    
    /**
     * <p>修改电话，此方法是针对UAT和生产环境phoneid 字段类型不一致而导致性能问题而添加的</p>
     * @param phone
     */
    void updatePhone(Phone phone);

    /**
     * 查找客户的备选电话
     * @param contactId
     */
    List<Phone> findBackupPhoneByContactId(String contactId, String phoneId);

    /**
     * 取消设置客户的备选电话
     * @param phoneId
     */
    void unsetBackupPhone(String phoneId);

    /**
     * 设置客户的备选电话
     * @param phoneId
     */
    void setBackupPhone(String phoneId);

    boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn2, String contactId);

    boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn2, String contactId, String phoneType, String phn1, String phn3);

    boolean checkExistPhone(Phone phone);
}
