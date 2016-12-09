package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackLogSpecification;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class CallbackLogDaoImpl extends GenericDaoHibernateBase<CallbackLog,Long> implements CallbackLogDao {
    public CallbackLogDaoImpl()
    {
        super(CallbackLog.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public Long findCallbackLogCount(CallbackLogSpecification specification){
        /**
         * TODO:ACD组过滤都没有实现
         */
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "select count(a.id) from CallbackLog a where a.id > 0 " +
                            (params.containsKey("callType") ? "and a.type = :callType " : "") +
                            (params.containsKey("startDate") ? "and a.crdt >= :startDate " : "") +
                            (params.containsKey("endDate") ? "and a.crdt <= :endDate " : "") +
                            (params.containsKey("acdGroups") ? "and exists(from Callback b where b.id=a.callbackId and b.acdGroup in (:acdGroups)) " : "") +
                            (params.containsKey("dnis") ? "and a.dnis = :dnis " : "") +
                            (params.containsKey("agentUser") ? "and (a.firstusrId=:agentUser or a.rdbusrId=:agentUser) " : "") +
                            (params.containsKey("allocatedNumber") ? "and exists(from Callback b where b.id=a.callbackId and b.allocateCount <= :allocatedNumber) " : "") +
                            (params.containsKey("priority") ? "and a.priority = :priority " : "") +
                            (params.containsKey("callDuration") ?
                                    ("a".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                            "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength <= 20) " :
                                            ("b".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                                    "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength > 20 and b.timeLength <=180) " :
                                                    ("c".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                                            "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength > 180) " : ""))) : "") +
                            (params.containsKey("batchId") ? "and a.batchId = :batchId " : "")
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    Object parameterValue = params.get(parameterName);
                    if(parameterValue instanceof Collection){
                        q.setParameterList(parameterName, (Collection)parameterValue);
                    } else {
                        q.setParameter(parameterName, parameterValue);
                    }
                }
            }
            return (Long)q.uniqueResult();
        } else {
            return 0L;
        }
    }

    public List<CallbackLog> findCallbackLogs(CallbackLogSpecification specification, Integer index, Integer size) {
        /**
         * TODO:ACD组过滤都没有实现
         */
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "from CallbackLog a where a.id > 0 " +
                    (params.containsKey("callType") ? "and a.type = :callType " : "") +
                    (params.containsKey("startDate") ? "and a.crdt >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and a.crdt <= :endDate " : "") +
                    (params.containsKey("acdGroups") ? "and exists(from Callback b where b.id=a.callbackId and b.acdGroup in (:acdGroups)) " : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis " : "") +
                    (params.containsKey("agentUser") ? "and (a.firstusrId=:agentUser or a.rdbusrId=:agentUser) " : "") +
                    (params.containsKey("allocatedNumber") ? "and exists(from Callback b where b.id=a.callbackId and b.allocateCount <= :allocatedNumber) " : "") +
                    (params.containsKey("priority") ? "and a.priority = :priority " : "") +
                    (params.containsKey("callDuration") ?
                            ("a".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                    "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength <= 20) " :
                                    ("b".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                            "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength > 20 and b.timeLength <=180) " :
                                            ("c".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                                    "and exists(from LeadInteraction b where b.id=a.leadInteractionId and b.timeLength > 180) " : ""))) : "") +
                    (params.containsKey("batchId") ? "and a.batchId = :batchId " : "")
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    Object parameterValue = params.get(parameterName);
                    if(parameterValue instanceof Collection){
                        q.setParameterList(parameterName, (Collection)parameterValue);
                    } else {
                        q.setParameter(parameterName, parameterValue);
                    }
                }
            }
            q.setFirstResult(index);
            q.setMaxResults(size);

            return q.list();
        } else {
            return new ArrayList<CallbackLog>();
        }
    }
}
