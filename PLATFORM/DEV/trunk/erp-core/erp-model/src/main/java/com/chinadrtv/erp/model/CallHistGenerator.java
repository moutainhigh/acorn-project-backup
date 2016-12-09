package com.chinadrtv.erp.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-8-1
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
public class CallHistGenerator implements IdentifierGenerator {
    public Serializable generate(final SessionImplementor session, final Object obj) throws HibernateException{
        String sequanceId = getSequenceId((Session)session);

        return sequanceId;
    }

    public String getSequenceId(Session session){
        Query q = session.createSQLQuery("select iagent.seqcallhist.nextval from dual");
        return  q.list().get(0).toString();
    }
}
