package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.uc.dao.WilcomCallDetailDao;
import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 14-1-7
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class WilcomCallDetailDaoImpl implements WilcomCallDetailDao {

    private HibernateTemplate hibernateTemplate;

    protected void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public Long findIvrCount(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select count(distinct a.ani)" +
                    "from (" +
                    "  select ucid, starttime, ani, dnis, calltype from wgs_maincdr" +
                    "  union all " +
                    "  select ucid, starttime, ani, dnis, calltype from wgs_maincdr_bak" +
                    ") a " +
                    "left join (" +
                    "  select t.ucid, t.acddevice from wgs_acdphase t" +
                    ") b on a.ucid = b.ucid " +
                    "where a.calltype in (0, 1) " +
                    (params.containsKey("acdGroups") ? "and b.acddevice in (:acdGroups) ":"") +
                    (params.containsKey("startDateString") ? "and a.starttime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.starttime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : ""));

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
            return Long.parseLong(String.valueOf(q.uniqueResult()));
        } else {
            return 0L;
        }
    }

    public List<String> findIvrNumbers(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select distinct a.ani " +
                    "from (" +
                    "  select ucid, starttime, ani, dnis, calltype from wgs_maincdr" +
                    "  union all " +
                    "  select ucid, starttime, ani, dnis, calltype from wgs_maincdr_bak" +
                    ") a " +
                    "left join (" +
                    "  select t.ucid, t.acddevice from wgs_acdphase t" +
                    ") b on a.ucid = b.ucid " +
                    "where a.calltype in (0, 1) " + //calltype = 2 为内部电话，不算IVR
                    (params.containsKey("acdGroups") ? "and b.acddevice in (:acdGroups) ":"") +
                    (params.containsKey("startDateString") ? "and a.starttime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.starttime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : ""));

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
            return q.list();
        } else {
            return new ArrayList<String>();
        }
    }

    public List<WilcomCallDetail> findIvrDetails(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select a.id," +
                            "     0 as isselect," +
                            "     a.ucid as caseid," +
                            "     a.starttime as createtime," +
                            "     a.endtime," +
                            "     a.ani as ani," +
                            "     a.dnis as dnis," +
                            "     (select t.acddevice from wgs_acdphase t " +
                            "      where a.ucid = t.ucid and t.acddevice in (:acdGroups) and rownum < 2) as callgrp," +
                            "     '未分配' as assign," +
                            "     '' as calllen," +
                            "     '' as calltype," +
                            "     '' as usrid," +
                            "     '' as mediaproduct " +
                            "from (" +
                            "  select id,ucid, starttime, endtime, ani, dnis, calltype from wgs_maincdr" +
                            "  union all " +
                            "  select id,ucid, starttime, endtime, ani, dnis, calltype from wgs_maincdr_bak" +
                            ") a " +
                            "where a.calltype in (0, 1) " + //calltype = 2 为内部电话，不算IVR
                            (params.containsKey("acdGroups") ? "and exists(select b.id from wgs_acdphase b where a.ucid = b.ucid and b.acddevice in (:acdGroups)) " : "") +
                            (params.containsKey("startDateString") ? "and a.starttime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("endDateString") ? "and a.starttime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("callId") ? "and a.ucid = :callId " : "") +
                            (params.containsKey("ani") ? "and a.ani = :ani " : "") +
                            (params.containsKey("dnis") ? "and a.dnis = :dnis " : ""))

                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("isselect", LongType.INSTANCE)
                    .addScalar("caseid", StringType.INSTANCE)
                    .addScalar("createtime", TimestampType.INSTANCE) //DateType.INSTANCE oracle 9i 回去去掉时分秒
                    .addScalar("endtime", TimestampType.INSTANCE)
                    .addScalar("ani", StringType.INSTANCE)
                    .addScalar("dnis", StringType.INSTANCE)
                    .addScalar("callgrp", StringType.INSTANCE)
                    .addScalar("assign", StringType.INSTANCE)
                    .addScalar("calllen", StringType.INSTANCE)
                    .addScalar("calltype", StringType.INSTANCE)
                    .addScalar("usrid", StringType.INSTANCE)
                    .addScalar("mediaproduct", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(WilcomCallDetail.class));

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    Object parameterValue = params.get(parameterName);
                    if(parameterValue instanceof Collection) {
                        q.setParameterList(parameterName, (Collection)parameterValue);
                    } else {
                        q.setParameter(parameterName, parameterValue);
                    }
                }
            }
            return q.list();
        } else {
            return new ArrayList<WilcomCallDetail>();
        }
    }

    public Long findAbandonCount(CallbackSpecification specification) {
        return 0L;
    }

    public List<String> findAbandonNumbers(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select distinct a.ani " +
                    "from (" +
                    "   select caseid, createtime, ani, dnis, scallgrp, agentcallresult, agentcalltype, agentdropspan as calllen, agent from v_t_callhist_ivr a where 1=1 " +
                            (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("endDateString") ? "and a.createtime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                    ") a " +
                    "where a.agentcalltype = 'IN' and a.agentcallresult = 'ABDN' "+
                    (params.containsKey("acdGroups") ? "and a.scallgrp in (:acdGroups) ":"") +
                    (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                    (params.containsKey("endDateString") ? "and a.createtime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                    (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                    (params.containsKey("dnis") ? "and a.dnis = :dnis" : ""));

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
            return q.list();
        }
        else
        {
            return new ArrayList<String>();
        }
    }

    public List<WilcomCallDetail> findAbandonDetails(CallbackSpecification specification) {


        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select min(isselect) as isselect," +
                    "       caseid," +
                    "       min(createtime) as createtime," +
                    "       min(ani) as ani," +
                    "       min(dnis) as dnis," +
                    "       min(callgrp) as callgrp," +
                    "       min(assign) as assign," +
                    "       min(calllen) as calllen," +
                    "       min(calltype) as calltype," +
                    "       min(usrid) as usrid," +
                    "       '' as mediaproduct " +
                    " from(" +
                    "  select distinct 0 as isselect," +
                    "       a.caseid," +
                    "       a.createtime," +
                    "       a.ani," +
                    "       a.dnis," +
                    "       a.scallgrp as callgrp," +
                    "       '未分配' as assign," +
                    "       to_char(a.calllen) as calllen," +
                    "       to_char(a.agentcalltype) as calltype," +
                    "       to_char(a.agent) as usrid " +
                    "  from (" +
                    "   select caseid, createtime, ani, dnis, scallgrp, agentcallresult, agentcalltype, agentdropspan as calllen, agent from v_t_callhist_ivr a where 1=1 " +
                        (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                        (params.containsKey("endDateString") ? "and a.createtime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                        ") a" +
                        "  where a.agentcalltype = 'IN' and a.agentcallresult = 'ABDN' "+
                        (params.containsKey("acdGroups") ? "and a.scallgrp in (:acdGroups) ":"") +
                        (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                        (params.containsKey("endDateString") ? "and a.createtime < to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') ":"") +
                        (params.containsKey("ani") ? "and a.ani = :ani" : "") +
                        (params.containsKey("dnis") ? "and a.dnis = :dnis" : "")+
                    ") group by caseid")
                    .addScalar("isselect", LongType.INSTANCE)
                    .addScalar("caseid", StringType.INSTANCE)
                    .addScalar("createtime", StandardBasicTypes.TIMESTAMP)
                    .addScalar("ani", StringType.INSTANCE)
                    .addScalar("dnis", StringType.INSTANCE)
                    .addScalar("callgrp", StringType.INSTANCE)
                    .addScalar("assign", StringType.INSTANCE)
                    .addScalar("calllen", StringType.INSTANCE)
                    .addScalar("calltype", StringType.INSTANCE)
                    .addScalar("usrid", StringType.INSTANCE)
                    .addScalar("mediaproduct", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(WilcomCallDetail.class));

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
            return q.list();
        } else {
            return new ArrayList<WilcomCallDetail>();
        }
    }


}
