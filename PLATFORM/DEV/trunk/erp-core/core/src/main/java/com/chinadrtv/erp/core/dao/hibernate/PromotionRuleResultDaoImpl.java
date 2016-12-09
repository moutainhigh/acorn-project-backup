package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.PromotionResultDao;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionRuleResult;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PromotionRuleResultDaoImpl extends GenericDaoHibernate<PromotionRuleResult, Long> implements
        PromotionResultDao {

	public PromotionRuleResultDaoImpl() {
		super(PromotionRuleResult.class);
	}

	public List<PromotionRuleResult> findByPromotion(Promotion promotion) {
		//	 TODO
		return null;
	}

    public List<PromotionRuleResult> findByOrderId(Long orderId) {
        return getHibernateTemplate().find("from PromotionRuleResult  p where p.orderId=?", orderId);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

}
