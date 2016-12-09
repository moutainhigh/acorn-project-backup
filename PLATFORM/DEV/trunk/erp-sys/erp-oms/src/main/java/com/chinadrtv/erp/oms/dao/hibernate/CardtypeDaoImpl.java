package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.oms.dao.*;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.Date;
import java.util.List;

@Repository
public class CardtypeDaoImpl extends GenericDaoHibernate<Cardtype, java.lang.String> implements CardtypeDao{

	public CardtypeDaoImpl() {
	    super(Cardtype.class);
	}
	
	
	public Cardtype getCardtypeById(String cardtypeId) {
        Session session = getSession();
        return (Cardtype)session.get(Cardtype.class, cardtypeId);
    }
	
	
	    public List getAllCardtype()
    {
			  Session session = getSession();
			  String sqlString = "select cardname||decode(type3,'1','(上海)') as name ,cardtypeid from iagent.cardtype where type1='2'";
			     //以SQL语句创建SQLQuery对象
			     List list = session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			   log.info(list);
        return list;
    }

    public List<Cardtype> getAllCardtype(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Cardtype");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }

    public List<String> getCreditCardNames(){
        Session session = getSession();
        Query query = session.createQuery("select distinct a.cardname from Cardtype a where a.type1='2' order by a.cardname");
        return query.list();
    }
	
	
    public void saveOrUpdate(Cardtype cardtype){
        getSession().saveOrUpdate(cardtype);
    }
	

}
