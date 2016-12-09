package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.oms.dao.LogisticsFeeRuleDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class LogisticsFeeRuleDaoImpl extends GenericDaoHibernate<LogisticsFeeRule, Integer> implements LogisticsFeeRuleDao{

	public LogisticsFeeRuleDaoImpl() {
	    super(LogisticsFeeRule.class);
	}

    public List<LogisticsFeeRule> getActiveLogisticsFeeRules(Integer contractId){
        Session session = getSession();
        Query q = session.createQuery("from LogisticsFeeRule a where a.status=1 and a.contract.id = :contractId");
        q.setParameter("contractId", contractId);
        return q.list();
    }

    public List<LogisticsFeeRule> getAllLogisticsFeeRules(Map<String, Object> params, int index, int size){
        if(params.size() > 1){
            Session session = getSession();
            Query q = session.createQuery("from LogisticsFeeRule a " +
                    "where a.id > 0 " +
                    (params.containsKey("contractId") ? "and a.id  = :contractId " : "") +
                    (params.containsKey("warehouseId") ? "and a.contract.warehouse.warehouseId = :warehouseId " : "") +
                    (params.containsKey("freightMethod") ? "and a.contract.freightMethod = :freightMethod " : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            q.setFirstResult(index);
            q.setMaxResults(size);

            return q.list();
        } else {
            return new ArrayList<LogisticsFeeRule>();
        }
    }

    public Long getLogisticsFeeRuleCount(Map<String, Object> params){
        if(params.size() > 1){
            Session session = getSession();
            Query q = session.createQuery("select count(a.id) from LogisticsFeeRule a " +
                    "where a.id > 0 " +
                    (params.containsKey("contractId") ? "and a.id  = :contractId " : "") +
                    (params.containsKey("warehouseId") ? "and a.contract.warehouse.warehouseId  = :warehouseId " : "")+
                    (params.containsKey("freightMethod") ? "and a.contract.freightMethod = :freightMethod " : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return Long.valueOf(q.list().get(0).toString());
        }
        else
        {
            return 0L;
        }
    }
}
