package com.chinadrtv.erp.distribution.dao.hibernate;

import com.chinadrtv.erp.distribution.dao.AgentCallbackDao;
import com.chinadrtv.erp.model.agent.CallbackEx;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-17
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AgentCallbackDaoImpl implements AgentCallbackDao {

    private HibernateTemplate hibernateTemplate;

    public Long findCallbackCount(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select count(a.callbackid) from iagent.callback a " +
                    "where a.callbackid > 0 " +
                    //(params.containsKey("acdGroup") ? "and a.acdGroup = :acdGroup ":"") +
                    (params.containsKey("startDateString") ? "and a.crdt >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.crdt <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("priority") ? "and a.priority = :priority " : "") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : "") +
                    (params.containsKey("agentUser") ? "and a.usrId = :agentUser" : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return Long.parseLong(String.valueOf(q.uniqueResult()));
        } else {
            return 0L;
        }
    }

    public List<String> findCallbackNumbers(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select distinct a.phn2 from iagent.callback a " +
                    "where a.callbackid > 0 " +
                    //(params.containsKey("acdGroup") ? "and a.acdGroup = :acdGroup ":"") +
                    (params.containsKey("startDateString") ? "and a.crdt >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.crdt <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("priority") ? "and a.priority = :priority " : "") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : "") +
                    (params.containsKey("agentUser") ? "and a.usrId = :agentUser" : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<String>();
        }
    }

    public List<CallbackEx> findCallbackDetails(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select a.* from iagent.callback a " +
                    "where a.callbackid > 0 " +
                    //(params.containsKey("acdGroup") ? "and a.acdGroup = :acdGroup ":"") +
                    (params.containsKey("startDateString") ? "and a.crdt >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.crdt <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("priority") ? "and a.priority = :priority " : "") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : "") +
                    (params.containsKey("agentUser") ? "and a.usrId = :agentUser" : "")
                    )
                    .addEntity(CallbackEx.class);
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<CallbackEx>();
        }
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
}
