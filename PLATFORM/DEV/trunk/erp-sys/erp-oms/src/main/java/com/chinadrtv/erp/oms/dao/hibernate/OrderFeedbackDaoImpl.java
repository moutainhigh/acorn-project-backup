package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.oms.dao.*;
import com.chinadrtv.erp.oms.dto.EdiClearDto;
import com.chinadrtv.erp.util.StringUtil;
import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.Date;
import java.util.List;

@Repository
public class OrderFeedbackDaoImpl extends GenericDaoHibernate<EdiClear, java.lang.Long> implements OrderFeedbackDao{

	public OrderFeedbackDaoImpl() {
	    super(EdiClear.class);
	}
	
	
	public EdiClear getOrderFeedbackById(String orderFeedbackId) {
        Session session = getSession();
        return (EdiClear)session.get(EdiClear.class, orderFeedbackId);
    }
	
	
	    public List<EdiClear> getAllOrderFeedback()
    {
        Session session = getSession();
        Query query = session.createQuery("from EdiClear");
        return query.list();
    }
    public List<EdiClearDto> getAllOrderFeedback(String companyid,int state,int settleType,Date beginDate,Date endDate,int index, int size,Boolean remark)
    {
        Session session = getSession();
        StringBuffer sb = new StringBuffer("select t.*,o.entityid,c.name as company from ACOAPP_OMS.Oms_Ediclear t left join iagent.orderhist o on t.orderid = o.orderid  left join iagent.company c on o.entityid=c.companyid  where 1=1 " );
        if(remark){   //是否结算
            sb.append(" and t.remark is null ");
        }
      //  super.getSessionFactory().getCurrentSession().createSQLQuery(hql).addEntity("menu",Menu.class).list();
        SQLQuery query = initQuery(companyid,state,settleType,beginDate,endDate,sb);
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(EdiClear orderFeedback){
        getSession().saveOrUpdate(orderFeedback);
    }


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.dao.OrderFeedbackDao#getOrderFeedbackCount(int)
	 */
	public int getOrderFeedbackCount(String companyid,int state,int settleType,Date beginDate,Date endDate,Boolean remark) {
	       Session session = getSession();
	        StringBuffer sb = new StringBuffer("select t.* ,o.entityid ,c.name as company from ACOAPP_OMS.Oms_Ediclear t left join iagent.orderhist o on t.orderid = o.orderid  left join iagent.company c on o.entityid=c.companyid   where 1=1 " );
            if(remark){   //是否结算
                sb.append(" and t.remark is null ");
            }
	        SQLQuery query = initQuery(companyid,state,settleType,beginDate,endDate,sb);
		return query.list().size();
	}
	
	private SQLQuery initQuery(String companyid,int state,int settleType, Date beginDate, Date endDate,
			StringBuffer sb) {
		String column = "";
		int i=0;
		
		if (state == 0)
			column = "t.impdt";

		if (state == 1)
			column = "t.senddt";

		if (beginDate != null) {
			sb.append(" and  " + column + "  >= :beginDate ");
		}

		if (endDate != null) {
			sb.append(" and  " + column + "  <= :endDate ");
		}
		
		if (settleType != 0){
			sb.append(" and  t.type  = :type ");
		}
		
        if(! StringUtil.isNullOrBank(companyid)){
		   sb.append("and o.entityid=:companyid");	
		}
        
        sb.append(" order by t.impdt  desc");

        SQLQuery q = getSession().createSQLQuery(sb.toString()); 
		  if (beginDate != null) {
	            q.setTimestamp("beginDate",beginDate);
	        }
	        if (endDate != null) {
	            q.setTimestamp("endDate", endDate);
	        }	        
			if (settleType != 0){
				 q.setInteger("type",settleType);
			}
	        if(! StringUtil.isNullOrBank(companyid)){
	       	 q.setString("companyid",companyid);
	 		}
            q.addScalar("orderid", Hibernate.STRING);  
            q.addScalar("clearid", Hibernate.STRING);  
            q.addScalar("mailid", Hibernate.STRING);  
            q.addScalar("senddt", Hibernate.TIMESTAMP);  
            q.addScalar("status", Hibernate.STRING);  
            q.addScalar("reason", Hibernate.STRING);  
            q.addScalar("totalprice", Hibernate.DOUBLE);
            q.addScalar("nowmoney", Hibernate.DOUBLE);
            q.addScalar("impdt", Hibernate.TIMESTAMP);
            q.addScalar("impusr", Hibernate.STRING);
            q.addScalar("type", Hibernate.INTEGER);
            q.addScalar("remark", Hibernate.STRING);
            q.addScalar("postFee", Hibernate.DOUBLE);
            q.addScalar("company", Hibernate.STRING);
	        q.setResultTransformer(new AliasToBeanResultTransformer(EdiClearDto.class));  
	        return q;

	}
	

}
