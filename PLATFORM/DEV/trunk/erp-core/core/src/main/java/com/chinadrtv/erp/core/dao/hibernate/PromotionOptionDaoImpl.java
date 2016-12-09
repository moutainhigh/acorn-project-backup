package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.PromotionOptionDao;
import com.chinadrtv.erp.model.PromotionOption;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-26
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PromotionOptionDaoImpl  extends GenericDaoHibernate<PromotionOption, Long> implements PromotionOptionDao {
    public PromotionOptionDaoImpl() {
        super(PromotionOption.class);
    }

    public void updatePromotionOption(PromotionOption promotionOption) {
         getHibernateTemplate().saveOrUpdate(promotionOption);
    }

    public void deletePromotionOption(PromotionOption promotionOption) {
       remove(promotionOption.getOptionId());
    }

    public void deletePromotionOption(Long key) {
        remove(key);
    }

    public void deleteByPromotion(Long key) {
        String hql = "delete PromotionOption s where s.promotion.id =:id";
        Query q = getSession().createQuery(hql);
        q.setLong("id", key);
        q.executeUpdate();
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
