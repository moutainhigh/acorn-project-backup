package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.uc.dao.IvrcallbackDao;
import com.chinadrtv.erp.uc.dto.IvrGrpNumCti;
import com.chinadrtv.erp.uc.dto.IvrDeptRate;
import com.chinadrtv.erp.uc.dto.IvrUser;
import com.chinadrtv.erp.uc.dto.Ivrdist;
import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.user.model.AgentUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.DoubleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: gaodj
 * Date: 14-1-8
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class IvrcallbackDaoImpl implements IvrcallbackDao {

    private HibernateTemplate hibernateTemplate;

    public List<IvrGrpNumCti> getIvrGrpNumCtis(String deptId){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.ivrgrpnum_cti a where a.deptId=:deptId ")
                         .addScalar("deptId", StringType.INSTANCE)
                         .addScalar("acdId", StringType.INSTANCE)
                         .addScalar("areaCode", StringType.INSTANCE)
                         .addScalar("showAllPhone", StringType.INSTANCE)
                         .setResultTransformer(Transformers.aliasToBean(IvrGrpNumCti.class));
        q.setParameter("deptId", deptId);
        return q.list();
    }

    public List<IvrGrpNumCti> getIvrGrpNumAllCtis(){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.ivrgrpnum_cti a where a.deptid in ('23', '24', '25', '27') ")
                         .addScalar("deptId", StringType.INSTANCE)
                         .addScalar("acdId", StringType.INSTANCE)
                         .addScalar("areaCode", StringType.INSTANCE)
                         .addScalar("showAllPhone", StringType.INSTANCE)
                         .setResultTransformer(Transformers.aliasToBean(IvrGrpNumCti.class));
        return q.list();
    }

    public List<IvrDeptRate> getIvrDeptRates(String ivrType){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if (StringUtils.isBlank(ivrType)) {
            Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.deptrate a where a.ivrType is null")
                    .addScalar("deptid", StringType.INSTANCE)
                    .addScalar("ivrtype", StringType.INSTANCE)
                    .addScalar("rate",  DoubleType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(IvrDeptRate.class));
            return q.list();
        } else {
            Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.deptrate a where a.ivrType=:ivrtype")
                    .addScalar("deptid", StringType.INSTANCE)
                    .addScalar("ivrtype", StringType.INSTANCE)
                    .addScalar("rate", DoubleType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(IvrDeptRate.class));
            q.setParameter("ivrtype", ivrType);
            return q.list();
        }
    }

    public IvrUser getIvrUser(String userId){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.ivruser a where a.userid= :userId and a.showtype_type in('1','3') and rownum < 2")
                .addScalar("userid", StringType.INSTANCE)
                .addScalar("deptid", StringType.INSTANCE)
                .addScalar("redist", StringType.INSTANCE)
                .addScalar("distall_dx", StringType.INSTANCE)
                .addScalar("showgrp_inb", StringType.INSTANCE)
                .addScalar("dist_type", StringType.INSTANCE)
                .addScalar("showgrp_type", StringType.INSTANCE)
                .addScalar("showtype_type", StringType.INSTANCE)
                .addScalar("disttogrp", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(IvrUser.class));
        q.setParameter("userId", userId);
        return (IvrUser)q.uniqueResult();
    }

    public Ivrdist getIvrdist(String caseid){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        Query q = session.createSQLQuery("select a.* from acoapp_ivrcallback.ivrdist a where a.caseid like :caseid and rownum < 2")
                .addScalar("ruid", LongType.INSTANCE)
                .addScalar("usrid", StringType.INSTANCE)
                .addScalar("grpid", StringType.INSTANCE)
                .addScalar("caseid", StringType.INSTANCE)
                .addScalar("recdat", DateType.INSTANCE)
                .addScalar("ani", StringType.INSTANCE)
                .addScalar("dnis", StringType.INSTANCE)
                .addScalar("createtime", DateType.INSTANCE)
                .addScalar("callgrp", StringType.INSTANCE)
                .addScalar("status", IntegerType.INSTANCE)
                .addScalar("type", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(Ivrdist.class));
        q.setParameter("caseid", caseid+"%");
        return (Ivrdist)q.uniqueResult();
    }

    public HashMap<String, Long> findAllocatedNumbers(CallbackSpecification specification){
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //-1/24减去一个小时，怕两个数据库的时间有差异
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select a.ani, count(1) as num from acoapp_ivrcallback.ivrdist a where a.type='IVR' " +
                            (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss')" : "") +
                            (params.containsKey("endDateString") ? "and a.createtime <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("dnis") ? "and a.dnis = :dnis " : "") +
                            (params.containsKey("anis") ? "and a.ani in (:anis) " : "") +
                            (params.containsKey("workGroups") ? "and a.callgrp in (:workGroups) " : "") +
                            "group by a.ani"
            )
            .setResultTransformer(Transformers.TO_LIST);

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

            HashMap<String, Long> map = new HashMap<String, Long>();
            for(List x : (List<List>)q.list()){
                map.put(String.valueOf(x.get(0)), Long.parseLong(String.valueOf(x.get(1))));
            }
            return map;
        }
        else{
            return new HashMap<String, Long>();
        }
    }

    public List<Ivrdist> findAllocatedIvrdists(CallbackSpecification specification){

        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select a.* from acoapp_ivrcallback.ivrdist a where a.type='IVR' "+
                            (params.containsKey("startDateString") ? "and a.createtime >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss')" : "") +
                            (params.containsKey("endDateString") ? "and a.createtime <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                            (params.containsKey("dnis") ? "and a.dnis = :dnis " : "") +
                            (params.containsKey("anis") ? "and a.ani in (:anis) " : "") +
                            (params.containsKey("workGroups") ? "and a.callgrp in (:workGroups) " : "")
                    )
                    .addScalar("ruid", LongType.INSTANCE)
                    .addScalar("usrid", StringType.INSTANCE)
                    .addScalar("grpid", StringType.INSTANCE)
                    .addScalar("caseid", StringType.INSTANCE)
                    .addScalar("recdat", DateType.INSTANCE)
                    .addScalar("ani", StringType.INSTANCE)
                    .addScalar("dnis", StringType.INSTANCE)
                    .addScalar("createtime", DateType.INSTANCE)
                    .addScalar("callgrp", StringType.INSTANCE)
                    .addScalar("status", IntegerType.INSTANCE)
                    .addScalar("type", StringType.INSTANCE);

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
        else{
            return new ArrayList<Ivrdist>();
        }
    }

    public void CreateIvrdist(AgentUser user, WilcomCallDetail callDetail, String owner, String grpid){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if(session != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Query q = session.createSQLQuery("insert into acoapp_ivrcallback.ivrdist(ruid, usrid, grpid, caseid, recpsn, recdat, ani, dnis, createtime, callgrp, status, type) " +
                                             "values(se_ivrdist.nextval,:usrid,:grpid,:caseid,:recpsn,to_date(:recdat,'yyyy-mm-dd hh24:mi:ss'),:ani,:dnis,to_date(:createtime,'yyyy-mm-dd hh24:mi:ss'),:callgrp,0,'IVR')");
            q.setParameter("usrid", owner);
            q.setParameter("grpid", grpid);
            q.setParameter("caseid", callDetail.getCaseid()+callDetail.getArea());
            q.setParameter("recpsn", user.getUserId());
            q.setParameter("recdat", formatter.format(new Date()));
            q.setParameter("ani", callDetail.getAni());
            q.setParameter("dnis", callDetail.getDnis());
            q.setParameter("createtime", formatter.format(callDetail.getCreatetime()));
            q.setParameter("callgrp", callDetail.getCallgrp());
            q.executeUpdate();
        }
    }

    @Autowired
    @Required
    @Qualifier("ivrcbSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
}
