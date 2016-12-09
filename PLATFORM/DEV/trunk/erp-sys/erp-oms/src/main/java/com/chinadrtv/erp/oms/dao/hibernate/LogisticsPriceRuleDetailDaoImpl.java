package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsFeeRuleDetail;
import com.chinadrtv.erp.model.LogisticsPriceRuleDetail;
import com.chinadrtv.erp.oms.dao.LogisticsPriceRuleDetailDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class LogisticsPriceRuleDetailDaoImpl extends GenericDaoHibernate<LogisticsPriceRuleDetail, Integer> implements LogisticsPriceRuleDetailDao {

	public LogisticsPriceRuleDetailDaoImpl() {
	    super(LogisticsPriceRuleDetail.class);
	}

    public List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId){
        Session session = getSession();
        Query q = session.createQuery("from LogisticsPriceRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        return q.list();
    }

    public Long getPriceRuleDetailCount(Integer ruleId){
        Session session = getSession();
        Query q = session.createQuery("select count(a.id) from LogisticsPriceRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        return Long.parseLong(String.valueOf(q.uniqueResult()));
    }

    public List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId, int index, int size){
        Session session = getSession();
        Query q = session.createQuery("from LogisticsPriceRuleDetail a where a.rule.id = :ruleId ");
        q.setParameter("ruleId", ruleId);
        q.setFirstResult(index);
        q.setMaxResults(size);
        return q.list();
    }
}
