/*
 * @(#)CardDaoImpl.java 1.0 2013-5-7下午4:28:25
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.CardDao;

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
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-7 下午4:28:25 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("cardDao")
public class CardDaoImpl extends GenericDaoHibernate<Card, Long> implements CardDao {

	public CardDaoImpl() {
		super(Card.class);
	}

	@Override
	public boolean isCardNumberExists(final Card card) {
		return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
			public Boolean doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("contactId", card.getContactId()));
				criteria.add(Restrictions.eq("cardNumber", card.getCardNumber()));
				return criteria.uniqueResult() != null;
			}
		});
	}

	@Override
	public List<Card> getCardsByContactId(final String contactId) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Card>>() {
			public List<Card> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("contactId", contactId));
				criteria.addOrder(Order.asc("type"));
				return criteria.list();
			}
		});
	}

	@Override
	public Card getCardByContactId(final String contactId, final String type) {
		return getHibernateTemplate().execute(new HibernateCallback<Card>() {
			public Card doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("contactId", contactId));
				criteria.add(Restrictions.eq("type", type));
				return (Card) criteria.uniqueResult();
			}
		});
	}
	
	@Override
	public List<Card> getCardsByContactId(final String contactId, final String type) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Card>>() {
			public List<Card> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("contactId", contactId));
				criteria.add(Restrictions.eq("type", type));
				return criteria.list();
			}
		});
	}

	@Override
	public List<Card> getCardsByContactIdExcludeCardTypes(final String contactId, final String... types) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Card>>() {
			public List<Card> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("contactId", contactId));
				if (types != null && types.length > 0) criteria.add(Restrictions.not(Restrictions.in("type", types)));
				criteria.addOrder(Order.desc("validDate"));
				return criteria.list();
			}
		});
	}

	@Override
    public List<Card> getCardsByContactIdIncludeCardTypes(final String contactId, final String... types) {
        return getHibernateTemplate().executeFind(new HibernateCallback<List<Card>>() {
            public List<Card> doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Card.class);
                criteria.add(Restrictions.eq("contactId", contactId));
                if (types != null && types.length > 0) criteria.add(Restrictions.in("type", types));
                criteria.addOrder(Order.desc("validDate"));
                criteria.list();
                return criteria.list();
            }
        });
    }

	@Override
	public Card getCardByCardNumber(final String cardNum) {
		return getHibernateTemplate().execute(new HibernateCallback<Card>() {
			public Card doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("cardNumber", cardNum));
				return (Card) criteria.uniqueResult();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Card> getCards(List<Long> cardIdList)
    {
        if(cardIdList==null||cardIdList.size()==0)
            return new ArrayList<Card>();
        Map<String, Parameter> mapParms=new HashMap<String, Parameter>();
        StringBuilder strBld=new StringBuilder("from Card where ");
        for(int index=0;index<cardIdList.size();index++)
        {
            String name="cardId"+index;
           if(index==0)
           {
              strBld.append("cardId=:"+name);
           }
           else
           {
               strBld.append(" or cardId=:"+name);
           }
            mapParms.put(name,new ParameterLong(name,cardIdList.get(index)));
        }

        return this.findList(strBld.toString(), mapParms);
    }

	@Override
	public Card getCardByCondition(final Card condition) {
		return getHibernateTemplate().execute(new HibernateCallback<Card>() {
			@Override
			public Card doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Card.class);
				criteria.add(Restrictions.eq("type", condition.getType()));
				criteria.add(Restrictions.eq("cardNumber", condition.getCardNumber()));
				criteria.add(Restrictions.eq("validDate", condition.getValidDate()));
				criteria.add(Restrictions.or(
						Restrictions.isNull("state"),
						Restrictions.ne("state", CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED)));
				if (StringUtils.isNotEmpty(condition.getExtraCode())) {
					criteria.add(Restrictions.eq("extraCode", condition.getExtraCode()));
				}
				return (Card) criteria.uniqueResult();
			}
		});
	}
}
