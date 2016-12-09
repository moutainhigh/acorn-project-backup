package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.admin.dao.PreTradeDao;
import com.chinadrtv.erp.admin.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PreTradeDaoImpl
 * 
 * @author haoleitao
 *
 */
@Repository
public class PreTradeDaoImpl extends GenericDaoHibernate<PreTrade, Long> implements PreTradeDao{

	public PreTradeDaoImpl() {
	    super(PreTrade.class);
	}
	
    public void saveOrUpdate(PreTrade preTrade){
    	getSession().saveOrUpdate(preTrade);
        
    }


	public List<PreTrade> searchPaginatedPreTradeByAppDate(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm, String id, String tradeId,
			int startIndex, Integer numPerPage) {
		StringBuilder sb = new StringBuilder();
		sb.append("from PreTrade a  where 1=1  ");
		Query q = initQuery(sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb);
		q.setFirstResult(startIndex*numPerPage);
		q.setMaxResults(numPerPage);
		return q.list();
	}
	
	public int searchPaginatedPreTradeByAppDate(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state ,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		   StringBuilder sb = new StringBuilder();

	        sb.append(" select count(a.id) from PreTrade a  where 1=1  ");
	        Query q = initQuery(sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb);
	        int count = Integer.valueOf(q.list().get(0).toString());
	        return count;
	}
	
	 private Query initQuery(Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
				Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus ,int refundStatusConfirm,String id, String tradeId, StringBuilder sb) {

            if (sourceId != 0){
                sb.append(" and  a.sourceId  = :sourceId ");
            }  
            
            if(! StringUtil.isNullOrBank(tradeFrom)){
            	sb.append( " and a.tradeFrom like :tradeFrom ");
            }
            if (alipayTradeId != null && !alipayTradeId.equals("")) {
                sb.append(" and  a.buyerNick  = :alipayTradeId ");
            }
		  
	        if (id != null && !id.equals("")) {
	            sb.append(" and  a.shipmentId  = :id ");
	        }
	        if (tradeId != null && !tradeId.equals("")) {
	            sb.append(" and  a.tradeId  = :tradeId ");
	        }
	        
	        if (max != null) {
	            sb.append(" and  a.payment  <= :max ");
	        }
	        if (min != null) {
	            sb.append(" and  a.payment  >= :min ");
	        }
	        
	        if (beginDate != null) {
	            sb.append(" and  a.outCrdt  >= :beginDate ");
	        }

	        if (endDate != null) {
	            sb.append(" and  a.outCrdt  <= :endDate ");
	        }
	        
	        if(! StringUtil.isNullOrBank(receiverMobile)) sb.append(" and a.receiverMobile = :receiverMobile ") ;
	        
	        if(buyerMessage != null ) sb.append(" and a.buyerMessage is not null ");
	        
	        if(sellerMessage != null ) sb.append("and a.sellerMessage is not null ");
	        //待审核 impStatus 为 null 或0 
	        if(state == 1 ) sb.append(" and nvl(a.impStatus,0) = 0 " );
	        //审核失败 impStatus   !=13 
	        if(state == 2 ) sb.append(" and a.impStatus != 13 and a.impStatus != 14 and a.impStatus != 17 and a.feedbackStatus != 2 and a.impStatus != 99  and nvl(a.impStatus,0) != 0  " );
	        //已审核未发货 impStatus  ==13  并且  feedbackStatus =="0" ,select t.customizestatus from orderhist  t  where val(customizestatus,0) == 0 
	        if(state == 3 ) sb.append(" and (a.impStatus = 13 or a.impStatus = 14 or a.impStatus = 17) and nvl(a.feedbackStatus,0) = 0" );
	        //已发货待反馈    impStatus  ==13 ,feedbackStatus =="0" || "4", shipmentId(通过iagentID 到)   select t.customizestatus from orderhist  t  where customizestatus == 2
	       // if(state == 4 ) sb.append(" and a.impStatus=13 and (nvl(a.feedbackStatus) = 0 or a.feedbackStatus=4 )" );
	        //标记完成
	        if(state == 4 ) sb.append(" and a.feedbackStatus = 2 )" );
	        //完成订单 impStatus  ==13,feedbackStatus =="1" 
	        //反馈失败
	        if(state == 5 ) sb.append(" and a.feedbackStatus = 4 " );
	        //前置压单 impStatus == 99
	        if(state == 6 ) sb.append(" and a.impStatus = 99 " );
	        //有反馈
	        if(refundStatus == 7 ) sb.append(" and nvl(a.refundStatus,-1) != -1 " );
	        //无反馈
	        if(refundStatus == 8 ) sb.append(" and nvl(a.refundStatus,-1) = -1 " );
	        //拒绝退款
	       // if(refundStatus == 9 ) sb.append(" and (a.refundStatus = 3  or (  a.refundStatus = 1 and a.refundStatusConfirm = 1 ))" );
	        //收有可以审核的订单 
	        if(refundStatus == 10) sb.append(" and nvl(a.refundStatus,0) != 1 and nvl(a.refundStatus,0) !=2 " );
	        
	        
	        if(refundStatusConfirm == -1) sb.append(" and nvl(a.refundStatusConfirm,-1) = -1 " );
	        if(refundStatusConfirm == 1) sb.append(" and a.refundStatusConfirm = 1 " );
	        if(refundStatusConfirm == 2) sb.append(" and a.refundStatusConfirm = 2 " );
	        sb.append(" order by a.outCrdt desc");
	        
	        Query q = getSession().createQuery(sb.toString());
	        

         if (sourceId != 0){
             q.setLong("sourceId", sourceId);
         }  
         
         if(! StringUtil.isNullOrBank(tradeFrom)){
         	q.setString("tradeFrom","%"+tradeFrom+"%");
         }
         
         if (alipayTradeId != null && !alipayTradeId.equals("")) {
             q.setString("alipayTradeId", alipayTradeId);
         }
	        
	        if (id != null && !id.equals("")) {
	            q.setString("id", id);
	        }
	        if (tradeId != null && !tradeId.equals("")) {
	            q.setString("tradeId", tradeId);
	        }
	        if (min != null) {
	            q.setDouble("min",min);
	        }
	        if (max != null) {
	            q.setDouble("max", max);
	        }
	        if (beginDate != null) {
	            q.setTimestamp("beginDate",beginDate);
	        }
	        if (endDate != null) {
	            q.setTimestamp("endDate", endDate);
	        }
	        
	        if(receiverMobile != null && !receiverMobile.equals("")){
	        	q.setString("receiverMobile", receiverMobile);
	        }
	        return q;
	    }

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.dao.PreTradeDao#savePreTrade(java.lang.String)
	 */
	public void savePreTrade(String ids) {
		 Session session = getSession();
		  String sqlString = "select a.companyid ,a.name from company a ";
		     //以SQL语句创建SQLQuery对象
		 
		
	}

	public int batchupdateConfrimState(Long[] ids,int value) {
	
		int result =0;
		 Session session = getSession();
		 String hql = " update PreTrade a set a.refundStatusConfirm =:value where a.id in(:ids)";
		 Query q= session.createQuery(hql);
		 
		 q.setInteger("value", value);
		 q.setParameterList("ids", ids);
		
		 return q.executeUpdate();
	}

	public PreTrade getByTradeId(String tradeId) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		 Session session = getSession();
		sb.append("from PreTrade a  where   a.tradeId  = :tradeId  ");
		 Query q= session.createQuery(sb.toString());
		 q.setString("tradeId", tradeId);
		return (PreTrade) q.list().get(0);
	}
	
	



}
