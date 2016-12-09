package com.chinadrtv.erp.distribution.dao.hibernate;

import com.chinadrtv.erp.distribution.dao.CallDetailDao;
import com.chinadrtv.erp.distribution.dto.CallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-17
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CallDetailDaoImpl implements CallDetailDao {

    private HibernateTemplate hibernateTemplate;

    @Deprecated
    public Long findIvrCount(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //ani: 014794482016 => 14794482016 加0使用原生号码查询
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select /*+INDEX(cd IDX_CALL_DETAIL_VIT) INDEX(vd IDX_VDN_DETAIL_BK1)*/ count(distinct cd.bk_1) as total from acoapp_gene_calldt2.call_detail cd " +
                    "join acoapp_gene_calldt2.vdn_detail vd on vd.conn_id=cd.conn_id " +
                    "where cd.id > 0 and vd.is_abandon = 1 and vd.vdn = '41117' " +
                    (params.containsKey("acdGroups") ? "and vd.bk_1 in (:acdGroups) ":"") +
                    (params.containsKey("startDate") ? "and cd.vdn_in_time >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and cd.vdn_in_time < :endDate ":"") +
                    (params.containsKey("ani") ? "and (cd.bk_1 = :ani or cd.bk_1 = '0'||:ani) " : "") +
                    (params.containsKey("dnis") ? "and cd.bk_2 = :dnis" : "")
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
            return Long.parseLong(String.valueOf(q.uniqueResult()));
        } else {
            return 0L;
        }
    }

    public List<String> findIvrNumbers(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //ani: 014794482016 => 14794482016 加0使用原生号码查询
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select /*+INDEX(cd IDX_CALL_DETAIL_VIT) INDEX(vd IDX_VDN_DETAIL_BK1)*/ distinct cd.bk_1 as callerId from acoapp_gene_calldt2.call_detail cd " +
                            "join acoapp_gene_calldt2.vdn_detail vd on vd.conn_id=cd.conn_id " +
                            "where cd.id > 0 and vd.is_abandon = 1 and vd.vdn = '41117' " +
                            (params.containsKey("acdGroups") ? "and vd.bk_1 in (:acdGroups) ":"") +
                            (params.containsKey("startDate") ? "and cd.vdn_in_time >= :startDate " : "") +
                            (params.containsKey("endDate") ? "and cd.vdn_in_time < :endDate ":"") +
                            (params.containsKey("ani") ? "and (cd.bk_1 = :ani or cd.bk_1 = '0'||:ani) " : "") +
                            (params.containsKey("dnis") ? "and cd.bk_2 = :dnis" : ""));

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

    public List<CallDetail> findIvrDetails(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //ani: 014794482016 => 14794482016 加0使用原生号码查询
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select /*+INDEX(cd IDX_CALL_DETAIL_VIT) INDEX(vd IDX_VDN_DETAIL_BK1)*/ distinct cd.Id,cd.conn_id as connId,cd.vdn,cd.vdn_in_time as vdnInTime,cd.vdn_out_time as vdnOutTime,cd.insert_time as insertTime,cd.bk_1 as bk1, cd.bk_2 as bk2, vd.bk_1 as skillGroup " +
                    "from acoapp_gene_calldt2.call_detail cd " +
                    "join acoapp_gene_calldt2.vdn_detail vd on vd.conn_id=cd.conn_id " +
                    "where cd.id > 0 and vd.is_abandon = 1 and vd.vdn = '41117' " +
                    (params.containsKey("acdGroups") ? "and vd.bk_1 in (:acdGroups) ":"") +
                    (params.containsKey("startDate") ? "and cd.vdn_in_time >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and cd.vdn_in_time < :endDate ":"") +
                    (params.containsKey("ani") ? "and (cd.bk_1 = :ani or cd.bk_1 = '0'||:ani) " : "") +
                    (params.containsKey("dnis") ? "and cd.bk_2 = :dnis" : ""))
                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("connId", StringType.INSTANCE)
                    .addScalar("vdn", StringType.INSTANCE)
                    .addScalar("vdnInTime", TimestampType.INSTANCE)
                    .addScalar("vdnOutTime", TimestampType.INSTANCE)
                    .addScalar("insertTime", TimestampType.INSTANCE)
                    .addScalar("bk1", StringType.INSTANCE)
                    .addScalar("bk2", StringType.INSTANCE)
                    .addScalar("skillGroup", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(CallDetail.class));
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
            return new ArrayList<CallDetail>();
        }
    }

    public List<String> findAbandonNumbers(CallbackSpecification specification) {
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //放弃并且不为IVR,坐席接通is_abandon一定为0
            //ani: 014794482016 => 14794482016 加0使用原生号码查询
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select /*+INDEX(cd IDX_CALL_DETAIL_VIT) INDEX(vd IDX_VDN_DETAIL_BK1)*/ distinct cd.bk_1 as callerId from acoapp_gene_calldt2.call_detail cd " +
                    "join acoapp_gene_calldt2.vdn_detail vd on vd.conn_id=cd.conn_id " +
                    "where cd.id > 0 and vd.is_abandon = 1 and vd.vdn <> '41117' " +
                    (params.containsKey("acdGroups") ? "and vd.bk_1 in (:acdGroups) ":"") +
                    (params.containsKey("startDate") ? "and cd.vdn_in_time >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and cd.vdn_in_time < :endDate ":"") +
                    (params.containsKey("ani") ? "and (cd.bk_1 = :ani or cd.bk_1 = '0'||:ani) " : "") +
                    (params.containsKey("dnis") ? "and cd.bk_2 = :dnis" : ""));

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

    public List<CallDetail> findAbandonDetails(CallbackSpecification specification){
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            //ani: 014794482016 => 14794482016 加0使用原生号码查询
            Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            Query q = session.createSQLQuery(
                    "select /*+INDEX(cd IDX_CALL_DETAIL_VIT) INDEX(vd IDX_VDN_DETAIL_BK1)*/ distinct cd.Id,cd.conn_id as connId,cd.vdn,cd.vdn_in_time as vdnInTime,cd.vdn_out_time as vdnOutTime,cd.insert_time as insertTime,cd.bk_1 as bk1, cd.bk_2 as bk2, vd.bk_1 as skillGroup " +
                    "from acoapp_gene_calldt2.call_detail cd " +
                    "join acoapp_gene_calldt2.vdn_detail vd on vd.conn_id=cd.conn_id " +
                    "where cd.id > 0 and vd.is_abandon = 1 and vd.vdn <> '41117' " +
                    (params.containsKey("acdGroups") ? "and vd.bk_1 in (:acdGroups) ":"") +
                    (params.containsKey("startDate") ? "and cd.vdn_in_time >= :startDate " : "") +
                    (params.containsKey("endDate") ? "and cd.vdn_in_time < :endDate ":"") +
                    (params.containsKey("ani") ? "and (cd.bk_1 = :ani or cd.bk_1 = '0'||:ani) " : "") +
                    (params.containsKey("dnis") ? "and cd.bk_2 = :dnis" : ""))
                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("connId", StringType.INSTANCE)
                    .addScalar("vdn", StringType.INSTANCE)
                    .addScalar("vdnInTime", TimestampType.INSTANCE)
                    .addScalar("vdnOutTime", TimestampType.INSTANCE)
                    .addScalar("insertTime", TimestampType.INSTANCE)
                    .addScalar("bk1", StringType.INSTANCE)
                    .addScalar("bk2", StringType.INSTANCE)
                    .addScalar("skillGroup", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(CallDetail.class));
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
            return new ArrayList<CallDetail>();
        }
    }

    @Autowired
    @Required
    @Qualifier("sessionFactoryCall")
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
}
