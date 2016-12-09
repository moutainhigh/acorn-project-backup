package com.chinadrtv.erp.admin.dao.hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.admin.dao.CMSPromotionDao;
import com.chinadrtv.erp.admin.model.Company;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.ChannelType;
import com.mysql.jdbc.CallableStatement;

/**
 * 
 * CMS促销服务
 * 
 * @author haoleitao
 *
 */
@Repository
public class CMSPromotionDaoImpl  implements CMSPromotionDao {

	public List getPromotion(String prodids, String moneys, String surid,
			String orderid) {
		// TODO Auto-generated method stub
		return null;
	}
	


//	public List getPromotion(String prodids, String moneys, String surid,String orderid) {
//	
//	    Connection conn = session.connection();  
//	    ResultSet rs =null;  
//	    CallableStatement call = null;
//		try {
//			call = (CallableStatement) conn.prepareCall("{Call IAGENT.p_GetSalesPromotionInfo(?,?,?,?,?)}");
//			call.setString(0, prodids);
//		    call.setString(1, moneys);
//		    call.setString(2, surid);
//		    call.setString(3, orderid);
//		    rs = call.executeQuery(); 
//		    if(rs.next()){
//			    System.out.println("存储过程得到的第一个返回值是:"+rs.getString(1));
//			    System.out.println("存储过程得到的第二个返回值是:"+rs.getString(2));
//			    } 
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	    
// 
//
//	    
//	    
//////	    SQLQuery query = sessiong.createSQLQuery("{Call IAGENT.p_GetSalesPromotionInfo(?,?,?,?,?)}");
//////	    query.setString(0, prodids);
//////	    query.setString(1, moneys);
//////	    query.setString(2, surid);
//////	    query.setString(3, orderid);
//////	    List list =query.list();  
////	    sessiong.close();  
//		return null;
//	}

}
