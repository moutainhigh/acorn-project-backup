/*
 * @(#)AddressDaoImpl.java 1.0 2013-2-19下午4:02:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.service.SchemaNames;

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
 * @since 2013-2-19 下午4:02:28 
 * 
 */
@Repository
public class AddressDaoImpl extends GenericDaoHibernateBase<Address, String>
		implements AddressDao {
	
	@Autowired
	private SchemaNames schemaNames;

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public AddressDaoImpl() {
		super(Address.class);
	}

	@Autowired
    @Required
    @Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
	
	/**
	 * <p>Title: getSequence</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#getSequence()
	 */
    public String getSequence(){
        Query q = getSession().createSQLQuery("select "+schemaNames.getAgentSchema()+"SEQADDRESS.nextval from dual");
        return  q.list().get(0).toString();
    }

	/** 
	 * <p>Title: getContactSecondarySeq</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#getContactSecondarySeq()
	 */ 
	public String getContactSecondarySeq() {
		Query q = getSession().createSQLQuery("select " + schemaNames.getAgentSchema() + "seqcontact_notdefault.nextval from dual");
		String seq = q.list().get(0).toString();
		return "A"+seq;
	}
	
	/** 
	 * <p>Title: 查询客户主地址</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return Address
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#getContactMainAddress(java.lang.String)
	 */ 
	public Address getContactMainAddress(String contactId) {
		String hql = "select a from Address a where a.contactid=:contactId and a.isdefault='"+AddressConstant.CONTACT_MAIN_ADDRESS+"'";
		return this.find(hql, new ParameterString("contactId", contactId));
	}

	/** 
	 * <p>Title: getAddressListByContactId</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return Address
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#getAddressListByContactId(java.lang.String)
	 */ 
	public Address getAddressByContactId(String contactId) {
		String hql = "select a from Address a where a.contactid=:contactId";
		return this.find(hql, new ParameterString("contactId", contactId));
	}

	/** 
	 * <p>Title: updateAddress</p>
	 * <p>Description: </p>
	 * @param currMainAddress
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#updateAddress(com.chinadrtv.erp.model.Address)
	 */ 
	@Override
	public void updateAddress(Address currMainAddress) {
		String hql = "update Address set isdefault=:isdefault, contactid=:contactid where addressid=:addressid";
		this.execUpdate(hql, new ParameterString("isdefault", currMainAddress.getIsdefault()),
							new ParameterString("contactid", currMainAddress.getContactid()),
							new ParameterString("addressid", currMainAddress.getAddressid()));
	}

	@Override
	public void saveOrUpdate(Address object) {
		super.saveOrUpdate(object);
	}

//    @Override
//    public void addToBlackList(String addressId) {
//        String hql = "update Address a set a.black=1 where a.addressid=:addressId";
//        Query query= this.getSession().createQuery(hql);
//        query.setString("addressId", addressId);
//        query.executeUpdate();
//    }

//    @Override
//    public void removeFromBlackList(String addressId) {
//        String hql = "update Address a set a.black=0 where a.addressid=:addressId";
//        Query query= this.getSession().createQuery(hql);
//        query.setString("addressId", addressId);
//        query.executeUpdate();
//    }
}
