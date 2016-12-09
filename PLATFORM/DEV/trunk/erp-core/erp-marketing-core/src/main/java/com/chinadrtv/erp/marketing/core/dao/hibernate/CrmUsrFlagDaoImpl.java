package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.CrmUsrFlagDao;
import com.chinadrtv.erp.model.marketing.CrmUsrFlag;

/**
 * 
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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class CrmUsrFlagDaoImpl extends
		GenericDaoHibernate<CrmUsrFlag, java.lang.String> implements
		Serializable, CrmUsrFlagDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CrmUsrFlagDaoImpl() {
		super(CrmUsrFlag.class);
	}

}
