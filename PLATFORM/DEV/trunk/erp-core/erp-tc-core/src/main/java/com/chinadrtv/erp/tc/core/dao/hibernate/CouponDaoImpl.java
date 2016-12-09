package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.CouponDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;

@Repository
@Deprecated
public class CouponDaoImpl extends GenericDaoHibernateBase<ConTicket, String>
        implements CouponDao
{
    @Autowired
    private SchemaNames schemaNames;

    public CouponDaoImpl()
    {
        super(ConTicket.class);
    }

    public String getCouponproc(ConTicket conTicket)
            throws SQLException
    {
        String str="";
        try {

        Connection conn = getSession().connection();
        CallableStatement proc = conn.prepareCall("{call "+schemaNames.getAgentSchema()+"P_N_ConTicket(?,?,?,?,?,?,?)}");
        proc.setString(1, conTicket.getStype());
        proc.setDouble(2,conTicket.getNcurprodprice());
        proc.setDouble(3,conTicket.getCurticketprice());
        proc.setString(4,conTicket.getSorderid());
        proc.setString(5,conTicket.getScontactid());
        proc.setString(6,conTicket.getScrusr());
        proc.registerOutParameter(7, Types.NVARCHAR);
        proc.execute();
            if(proc.getObject(7)!=null){
                str=proc.getString(7);
            }
        if (conn != null)
                conn.close();
            return str;
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            return "";
        }
    }
    
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}