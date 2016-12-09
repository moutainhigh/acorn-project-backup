package com.chinadrtv.erp.uc.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.uc.dao.CardtypeDao;

/**
 * Created with IntelliJ IDEA.
 * Title: CardtypeDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Repository("cardtypeDao")
public class CardtypeDaoImpl extends GenericDaoHibernate<Cardtype, String> implements CardtypeDao {

    public CardtypeDaoImpl() {
        super(Cardtype.class);
    }

    @Override
    public List<Cardtype> queryUsefulCardtypes() {
        return getHibernateTemplate().executeFind(new HibernateCallback<List<Cardtype>>() {
			public List<Cardtype> doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Cardtype.class);
                criteria.add(Restrictions.eq("valid", "-1"));
                criteria.addOrder(Order.asc("bankName"));
                return criteria.list();
            }
        });
    }

	@Override
	public List<String> getByCategory(final String category) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Map<String, String>>>()  {
			@Override
			public List<Map<String, String>> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Cardtype.class);
                ProjectionList pl = Projections.projectionList();
                pl.add(Projections.property("bankName"));
                criteria.setProjection(Projections.distinct(pl));
                if (StringUtils.isNotEmpty(category)) criteria.add(Restrictions.eq("type1", category));
                criteria.add(Restrictions.eq("valid", "-1"));
                criteria.add(Restrictions.isNotNull("bankName"));
                criteria.addOrder(Order.asc("bankName"));
                return criteria.list();
			}
		});
	}

	@Override
	public List<Cardtype> getAllBankCards() {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Cardtype>>()  {
			@Override
			public List<Cardtype> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Cardtype.class);
                criteria.add(Restrictions.eq("valid", "-1"));
                criteria.add(Restrictions.eq("type1", "2"));
                criteria.addOrder(Order.asc("bankName"));
				return criteria.list();
			}
		});
	}

    public List<Cardtype> getCardtypes(List<String> cardtypeList)
    {
        if(cardtypeList==null||cardtypeList.size()==0)
            return new ArrayList<Cardtype>();
        Map<String, Parameter> mapParms=new HashMap<String, Parameter>();
        StringBuilder strBld=new StringBuilder("from Cardtype where ");
        for(int index=0;index<cardtypeList.size();index++)
        {
            String name="cardtypeid"+index;
            if(index==0)
            {
                strBld.append("cardtypeid=:"+name);
            }
            else
            {
                strBld.append(" or cardtypeid=:"+name);
            }
            mapParms.put(name,new ParameterString(name,cardtypeList.get(index)));
        }

        return this.findList(strBld.toString(), mapParms);
    }
}
