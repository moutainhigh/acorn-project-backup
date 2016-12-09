package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;

/**
 * 客户相关接口
 * User: xieguoqiang
 * Date: 13-5-3
 * Time: 下午4:34
 */
public interface ContactDao extends GenericDao<Contact, String> {
    /**
     * 根据电话 名字 地址查找客户
     * @param customerBaseSearchDto
     * @param index
     * @param rows
     * @return List<CustomerDto>
     */
    List<CustomerDto> findByPhoneAndNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto, int index, int rows);

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
     * 根据面单号查找对应正式客户
     * @param mailId
     * @return Contact
     */
    Contact findByMailId(String mailId);

    /**
     * 获取客户积分
     * @param contactId
     * @return
     */
    Double findJiFenByContactId(String contactId);

    /**
     * 根据ID查找客户级别
     * @param contactId
     * @return String
     */
    String findLevelByContactId(String contactId);

    /**
     * 根据名字查找客户（正式客户或者潜客）
     * @param name
     * @param index
     * @param rows
     * @return
     */
    List<CustomerDto> findByName(String name, int index, int rows);

    /**
     * 根据名字和地址查找正式客户
     * @param customerBaseSearchDto
     * @param index
     * @param rows
     * @return
     */
    List<CustomerDto> findByNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto, int index, int rows);

    /**
     * 根据名字和电话查找客户（正式客户或者潜客）
     * @param phone
     * @param name
     * @param index
     * @param rows
     * @return
     */
    List<CustomerDto> findByPhoneAndName(String phone, String name, int index, int rows);

    /**
     * 根据电话查找客户（正式客户或潜客）
     * @param phone
     * @param index
     * @param rows
     * @return
     */
    List<CustomerDto> findByPhone(String phone, int index, int rows);

    /**
     * 根据电话查找正式客户（正式客户）
     * @param phone
     * @param index
     * @param rows
     * @return
     */
    List<CustomerDto> findContactByPhone(String phone, int index, int rows);

    /**
     * 获取联系人Sequance(新增时用)
     * @return
     */
    String getSequence();

    /**
     * 根据电话查找客户数量（正式客户或潜客）
     * @param phone
     * @return 数量
     */
    int findCountByPhone(String phone);

    /**
     * 根据电话查找正式客户数量（正式客户）
     * @param phone
     * @return 数量
     */
    int findContactCountByPhone(String phone);

    /**
     * 根据姓名查找客户数量（正式客户或潜客）
     * @param name
     * @return 数量
     */
    int findCountByName(String name);

    /**
     * 根据电话和姓名查找客户数量（正式客户或潜客）
     * @param phone
     * @param name
     * @return
     */
    int findCountByPhoneAndName(String phone, String name);

    /**
     * 根据名字和地址查找正式客户数量
     * @param customerBaseSearchDto
     */
    int findCountByNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto);

    /**
     * 根据电话 名字 地址查找客户数量
     * @param customerBaseSearchDto
     * @return
     */
    int findCountByPhoneAndNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto);

	/**
	 * <p>查询成单客户行数</p>
	 * @param obCustomerDto
	 * @return Integer
	 */
	int queryPageCount(ObCustomerDto obCustomerDto);

	/**
	 * <p>分页查询成单客户</p>
	 * @param dataGridModel
	 * @param obCustomerDto
	 * @return List<ObCustomerDto>
	 */
	List<ObCustomerDto> queryPageList(DataGridModel dataGridModel, ObCustomerDto obCustomerDto);

	/**
	 * <p>查询审批列表行数</p>
	 * @param customerDto
	 * @return Integer
	 */
	Integer queryAuditPageCount(ObCustomerDto customerDto);

	/**
	 * <p>查询江审批列表</p>
	 * @param dataGrid
	 * @param customerDto
	 * @return List<ObCustomerDto>
	 */
	List<ObCustomerDto> queryAuditPageList(DataGridModel dataGrid, ObCustomerDto customerDto);

    //
    List<Contact> getContactFromIds(List<String> contactIdList);

    /**
     * 查询contactNum
     * @param phoneNumList
     * @return
     */
    Integer findContactCountByPhoneList(List<String> phoneNumList);

    /**
     * 查询contactList
     * @param phoneNumList
     * @param startRow
     * @param rows
     * @return
     */
    List<Contact> findContactByPhoneList(List<String> phoneNumList, int startRow, int rows);

    int findCountByFixedPhone(String phn1, String phn2);

    List<CustomerDto> findByFixedPhone(String phn1, String phn2, int startRow, int rows);

    int findContactCountByFixPhone(String phoneNum);

    List<CustomerDto> findContactByFixPhone(String phoneNum, int startRow, int rows);
}
