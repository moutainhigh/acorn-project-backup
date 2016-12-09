package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Phone;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-28
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
public interface PhoneDao extends GenericDao<Phone, Long> {
    Phone findByPhoneNum(String PhoneNum);
    void saveOrUpdate(Phone phone);
	
    /**
     * 根据联系人ID 和电话类型查询电话
	* @Description: TODO
	* @param contactId
	* @param phoneTypeHome
	* @return
	* @return Phone
	* @throws
	*/ 
	Phone getByContactIdAndType(String contactId, int phoneType);

    List<Phone> findPhoneListFromContactAndType(String contactId, int phoneType);
}
