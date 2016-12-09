package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.OrderIntegarlDao;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-1-29
 * Time: 下午1:08
 * 积分服务 执行存储过程
 */
@Repository("orderIntegarlDao")
@Deprecated
public class OrderIntegarlDaoImpl extends GenericDaoHibernateBase<Order, String> implements OrderIntegarlDao{

    @Autowired
    private SchemaNames schemaNames;

    public OrderIntegarlDaoImpl() {
        super(Order.class);
    }

    /**
     * 执行存储过程
     * @param integarl
     * @return
     * @throws SQLException 
     * @throws NumberFormatException 
     */
    @SuppressWarnings("deprecation")
	public String getIntegarlproc(Map<String, Object> mapval) throws SQLException {
            Connection conn=null;
            String getstr="";
            conn =getSession().connection();
            CallableStatement proc =conn.prepareCall("{call "+ schemaNames.getAgentSchema()+"P_N_USECONPOINT(?,?,?,?,?,?)}");
            proc.setString(1,mapval.get("stype").toString());
            proc.setString(2,mapval.get("sorderid").toString());
            proc.setString(3,mapval.get("scontactid").toString());
            proc.setDouble(4,Double.valueOf(mapval.get("npoint").toString()));
            proc.setString(5,mapval.get("scrusr").toString());
            proc.registerOutParameter(6, Types.NVARCHAR);
            proc.execute();
            if(proc.getObject(6)!=null){
                getstr=proc.getString(6);
               }
                if(conn!=null){
                   conn.close();
                }
            return getstr;
    }
    
    @Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

}
