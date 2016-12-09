/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.PromotionDao;
import com.chinadrtv.erp.model.Promotion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author richard.mao
 */
@Repository
public class PromotionDaoImpl extends GenericDaoHibernate<Promotion, Long> implements PromotionDao {

    public PromotionDaoImpl() {
        super(Promotion.class);
    }

    public Promotion findById(Long promotionId) {
        return (Promotion) get((Long) promotionId);
    }

    public void deletePromotion(Long promotionId) {
        remove((Long) promotionId);
    }

    public Promotion savePromotion(Promotion promotion) {
        Date date = new Date();
        if (promotion.getDateAdded() == null) promotion.setDateAdded(date);
        if (promotion.getLastModified() == null) promotion.setLastModified(date);

        getHibernateTemplate().saveOrUpdate((Promotion) promotion);
        return promotion;
    }

    @SuppressWarnings("unchecked")
    public List<Promotion> getAllPromotions(String channel,Date orderDate,Boolean activeOnly, Boolean requiresCouponOnly, Boolean includeExpired, Integer calcRound) {
        Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
        StringBuilder sb = new StringBuilder("from Promotion p where (p.deleted = false or p.deleted is null)  and (p.usedTimes < p.maxuse or p.maxuse = -1 ) ");
        if (channel != null && !channel.isEmpty()) {
            sb.append(" and p.channel like :channel ");
        }
        if (activeOnly) {
            sb.append(" and p.active = true ");
        }
        if (requiresCouponOnly) {
            sb.append(" and p.requiresCoupon=true ");
        }
        if (calcRound != 0){
            sb.append(" and p.calcRound =:calcRound ");
        }
        if (!includeExpired) {
            sb.append(" and (p.startDate is null or p.startDate<=:startDate) and (p.endDate is null or p.endDate>=:endDate) ");
        }
        sb.append("  order by p.execOrder asc ");
        Query q = session.createQuery(sb.toString());

        if (channel != null && !channel.isEmpty()) {
            q.setString("channel", "%" + channel + "%");
        }
        if (calcRound != 0){
            q.setInteger("calcRound", calcRound);
        }
        if (!includeExpired) {
            Date date = new Date();
            q.setTimestamp("startDate", orderDate);
            q.setTimestamp("endDate", orderDate);
        }
        return q.list();
    }

    public List<Promotion> getGlobalPromotions() {
        Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
        StringBuilder sb = new StringBuilder("from Promotion p where (p.deleted = false or p.deleted is null)  and (p.usedTimes < p.maxuse or p.maxuse = -1 ) ");
        sb.append(" and p.active = true ");
        sb.append(" and p.requiresCoupon=false ");
        sb.append(" and p.calcRound =1 ");
        sb.append(" and size(p.products) = 0 ");
        sb.append(" and size(p.coupons) = 0 ");
        sb.append(" and size(p.tags) = 0 ");
        sb.append(" and (p.startDate is null or p.startDate<=:startDate) and (p.endDate is null or p.endDate>=:endDate) ");
        sb.append("  order by p.execOrder asc ");
        Query q = session.createQuery(sb.toString());
        Date date = new Date();
        q.setTimestamp("startDate", date);
        q.setTimestamp("endDate", date);
        return q.list();
    }

    public void setActiveForRule(Long ruleId, Boolean active) {
        Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
        Query q = session.createQuery("update Promotion p set p.active=" + active + " where p.promotionRule.ruleId=?");
        q.setLong(0, ruleId);
        q.executeUpdate();
    }

    public int getPromotionsCount() {
        Query q = getSession().createQuery("select count(s.id) from Promotion s");
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }

    public List<Promotion> getPaginatedPromotionsList(int start_index, Integer num_per_page) {
        String hql = " from Promotion s";
        Query q = getSession().createQuery(hql);
        q.setFirstResult(start_index);
        q.setMaxResults(num_per_page);
        return q.list();
    }

    public int getPromotionsCountByNameActive(int start_index, Integer num_per_page,String promotionName,int active) {
        StringBuffer hql = new StringBuffer("select count(s.id) from Promotion s where 1=1 and (s.deleted = false or s.deleted is null) ");
        if (!promotionName.isEmpty()) {
            hql.append(" and s.name like :name");
        }
        if (active == 1) {
            hql.append(" and s.active=true");
        } else if (active == 0) {
            hql.append(" and s.active=false");
        }

        Query q = getSession().createQuery(hql.toString());
        if (!promotionName.isEmpty()) {
            q.setString("name", "%" + promotionName + "%");
        }
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }


    public List<Promotion> getPaginatedPromotionsListByNameActive(int start_index, Integer num_per_page,String promotionName,int active) {
        Query q = getQuery(promotionName, active);

        q.setFirstResult(start_index);
        q.setMaxResults(num_per_page);
        return q.list();
    }

    public List<Promotion> getPaginatedPromotionsListByNameActive(String promotionName,int active) {
        Query q = getQuery(promotionName, active);
        return q.list();
    }

    public int usePromotion(Promotion promotion) {
        String hql = "update Promotion p set p.usedTimes =  p.usedTimes + 1 where (p.usedTimes < p.maxuse or p.maxuse = -1 ) and p.promotionid=:promotionid";
        Query q = getSession().createQuery(hql);
        q.setLong("promotionid", promotion.getPromotionid());
        return q.executeUpdate();
    }

    private Query getQuery(String promotionName, int active) {
        StringBuffer hql =  new StringBuffer(" from Promotion s where 1=1 and (s.deleted = false or s.deleted is null) ");
        if (!promotionName.isEmpty()) {
            hql.append(" and s.name like :name");
        }
        if (active == 1) {
            hql.append(" and s.active=true");
        } else if (active == 0) {
            hql.append(" and s.active=false");
        }
        hql.append(" order by s.execOrder");
        Query q = getSession().createQuery(hql.toString());
        if (!promotionName.isEmpty()) {
            q.setString("name", "%" + promotionName + "%");
        }
        return q;
    }

    public void deletePromotionByIds(String promotionIDs) {
        promotionIDs = promotionIDs.trim();
        if (promotionIDs.endsWith("_")) {
            promotionIDs = promotionIDs.substring(0, promotionIDs.length() - 1);
        }
        String hql = "update Promotion s set s.deleted = true where s.id in (" + promotionIDs.replaceAll("_", ",") + ")";
        Query q = getSession().createQuery(hql);
        q.executeUpdate();
    }

    public void setActive(Long promotionId, Boolean active) {
        Promotion promotion = findById(promotionId);
        if (promotion.getActive() != active) {
            promotion.setActive(active);
            savePromotion(promotion);
        }

    }

    public Integer getMaxExecOrder() {
        Query q = getSession().createQuery("select max(s.execOrder) from Promotion s where s.active=true and (s.deleted = false or s.deleted is null)");
        Object max = q.list().get(0);
        if (max == null) {
            return 1;
        } else {
            int count = Integer.valueOf(max.toString());
            return count;
        }
    }

    public void clearExecOrder() {
        Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
        Query q = session.createQuery("update Promotion p set p.execOrder=null where p.active=false");
        q.executeUpdate();
    }

    public void updateExecOrder(Integer execOrder) {
        String hql = "update Promotion s set s.execOrder = s.execOrder + 1 where s.execOrder=:execOrder";
        Query q = getSession().createQuery(hql);
        q.setInteger("execOrder", execOrder);
        q.executeUpdate();
    }


    public Promotion getLatePromotion(Integer execOrder) {
        String hql = " from Promotion s  where s.execOrder>:execOrder and  s.active=true and (s.deleted = false or s.deleted is null) order by s.execOrder";
        Query q = getSession().createQuery(hql);
        q.setInteger("execOrder", execOrder);
        List<Promotion> pList = q.list();
        if (pList.size() > 0) {
            return (Promotion)q.list().get(0);
        } else {
            return null;
        }
    }

    public Promotion getEarlyPromotion(Integer execOrder) {
        String hql = " from Promotion s  where s.execOrder<:execOrder and  s.active=true and (s.deleted = false or s.deleted is null) order by s.execOrder desc";
        Query q = getSession().createQuery(hql);
        q.setInteger("execOrder", execOrder);
        List<Promotion> pList = q.list();
        if (pList.size() > 0) {
            return (Promotion)q.list().get(0);
        } else {
            return null;
        }
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

	public int updatePromotinActive(Long promotionId, Boolean active) {
		// TODO Auto-generated method stub
		String hql = " update  Promotion s set s.active=:active  where s.promotionid =:promotionId";
        Query q = getSession().createQuery(hql);
        q.setBoolean("active", active);
        q.setLong("promotionId",promotionId);
        return q.executeUpdate();
        
	}
}