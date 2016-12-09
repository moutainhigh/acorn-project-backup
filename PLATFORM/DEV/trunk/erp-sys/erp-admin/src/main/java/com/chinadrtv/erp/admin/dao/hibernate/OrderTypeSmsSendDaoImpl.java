package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.OrderTypeSmsSendDao;
import com.chinadrtv.erp.admin.model.OrderTypeSmsSend;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-13
 * Time: 下午1:46
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderTypeSmsSendDaoImpl extends GenericDaoHibernate<OrderTypeSmsSend, Integer> implements OrderTypeSmsSendDao{

    public OrderTypeSmsSendDaoImpl() {
        super(OrderTypeSmsSend.class);
    }
    public Query initQuery(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                           String usid,String paytype,Date beginDate, Date endDate,StringBuilder sb)  {
        if(v_smsType!=null && !v_smsType.equals("")){
           sb.append(" and smstype =:smstype ");
        }
        if(v_ordertype!=null && !v_ordertype.equals("")){
            sb.append(" and ordertype =:ordertype ");
        }
        if(v_setProName!=null && !v_setProName.equals("")) {
          //  sb.append("and setproid =:setproid");
        }
        if(v_smsname!=null && !v_smsname.equals("")){
          sb.append("and smsname like :smsname");
        }
        if(usid!=null&& !usid.equals("")) {
            sb.append("and setproid = :setproid");
        }
        if (beginDate != null) {
         sb.append(" and  setdate  >= :beginDate ");
        }

        if (endDate != null) {
        sb.append(" and  setdate  <= :endDate ");
        }
        if(paytype!=null&& !paytype.equals("")) {
            sb.append(" and paytype in (:paytype) ");
        }

        sb.append(" order by setdate desc");
        Query q = getSession().createQuery(sb.toString());

        if(v_smsType!=null && !v_smsType.equals("")){
           q.setString("smstype",v_smsType);
        }
        if(v_ordertype!=null && !v_ordertype.equals("")){
            //q.setString("orderType",v_ordertype);
        }
        if(v_setProName!=null && !v_setProName.equals("")) {
          //  q.setString("setproid", v_setProName);
        }
        if(v_smsname!=null && !v_smsname.equals("")){
            q.setString("smsname","%"+v_smsname+"%");
        }
        if(usid!=null && !usid.equals("")){
            q.setString("setproid",usid);
        }
        if (beginDate != null) {
           q.setDate("beginDate",beginDate);
        }
        if (endDate != null) {
            q.setDate("endDate", endDate);
        }
        if(paytype!=null&& !paytype.equals("")) {
            q.setParameter("paytype", paytype);
        }
        return q;
    }
   /*
    获得当前查询的总行数
   * */
    public int getOrderTypeSmsSendCountByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                                 String usid,String paytype,Date beginDate, Date endDate)   {
        String smswhere="";
        if(!v_ordertype.equals("") && v_ordertype!=null) {
            smswhere+=" and (a.orderType.id  in ("+v_ordertype+")) ";
        }
        if(!paytype.equals("") && paytype!=null){
            smswhere+=" and (a.paytype.id in ("+paytype+")) ";
        }
        StringBuilder sb = new StringBuilder();
            sb.append(" select count(a.id) from OrderTypeSmsSend a  where 1=1 " + smswhere);
             Query q = initQuery(v_smsType, "",v_setProName,v_smsname,usid,"",beginDate,endDate, sb);

        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }
    /*
     获得当前数据的list集合
    * */
    public List<OrderTypeSmsSend> searchPaginatedOrderTypeSmsSendByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                                                           String usid,String paytype,Date beginDate, Date endDate, int startIndex, Integer numPerPage) {
         String smswhere="";
        if(!v_ordertype.equals("") && v_ordertype!=null) {
           smswhere+=" and (a.orderType.id  in ("+v_ordertype+")) ";
          }
          if(!paytype.equals("") && paytype!=null){
               smswhere+=" and (a.paytype.id in ("+paytype+")) ";
          }
        StringBuilder sb = new StringBuilder();
          sb.append("from OrderTypeSmsSend a  where 1=1 "+smswhere);
        Query q = initQuery(v_smsType, "",v_setProName,v_smsname,usid,"",beginDate,endDate, sb);
        q.setFirstResult(startIndex*numPerPage);
        q.setMaxResults(numPerPage);
        return q.list();
    }
    public void addOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend){
        getSession().save(orderTypeSmsSend);
    }
    public void saveOrUpdate(OrderTypeSmsSend orderTypeSmsSend) {
        getSession().saveOrUpdate(orderTypeSmsSend);
    }

}
