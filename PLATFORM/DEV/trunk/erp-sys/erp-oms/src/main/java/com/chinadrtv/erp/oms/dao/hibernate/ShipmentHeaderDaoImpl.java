package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.ShipmentHeaderDao;


/**
 * 发运单-数据访问
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ShipmentHeaderDaoImpl extends GenericDaoHibernate<ShipmentHeader, Long> implements ShipmentHeaderDao {
    public ShipmentHeaderDaoImpl() {
        super(ShipmentHeader.class);
    }

    public ShipmentHeader getShipmentById(Long id)
    {
        return get(id);
    }

    public Long getLogisticsShipmentCount(Map<String, Object> params){
        Session session = getSession();
        if(params.size() > 0){
            String str = "";
            if(params.containsKey("capture")){  //查询勾选是否已确认付款
                str = "where a.logistics_status_Id != '1' and (b.crdt < sysdate-30*1/24/60) and (b.status = 1 or (b.status=2 and (b.customizestatus is null or b.customizestatus = 0))) ";
            }else { //查询未勾选是否已确认付款，查询自动去掉付款方式（2、12、13）
                str = "where a.logistics_status_Id != '1' and b.paytype not in ('2','12','13') and (b.crdt < sysdate-30*1/24/60) and (b.status = 1 or (b.status=2 and (b.customizestatus is null or b.customizestatus = 0))) ";
            }
            Query q = session.createSQLQuery(
                    "select count(1) " +
                            "from acoapp_oms.Shipment_Header a " +
                            "left join iagent.orderhist b on a.order_id = b.orderid " +
                            "left join iagent.address_ext d on b.addressid=d.addressid " +
                            "left join iagent.address e on b.addressid = e.addressid " +
                            "left join iagent.cardauthorization k on b.orderid=k.orderid and b.cardrightnum=k.cardrightnum "+
                            str +
                            (params.containsKey("shipmentId") ? "and a.shipment_id = :shipmentId " : "") +
                            (params.containsKey("orderId") ? "and b.orderid = :orderId " : "") +
                            (params.containsKey("provinceId") ? "and d.provinceid = :provinceId " : "") +
                            (params.containsKey("cityId") ? "and d.cityid = :cityId " : "") +
                            (params.containsKey("countyId") ? "and d.countyid = :countyId " : "") +
                            (params.containsKey("accountStatusId") ? "and b.status = :accountStatusId " : "") +
                            (params.containsKey("warehouseId") ? "and a.warehouse_id = :warehouseId " : "") +
                            (params.containsKey("entityId") ? "and a.entity_id = :entityId " : "") +
                            (params.containsKey("paytypeId") ? "and b.paytype = :paytypeId " : "") +
                            (params.containsKey("minAmount") ? "and b.totalprice >= :minAmount " : "") +
                            (params.containsKey("maxAmount") ? "and b.totalprice <= :maxAmount " : "") +
                            (params.containsKey("capture") ? "and b.confirm = :capture " : "") +
                            (params.containsKey("cardtype") ? "and b.cardtype = :cardtype " : "") +
                            //创建时间段查询
                            (params.containsKey("crdtStart") ? "and b.crdt  >= to_date(:crdtStart,'yyyy-MM-dd HH24:mi:ss') and a.crdt >= to_date(:crdtStart,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("crdtEnd") ? "and b.crdt < to_date(:crdtEnd,'yyyy-MM-dd HH24:mi:ss')" : "") +
                            //交际时间段查询
                            (params.containsKey("senddtStart") ? "and a.senddt  >= to_date(:senddtStart,'yyyy-MM-dd HH24:mi:ss')" : "") +
                            (params.containsKey("senddtEnd") ? "and a.senddt < to_date(:senddtEnd,'yyyy-MM-dd HH24:mi:ss')" : "") +
                            (params.containsKey("productId") ? "and exists(select aa.id from acoapp_oms.shipment_detail aa where aa.shipment_id=a.shipment_id and aa.item_id = :productId) " : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return Long.valueOf(q.list().get(0).toString());
        } else {
            return 0L;
        }
    }

    public List getLogisticsShipments(Map<String, Object> params, int index, int size){
        Session session = getSession();
        if(params.size() > 0){
            String str = "";
            if(params.containsKey("capture")){  //查询勾选是否已确认付款
                str = "where a.logistics_status_Id != '1' and (b.crdt < sysdate-30*1/24/60) and (b.status = 1 or (b.status=2 and (b.customizestatus is null or b.customizestatus = 0))) ";
            }else { //查询未勾选是否已确认付款，查询自动去掉付款方式（2、12、13）
                str = "where a.logistics_status_Id != '1' and b.paytype not in ('2','12','13') and (b.crdt < sysdate-30*1/24/60) and (b.status = 1 or (b.status=2 and (b.customizestatus is null or b.customizestatus = 0))) ";
            }
            Query q = session.createSQLQuery("select " +
                    "a.id,a.order_id,a.shipment_id,e.address,b.totalprice,a.warehouse_id,a.entity_id,a.logistics_status_id,b.status,"+
                    "mymerge(cast(multiset (select p.prodname from iagent.orderdet t join iagent.product p on t.prodid = p.prodid"+
                    " where t.orderid = b.orderid) as strings_table),',') prodname,k.confirmdt "+
                    "from acoapp_oms.Shipment_Header a " +
                    "left join iagent.orderhist b on a.order_id = b.orderid " +
                    "left join iagent.address_ext d on b.addressid=d.addressid " +
                    "left join iagent.address e on b.addressid = e.addressid " +
                    "left join iagent.cardauthorization k on b.orderid=k.orderid and b.cardrightnum=k.cardrightnum " +
                     str +
                    (params.containsKey("shipmentId") ? "and a.shipment_id = :shipmentId " : "") +
                    (params.containsKey("orderId") ? "and b.orderid = :orderId " : "") +
                    (params.containsKey("provinceId") ? "and d.provinceid = :provinceId " : "") +
                    (params.containsKey("cityId") ? "and d.cityid = :cityId " : "") +
                    (params.containsKey("countyId") ? "and d.countyid = :countyId " : "") +
                    (params.containsKey("accountStatusId") ? "and b.status = :accountStatusId " : "") +
                    (params.containsKey("warehouseId") ? "and a.warehouse_id = :warehouseId " : "") +
                    (params.containsKey("entityId") ? "and a.entity_id = :entityId " : "") +
                    (params.containsKey("paytypeId") ? "and b.paytype = :paytypeId " : "") +
                    (params.containsKey("minAmount") ? "and b.totalprice >= :minAmount " : "") +
                    (params.containsKey("maxAmount") ? "and b.totalprice <= :maxAmount " : "") +
                    (params.containsKey("capture") ? "and b.confirm = :capture " : "") +
                    (params.containsKey("cardtype") ? "and b.cardtype = :cardtype " : "") +
                    //创建时间段查询
                    (params.containsKey("crdtStart") ? "and b.crdt  >= to_date(:crdtStart,'yyyy-MM-dd HH24:mi:ss') and a.crdt >= to_date(:crdtStart,'yyyy-MM-dd HH24:mi:ss')" : "") +
                    (params.containsKey("crdtEnd") ? "and b.crdt < to_date(:crdtEnd,'yyyy-MM-dd HH24:mi:ss')" : "") +
                    //交际时间段查询
                    (params.containsKey("senddtStart") ? "and a.senddt  >= to_date(:senddtStart,'yyyy-MM-dd HH24:mi:ss')" : "") +
                    (params.containsKey("senddtEnd") ? "and a.senddt < to_date(:senddtEnd,'yyyy-MM-dd HH24:mi:ss')" : "") +
                    (params.containsKey("productId") ? "and exists(select aa.id from acoapp_oms.shipment_detail aa where aa.shipment_id=a.shipment_id and aa.item_id = :productId) " : "")
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
        }else {
            return new ArrayList<Object>();
        }
    }

    /*
    public Long getLogisticsShipmentCount(String shipmentId, String orderId, String provinceId, Long cityId){
           Session session = getSession();
           Query q = session.createQuery("select count(a.id) from ShipmentHeader as a, Orderhist as b " +
                                           "where a.logisticsStatusId != '1' and a.orderId = b.orderid " +
                                            ("".equals(shipmentId) ? "":"and a.shipmentId = :shipmentId ") +
                                            ("".equals(orderId) ? "":"and a.orderId = :orderId ") +
                                            ("".equals(provinceId) ? "":"and b.provinceid = :provinceId ") +
                                            (cityId == null || cityId == 0 ? "":"and b.cityid = :cityId ")
                                        );

           List<String> parameters = Arrays.asList(q.getNamedParameters());

           if(parameters.contains("shipmentId")){
               q.setString("shipmentId", shipmentId);
           }
           if(parameters.contains("orderId")){
               q.setString("orderId", orderId);
           }
           if(parameters.contains("provinceId")){
               q.setString("provinceId", provinceId);
           }
           if(parameters.contains("cityId")){
               q.setParameter("cityId", cityId);
           }
           return Long.valueOf(q.list().get(0).toString());
       }

       public List<ShipmentHeader> getLogisticsShipments(String shipmentId, String orderId, String provinceId, Long cityId, int index, int size)
       {
           Session session = getSession();
           Query q = session.createQuery("from ShipmentHeader as a, Orderhist as b " +
                   "where a.logisticsStatusId != '1' and a.orderId = b.orderid " +
                   ("".equals(shipmentId) ? "" : "and a.shipmentId = :shipmentId ") +
                   ("".equals(orderId) ? "" : "and a.orderId = :orderId ") +
                   ("".equals(provinceId) ? "" : "and b.provinceid = :provinceId ") +
                   (cityId == null || cityId == 0 ? "" : "and b.cityid = :cityId ")
           );

           List<String> parameters = Arrays.asList(q.getNamedParameters());

           if(parameters.contains("shipmentId")){
               q.setString("shipmentId", shipmentId);
           }
           if(parameters.contains("orderId")){
               q.setString("orderId", orderId);
           }
           if(parameters.contains("provinceId")){
               q.setString("provinceId", provinceId);
           }
           if(parameters.contains("cityId")){
               q.setParameter("cityId", cityId);
           }
           q.setFirstResult(index);
           q.setMaxResults(size);

           List<ShipmentHeader> headers = new ArrayList<ShipmentHeader>();

           List list = q.list();
           if(list != null){
               for(Object item : list)
               {
                   headers.add((ShipmentHeader)((Object[])item)[0]);
               }
           }
           return headers;
       }
    */
       /**
        * 获取制定发运单号的发运单
        * @param shipmentId
        * @return
        */
    public List<ShipmentHeader> getShipments(String shipmentId, String orderId)
    {
        Session session = getSession();
        Query q = session.createQuery(
                "from ShipmentHeader a " +
                "where a.logisticsStatusId != '1' and a.accountStatusId = '2' " + //1:取消
                ("".equals(shipmentId) ? "":"and a.shipmentId = :shipmentId ") +
                ("".equals(orderId) ? "":"and a.orderId = :orderId ")
        );
        List<String> parameters = Arrays.asList(q.getNamedParameters());

        if(parameters.contains("shipmentId")){
            q.setString("shipmentId", shipmentId);
        }
        if(parameters.contains("orderId")){
            q.setString("orderId", orderId);
        }
        return q.list();

    }

    public Long getShipmentCount(String shipmentId, String orderId)
    {
        if((null == shipmentId ||"".equals(shipmentId)) && (null == orderId || "".equals(orderId)))
        {
            return 0L;
        } else {
            Session session = getSession();
            Query q = session.createQuery(
                    "select count(a.id) from ShipmentHeader a " +
                            "where a.logisticsStatusId != '1' and a.accountStatusId = '2' " + //1:取消
                            ("".equals(shipmentId) ? "":"and a.shipmentId = :shipmentId ") +
                            ("".equals(orderId) ? "":"and a.orderId = :orderId ")
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());

            if(parameters.contains("shipmentId")){
                q.setString("shipmentId", shipmentId);
            }
            if(parameters.contains("orderId")){
                q.setString("orderId", orderId);
            }
            return Long.valueOf(q.list().get(0).toString());
        }
    }

    public List<ShipmentHeader> getShipments(String shipmentId, String orderId, int index, int size)
    {
        if((null == shipmentId ||"".equals(shipmentId)) && (null == orderId || "".equals(orderId)))
        {
            return new ArrayList<ShipmentHeader>();
        } else {
            Session session = getSession();
            Query q = session.createQuery(
                    "from ShipmentHeader a " +
                            "where a.logisticsStatusId != '1' and a.accountStatusId = '2' " + //1:取消
                            ("".equals(shipmentId) ? "":"and a.shipmentId = :shipmentId ") +
                            ("".equals(orderId) ? "":"and a.orderId = :orderId ")
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());

            if(parameters.contains("shipmentId")){
                q.setString("shipmentId", shipmentId);
            }
            if(parameters.contains("orderId")){
                q.setString("orderId", orderId);
            }

            q.setFirstResult(index);
            q.setMaxResults(size);

            return q.list();
        }
    }

	/* 
	 * 根据shipmentId 获取 ShipmentHeader
	* <p>Title: 根据shipmentId 获取 ShipmentHeader</p>
	* <p>Description: </p>
	* @param shipmentId
	* @return
	* @see com.chinadrtv.erp.shipment.service.ShipmentService#getByShipmentId(java.lang.Long)
	*/ 
	public ShipmentHeader queryByShipmentId(String shipmentId) {
		String hql = "select sh from ShipmentHeader sh where sh.shipmentId = :shipmentid";
        Parameter<String> param = new ParameterString("shipmentid", shipmentId);
        return this.find(hql,param);
	}

    //start by xzk
    public ShipmentHeader getShipmentFromShipmentId(String shipmentId)
    {
        return this.find("from ShipmentHeader where shipmentId=:shipmentId",new ParameterString("shipmentId",shipmentId));
    }

    public ShipmentHeader getShipmentFromOrderId(String orderId)
    {
        return this.find("from ShipmentHeader where orderId=:orderId and logisticsStatusId !='1' and accountType='1'", new ParameterString("orderId",orderId));
    }

    public List<Object[]> getReceiptPayments(Map<String, Object> params, int index, int size) {
		
        Session session = getSession();
        
        String sql = "select o.ID as id, o.FBDT as fbdt, o.SHIPMENT_ID as shipmentId, o.MAIL_ID as mailId, " +
        		"nvl(o.PROD_PRICE,0)+nvl(o.MAIL_PRICE,0) as A, " +
        		"nvl(ol1.MAIL_PRICE,0) as B, " +
        		"nvl(ol2.PROD_PRICE,0)+nvl(ol2.MAIL_PRICE,0) as C, " +
        		"nvl(o.POST_FEE1,0) as E, " +
        		"nvl(o.POST_FEE2,0) as F, " +
        		"nvl(o.CLEAR_FEE,0) as G, " +
        		"nvl(o.CLEAR_POST_FEE,0) as H, " +
        		"nvl(o.POST_FEE3,0) as J, " +
        		"nvl(o.POST_FEE1,0) + nvl(o.POST_FEE2,0) + nvl(o.POST_FEE3,0) as K, " +
        		"o.WEIGHT as weight " +
        		"from acoapp_oms.Shipment_Header o " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol1 on o.id = ol1.ASSOCIATED_ID and OL1.ACCOUNT_TYPE='3' " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol2 on o.id = ol2.ASSOCIATED_ID and OL2.ACCOUNT_TYPE='2' " +
        		"WHERE o.ACCOUNT_TYPE='1' and o.ACCOUNT_STATUS_ID in ('5') ";
        
        boolean accounted = (Boolean) params.get("accounted");
        if(accounted){
        	sql += " and nvl(o.RECONCIL_FLAG,0)=1 ";
        }else{
        	sql += " and nvl(o.RECONCIL_FLAG,0)=0 ";
        }
//        if(accounted){
//        	sql += " and nvl(o.RECONCIL_FLAG,0)=1 and o.ACCOUNT='Y' ";
//        }else{
//        	sql += " and nvl(o.RECONCIL_FLAG,0)=0 and (o.ACCOUNT IS null or o.ACCOUNT!='Y') ";
//        }
        
        boolean exclude = (Boolean) params.get("exclude");
        if(exclude){
        	sql += " and ((nvl(o.PROD_PRICE,0)+nvl(o.MAIL_PRICE,0)) + nvl(ol1.MAIL_PRICE,0) + nvl(ol2.PROD_PRICE,0) + nvl(ol2.MAIL_PRICE,0) - nvl(o.CLEAR_FEE,0))=0";
        }
        
        if(params!=null && params.size()>0){
            sql += (params.containsKey("shipmentId") ? " and o.shipment_id like :shipmentId " : "") +
            		(params.containsKey("mailId") ? " and o.mail_id = :mailId " : "") +
            		(params.containsKey("entityId") ? " and o.entity_id = :entityId " : "") +
            		(params.containsKey("warehouseId") ? " and o.warehouse_id = :warehouseId " : "") +
            		(params.containsKey("fbDtS") ? " and o.fbdt >= :fbDtS " : "") +
            		(params.containsKey("fbDtE") ? " and o.fbdt <= :fbDtE " : "");       	
        }

        Query q = session.createSQLQuery(sql);
        
        if(params!=null && params.size()>0){
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }        	
        }

        q.setFirstResult(index);
        q.setMaxResults(size);
		return q.list();
	}

	public Long getReceiptPaymentCount(Map<String, Object> params) {
        Session session = getSession();
        String sql = "select count(1) " + 
        		"from acoapp_oms.Shipment_Header o " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol1 on o.id = ol1.ASSOCIATED_ID and OL1.ACCOUNT_TYPE='3' " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol2 on o.id = ol2.ASSOCIATED_ID and OL2.ACCOUNT_TYPE='2' " +
        		"WHERE o.ACCOUNT_TYPE='1' and o.ACCOUNT_STATUS_ID in ('5') ";
        
        boolean accounted = (Boolean) params.get("accounted");
        if(accounted){
        	sql += " and nvl(o.RECONCIL_FLAG,0)=1 ";
        }else{
        	sql += " and nvl(o.RECONCIL_FLAG,0)=0 ";
        }
//        if(accounted){
//        	sql += " and nvl(o.RECONCIL_FLAG,0)=1 and o.ACCOUNT='Y' ";
//        }else{
//        	sql += " and nvl(o.RECONCIL_FLAG,0)=0 and (o.ACCOUNT IS null or o.ACCOUNT!='Y') ";
//        }
        
        boolean exclude = (Boolean) params.get("exclude");
        if(exclude){
        	sql += " and ((nvl(o.PROD_PRICE,0)+nvl(o.MAIL_PRICE,0)) + nvl(ol1.MAIL_PRICE,0) + nvl(ol2.PROD_PRICE,0) + nvl(ol2.MAIL_PRICE,0) - nvl(o.CLEAR_FEE,0))=0";
        }
        
        if(params!=null && params.size()>0){
            sql += (params.containsKey("shipmentId") ? " and o.shipment_id like :shipmentId " : "") +
            		(params.containsKey("mailId") ? " and o.mail_id = :mailId " : "") +
            		(params.containsKey("entityId") ? " and o.entity_id = :entityId " : "") +
            		(params.containsKey("warehouseId") ? " and o.warehouse_id = :warehouseId " : "") +
            		(params.containsKey("fbDtS") ? " and o.fbdt >= :fbDtS " : "") +
            		(params.containsKey("fbDtE") ? " and o.fbdt <= :fbDtE " : "");       	
        }

        Query q = session.createSQLQuery(sql);
        
        if(params!=null && params.size()>0){
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }        	
        }
		return Long.valueOf(q.list().get(0).toString());
	}

	public List<ShipmentHeader> getShipmentByAssociatedId(String id) {
        Session session = getSession();
        Query q = session.createQuery("from ShipmentHeader a where a.id = :associatedId or a.associatedId = :associatedId ");
        q.setString("associatedId", id);
        return q.list();
	}

	public List<Object[]> accAble(String shipmentHeaderId) {
        Session session = getSession();
        String sql = "select ((nvl(o.PROD_PRICE,0)+nvl(o.MAIL_PRICE,0)) + nvl(ol1.MAIL_PRICE,0) + nvl(ol2.PROD_PRICE,0)+nvl(ol2.MAIL_PRICE,0) - nvl(o.CLEAR_FEE,0)) as i, " +
        		"o.ENTITY_ID as entityId " +
        		"from acoapp_oms.Shipment_Header o " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol1 on o.id = ol1.ASSOCIATED_ID and OL1.ACCOUNT_TYPE='3' " +
        		"LEFT JOIN acoapp_oms.Shipment_Header ol2 on o.id = ol2.ASSOCIATED_ID and OL2.ACCOUNT_TYPE='2' " +
        		"WHERE o.id=:id and o.ACCOUNT_TYPE='1' and o.ACCOUNT_STATUS_ID in ('5') and nvl(o.RECONCIL_FLAG,0)=0";
//        		"WHERE o.id=:id and o.ACCOUNT_TYPE='1' and (o.ACCOUNT IS null or o.ACCOUNT!='Y') and o.ACCOUNT_STATUS_ID in ('5','6') and nvl(o.RECONCIL_FLAG,0)=0";
        Query q = session.createSQLQuery(sql);
        q.setParameter("id", shipmentHeaderId);
		return q.list();
	}
}
