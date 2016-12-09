package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.LowerPriceInfoDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 上午11:32
 * To change this template use File | Settings | File Templates.
 */
@Repository("lowerPriceInfoDao")
public class LowerPriceInfoDaoImpl extends GenericDaoHibernate<ShipmentHeader, Long> implements LowerPriceInfoDao {
    public LowerPriceInfoDaoImpl(){
        super(ShipmentHeader.class);
    }

    @Override
    public List getShipmentHeader(String orderId, String mailId, int index, int size) {
        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select a.senddt,a.shipment_id,a.mail_id,f.dsc,a.prod_price,");
        sb.append("e.address,c.name,a.order_id,a.mail_price from acoapp_oms.shipment_header a");
        sb.append(" inner join iagent.orderhist b on a.order_id = b.orderid");
        sb.append(" inner join iagent.contact c on b.contactid = c.contactid");
        sb.append(" inner join iagent.address e on b.addressid = e.addressid");
        sb.append(" left join iagent.names f on b.ordertype = f.id and f.tid = 'ORDERTYPE'");
        sb.append(" where CAST(a.logistics_status_id as NUMBER) >= 2 and CAST(a.logistics_status_id as NUMBER) not in (5,6)");
        sb.append( " and NVL(a.RECONCIL_FLAG,0)=0 and a.account_type = 1");
        if(!"".equals(orderId)){
            sb.append(" and a.order_id = :orderId");
        }
        if(!"".equals(mailId)){
            sb.append(" and a.mail_id = :mailId");
        }
        Query q = session.createSQLQuery(sb.toString());
        if(!"".equals(orderId) && orderId != null){
            q.setString("orderId",orderId);
        }
        if(!"".equals(mailId) && mailId != null){
            q.setString("mailId",mailId);
        }
        q.setFirstResult(index);
        q.setMaxResults(size);

        return q.list();
    }

    @Override
    public Long getShipmentHeaderCount(String orderId, String mailId) {
        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(a.id) from acoapp_oms.shipment_header a");
        sb.append(" inner join iagent.orderhist b on a.order_id = b.orderid");
        sb.append(" inner join iagent.contact c on b.contactid = c.contactid");
        sb.append(" inner join iagent.address e on b.addressid = e.addressid");
        sb.append(" left join iagent.names f on b.ordertype = f.id and f.tid = 'ORDERTYPE'");
        sb.append(" where CAST(a.logistics_status_id as NUMBER) >= 2  and NVL(a.RECONCIL_FLAG,0)=0 and a.account_type = 1");
        if(!"".equals(orderId)){
            sb.append(" and a.order_id = :orderId");
        }
        if(!"".equals(mailId)){
            sb.append(" and a.mail_id = :mailId");
        }
        Query q = session.createSQLQuery(sb.toString());
        if(!"".equals(orderId) && orderId != null){
            q.setString("orderId",orderId);
        }
        if(!"".equals(mailId) && mailId != null){
            q.setString("mailId",mailId);
        }
        return ((BigDecimal)q.uniqueResult()).longValue();
    }

    @Override
    public List getOrderDetails(String orderId) {
        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select d.orderid,d.uprice,d.upnum,d.sprice,d.spnum,f.prodid,f.prodname,f.lprice from");
        sb.append(" iagent.orderdet d left join (select a.prodid,a.prodname,");
        sb.append("case when nvl(a.lprice, 0) > b.grplprice then b.grplprice");
        sb.append(" else nvl(a.lprice, 0) end lprice from iagent.product a");
        sb.append(" left join (select a.prodid, min(nvl(a.lprice, 0)) grplprice");
        sb.append(" from iagent.productgrplimit a where a.status = '-1' and enddt >= sysdate");
        sb.append(" and startdt <= sysdate group by a.prodid) b");
        sb.append(" on a.prodid = b.prodid) f on d.prodid = f.prodid");
        sb.append(" where d.orderid = :orderId");
        Query q = session.createSQLQuery(sb.toString());
        q.setString("orderId",orderId);
        return q.list();
    }

    @Override
    public String getAmountByShipmentTotalPriceAdj(String orderId) {
        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select cd.amount from acoapp_oms.SHIPMENT_TOTALPRICE_ADJ cd");
        sb.append(" where cd.order_id = :orderId");
        Query q = session.createSQLQuery(sb.toString());
        q.setString("orderId",orderId);

        if(q.uniqueResult() == null){
            return "0.00";
        }else {
            return q.uniqueResult().toString();
        }
    }
}
