package com.chinadrtv.erp.marketing.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.dao.SmsSendVarDao;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:短信发送管理_静态变量</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class SmsSendVarDaoImpl extends
		GenericDaoHibernate<SmsSendVar, java.lang.Long> implements
		Serializable, SmsSendVarDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public SmsSendVarDaoImpl() {
		super(SmsSendVar.class);
	}

	@Override
	@Autowired
	@Qualifier("sessionFactorySms")
	public void setSessionFactory(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}

	public List<SmsSendVar> query(String batchId) {
		StringBuffer sql = new StringBuffer(
				"FROM SmsSendVar WHERE batch_id = ? ");
		List<SmsSendVar> objList = getHibernateTemplate().find(sql.toString(),
				new Object[] { batchId });
		return objList;
	}

}
