package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;
import com.chinadrtv.erp.oms.dao.LogisticsWeightRuleDetailDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogisticsWeightRuleDetailDaoImpl extends GenericDaoHibernate<LogisticsWeightRuleDetail, Integer> implements LogisticsWeightRuleDetailDao {

	public LogisticsWeightRuleDetailDaoImpl() {
	    super(LogisticsWeightRuleDetail.class);
	}

    public List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId){
        Session session = getSession();
        Query q = session.createQuery("from LogisticsWeightRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        return q.list();
    }

    public Long getWeightRuleDetailCount(Integer ruleId){
        Session session = getSession();
        Query q = session.createQuery("select count(a.id) from LogisticsWeightRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        return Long.parseLong(String.valueOf(q.uniqueResult()));
    }

    public List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId, int index, int size){
        Session session = getSession();
        Query q = session.createQuery("from LogisticsWeightRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        q.setFirstResult(index);
        q.setMaxResults(size);
        return q.list();
    }
}
