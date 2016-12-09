package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.oms.dao.CompanyContractDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyContractDaoImpl extends GenericDaoHibernate<CompanyContract, Integer> implements CompanyContractDao {

	public CompanyContractDaoImpl() {
	    super(CompanyContract.class);
	}

    public List<CompanyContract> getAllContracts(){
        Session session = getSession();
        Query q = session.createQuery("from CompanyContract");
        return q.list();
    }

    public List<CompanyContract> getAllContracts(Long warehouseId){
        Session session = getSession();
        Query q = session.createQuery("from CompanyContract a where a.warehouse.warehouseId = :warehouseId");
        q.setParameter("warehouseId", warehouseId);
        return q.list();
    }

    public List<CompanyContract> getAllContracts(Map<String, Object> params, int index, int size){
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("from CompanyContract a " +
                    "where 1=1 " + //二级承运商  a.parent is not null
                    (params.containsKey("companyId") ? "and a.id  = :companyId " : "") +
                    (params.containsKey("nccompanyId") ? "and a.nccompanyId  = :nccompanyId " : "") +
                    (params.containsKey("warehouseId") ? "and a.warehouse.warehouseId  = :warehouseId " : "") +
                    (params.containsKey("quYuId") ? "and a.distributionRegion  = :quYuId " : "") +
                    (params.containsKey("status") ? "and a.status  = :status " : "") +
                    (params.containsKey("channelId") ? "and a.channel.id  = :channelId " : "") +
                    (params.containsKey("startDate") ? "and a.beginDate >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and a.endDate <= :endDate " : "")
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
            return new ArrayList<CompanyContract>();
        }
    }

    public Long getContractCount(Map<String, Object> params){
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("select count(a.id) from CompanyContract a " +
                    "where 1=1 " + //二级承运商  a.parent is not null
                    (params.containsKey("companyId") ? "and a.id  = :companyId " : "") +
                    (params.containsKey("nccompanyId") ? "and a.nccompanyId  = :nccompanyId " : "") +
                    (params.containsKey("warehouseId") ? "and a.warehouse.warehouseId  = :warehouseId " : "") +
                    (params.containsKey("quYuId") ? "and a.distributionRegion  = :quYuId " : "") +
                    (params.containsKey("status") ? "and a.status  = :status " : "") +
                    (params.containsKey("channelId") ? "and a.channel.id  = :channelId " : "") +
                    (params.containsKey("startDate") ? "and a.beginDate >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and a.endDate <= :endDate " : "")
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
