package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.oms.dao.PreTradeDao;
import com.chinadrtv.erp.oms.util.StringUtil;
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
 *  
 *  前置订单D
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:31:39
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class PreTradeDaoImpl extends GenericDaoHibernate<PreTrade, Long> implements PreTradeDao{

	public PreTradeDaoImpl() {
	    super(PreTrade.class);
	}
	
    public void saveOrUpdate(PreTrade preTrade){
    	try{
    		getSession().saveOrUpdate(preTrade);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
        
    }


	public List<PreTrade> searchPaginatedPreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm, String id, String tradeId,
			int startIndex, Integer numPerPage) {
		StringBuilder sb = new StringBuilder();
		sb.append("from PreTrade a  where 1=1  ");
		Query q = initQuery(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb,"outCrdt");
		q.setFirstResult(startIndex*numPerPage);
		q.setMaxResults(numPerPage);
		return q.list();
	}
	
	public int searchPaginatedPreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state ,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		   StringBuilder sb = new StringBuilder();

	        sb.append(" select count(a.id) from PreTrade a  where 1=1  ");
	        Query q = initQuery(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb,null);
	        int count = Integer.valueOf(q.list().get(0).toString());
	        return count;
	}
	
	//获取满足合单条件的数据
	public List<String> getCombinePreTradeId(PreTrade preTrade,Long sourceId){
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct  a.tradeId from PreTrade a ,PreTrade b where nvl(a.receiverName,'0')=nvl(b.receiverName,'0')");
		sb.append(" and nvl(a.receiverArea,'0') = nvl(b.receiverArea,'0')");
		sb.append(" and nvl(a.receiverCounty,'0') = nvl(b.receiverCounty,'0')");
		sb.append(" and nvl(a.receiverAddress,'0') = nvl(b.receiverAddress,'0')");
		sb.append(" and nvl(a.receiverMobile,'0') = nvl(b.receiverMobile,'0')");
		sb.append(" and a.id != b.id ");
		sb.append(" and a.sourceId =:sourceId");
		sb.append(" and b.sourceId =:sourceId");
		sb.append(" and a.crdt > sysdate-1 ");
		sb.append(" and b.crdt > sysdate-1");
        sb.append(" and nvl(a.splitFlag,'0') != '-1'");
        sb.append(" and nvl(b.splitFlag,'0') != '-1'");

		//sb.append(" and nvl(TO_NUMBER(a.impStatus),0) = 0 ");
		//sb.append("and TO_NUMBER(a.impStatus) != 13 and TO_NUMBER(a.impStatus) != 14 and TO_NUMBER(a.impStatus) != 17 and nvl(TO_NUMBER(a.feedbackStatus),0) != 2 and TO_NUMBER(a.impStatus) != 99 ");
		Query q = getSession().createQuery(sb.toString());
		q.setLong("sourceId", sourceId);
		List<String> list =q.list();
		if(list.contains(preTrade.getTradeId())){
			sb.append("and a.receiverName =:receiverName");
			q = getSession().createQuery(sb.toString());
			q.setString("receiverName",preTrade.getReceiverName());
			q.setLong("sourceId", sourceId);
		}else{
			return null;
		}
		return q.list();
//	 StringBuffer sb = new StringBuffer("select a.* from ACOAPP_OMS.PRE_TRADE a join (");
//	 sb.append(" select RECEIVER_NAME,RECEIVER_AREA,RECEIVER_COUNTY,RECEIVER_ADDRESS,RECEIVER_MOBILE from ACOAPP_OMS.PRE_TRADE ");
//	 sb.append(" where  nvl(TO_NUMBER(IMP_STATUS), 0)=0");
//	 sb.append(" and crdt > sysdate-2 ");
//	 sb.append(" and SOURCE_ID=2");
//	 sb.append(" group by RECEIVER_NAME,RECEIVER_AREA,RECEIVER_COUNTY,RECEIVER_ADDRESS,RECEIVER_MOBILE");
//	 sb.append(" having count(1) > 1");
//	 sb.append(" ) b on nvl(a.RECEIVER_NAME,'0')=nvl(b.RECEIVER_NAME,'0') ");
//	 sb.append(" and nvl(a.RECEIVER_AREA,'0')=nvl(b.RECEIVER_AREA,'0') ");
//	 sb.append(" and nvl(a.RECEIVER_COUNTY,'0')=nvl(b.RECEIVER_COUNTY,'0') ");
//	 sb.append(" and nvl(a.RECEIVER_ADDRESS,'0')=nvl(b.RECEIVER_ADDRESS,'0') ");
//	 sb.append(" and nvl(a.RECEIVER_MOBILE,'0')=nvl(b.RECEIVER_MOBILE,'0') ");
//	 sb.append(" and nvl(TO_NUMBER(a.IMP_STATUS), 0)=0");
//	 sb.append(" and a.crdt > sysdate-2 ");
//	 sb.append(" and a.SOURCE_ID=2");
//		return 	getSession().createSQLQuery(sb.toString());
	}
	
	
	public List<PreTrade> searchPaginatedIsCombinePreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm, String id, String tradeId,
			int startIndex, Integer numPerPage) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct a from PreTrade a ,PreTrade b where nvl(a.receiverName,'0') =nvl(b.receiverName,'0') ");
		sb.append(" and nvl(a.receiverArea,'0')  = nvl(b.receiverArea,'0') ");
		sb.append(" and nvl(a.receiverCounty,'0')  = nvl(b.receiverCounty,'0') ");
		sb.append(" and nvl(a.receiverAddress,'0')  = nvl(b.receiverAddress,'0') ");
		sb.append(" and nvl(a.receiverMobile,'0')  = nvl(b.receiverMobile,'0') ");
		sb.append(" and a.id != b.id ");
		sb.append(" and a.crdt > sysdate-1 ");
		sb.append(" and b.crdt > sysdate-1 ");
        sb.append(" and nvl(a.splitFlag,'0') != '-1'");
        sb.append(" and nvl(b.splitFlag,'0') != '-1'");
		Query q = initQuery(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb,"receiverName,a.outCrdt ");
		
		q.setFirstResult(startIndex*numPerPage);
		q.setMaxResults(numPerPage);
		return q.list();
	}
	
	public int searchPaginatedIsCombinePreTradeByAppDate(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state ,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		   StringBuilder sb = new StringBuilder();

	        sb.append(" select count(distinct a.id)  from PreTrade a , PreTrade b where nvl(a.receiverName,'0') = nvl(b.receiverName,'0') ");
	        sb.append(" and nvl(a.receiverArea,'0')  = nvl(b.receiverArea,'0') ");
	        sb.append(" and nvl(a.receiverCounty,'0')  = nvl(b.receiverCounty,'0') ");
	        sb.append(" and nvl(a.receiverAddress,'0')  = nvl(b.receiverAddress,'0') ");
	        sb.append(" and nvl(a.receiverMobile,'0')  = nvl(b.receiverMobile,'0') ");
	        sb.append(" and a.id != b.id");
	        sb.append(" and a.crdt > sysdate-1 ");
	        sb.append(" and b.crdt > sysdate-1 ");
            sb.append(" and nvl(a.splitFlag,'0') != '-1'");
            sb.append(" and nvl(b.splitFlag,'0') != '-1'");
	       
	        Query q = initQuery(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate, min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,sb,null);
	        int count = Integer.valueOf(q.list().get(0).toString());
	        return count;
	}
	
	 private Query initQuery(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
				Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus ,int refundStatusConfirm,String id, String tradeId, StringBuilder sb,String orderbyName) {

		 
		   if(! StringUtil.isNullOrBank(treadType)){
           	sb.append( " and a.tradeType = :treadType ");
           }
		 
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
	        if(state == 1 ) sb.append(" and nvl(TO_NUMBER(a.impStatus),0) = 0 " );
	        //审核失败 impStatus   !=13 
	        if(state == 2 ) sb.append(" and TO_NUMBER(nvl(a.impStatus,0)) != 13 and TO_NUMBER(nvl(a.impStatus,0)) != 14 and TO_NUMBER(nvl(a.impStatus,0)) != 17 and nvl(TO_NUMBER(a.feedbackStatus),0) != 2 and TO_NUMBER(nvl(a.impStatus,0)) != 99  and nvl(TO_NUMBER(a.impStatus),0) != 0  " );
	        //已审核未发货 impStatus  ==13  并且  feedbackStatus =="0" ,select t.customizestatus from orderhist  t  where val(customizestatus,0) == 0 
	        if(state == 3 ) sb.append(" and (TO_NUMBER(nvl(a.impStatus,0)) = 13 or TO_NUMBER(nvl(a.impStatus,0)) = 14 or TO_NUMBER(nvl(a.impStatus,0)) = 17) and nvl(TO_NUMBER(a.feedbackStatus),0) = 0" );
	        //已发货待反馈    impStatus  ==13 ,feedbackStatus =="0" || "4", shipmentId(通过iagentID 到)   select t.customizestatus from orderhist  t  where customizestatus == 2
	        // if(state == 4 ) sb.append(" and nvl(a.impStatus,0)=13 and (nvl(a.feedbackStatus) = 0 or a.feedbackStatus=4 )" );
	        //标记完成
	        if(state == 4 ) sb.append(" and TO_NUMBER(nvl(a.feedbackStatus,0)) = 2 )" );
	        //完成订单 impStatus  ==13,feedbackStatus =="1" 
	        //反馈失败
	        if(state == 5 ) sb.append(" and TO_NUMBER(nvl(a.feedbackStatus,0)) = 4 " );
	        //前置压单 impStatus == 99
	        if(state == 6 ) sb.append(" and TO_NUMBER(nvl(a.impStatus,0)) = 99 " );
	        //有反馈
	        if(refundStatus == 7 ) sb.append(" and nvl(TO_NUMBER(a.refundStatus),-1) != -1 " );
	        //无反馈
	        if(refundStatus == 8 ) sb.append(" and nvl(TO_NUMBER(a.refundStatus),-1) = -1 " );
            //新增排除拆分的订单,父订单不参与审核
            sb.append( " and nvl(a.splitFlag,0) < 2 ");
	        //拒绝退款
	       // if(refundStatus == 9 ) sb.append(" and (a.refundStatus = 3  or (  a.refundStatus = 1 and a.refundStatusConfirm = 1 ))" );
	        //收有可以审核的订单 
	        if(state == 10) sb.append(" and TO_NUMBER(nvl(a.impStatus,0)) != 13 and TO_NUMBER(nvl(a.impStatus,0)) != 14 and TO_NUMBER(nvl(a.impStatus,0)) != 17  and TO_NUMBER(nvl(a.impStatus,0)) != 2 and nvl(TO_NUMBER(a.feedbackStatus),0) != 2 and TO_NUMBER(nvl(a.impStatus,0)) != 99 and a.crdt > sysdate-30  " );
	        
	        
	        if(refundStatusConfirm == -1) sb.append(" and nvl(TO_NUMBER(a.refundStatusConfirm),-1) = -1 " );
	        if(refundStatusConfirm == 1) sb.append(" and TO_NUMBER(nvl(a.refundStatusConfirm,0)) = 1 " );
	        if(refundStatusConfirm == 2) sb.append(" and TO_NUMBER(nvl(a.refundStatusConfirm,0)) = 2 " );
	        sb.append(" and nvl(a.tmsCode,'-1') != '999'");
	        if(orderbyName != null){
	        sb.append(" order by a."+orderbyName+" desc");
	        }
	        Query q = getSession().createQuery(sb.toString());
	        
	     if(! StringUtil.isNullOrBank(treadType)){
	    	 q.setString("treadType",treadType);
		 }
	        
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
		 String hql = "update PreTrade a set a.refundStatusConfirm =:value where a.id in(:ids)";
		
		 if(value == 2){//取消发货
			  hql = "update PreTrade a set a.refundStatusConfirm =:value , a.impStatus =:impStatus  where a.id in(:ids)";
		 }
		 Query q= session.createQuery(hql);
		 if(value == 2) {
			 q.setString("impStatus", "100");//废弃订单
		 }
		 
		 q.setInteger("value", value);
		 q.setParameterList("ids", ids);
		
		 return q.executeUpdate();
	}
	
	



}
