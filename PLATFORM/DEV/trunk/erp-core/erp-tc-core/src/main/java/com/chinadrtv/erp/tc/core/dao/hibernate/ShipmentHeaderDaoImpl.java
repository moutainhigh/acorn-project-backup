package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.model.trade.WmsShipmentHeader;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Repository("tcCoreShipmentHeaderDao")
public class ShipmentHeaderDaoImpl extends GenericDaoHibernateBase<ShipmentHeader, Long> implements ShipmentHeaderDao {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentHeaderDaoImpl.class);
    public ShipmentHeaderDaoImpl() {
        super(ShipmentHeader.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public ShipmentHeader addShipmentHeader(ShipmentHeader shipmentHeader) {
        return this.save(shipmentHeader);
    }

    public ShipmentHeader updateShipmentHeader(ShipmentHeader shipmentHeader) {
        Session session = this.getSession();
        ShipmentHeader newSh = this.save(shipmentHeader);
        session.flush();

        /*FlushMode flushMode=session.getFlushMode();
        boolean bll=session.isDirty();
        //session.flush();

        bll=session.isDirty();
        //session.flush();
        System.out.println(bll); */

        return newSh;
    }

    public void deleteShipmentHeader(ShipmentHeader shipmentHeader) {
        if (shipmentHeader.getId() != null) {
            ParameterInteger parm = new ParameterInteger("id", Integer.parseInt(shipmentHeader.getId().toString()));
            this.execUpdate("delete from ShipmentHeader where id=:id", parm);
        }
    }

    /* (非 Javadoc)
    * <p>Title: getByOrderId</p>
    * <p>Description: </p>
    * @param orderId
    * @return
    * @see com.chinadrtv.erp.shipment.dao.ShipmentHeaderDao#getByOrderId(java.lang.String)
    */
    public List<ShipmentHeader> getByOrderId(String orderId) {
        String hql = "select sh from ShipmentHeader sh where sh.orderId=:orderId";
        Parameter<String> param = new ParameterString("orderId", orderId);
        return this.findList(hql, param);
    }

    public ShipmentHeader getByShipmentId(String shipmentId){
        String hql = "select sh from ShipmentHeader sh where sh.shipmentId = :shipmentid";
        Parameter<String> param = new ParameterString("shipmentid", shipmentId);
        return this.find(hql,param);
    }

    /* (非 Javadoc)
    * <p>Title: queryShipmentList</p>
    * <p>Description: </p>
    * @param params
    * @return
    * @see com.chinadrtv.erp.shipment.dao.ShipmentHeaderDao#queryShipmentList(java.util.Map)
    */
    @Deprecated
    public List<ShipmentHeader> queryShipmentList(Map<String, Object> params) {
        Map<String, Parameter> queryParams = new HashMap<String, Parameter>();
        StringBuffer sb = new StringBuffer();
        sb.append("select sh from ShipmentHeader sh where 1=1 and logisticsStatusId=0");
        if (null != params.get("orderId") && !"".equals(params.get("orderId".toString()))) {
            sb.append(" and sh.orderId=:orderId ");
            Parameter<String> orderParam = new ParameterString("orderId", params.get("orderId").toString());
            queryParams.put("orderId", orderParam);
        }
        if (null != params.get("mailId") && !"".equals(params.get("mailId").toString())) {
            sb.append(" and sh.mailId=:mailId ");
            Parameter<String> mailParam = new ParameterString("mailId", params.get("mailId").toString());
            queryParams.put("mailId", mailParam);
        }
        if (null != params.get("shipmentId") && !"".equals(params.get("shipmentId").toString())) {
            sb.append(" and sh.shipmentId=:shipmentId ");
            Parameter<String> shipmentParam = new ParameterString("shipmentId", params.get("shipmentId").toString());
            queryParams.put("shipmentId", shipmentParam);
        }
        if (null != params.get("warehouseId") && !"".equals(params.get("warehouseId").toString())) {
            sb.append(" and sh.warehouseId=:warehouseId ");
            Parameter<String> warehouseParam = new ParameterString("warehouseId", params.get("warehouseId").toString());
            queryParams.put("warehouseId", warehouseParam);
        }
        return this.findList(sb.toString(), queryParams);
    }

    /* (非 Javadoc)
     * <p>Title: generateTaskNo</p>
     * <p>Description: </p>
     * @return
     * @see com.chinadrtv.erp.shipment.dao.WmsShipmentHeaderDao#generateTaskNo()
    */
    public Integer generateTaskNo() {
        String sql = "select shipment_download_tasksno_seq.nextval from dual";
        String taskNo = getSession().createSQLQuery(sql).list().get(0).toString();
        return Integer.parseInt(taskNo);
    }

    /*
	 *  根据ShipmentID 查询
	* <p>Title: queryByShipmentId</p>
	* <p>Description: </p>
	* @param shipmentId
	* @return
	* @see com.chinadrtv.erp.shipment.dao.WmsShipmentHeaderDao#queryByShipmentId(java.lang.String)
	*/
    //@SuppressWarnings("unchecked")
    public WmsShipmentHeader queryByShipmentId(String shipmentId) {
        String sql = "SELECT PAYMENT_METHOD, SHIPMENT_ID, REVISON, CUSTOMER, SHIP_TO_NAME, SHIP_TO_ADDRESS1, FREIGHT_BILL_TO,  " +
                " SHIP_TO_ADDRESS2, SHIP_TO_POSTAL_CODE, SHIP_TO_CITY, SHIP_TO_COUNTRY, HOME_PHONE_NUM, MOBILE_PHONE_NUM,  " +
                " OFFICE_PHONE_NUM, ISSUE_ORGANIZATION, RECEIPT_ORGANIZATION, TRANSPORTATION_MODE, SHIPMENT_TYPE, ORDER_DATETIME,  " +
                " SCHEDULE_SHIP_DATE, PAYMENT_TERM, MEMO, CERTIFICATION_CODE, CARRIER_TYPE, CARRIER, ORDER_TYPER, INVOICE_TYPE,  " +
                " INVOICE_TITLE, VALUE_SHIPPED, WAREHOUSE, SALES, CARRIAGE, TASKSNO, UPSTATUS, UPDAT, UPUSER, DNSTATUS, ISIAGENT,  " +
                " MAILETYPE, POSTFEE, ALLOW_REPEAT, CHINESE, CITYNAME, COUNTYNAME  " +
                " FROM ACOAPP_OMS.VW_WMS_SHIPMENT_HEADER WSH  " +
                " WHERE WSH.OMS_SHIPMENT_ID= :shipmentId ";
                //" WHERE WSH.OMS_SHIPMENT_ID='"+shipmentId+"' ";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("shipmentId", shipmentId);
        query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        // 常量或char类型会被映射成java.lang.Character，只能取到一个字符
        List<Map<String, Object>> wshList = query.list();

        WmsShipmentHeader wsh = null;
        if(null != wshList && wshList.size()>0){
            Map<String, Object> resultMap = wshList.get(0);

            wsh = new WmsShipmentHeader();
            wsh.setPaymentMethod(null==resultMap.get("PAYMENT_METHOD") ? "" : resultMap.get("PAYMENT_METHOD").toString());
            wsh.setShipmentId(null==resultMap.get("SHIPMENT_ID") ? "" : resultMap.get("SHIPMENT_ID").toString());
            wsh.setRevison(null==resultMap.get("REVISON") ? "" : resultMap.get("REVISON").toString());
            wsh.setCustomer(null==resultMap.get("CUSTOMER") ? "" : resultMap.get("CUSTOMER").toString());
            wsh.setShipToName(null==resultMap.get("SHIP_TO_NAME") ? "" : resultMap.get("SHIP_TO_NAME").toString());
            wsh.setShipToAddress1(null==resultMap.get("SHIP_TO_ADDRESS1") ? "" : resultMap.get("SHIP_TO_ADDRESS1").toString());
            wsh.setFreightBillTo(null==resultMap.get("FREIGHT_BILL_TO") ? "" : resultMap.get("FREIGHT_BILL_TO").toString());
            wsh.setShipToAddress2(null==resultMap.get("SHIP_TO_ADDRESS2") ? "" : resultMap.get("SHIP_TO_ADDRESS2").toString());
            wsh.setShipToPostalCode(null==resultMap.get("SHIP_TO_POSTAL_CODE") ? "" : resultMap.get("SHIP_TO_POSTAL_CODE").toString());
            wsh.setShipToCity(null==resultMap.get("SHIP_TO_CITY") ? "" : resultMap.get("SHIP_TO_CITY").toString());
            wsh.setShipToCountry(null==resultMap.get("SHIP_TO_COUNTRY") ? "" : resultMap.get("SHIP_TO_COUNTRY").toString());

            wsh.setHomePhoneNum(null==resultMap.get("HOME_PHONE_NUM") ? "" : resultMap.get("HOME_PHONE_NUM").toString());
            wsh.setMobilePhoneNum(null==resultMap.get("MOBILE_PHONE_NUM") ? "" : resultMap.get("MOBILE_PHONE_NUM").toString());
            wsh.setOfficePhoneNum(null==resultMap.get("OFFICE_PHONE_NUM") ? "" : resultMap.get("OFFICE_PHONE_NUM").toString());
            wsh.setIssueOrganization(null==resultMap.get("ISSUE_ORGANIZATION") ? "" :resultMap.get("ISSUE_ORGANIZATION").toString());
            wsh.setReceiptOrganization(null==resultMap.get("RECEIPT_ORGANIZATION") ? "" : resultMap.get("RECEIPT_ORGANIZATION").toString());
            wsh.setTransportationMode(null==resultMap.get("TRANSPORTATION_MODE") ? "" : resultMap.get("TRANSPORTATION_MODE").toString());
            wsh.setShipmentType(null==resultMap.get("SHIPMENT_TYPE") ? "" : resultMap.get("SHIPMENT_TYPE").toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                if(null != resultMap.get("ORDER_DATETIME")){
                    wsh.setOrderDatetime(sdf.parse(resultMap.get("ORDER_DATETIME").toString()));
                }
                if(null != resultMap.get("SCHEDULE_SHIP_DATE")){
                    wsh.setScheduleShipDate(sdf.parse(resultMap.get("SCHEDULE_SHIP_DATE").toString()));
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
            }
            wsh.setPaymentTerm(null==resultMap.get("PAYMENT_TERM") ? "" : resultMap.get("PAYMENT_TERM").toString());
            wsh.setMemo(null==resultMap.get("MEMO") ? "" : resultMap.get("MEMO").toString());
            wsh.setCertificationCode(null==resultMap.get("CERTIFICATION_CODE") ? "" : resultMap.get("CERTIFICATION_CODE").toString());
            wsh.setCarrierType(null==resultMap.get("CARRIER_TYPE") ? "" : resultMap.get("CARRIER_TYPE").toString());
            wsh.setCarrier(null==resultMap.get("CARRIER") ? "" : resultMap.get("CARRIER").toString());
            if(null!=resultMap.get("ORDER_TYPER")){
                wsh.setOrderTyper(Integer.parseInt(resultMap.get("ORDER_TYPER").toString()));
            }

            wsh.setInvoiceType(null==resultMap.get("INVOICE_TYPE") ? "" : resultMap.get("INVOICE_TYPE").toString());
            wsh.setInvoiceTitle(null==resultMap.get("INVOICE_TITLE") ? "" : resultMap.get("INVOICE_TITLE").toString());
            if(null != resultMap.get("VALUE_SHIPPED")){
                wsh.setValueShipped(new BigDecimal(resultMap.get("VALUE_SHIPPED").toString()));
            }

            wsh.setWarehouse(null==resultMap.get("WAREHOUSE") ? "" : resultMap.get("WAREHOUSE").toString());
            wsh.setSales(null==resultMap.get("SALES") ? "" : resultMap.get("SALES").toString());
            if(null != resultMap.get("CARRIAGE")){
                wsh.setCarriage(new BigDecimal(resultMap.get("CARRIAGE").toString()));
            }
            if(null != resultMap.get("TASKSNO")){
                wsh.setTasksno(Integer.parseInt(resultMap.get("TASKSNO").toString()));
            }
            if(null != resultMap.get("UPSTATUS")){
                wsh.setUpstatus(Integer.parseInt(resultMap.get("UPSTATUS").toString()));
            }
            try {
                if(null != resultMap.get("UPDAT")){
                    wsh.setUpdat(sdf.parse(resultMap.get("UPDAT").toString()));
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
            }
            wsh.setUpuser(null==resultMap.get("UPUSER") ? "" : resultMap.get("UPUSER").toString());
            if(null != resultMap.get("DNSTATUS")){
                wsh.setDnstatus(Boolean.valueOf("1".equals(resultMap.get("DNSTATUS").toString())));
            }
            if(null != resultMap.get("ISIAGENT")){
                wsh.setIsiagent(Boolean.valueOf("1".equals(resultMap.get("ISIAGENT").toString())));
            }
            if(null != resultMap.get("MAILETYPE")){
                wsh.setMailetype(Integer.parseInt(resultMap.get("MAILETYPE").toString()));
            }
            if(null != resultMap.get("POSTFEE")){
                wsh.setPostfee(new BigDecimal(resultMap.get("POSTFEE").toString()));
            }
            if(null != resultMap.get("ALLOW_REPEAT")){
                wsh.setAllowRepeat(Boolean.valueOf("1".equals(resultMap.get("ALLOW_REPEAT").toString())));
            }
            wsh.setShipToProvinceName(resultMap.get("CHINESE")!=null?resultMap.get("CHINESE").toString():null);
            wsh.setShipToCityName(resultMap.get("CITYNAME")!=null?resultMap.get("CITYNAME").toString():null);
            wsh.setShipToCountyName(resultMap.get("COUNTYNAME")!=null?resultMap.get("COUNTYNAME").toString():null);
        }
        return wsh;
    }

    public ShipmentHeader getByMailId(String mailId, String orderId){
        String hql = "select sh from ShipmentHeader sh where sh.orderId = :orderId and sh.mailId = :mailId and sh.accountType = '1'";
        Parameter<String> paramOrderId = new ParameterString("orderId", orderId);
        Parameter<String> paramMailId = new ParameterString("mailId", mailId);
        return this.find(hql, paramOrderId, paramMailId);
    }

    public List<ShipmentHeader> queryShipmentFromOrderIds(Map<String,Long> orderIdList)
    {
        //找到对应的订单
        int index=0;
        Map<String, Parameter> mapParams=new HashMap<String, Parameter>();
        StringBuffer stringBuffer=new StringBuffer("from ShipmentHeader where ");
        for(Map.Entry<String,Long> entry :orderIdList.entrySet())
        {
            //合成运单号
            String parmNameOrderId="orderId"+index;
            String parmNameRefVersion="version"+index;
            if(index==0)
                stringBuffer.append(String.format(" (orderId=:%s and orderRefRevisionId=:%s)",parmNameOrderId, parmNameRefVersion));
            else
                stringBuffer.append(String.format(" or (orderId=:%s and orderRefRevisionId=:%s)", parmNameOrderId, parmNameRefVersion));
            mapParams.put(parmNameOrderId, new ParameterString(parmNameOrderId,entry.getKey()));
            mapParams.put(parmNameRefVersion,new ParameterLong(parmNameRefVersion,entry.getValue()));
            index++;
        }
        return this.findList(stringBuffer.toString(),mapParams);
    }

}
