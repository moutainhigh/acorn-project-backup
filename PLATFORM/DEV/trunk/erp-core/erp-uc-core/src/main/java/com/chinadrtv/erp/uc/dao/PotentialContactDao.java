package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.uc.dto.CustomerDto;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-8 下午5:51:52
 */
public interface PotentialContactDao extends GenericDao<PotentialContact, Long> {
	/**
	 * <p>
	 * SR1: API-UC-20.新增潜客
	 * </p>
	 * 
	 * @param potentialContact
	 */
	Long savePotentialContact(PotentialContact potentialContact);

	/**
	 * 更加基本信息查找潜客
	 * 
	 * @param potentialContactId
	 * @param name
	 * @param phoneNum
	 * @param phone1
	 * @param phone2
	 * @param phone3
	 * @param index
	 * @param rows
	 * @return
	 */
	List<CustomerDto> findByBaseCondition(String potentialContactId,
			String name, String phoneNum, String phone1, String phone2,
			String phone3, int index, int rows);

	/**
	 * 更新潜客
	 * 
	 * @param potentialContact
	 */
	void updatePotentialContact(PotentialContact potentialContact);

	/**
	 * 
	 * @Description: 根据潜客id 查询信息
	 * @param contactId
	 * @return
	 * @return PotentialContact
	 * @throws
	 */
	public PotentialContact getByContactId(Long contactId);
}
