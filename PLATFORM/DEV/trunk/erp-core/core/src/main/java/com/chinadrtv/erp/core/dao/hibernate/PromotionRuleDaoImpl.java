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

import com.chinadrtv.erp.core.dao.PromotionRuleDao;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleOption;
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
import java.util.Set;

/**
 * 
 * @author richard.mao
 *
 */
@Repository
public class PromotionRuleDaoImpl extends GenericDaoHibernate<PromotionRule, Long> implements
        PromotionRuleDao {
	
	public PromotionRuleDaoImpl(){
		super(PromotionRule.class);
	}

	public PromotionRule findByRuleId(Long ruleId) {
		return this.get(ruleId);
	}

	public PromotionRule saveRule(PromotionRule promotionRule){
		if(promotionRule==null) return null;
		String dir=System.getProperty("RULE_DIR");
		if(dir==null ||dir.isEmpty()) throw new NullPointerException("RULE_DIR system property isn't set");
		
		String ruleFileName=promotionRule.getRuleFileName();
		if(ruleFileName==null || ruleFileName.isEmpty()) promotionRule.setRuleFileName(promotionRule.getRuleName()+".mvel");
		
		String ruleUIFileName=promotionRule.getRuleUIFileName();
		if(ruleUIFileName==null || ruleUIFileName.isEmpty()) promotionRule.setRuleUIFileName(promotionRule.getRuleName()+".ui.mvel");
		
		Date date=new Date();
		if(promotionRule.getDateAdded()==null) promotionRule.setDateAdded(date);
		if(promotionRule.getLastModified()==null) promotionRule.setLastModified(date);
		
		Set<PromotionRuleOption> options=promotionRule.getRuleOptions();
		if(options!=null){
			for(PromotionRuleOption option: options){
				if(option.getDateAdded()==null) option.setDateAdded(date);
				if(option.getLastModified()==null) option.setLastModified(date);
			}
		}
		
		getHibernateTemplate().saveOrUpdate(promotionRule);
		
		return promotionRule;
	}

	public void setInstalled(Long ruleId, Boolean installed) {
		PromotionRule rule=this.findByRuleId(ruleId);
		if(rule==null) throw new IllegalArgumentException("cannot find the rule for id:"+ruleId);
		
		if(!rule.isInstalled().equals(installed)){
			rule.setInstalled(installed);
			this.save(rule);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PromotionRule> findAllRules(Boolean installedOnly) {
		Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
		Query q = null;
		if(installedOnly) 
			q=session.createQuery("from PromotionRule p where p.installed=true");
		else
			q=session.createQuery("from PromotionRule p");
		
		return (List<PromotionRule>)q.list();
	}

	public void deleteRule(Long ruleId) {
		this.remove(ruleId);
	}

	public PromotionRuleOption findPromotionRuleOption(Long ruleId,
			String optionSpecKey) {
		Session session = SessionFactoryUtils.getSession(getSessionFactory(), true);
		Query q=session.createQuery("from PromotionRuleOption spec where spec.promotionRule.ruleId=? and spec.key=?");
		q.setLong(0, ruleId);
		q.setString(1, optionSpecKey);
		List<?> resultList=q.list();
		if(resultList==null ||resultList.isEmpty()) return null;
		else return (PromotionRuleOption)resultList.get(0);
	}

    public int getPromotionRulesCount() {
        Query q = getSession().createQuery("select count(s.id) from PromotionRule s");
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }


    public List<PromotionRule> getPaginatedPromotionRulesList(int start_index, Integer num_per_page) {
        String hql = " from PromotionRule s";
        Query q = getSession().createQuery(hql);
        q.setFirstResult(start_index);
        q.setMaxResults(num_per_page);
        return q.list();
    }

    public void deletePromotionRuleByIds(String promotionRuleIDs) {
        promotionRuleIDs = promotionRuleIDs.trim();
        if (promotionRuleIDs.endsWith("_")) {
            promotionRuleIDs = promotionRuleIDs.substring(0, promotionRuleIDs.length() - 1);
        }
        String hql = "delete PromotionRule s where s.id in (" + promotionRuleIDs.replaceAll("_", ",") + ")";
        Query q = getSession().createQuery(hql);
        q.executeUpdate();
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
