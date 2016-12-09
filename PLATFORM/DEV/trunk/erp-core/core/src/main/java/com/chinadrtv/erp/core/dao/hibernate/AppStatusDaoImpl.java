package com.chinadrtv.erp.core.dao.hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.AppStatusDao;
import com.chinadrtv.erp.util.AppValidationResource;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AppStatusDaoImpl extends GenericDaoHibernateBase<Object, Long> implements AppStatusDao{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AppStatusDaoImpl.class);
    public AppStatusDaoImpl() {
        super(Object.class);
    }

    
    
  

	@Override
	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}



	/**
	* <p>Title: 测试连接是否正常</p>
	* <p>Description: </p>
	* @return
	 * @throws SQLException 
	* @see com.chinadrtv.erp.core.dao.AppStatusDao#testConStatus()
	*/ 
	public boolean testConStatus(Connection con) throws SQLException {
	
		int result = -1;
		Statement st = null;
		ResultSet rs = null;
		try{
			st = con.createStatement();
			rs = st.executeQuery(AppValidationResource.validationQuery);
			
			if(rs.next()){
				result = rs.getInt(1);
				return true;
			}
			
		}catch (Exception e) {
			logger.error("",e);
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(st!=null){
				st.close();
			}
		}
		return false;
	}
}
