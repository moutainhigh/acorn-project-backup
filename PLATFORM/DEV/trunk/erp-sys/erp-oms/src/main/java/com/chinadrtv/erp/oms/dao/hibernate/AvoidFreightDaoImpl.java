package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AvoidFreightDao;
import com.chinadrtv.erp.oms.dto.AvoidFreightDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 * 免运费登记
 */
@Repository("avoidFreightDao")
public class AvoidFreightDaoImpl extends GenericDaoHibernate<ShipmentHeader, Long> implements AvoidFreightDao {
    public AvoidFreightDaoImpl(){
        super(ShipmentHeader.class);
    }

    /**
     * 获取分页数据
     * @param avoidFreightDto
     * @param dataModel
     * @return
     */
    public List getFreightList(AvoidFreightDto avoidFreightDto, DataGridModel dataModel) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.id,t.senddt,t.shipment_id,t.mail_id,t.dsc,");
        sql.append("t.mail_price,t.address,t.prod_price,t.name from (");
        sql.append("select rownum r,a.id,a.senddt,a.shipment_id,a.mail_id,f.dsc,a.mail_price,");
        sql.append("e.address,a.prod_price,c.name from acoapp_oms.shipment_header a");
        sql.append(" inner join iagent.orderhist b on a.order_id = b.orderid");
        sql.append(" inner join iagent.contact c on b.contactid = c.contactid");
        sql.append(" inner join iagent.address e on b.addressid = e.addressid");
        sql.append(" left join iagent.names f on b.ordertype = f.id and f.tid = 'ORDERTYPE'");
        sql.append(" where CAST(a.logistics_status_id as NUMBER) >= 2  and NVL(a.RECONCIL_FLAG,0)=0 and a.account_type = 1");
        
        //拼接查询条件
        this.genSql(sql,avoidFreightDto);
        sql.append(") t where t.r >:page and t.r < =:rows");
        Query sqlQuery = getSession().createSQLQuery(sql.toString());

        if(!StringUtils.isEmpty(avoidFreightDto.getShipmentId()))
        {   //订单号
            sqlQuery.setString("ShipmentId",avoidFreightDto.getShipmentId());
        }
        if(!StringUtils.isEmpty(avoidFreightDto.getMailId()))
        {   //邮件号
            sqlQuery.setString("MailId",avoidFreightDto.getMailId());
        }
        sqlQuery.setInteger("page", dataModel.getStartRow());
        sqlQuery.setInteger("rows", dataModel.getStartRow()+dataModel.getRows());

        List objList = sqlQuery.list();

        return objList;
    }

    /**
     * 获取分页信息数量
     * @param avoidFreightDto
     * @return
     */
    public Integer queryCount(AvoidFreightDto avoidFreightDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from acoapp_oms.shipment_header a");
        sql.append(" inner join iagent.orderhist b on a.order_id = b.orderid");
        sql.append(" inner join iagent.contact c on b.contactid = c.contactid");
        sql.append(" inner join iagent.address e on b.addressid = e.addressid");
        sql.append(" left join iagent.names f on b.ordertype = f.id and f.tid = 'ORDERTYPE'");
        sql.append(" where a.logistics_status_id >= '2' and NVL(a.RECONCIL_FLAG,0)=0 and a.account_type = 1");
        //查询条件拼接
        this.genSql(sql,avoidFreightDto);
        Query sqlQuery = getSession().createSQLQuery(sql.toString());

        if(!StringUtils.isEmpty(avoidFreightDto.getShipmentId()))
        {   //订单号
            sqlQuery.setString("ShipmentId",avoidFreightDto.getShipmentId());
        }
        if(!StringUtils.isEmpty(avoidFreightDto.getMailId()))
        {   //邮件号
            sqlQuery.setString("MailId",avoidFreightDto.getMailId());
        }

        List list = sqlQuery.list();
        Integer result = ((BigDecimal)list.get(0)).intValue();
        return result;
    }

    /**
     * 查询条件
     * @param sql
     * @param avoidFreightDto
     */
    private void genSql(StringBuilder sql,AvoidFreightDto avoidFreightDto)
    {
        if(!StringUtils.isEmpty(avoidFreightDto.getShipmentId()))
        {   //订单号
            sql.append(" AND a.order_id = :ShipmentId");
        }
        if(!StringUtils.isEmpty(avoidFreightDto.getMailId()))
        {   //邮件号
            sql.append(" AND a.mail_id = :MailId");
        }

    }

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public ShipmentHeader getShipmentById(Long id)
    {
        return get(id);
    }

    /**
     * 数据保存
     * @param shipmentHeader
     */
    public void saveShipmentHeader(ShipmentHeader shipmentHeader) {
        this.saveOrUpdate(shipmentHeader);
    }

    /**
     * 获取明细信息
     * @param id
     * @return
     */
    public ShipmentHeader searchShipmentById(Long id) {
        Query q = getSession().createQuery(" from ShipmentHeader a where a.id = :Id");
        q.setParameter("Id",id);
        return (ShipmentHeader)q.uniqueResult();
    }

    /**
     * 删除逆向单表头数据
     * @param shipmentId
     */
    public void deleteByShipmentId(String shipmentId) {
        Query q = getSession().createQuery("delete ShipmentHeader a where a.shipmentId = :ShipmentId");
        q.setParameter("ShipmentId",shipmentId);
        q.executeUpdate();
    }

    /**
     * 根据shipmentId获取逆向发运单
     * @param shipmentId
     * @return
     */
    public ShipmentHeader searchShipmentByShipmentId(String  shipmentId){
        Query q = getSession().createQuery(" from ShipmentHeader a where a.shipmentId = :shipmentId");
        q.setParameter("shipmentId",shipmentId);
        return (ShipmentHeader)q.uniqueResult();
    }
}
