package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.CouponReviseDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;

@Repository("CouponReviseDao")
public class CouponReviseDaoImpl extends GenericDaoHibernateBase<ConTicket, String>
        implements CouponReviseDao
{
    @Autowired
    private SchemaNames schemaNames;

    public CouponReviseDaoImpl()
    {
        super(ConTicket.class);
    }

    public void getCouponReviseproc(ConTicket conTicket)
            throws SQLException
    {
        //Connection conn = conn = getSession().connection();
    	Connection conn = getSession().connection();
        CallableStatement proc = conn.prepareCall("{call "+ schemaNames.getAgentSchema()+"P_N_ConTicketAdjust(?,?,?)}");
        proc.setString(1, conTicket.getSorderid());
        proc.setString(2,conTicket.getScontactid());
        proc.setString(3,conTicket.getScrusr());
        proc.execute();
        if (conn != null)
                conn.close();
       
    }
    
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}