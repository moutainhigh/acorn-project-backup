package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.admin.dao.PlubasInfoDao;
import com.chinadrtv.erp.model.inventory.PlubasInfo;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleCallableStatement;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * 产品信息表
 * 
 * @author haoleitao
 * @date 2013-3-25 下午5:23:04
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class PlubasInfoDaoImpl extends
		GenericDaoHibernateBase<PlubasInfo, Long> implements PlubasInfoDao {

	public PlubasInfoDaoImpl() {
		super(PlubasInfo.class);
	}

	public PlubasInfo getPlubasInfo(String plucode) {
		Session session = getSession();
		Query q = session
				.createQuery("from PlubasInfo a where a.plucode = :plucode");
		q.setString("plucode", plucode);
		List result = q.list();
		return result.size() > 0 ? (PlubasInfo) result.get(0) : null;
	}

	public PlubasInfo getPlubasInfoByplid(Long ruid) {
		Session session = getSession();
		Query q = session.createQuery("from PlubasInfo a where a.ruid = :ruid");
		q.setLong("ruid", ruid);
		List result = q.list();
		return result.size() > 0 ? (PlubasInfo) result.get(0) : null;

	}
	


	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

	public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size ) {

		StringBuffer sb = new StringBuffer();
		sb.append(" from PlubasInfo a where 1=1 ");
		Query q = initQuery(catcode,pluname,sb);
		q.setFirstResult(index * size);
		q.setMaxResults(size);
		List<PlubasInfo> list = null;
		try{
		list= q.list();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}


	public int getAllPlubasInfosCount(String catcode,String pluname) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select count(a.ruid) from PlubasInfo a where 1=1 ");
		Query q= initQuery(catcode,pluname,sb);
		List list = null;
		try{
			list = q.list();
		}catch(Exception e){
		    log.error(e.getMessage());
		}
		return Integer.valueOf(list.get(0).toString());
	}

    private Query initQuery(String catcode,String pluname,StringBuffer sb){
    	if(StringUtils.isNotBlank(catcode)){
    		sb.append(" and a.plucode like :catcode");
    	}
    	if(StringUtils.isNotBlank(pluname)){
    		sb.append(" and a.pluname like :pluname");
    	}
    	
    	Query q = getSession().createQuery(sb.toString());
    	
    	if(StringUtils.isNotBlank(catcode)){
    		q.setString("catcode", "%"+catcode+"%");
    	}
    	if(StringUtils.isNotBlank(pluname)){
    		q.setString("pluname", "%"+pluname+"%");
    	}
    	return q;
    	
    }


    
    public List  getCmsPromotion(String prodids,String moneys,String surid,String orderid){
    	Session session = getSession();
    	ResultSet rs = null; 
    	try {
    		
    		 
            
    		Connection conn = session.connection();
              
      
                CallableStatement cs = conn.prepareCall("{p_getsalespromotioninfo(?,?,?,?,?)}");  
                cs.registerOutParameter(5, OracleTypes.CURSOR);  
                cs.setString(1, prodids);
                cs.setString(2, moneys);
                cs.setString(3, surid);
                cs.setString(4, orderid);
    
                  
                cs.execute();  
                  
               rs = ((OracleCallableStatement)cs).getCursor(5);  
                
                while(rs.next())  
                {  
                    System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+ rs.getDate(4)+"  "+rs.getString(5));  
                }  
                  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } catch (Exception e){  
                e.printStackTrace();  
            }   
              
            return null;
//    		state = conn.prepareCall("{Call IAGENT.p_GetSalesPromotionInfo(?,?,?,?,?)}");
//    		state.setString(0, prodids);
//    		state.setString(1, moneys);
//    		state.setString(2, surid);
//    		state.setString(3, orderid);
//    		
//    		state.registerOutParameter(4, Types.);
//    		state.execute();
//    		int num = state.getInt(1);
//    		System.out.println(num);
    
//    SQLQuery query = session .createSQLQuery("{? = Call IAGENT.p_GetSalesPromotionInfo(:a,:b,:c,:d)}");
//    query.setString("a", prodids);
//    query.setString("b", moneys);
//    query.setString("c", surid);
//    query.setString("d", orderid);
//      System.out.println("111111111111111111111111111");
//    List list =query.list();  
//    return list;
   
    }

    

}
