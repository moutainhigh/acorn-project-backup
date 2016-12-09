package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;
import com.chinadrtv.erp.oms.dao.ShipmentSettlementDao;


/**
 * 发运单-数据访问
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ShipmentSettlementDaoImpl extends GenericDaoHibernate<ShipmentSettlement, Long> implements ShipmentSettlementDao {
	
    public ShipmentSettlementDaoImpl() {
        super(ShipmentSettlement.class);
    }

    public Long getSettlementCount(Map<String, Object> params){
        if(params.size() > 0){
            Session session = getSession();

            Query q = session.createQuery("select count(a.id) from ShipmentSettlement a where a.id > 0 " +
                    (params.containsKey("companyId") ? "and a.companyId  = :companyId " : "")+
                    (params.containsKey("shipmentId") ? "and a.shipmentHeader.shipmentId  = :shipmentId " : "")+
                    (params.containsKey("ftStart") ? "and a.shipmentHeader.fbdt >= :ftStart " : "")+
                    (params.containsKey("ftEnd") ? "and a.shipmentHeader.fbdt <= :ftEnd " : "") +
                    (params.containsKey("orderId") ? "and a.shipmentHeader.orderId  = :orderId " : "")+
                    (params.containsKey("rfStart") ? "and a.shipmentHeader.rfoutdat >= :rfStart " : "")+
                    (params.containsKey("rfEnd") ? "and a.shipmentHeader.rfoutdat <= :rfEnd " : "") +
                    (params.containsKey("isSettled") ? "and a.isSettled in ('0',:isSettled) " : "and a.isSettled in ('0') ")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }

            return Long.valueOf(q.uniqueResult().toString());
        } else {
            return 0L;
        }
    }

    public List<ShipmentSettlement> getSettlements(Map<String, Object> params, int index, int size){
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("from ShipmentSettlement a where a.id > 0 " +
                    (params.containsKey("companyId") ? "and a.companyId  = :companyId " : "")+
                    (params.containsKey("shipmentId") ? "and a.shipmentHeader.shipmentId  = :shipmentId " : "")+
                    (params.containsKey("ftStart") ? "and a.shipmentHeader.fbdt >= :ftStart " : "")+
                    (params.containsKey("ftEnd") ? "and a.shipmentHeader.fbdt <= :ftEnd " : "") +
                    (params.containsKey("orderId") ? "and a.shipmentHeader.orderId  = :orderId " : "")+
                    (params.containsKey("rfStart") ? "and a.shipmentHeader.rfoutdat >= :rfStart " : "")+
                    (params.containsKey("rfEnd") ? "and a.shipmentHeader.rfoutdat <= :rfEnd " : "") +
                    (params.containsKey("isSettled") ? "and a.isSettled in ('0',:isSettled) " : "and a.isSettled in ('0') ")
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
        }
        else{
            return new ArrayList<ShipmentSettlement>();
        }
    }

    public List<ShipmentSettlement> getSettlements(Long[] settlementIds)
    {
        Session session = getSession();
        Query q = session.createQuery("from ShipmentSettlement a where a.id in (:settlementIds)");
        q.setParameterList("settlementIds", settlementIds);
        return q.list();
    }

	public List<ShipmentSettlement> findByShipmentHeader(ShipmentHeader shipmentHeader) {
		if(shipmentHeader==null || shipmentHeader.getId()==null || shipmentHeader.getAccountType()==null){
			return null;
		}
        Session session = getSession();
        Query q = session.createQuery("from ShipmentSettlement a where a.shipmentHeader.id = :shipmentHeaderId and a.type = :type");
        q.setParameter("shipmentHeaderId", shipmentHeader.getId());
        q.setParameter("type", Long.parseLong(shipmentHeader.getAccountType()));
        return q.list();
	}

    /**
     * 获取结算单信息
     * @param params
     * @param index
     * @param size
     * @return
     */
    public List getShipmentSettlementInfo(Map<String, Object> params, int index, int size) {
        if(params.size() > 0){
            Session session = getSession();
            Query sqlQuery = session.createSQLQuery("select t1.id,t1.create_date,t2.rfoutdat,t2.shipment_id,t2.mail_id," +
                    "t1.ar_amount,t1.is_settled,t1.company_id,t4.payment_date from acoapp_oms.shipment_settlement t1 " +
                    "left join acoapp_oms.shipment_header t2 on t1.shipment_header_id = t2.id " +
                    "left join acoapp_oms.company_payment_settle t3 on t1.id = t3.shipment_settlement_id " +
                    "left join acoapp_oms.company_payment t4 on t4.id = t3.company_payment_id where 1=1 " +
                    (params.containsKey("companyId") ? "and t1.companyId  = :companyId " : "")+
                    (params.containsKey("shipmentId") ? "and t2.shipment_id  = :shipmentId " : "")+
                    (params.containsKey("ftStart") ? "and t2.fbdt >= :ftStart " : "")+
                    (params.containsKey("ftEnd") ? "and t2.fbdt <= :ftEnd " : "") +
                    (params.containsKey("orderId") ? "and t2.order_id  = :orderId " : "")+
                    (params.containsKey("rfStart") ? "and t2.rfoutdat >= :rfStart " : "")+
                    (params.containsKey("rfEnd") ? "and t2.rfoutdat <= :rfEnd " : "") +
                    (params.containsKey("isSettled") ? "and t1.is_settled in ('0',:isSettled) " : "and t1.is_settled in ('0') ")
            );
            List<String> parameters = Arrays.asList(sqlQuery.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    sqlQuery.setParameter(parameterName, params.get(parameterName));
                }
            }

            sqlQuery.setFirstResult(index);
            sqlQuery.setMaxResults(size);
            return sqlQuery.list();
        }else {
            return new ArrayList<Object>();
        }
    }
}