package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.JifenReviseDao;
import com.chinadrtv.erp.tc.core.model.Integarl;

@Deprecated
@Repository("jifenReviseDao")
public class JifenReviseDaoImpl extends GenericDaoHibernateBase<OrderDetail, String> implements JifenReviseDao {
	
	@Autowired
	private SchemaNames schemaNames;

	public JifenReviseDaoImpl() {
		super(OrderDetail.class);
	}
	
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /**
     * 校正积分的存储过程
     */
    @Deprecated
    public void getJifenproc(Integarl integarl)
            throws SQLException
    {
        Connection conn = getSession().connection();
        
        CallableStatement proc = conn.prepareCall("{call "+ schemaNames.getAgentSchema()+"p_n_conpointfeedback(?,?)}");
        proc.setString(1, integarl.getStype());
        proc.setString(2, integarl.getScrusr());
        proc.execute();
        try {
            if (conn != null)
                conn.close();
        }
        catch (Exception ex) {
          //  System.out.print(ex.getMessage());
        }
    }
    
    @Deprecated
    public void callProcedure(final String sql, final List<ParameterString> parmList)
    {
    	this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery= session.createSQLQuery(sql);
                for(ParameterString parm:parmList)
                {
                    sqlQuery.setParameter(parm.getName(),parm.getValue());
                }
                sqlQuery.executeUpdate();
                return null;
            }
       });
    }
    
}