/*
 * @(#)ContactAssignHistDaoImpl.java 1.0 2013年8月29日上午10:04:24
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import com.chinadrtv.erp.marketing.core.dto.SearchContactAssignHistDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.ContactAssignHistDao;
import com.chinadrtv.erp.model.marketing.ContactAssignHist;

import java.util.List;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年8月29日 上午10:04:24 
 * 
 */
@Repository
public class ContactAssignHistDaoImpl extends GenericDaoHibernate<ContactAssignHist, Long> implements
		ContactAssignHistDao {

	public ContactAssignHistDaoImpl() {
		super(ContactAssignHist.class);
	}

    @Override
    public List<ContactAssignHist> findPageList(SearchContactAssignHistDto searchContactAssignHistDto, int startRow, int rows) {
        StringBuffer hql = new StringBuffer("from ContactAssignHist c where 1=1");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) hql.append(" and c.campaignId=:campaignId ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) hql.append(" and c.batchCode=:batchCode ");
        if (null != searchContactAssignHistDto.getAssignStartTime()) hql.append(" and c.assignTime>:assignStartTime ");
        if (null != searchContactAssignHistDto.getAssignEndTime()) hql.append(" and c.assignTime<:assignEndTime ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) hql.append(" and c.status=:status ");

        Query query= this.getSession().createQuery(hql.toString());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) query.setString("campaignId", searchContactAssignHistDto.getCampaignId());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) query.setString("batchCode", searchContactAssignHistDto.getBatchCode());
        if (null != searchContactAssignHistDto.getAssignStartTime()) query.setTimestamp("assignStartTime", searchContactAssignHistDto.getAssignStartTime());
        if (null != searchContactAssignHistDto.getAssignEndTime()) query.setTimestamp("assignEndTime", searchContactAssignHistDto.getAssignEndTime());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) query.setString("status", searchContactAssignHistDto.getStatus());
        query.setFirstResult(startRow);
        query.setMaxResults(rows);
        return query.list();
    }

    @Override
    public List<ContactAssignHist> find(SearchContactAssignHistDto searchContactAssignHistDto) {
        StringBuffer hql = new StringBuffer("from ContactAssignHist c where 1=1");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) hql.append(" and c.campaignId=:campaignId ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) hql.append(" and c.batchCode=:batchCode ");
        if (null != searchContactAssignHistDto.getAssignStartTime()) hql.append(" and c.assignTime>:assignStartTime ");
        if (null != searchContactAssignHistDto.getAssignEndTime()) hql.append(" and c.assignTime<:assignEndTime ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) hql.append(" and c.status=:status ");

        Query query= this.getSession().createQuery(hql.toString());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) query.setString("campaignId", searchContactAssignHistDto.getCampaignId());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) query.setString("batchCode", searchContactAssignHistDto.getBatchCode());
        if (null != searchContactAssignHistDto.getAssignStartTime()) query.setDate("assignStartTime", searchContactAssignHistDto.getAssignStartTime());
        if (null != searchContactAssignHistDto.getAssignEndTime()) query.setDate("assignEndTime", searchContactAssignHistDto.getAssignEndTime());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) query.setString("status", searchContactAssignHistDto.getStatus());
        return query.list();
    }

    @Override
    public int findCount(SearchContactAssignHistDto searchContactAssignHistDto) {
        StringBuffer hql = new StringBuffer("select count(c.id) from ContactAssignHist c where 1=1 ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) hql.append(" and c.campaignId=:campaignId ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) hql.append(" and c.batchCode=:batchCode ");
        if (null != searchContactAssignHistDto.getAssignStartTime()) hql.append(" and c.assignTime>:assignStartTime ");
        if (null != searchContactAssignHistDto.getAssignEndTime()) hql.append(" and c.assignTime<:assignEndTime ");
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) hql.append(" and c.status=:status ");

        Query query= this.getSession().createQuery(hql.toString());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getCampaignId())) query.setString("campaignId", searchContactAssignHistDto.getCampaignId());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getBatchCode())) query.setString("batchCode", searchContactAssignHistDto.getBatchCode());
        if (null != searchContactAssignHistDto.getAssignStartTime()) query.setDate("assignStartTime", searchContactAssignHistDto.getAssignStartTime());
        if (null != searchContactAssignHistDto.getAssignEndTime()) query.setDate("assignEndTime", searchContactAssignHistDto.getAssignEndTime());
        if (StringUtils.isNotBlank(searchContactAssignHistDto.getStatus())) query.setString("status", searchContactAssignHistDto.getStatus());
        return Integer.valueOf(query.uniqueResult().toString());
    }
}
