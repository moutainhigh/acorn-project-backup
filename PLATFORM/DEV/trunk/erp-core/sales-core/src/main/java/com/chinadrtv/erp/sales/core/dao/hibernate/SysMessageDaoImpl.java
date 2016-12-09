package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.dao.SysMessageDao;
import com.chinadrtv.erp.sales.core.model.MessageCheckStatus;
import com.chinadrtv.erp.sales.core.model.MessageQueryDto;
import com.chinadrtv.erp.sales.core.model.UsrMessage;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.utils.BulkListSplitter;
import com.google.code.ssm.api.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class SysMessageDaoImpl extends GenericDaoHibernateBase<SysMessage, Long> implements SysMessageDao {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SysMessageDaoImpl.class);
    public SysMessageDaoImpl() {
        super(SysMessage.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<SysMessage> queryMessages(MessageQueryDto messageQueryDto) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SysMessage.class);
        Criterion criterionDate = getCriterionFromDate("createDate", messageQueryDto.getBeginDate(), messageQueryDto.getEndDate());
        if (criterionDate != null)
            criteria.add(criterionDate);
        if (StringUtils.isNotEmpty(messageQueryDto.getReceiverId())) {
            criteria.add(Restrictions.eq("receiverId", messageQueryDto.getReceiverId()));
        }
        /*if(StringUtils.isNotEmpty(messageQueryDto.getRecivierGroup()))
        {
            criteria.add(Restrictions.eq("recivierGroup",messageQueryDto.getRecivierGroup()));
        }*/
        //查询组列表
        if (messageQueryDto.getGrpIdList() != null) {
            Criterion grpCriterion = null;
            for (String grpId : messageQueryDto.getGrpIdList()) {
                if (grpCriterion == null) {
                    grpCriterion = Restrictions.eq("recivierGroup", grpId);
                } else {
                    grpCriterion = Restrictions.or(grpCriterion, Restrictions.eq("recivierGroup", grpId));
                }
            }

            criteria.add(grpCriterion);
        }


        if (messageQueryDto.getCheckStatusList()!=null&&messageQueryDto.getCheckStatusList().size()>0) {
            Criterion checkCriterion = null;
            for(Integer checkStatus:messageQueryDto.getCheckStatusList())
            {
                if(checkCriterion==null)
                {
                    checkCriterion=Restrictions.eq("checked",checkStatus);
                }
                else
                {
                    checkCriterion=Restrictions.or(checkCriterion,Restrictions.eq("checked",checkStatus));
                }
            }

            criteria.add(checkCriterion);
        }

        if (messageQueryDto.getMessageType() != null) {
            criteria.add(Restrictions.eq("sourceTypeId", String.valueOf(messageQueryDto.getMessageType().getIndex())));
        }

        /*if(messageQueryDto.getHaveReceiver()!=null&&messageQueryDto.getHaveReceiver().booleanValue()==true)
        {
            criteria.add(Restrictions.isNotNull("receiverId"));
        }*/

        if (StringUtils.isNotEmpty(messageQueryDto.getDepartmentId())) {
            criteria.add(Restrictions.eq("departmentId", messageQueryDto.getDepartmentId()));
        }

        if (StringUtils.isNotEmpty(messageQueryDto.getDepartmentId()) || (messageQueryDto.getGrpIdList() != null && messageQueryDto.getGrpIdList().size() > 0)) {
            criteria.add(Restrictions.isNull("receiverId"));
        }

        if(StringUtils.isNotBlank(messageQueryDto.getContent()))
        {
            criteria.add(Restrictions.eq("content", messageQueryDto.getContent()));
        }

        if (messageQueryDto.getPageSize() != null)
            return this.hibernateTemplate.findByCriteria(criteria, messageQueryDto.getStartPos(), messageQueryDto.getPageSize());
        else
            return this.hibernateTemplate.findByCriteria(criteria);
    }

    private Criterion getCriterionFromDate(String fldName, Date beginDate, Date endDate) {
        if (beginDate != null && endDate != null) {
            return Restrictions.and(
                    Restrictions.ge(fldName, beginDate),
                    Restrictions.le(fldName, endDate));
        } else {
            if (beginDate != null)
                return Restrictions.ge(fldName, beginDate);
            if (endDate != null)
                return Restrictions.le(fldName, endDate);

        }
        return null;
    }

    public void checkMessageByExample(SysMessage sysMessage) {
        StringBuilder strBld = new StringBuilder("update SysMessage set checked=:check,checkDate=:checkDate where ");
        //String hql="update SysMessage set checked=:check,checkDate=:checkDate where ";
        Map<String, Object> mapParm = new HashMap<String, Object>();
        if(sysMessage.getChecked()!=null)
            mapParm.put("check", sysMessage.getChecked());
        else
            mapParm.put("check", MessageCheckStatus.READED.getIndex());
        mapParm.put("checkDate", new Date());
        boolean bFirst = true;
        if (StringUtils.isNotEmpty(sysMessage.getReceiverId())) {
            if (bFirst == true) {
                bFirst = false;
            } else {
                strBld.append(" and ");
            }
            strBld.append("receiverId=:receiverId");
            mapParm.put("receiverId", sysMessage.getReceiverId());
        }
        if (sysMessage.getCreateDate() != null) {
            if (bFirst == true) {
                bFirst = false;
            } else {
                strBld.append(" and ");
            }
            strBld.append("createDate>=:createDate");
            mapParm.put("createDate", sysMessage.getCreateDate());
        }
        if (StringUtils.isNotEmpty(sysMessage.getRecivierGroup())) {
            if (bFirst == true) {
                bFirst = false;
            } else {
                strBld.append(" and ");
            }
            strBld.append("recivierGroup=:recivierGroup");
            mapParm.put("recivierGroup", sysMessage.getRecivierGroup());
        }

        if (StringUtils.isNotEmpty(sysMessage.getSourceTypeId())) {
            if (bFirst == true) {
                bFirst = false;
            } else {
                strBld.append(" and ");
            }
            strBld.append("sourceTypeId=:sourceTypeId");
            mapParm.put("sourceTypeId", sysMessage.getSourceTypeId());
        }

        if (StringUtils.isNotEmpty(sysMessage.getDepartmentId())) {
            if (bFirst == true) {
                bFirst = false;
            } else {
                strBld.append(" and ");
            }
            strBld.append("departmentId=:departmentId");
            mapParm.put("departmentId", sysMessage.getDepartmentId());
        }

        if(bFirst==true)
        {
            bFirst=false;
        }
        else
        {
            strBld.append(" and ");
        }
        strBld.append("checked=0");

        Query query = this.getSession().createQuery(strBld.toString());
        for (Map.Entry<String, Object> entry : mapParm.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.executeUpdate();
    }

    public void checkMessageByIds(List<Long> msgIdList) {
        BulkListSplitter<Long> bulkListSplitter = new BulkListSplitter<Long>(50, "SysMessage", "id");
        List<List<Long>> bulkIdList = bulkListSplitter.splitList(msgIdList);
        for (List<Long> idList : bulkIdList) {
            Map<String, Long> mapParms = new HashMap<String, Long>();
            String hql = bulkListSplitter.getWhereHql(idList, mapParms);
            String str = "update SysMessage set checked=:check,checkDate=:checkDate" + hql;

            Query query = this.getSession().createQuery(str);
            for (Map.Entry<String, Long> entry : mapParms.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setParameter("check", MessageCheckStatus.READED.getIndex());
            query.setParameter("checkDate", new Date());

            query.executeUpdate();
        }


    }



    @ReadThroughSingleCache(namespace = CacheNames.CACHE_SYSMESSAGE, expiration = 3600)
    public List<SysMessage> queryUsrMessages(@ParameterValueKeyProvider UsrMessage usrMessage)
    {
        logger.debug("query message for usrId-"+usrMessage.getUsrId()+" departId-"+usrMessage.getDepartManager());
        //读取用户未读消息
        //目前缓存座席、组管理员和部门管理员
        MessageQueryDto messageQueryDto=new MessageQueryDto();
        messageQueryDto.setPageSize(Integer.MAX_VALUE);
        messageQueryDto.setStartPos(0);
        messageQueryDto.setCheckStatusList(new ArrayList<Integer>());
        messageQueryDto.getCheckStatusList().add(MessageCheckStatus.NOT_READ.getIndex());
        messageQueryDto.getCheckStatusList().add(MessageCheckStatus.READED.getIndex());
        messageQueryDto.setBeginDate(usrMessage.getBeginDate());
        if(StringUtils.isNotEmpty(usrMessage.getDepartManager()))
        {
            messageQueryDto.setDepartmentId(usrMessage.getDepartManager());
            messageQueryDto.setHaveReceiver(false);
        }
        else if(StringUtils.isNotEmpty(usrMessage.getGrpManager()))
        {
            messageQueryDto.setRecivierGroup(usrMessage.getGrpManager());
            messageQueryDto.setHaveReceiver(false);
        }
        else
        {
            messageQueryDto.setReceiverId(usrMessage.getUsrId());
        }

        return this.queryMessages(messageQueryDto);
    }

    @InvalidateMultiCache(namespace = CacheNames.CACHE_SYSMESSAGE)
    public void invalidUsrMessages(@ParameterValueKeyProvider List<String> usrIdList) {
        //目前实现为空，为的就是无效缓存
    }

    @UpdateSingleCache(namespace = CacheNames.CACHE_SYSMESSAGE, expiration =3600)
    public void updateUsrMessage(@ParameterValueKeyProvider String usrId, @ParameterDataUpdateContent List<SysMessage> sysMessageList)
    {
        //目前实现为空，为的就是更新缓存
    }

    @UpdateMultiCache(namespace = CacheNames.CACHE_SYSMESSAGE, expiration =3600)
    public void updateMultiUsrMessage(@ParameterValueKeyProvider List<String> usrIdList, @ParameterDataUpdateContent List<SysMessage> sysMessageList)
    {
        //目前实现为空，为的就是更新缓存
    }

    public  List<SysMessage> queryOrderMessage(String orderId, Date dt)
    {
        Query query=this.getSession().createQuery("from SysMessage where content=:orderid and createDate>:crdt and checked in(:status1,:status2)");
        query.setParameter("orderid",orderId);
        query.setParameter("status1",0);
        query.setParameter("status2",1);
        query.setParameter("crdt",dt);
        return query.list();
    }
}
