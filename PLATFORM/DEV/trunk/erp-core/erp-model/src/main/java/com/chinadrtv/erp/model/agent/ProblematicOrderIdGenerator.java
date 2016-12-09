package com.chinadrtv.erp.model.agent;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 联系人ID生成器
 * User: Administrator
 * Date: 13-5-21
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
public class ProblematicOrderIdGenerator implements IdentifierGenerator
{
    public Serializable generate(final SessionImplementor session, final Object obj) throws HibernateException
    {
        String sequanceId = getSequenceId((Session)session);
//        sequanceId = getNewId(sequanceId);
        return sequanceId;
    }

    public String getSequenceId(Session session){
        Query q = session.createSQLQuery("select iagent.seqproblematicorder.nextval from dual");
        return  q.list().get(0).toString();
    }

/*    private String getNewId(String sequanceId){
        int[] index = new int[] { 5, 4, 6, 3, 7, 1, 8, 2 };
        int len = sequanceId.length() - index.length;
        if(len < 0) len = 0;
        String ret = sequanceId.substring(0, len);

        for(int i = 0; i< index.length; i++) {
            ret += sequanceId.substring(index[i] + len - 1, index[i] + len);
        }

        if(sequanceId.length() == ret.length()){
            return  ret;
        }
        return sequanceId;
    }*/
}
