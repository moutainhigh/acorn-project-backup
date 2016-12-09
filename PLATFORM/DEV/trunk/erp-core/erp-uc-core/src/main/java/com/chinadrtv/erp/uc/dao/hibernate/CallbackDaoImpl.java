package com.chinadrtv.erp.uc.dao.hibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dto.CallbackDto;
import com.chinadrtv.erp.uc.dto.GroupDto;
import com.chinadrtv.erp.uc.service.SchemaNames;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.util.StringUtil;


/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-19
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CallbackDaoImpl extends GenericDaoHibernate<Callback,Long> implements CallBackDao {

    @Autowired
    private SchemaNames schemaNames;

    public CallbackDaoImpl() {
        super(Callback.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<GroupDto> findValidGroup(String userId, String groupid, Long grpType, Long callType, String grpName) {
        String sql = "";
        if(StringUtil.isNullOrBank(grpName)){
            sql = "select t.grpid,t.grpname from acoapp_marketing.callback_group t where t.active = 1 and t.grptype=:grptype and t.calltype=:calltype and t.grpidm =:groupid ";
        }else{
            sql = "select t.grpid,t.grpname from acoapp_marketing.callback_group t where t.active = 1 and t.grptype=:grptype and t.calltype=:calltype and t.grpidm =:groupid and t.grpname like :grpname ";
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);

        sqlQuery.setString("groupid",groupid);
        sqlQuery.setLong("grptype", grpType);
        sqlQuery.setLong("calltype", callType);
        if(!StringUtil.isNullOrBank(grpName)){
            sqlQuery.setString("grpname","%"+grpName+"%") ;
        }
        sqlQuery.addScalar("grpid",StandardBasicTypes.STRING);
        sqlQuery.addScalar("grpname",StandardBasicTypes.STRING);
        sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(GroupDto.class));
        return sqlQuery.list();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<GroupDto> findValidGroup(Long grpType, Long callType, String workGroups){
            Query q = getSession().createSQLQuery("select t.grpid,t.grpname from acoapp_marketing.callback_group t where t.active = 1 and t.grptype=:grptype and t.calltype=:calltype and t.grpidm in (:workGroups)")
                                  .addScalar("grpid",StandardBasicTypes.STRING)
                                  .addScalar("grpname",StandardBasicTypes.STRING)
                                  .setResultTransformer(new AliasToBeanResultTransformer(GroupDto.class));
            q.setLong("grptype", grpType);
            q.setLong("calltype", callType);
            q.setParameterList("workGroups", Arrays.asList(workGroups.split(",")));
            return q.list();
        }

    public Long findAllocatedCount(CallbackSpecification specification){
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "select count(a.callbackId) from Callback a " +
                            "where a.taskId is not null " +
                            "and ((a.type<>:callType and a.dbdt > current_timestamp()-8/24 and a.phn2=:ani) or (a.type=:callType "+ whereSQL(params, "a") +"))");

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

            q.setString("ani", specification.getAni());

            return Long.parseLong(String.valueOf(q.uniqueResult()));
        }
        else{
            return 0L;
        }
    }

    public HashMap<String, Long> findAllocatedNumbers(CallbackSpecification specification){
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "select a.ani as ani, count(a.callbackId) as num from Callback a " +
                            "where a.taskId is not null " +
                            "and ((a.type<>:callType and a.dbdt > current_timestamp()-8/24 and (a.ani in (:anis) or '0'||a.ani in(:anis))) or (a.type=:callType "+ whereSQL(params, "a") +")) " +
                            "group by a.ani")
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

    /**
     * API-CALLBACK-8.	查询已分配的CALL_BACK
     * @return
     */
    public List<Callback> findAllocatedCallbacks(CallbackSpecification specification){

        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "from Callback a " +
                            "where a.taskId is not null " +
                            "and ((a.type<>:callType and a.dbdt > current_timestamp()-8/24 and a.phn2=:ani) or (a.type=:callType "+ whereSQL(params, "a") +"))");

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
            q.setString("ani", specification.getAni());

            return q.list();
        }
        else{
            return new ArrayList<Callback>();
        }
    }

    public List<Callback> findCallbacksByCaseId(String caseId){
        Session session = getSession();
        Query q = session.createQuery("from Callback a where a.caseId = :caseId");
        q.setString("caseId", caseId);
        return q.list();
    }

    /**
     * API-CALLBACK-8.	查询可分配的CALL_BACK
     * @return
     */
    public List<Callback> findCallbacks(CallbackSpecification specification){
        /**
         * TODO:ACD组、通话状态、商品过滤都没有实现
         */
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();

            Query q = session.createQuery(
                    "from Callback a where not exists(" +
                            "select b.callbackId from Callback b " +
                            "where b.taskId is not null and b.type<>:callType and (b.contactId=a.contactId or b.latentcontactId=a.latentcontactId) and b.mediaplanId=a.mediaplanId and b.phn2 = a.phn2 and b.dbdt > current_timestamp()-8/24 " +
                            ") and not exists(" +
                            "select c.callbackId from Callback c " +
                            "where c.taskId is not null and c.type=:callType and (c.contactId=a.contactId or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
                            ") and a.callbackId = nvl((" +
                            "select max(c.callbackId) from Callback c where c.taskId is null and (c.contactId=a.contactId  or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
                            "), a.callbackId) and a.taskId is null and a.type=:callType "+ whereSQL(params, "a"));
            /*
            Query q = session.createQuery(
                    "from Callback a where not exists(" +
                    "select b.callbackId from Callback b " +
                    "where b.taskId is not null and b.type<>:callType and (b.contactId=a.contactId or b.latentcontactId=a.latentcontactId) and b.mediaplanId=a.mediaplanId and b.phn2 = a.phn2 and b.dbdt > current_timestamp()-8/24 " +
                    ") and a.callbackId = nvl((" +
                    "select max(c.callbackId) from Callback c where c.taskId is null and (c.contactId=a.contactId  or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
                    "), a.callbackId) and a.taskId is null and a.type=:callType "+ whereSQL(params, "a"));
            */
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

            q.setString("callType", String.valueOf(CallbackType.CALLBACK.getIndex()));

            return q.list();
        }
        else{
            return new ArrayList<Callback>();
        }
    }

    public List<Callback> findExcludedCallbacks(CallbackSpecification specification, Callback callback){
        /**
         * TODO:ACD组、通话状态、商品过滤都没有实现
         */
        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "from Callback a where a.callbackId <> :callbackId and a.callbackId in ("+
                            "select c.callbackId from Callback c where c.taskId is null and (c.latentcontactId = a.latentcontactId or c.contactId=a.contactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 and c.type=:callType "+ whereSQL(params, "c") +
                            ") and a.taskId is null and a.type=:callType "+ whereSQL(params, "a") + " and (a.contactId=:contactId or a.latentcontactId=:latentcontactId) and a.mediaplanId=:mediaplanId and a.phn2=:phn2");

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
            q.setLong("callbackId", callback.getCallbackId());
            q.setString("contactId", callback.getContactId() != null ? callback.getContactId() : "");
            q.setString("latentcontactId", callback.getLatentcontactId() != null ? callback.getLatentcontactId() : "");
            q.setString("phn2", callback.getPhn2());
            q.setString("mediaplanId", callback.getMediaplanId());

            q.setString("callType", callback.getType());

            return q.list();
        }
        else{
            return new ArrayList<Callback>();
        }
    }

    private String whereSQL(Map<String, Object> params, String aliasName){
        //return (params.containsKey("callType") ? "and "+aliasName+".type = :callType " : "") +
        return (params.containsKey("callDuration") ?
                ("a".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                        "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.timeLength <= 20) " :
                        ("b".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.timeLength > 20 and b.timeLength <=180) " :
                                ("c".equalsIgnoreCase(String.valueOf(params.get("callDuration"))) ?
                                        "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.timeLength > 180) " : ""))) : "") +
                //(params.containsKey("startDateString") ? "and "+aliasName+".crdt >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                //(params.containsKey("endDateString") ? "and "+aliasName+".crdt <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss') " : "") +
                //ivr分配,线索创建与呼入时间可能相差很多
                (params.containsKey("startDateString") && params.containsKey("endDateString") ? "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.beginDate >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss') and b.beginDate <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss')) ":
                        (params.containsKey("startDateString") ? "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.beginDate >= to_date(:startDateString,'yyyy-MM-dd HH24:mi:ss')) " :
                                (params.containsKey("endDateString") ? "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.beginDate <= to_date(:endDateString,'yyyy-MM-dd HH24:mi:ss')) " : ""))) +

                (params.containsKey("agentUser") ? "and "+aliasName+".usrId = :agentUser " : "") +
                (params.containsKey("dnis") ? "and "+aliasName+".dnis = :dnis " : "") +
                (params.containsKey("anis") ? "and ("+aliasName+".ani in (:anis) or '0'||"+aliasName+".ani in (:anis)) " : "") +
                (params.containsKey("acdGroups") ? "and "+aliasName+".acdGroup in (:acdGroups) " : "") +
                (params.containsKey("workGroups") ? "and "+aliasName+".usrGrp in (:workGroups) " : "") +
                (params.containsKey("priorities") ? "and "+aliasName+".priority in (:priorities) " : "") +
                (params.containsKey("result") ? "and exists(from LeadInteraction b where b.id="+aliasName+".leadInteractionId and b.resultCode = :result) " : "") +
                //(params.containsKey("allocatedNumber") ? "and ("+aliasName+".allocateCount = :allocatedNumber or (:allocatedNumber=0 and "+aliasName+".allocateCount is null)) " : "") +
                (params.containsKey("orderId") ? "and "+aliasName+".orderId = :orderId " : "") +
                (params.containsKey("productId") ? "and (("+aliasName+".mediaprodId=:productId) or exists(from LeadTypeValue cc where cc.campaignId = "+aliasName+".mediaplanId and cc.value = :productId)) " : "") +
                (params.containsKey("batchId") ? "and "+aliasName+".batchId = :batchId " : "");
    }

    public Long findCallbackCount(CallbackSpecification specification) throws SQLException{

        Map<String, Object> params = specification.GetParameters();
        if(params.size() > 0){
            Session session = getSession();
            /*
            "select count(a.callbackId) from Callback a where not exists(" +
            "select b.callbackId from Callback b " +
            "where b.taskId is not null and b.type<>:callType and (b.contactId=a.contactId or b.latentcontactId=a.latentcontactId) and b.mediaplanId=a.mediaplanId and b.phn2 = a.phn2 and b.dbdt > current_timestamp()-8/24 " +
            ") and not exists(" +
            "select c.callbackId from Callback c " +
            "where c.taskId is not null and c.type=:callType and (c.contactId=a.contactId or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
            ") and a.taskId is null and a.type=:callType "+ whereSQL(params, "a")
            */
            Query q = session.createQuery(
                "select count(a.callbackId) from Callback a where not exists(" +
                    "select b.callbackId from Callback b " +
                    "where b.taskId is not null and b.type<>:callType and (b.contactId=a.contactId or b.latentcontactId=a.latentcontactId) and b.mediaplanId=a.mediaplanId and b.phn2 = a.phn2 and b.dbdt > current_timestamp()-8/24 " +
                ") and not exists(" +
                    "select c.callbackId from Callback c " +
                    "where c.taskId is not null and c.type=:callType and (c.contactId=a.contactId or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
                ") and a.callbackId = nvl((" +
                    "select max(c.callbackId) from Callback c where c.taskId is null and (c.contactId=a.contactId  or c.latentcontactId=a.latentcontactId) and c.mediaplanId=a.mediaplanId and c.phn2 = a.phn2 "+ whereSQL(params, "c") +
                "), a.callbackId) and a.taskId is null and a.type=:callType "+ whereSQL(params, "a")
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

            q.setString("callType", String.valueOf(CallbackType.CALLBACK.getIndex()));

            return Long.parseLong(String.valueOf(q.uniqueResult()));
        } else {
            return 0L;
        }
        /*
    	StringBuilder sql = new StringBuilder();
    	sql.append("select count(cb.id)")
		   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback cb ")
		   .append("where 1=1 ");
		if(specification.getStartDate() != null) {
			sql.append(" AND to_char(cb.crdt,'yyyy-mm-dd hh24:mi:ss') >= :startDate");
		}
		if(specification.getEndDate() != null) {
			sql.append(" AND to_char(cb.crdt,'yyyy-mm-dd hh24:mi:ss') < :endDate");
		}
		if(StringUtils.isNotBlank(specification.getCallType())) {
			sql.append(" AND cb.type = :callType");
		}
		if(StringUtils.isNotBlank(specification.getAgentUser())) {
			sql.append(" AND (cb.RDBUSRID = :userId or cb.FIRSTUSRID=:userId) ");
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		if(specification.getStartDate() != null) {
			sqlQuery.setString("startDate", DateUtil.date2FormattedString(specification.getStartDate(),"yyyy-MM-dd hh:mm:ss"));
		}
		if(specification.getEndDate() != null) {
			sqlQuery.setString("endDate", DateUtil.date2FormattedString(specification.getEndDate(),"yyyy-MM-dd hh:mm:ss"));
		}
		if(StringUtils.isNotBlank(specification.getCallType())) {
			sqlQuery.setString("callType", specification.getCallType());
		}
		if(StringUtils.isNotBlank(specification.getAgentUser())) {
			sqlQuery.setString("userId", specification.getAgentUser());
		}
		Object obj = sqlQuery.uniqueResult();
		int count = ((BigDecimal) obj).intValue();
		return count;
		*/
    }

    public Integer findExecutedCallbackCount(CallbackSpecification specification) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(distinct(cb.task_id)) ")
                .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback cb ")
                .append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_task lt on cb.TASK_ID = lt.bpm_instance_id ")
                .append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead ld on lt.LEAD_ID = ld.ID ")
                .append("where 1=1 ");
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sql.append(" AND cb.dbdt >= TO_DATE(:startDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sql.append(" AND cb.dbdt < TO_DATE(:endDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sql.append(" AND cb.type = :callType");
        }

        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sql.append(" AND (cb.RDBUSRID = :userId or cb.FIRSTUSRID=:userId) ");
        }
        else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sql.append(" AND (ld.OWNER is null and ld.USER_GROUP=:workGroup) ");
        }

        sql.append(" and cb.task_id is not null");
        if(specification.isQueryExecuted()) {
            sql.append(" AND lt.status in ('1','3')");
        }
        Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sqlQuery.setString("startDate", specification.getStartDateString());
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sqlQuery.setString("endDate", specification.getEndDateString());
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sqlQuery.setString("callType", specification.getCallType());
        }
        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sqlQuery.setString("userId", specification.getAgentUser());
        } else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sqlQuery.setString("workGroup", specification.getWorkGroup());
        }
        Object obj = sqlQuery.uniqueResult();
        int count = ((BigDecimal) obj).intValue();
        return count;
    }

    public Map<String, AgentUserInfo4TeleDist> findExecutedCallbackCountInBatch(CallbackSpecification specification) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select cb.FIRSTUSRID userId, count(distinct(cb.task_id)) intradayExecutedCount")
                .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback cb ")
                .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_task lt on cb.TASK_ID = lt.bpm_instance_id ")
                .append(" where 1=1 ");
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sql.append(" AND cb.dbdt >= TO_DATE(:startDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sql.append(" AND cb.dbdt < TO_DATE(:endDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sql.append(" AND cb.type =:callType");
        }
        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sql.append(" AND cb.FIRSTUSRID=:userId ");
        }
        else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sql.append(" AND (ld.OWNER is null and ld.USER_GROUP=:workGroup) ");
        } else if(specification.getAgentUserIds() != null && specification.getAgentUserIds().size() > 0) {
        	sql.append(" AND cb.FIRSTUSRID in(:userIds)");
        }

        sql.append(" and cb.task_id is not null");
        if(specification.isQueryExecuted()) {
            sql.append(" AND lt.status in ('1','3')");
        }
        sql.append(" group by cb.FIRSTUSRID");
        Query sqlQuery = this.getSessionFactory().getCurrentSession()
        		.createSQLQuery(sql.toString())
        		.addScalar("userId", StringType.INSTANCE)
        		.addScalar("intradayExecutedCount", IntegerType.INSTANCE)
        		.setResultTransformer(Transformers.aliasToBean(AgentUserInfo4TeleDist.class));
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sqlQuery.setString("startDate", specification.getStartDateString());
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sqlQuery.setString("endDate", specification.getEndDateString());
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sqlQuery.setString("callType", specification.getCallType());
        }
        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sqlQuery.setString("userId", specification.getAgentUser());
        } else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sqlQuery.setString("workGroup", specification.getWorkGroup());
        } else if(specification.getAgentUserIds() != null && specification.getAgentUserIds().size() > 0) {
        	sqlQuery.setParameterList("userIds", specification.getAgentUserIds());
        }
        List<AgentUserInfo4TeleDist> userCounts= sqlQuery.list();
		Map<String, AgentUserInfo4TeleDist> agentUsersMap = new HashMap<String, AgentUserInfo4TeleDist>();
		for(AgentUserInfo4TeleDist agentUser : userCounts) {
			agentUsersMap.put(agentUser.getUserId(), agentUser);
		}
		return agentUsersMap;
    }
    
    public Map<String, AgentUserInfo4TeleDist> findAllocatedCallbackCountInBatch(CallbackSpecification specification) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select cb.FIRSTUSRID userId, count(distinct(cb.task_id)) intradayAllocatedCount")
                .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback cb ")
                .append(" where 1=1 ");
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sql.append(" AND cb.dbdt >= TO_DATE(:startDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sql.append(" AND cb.dbdt < TO_DATE(:endDate, 'YYYY-MM-DD HH24:MI:SS')");
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sql.append(" AND cb.type =:callType");
        }

        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sql.append(" AND cb.FIRSTUSRID=:userId ");
        }
        else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sql.append(" AND (ld.OWNER is null and ld.USER_GROUP=:workGroup) ");
        } else if(specification.getAgentUserIds() != null && specification.getAgentUserIds().size() > 0) {
        	sql.append(" AND cb.FIRSTUSRID in(:userIds)");
        }

        sql.append(" and cb.task_id is not null");
        if(specification.isQueryExecuted()) {
            sql.append(" AND lt.status in ('1','3')");
        }
        sql.append(" group by cb.FIRSTUSRID");
        Query sqlQuery = this.getSessionFactory().getCurrentSession()
        		.createSQLQuery(sql.toString())
        		.addScalar("userId", StringType.INSTANCE)
        		.addScalar("intradayAllocatedCount", IntegerType.INSTANCE)
        		.setResultTransformer(Transformers.aliasToBean(AgentUserInfo4TeleDist.class));
        if(StringUtils.isNotBlank(specification.getStartDateString())) {
            sqlQuery.setString("startDate", specification.getStartDateString());
        }
        if(StringUtils.isNotBlank(specification.getEndDateString())) {
            sqlQuery.setString("endDate", specification.getEndDateString());
        }
        if(StringUtils.isNotBlank(specification.getCallType())) {
            sqlQuery.setString("callType", specification.getCallType());
        }
        if(StringUtils.isNotBlank(specification.getAgentUser())) {
            sqlQuery.setString("userId", specification.getAgentUser());
        } else if(StringUtils.isNotBlank(specification.getWorkGroup())) {
            //获取组未分配数量统计  2014-02-17 gao
            sqlQuery.setString("workGroup", specification.getWorkGroup());
        } else if(specification.getAgentUserIds() != null && specification.getAgentUserIds().size() > 0) {
        	sqlQuery.setParameterList("userIds", specification.getAgentUserIds());
        }
        List<AgentUserInfo4TeleDist> userCounts= sqlQuery.list();
		Map<String, AgentUserInfo4TeleDist> agentUsersMap = new HashMap<String, AgentUserInfo4TeleDist>();
		for(AgentUserInfo4TeleDist agentUser : userCounts) {
			agentUsersMap.put(agentUser.getUserId(), agentUser);
		}
		return agentUsersMap;
    }
    
    public String queryPhoneByTaskId(String taskId) {
        String phoneNo = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select cb.phn2 ")
                .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback cb ")
                .append("where cb.task_id=:taskId");
        Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("taskId", taskId);
        Object obj = sqlQuery.uniqueResult();
        phoneNo = (String)obj;
        return phoneNo;
    }

    public Integer countAssignableVirtualCallback(final CallbackDto callbackDto) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Callback.class);
                criteria.setProjection(Projections.rowCount());
                addCriteria(criteria, callbackDto);
                return ((Long) criteria.uniqueResult()).intValue();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<Callback> queryAssignableVirtualCallback(final CallbackDto callbackDto) {
        return getHibernateTemplate().executeFind(new HibernateCallback<List<Callback>>() {
            public List<Callback> doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Callback.class);
                addCriteria(criteria, callbackDto);
                return criteria.list();
            }
        });
    }

    private void addCriteria(Criteria criteria, CallbackDto callbackDto) {
        criteria.add(Restrictions.eq("type",
                String.valueOf(CallbackType.VIRTUAL_PHONECALL.getIndex())));
        criteria.add(Restrictions.isNull("groupAssigner"));
        criteria.add(Restrictions.isNull("userAssigner"));
        if (StringUtils.isNotEmpty(callbackDto.getStartDate())) {
            criteria.add(Restrictions.sqlRestriction(
                    "{alias}.crdt >= to_date(?,'yyyy-MM-dd')",
                    callbackDto.getStartDate(), StandardBasicTypes.STRING));
        }
        if (StringUtils.isNotEmpty(callbackDto.getEndDate())) {
            criteria.add(Restrictions.sqlRestriction(
                    "{alias}.crdt < to_date(?,'yyyy-MM-dd')",
                    callbackDto.getEndDate(), StandardBasicTypes.STRING));
        }
        if (StringUtils.isNotEmpty(callbackDto.getUsrGrp())) {
            criteria.add(Restrictions.eq("usrGrp", callbackDto.getUsrGrp()));
        }
    }

    public String getBatchSeq() {
        Query q = getSession().createSQLQuery("select " + schemaNames.getCrmmarketingSchema() + "SEQ_CALLBACK_BATCH.nextval from dual");
        return q.list().get(0).toString();
    }

	/** 
	 * <p>Title: queryAvaliableQtyByGroups</p>
	 * <p>Description: </p>
	 * @param agentUsers
	 * @return
	 * @see com.chinadrtv.erp.uc.dao.CallBackDao#queryAvaliableQtyByGroups(java.util.List)
	 */ 
	@Override
	public Integer queryAvaliableQtyByGroups(List<Map<String, Object>> agentUsers) {
		
		StringBuffer sb = new StringBuffer();
		Set<String> hashSet = new HashSet<String>();
		for(int i=0; i<agentUsers.size(); i++) {
			Map<String, Object> groupMap = agentUsers.get(i);
			String userGroup = groupMap.get("userGroup").toString();
			hashSet.add(userGroup);
		}
		
		for(Iterator<String> iter = hashSet.iterator(); iter.hasNext();){
			String grp = iter.next();
			sb.append("'");
			sb.append(grp);
			sb.append("'");
			sb.append(",");
		}
		
		String grps = sb.toString();
		if(grps.length() > 2) {
			grps = grps.substring(0, grps.length() - 1);
		}
		
		String sql = "select count(*) from acoapp_marketing.callback cb " +
				" where cb.usrgrp in (" + grps + ") " + 
				" and cb.type = '3' " +
				" and (cb.usrid is null or cb.usrid='')";
		
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		
		BigDecimal totalCount = (BigDecimal) query.list().get(0);
		return totalCount.intValue();
	}

	/** 
	 * <p>Title: queryAvaliableListByGroup</p>
	 * <p>Description: </p>
	 * @param userGroup
	 * @return
	 * @see com.chinadrtv.erp.uc.dao.CallBackDao#queryAvaliableListByGroup(java.lang.String)
	 */ 
	@Override
	public List<Callback> queryAvaliableListByGroup(String userGroup) {
		String hql = "select cb from Callback cb " +
				" where cb.usrGrp = :group " + 
				" and cb.type = '3' " +
				" and (cb.usrId is null or cb.usrId='')" +
				" order by cb.crdt asc";
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("group", userGroup);
	
		return query.list();
	}
}