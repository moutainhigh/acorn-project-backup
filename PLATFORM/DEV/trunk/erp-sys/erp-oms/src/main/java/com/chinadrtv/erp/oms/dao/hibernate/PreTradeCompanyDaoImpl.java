package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.PreTradeCompany;
import com.chinadrtv.erp.oms.dao.PreTradeCompanyDao;
@Repository
public class PreTradeCompanyDaoImpl  extends GenericDaoHibernate<PreTradeCompany, Long> implements PreTradeCompanyDao {

	public PreTradeCompanyDaoImpl() {
		super(PreTradeCompany.class);
	}

	public List<PreTradeCompany> getPreTradeCompanyBySourceid(Long sourceId) {
		String hql = "select pc from PreTradeCompany pc where pc.sourceId=:sourceId";
		return this.findList(hql, new ParameterLong("sourceId", sourceId));
	}

}
