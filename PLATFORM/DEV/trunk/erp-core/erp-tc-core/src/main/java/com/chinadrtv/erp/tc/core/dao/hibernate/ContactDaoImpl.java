package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.tc.core.utils.BulkListSplitter;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.ContactDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-1-4
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.ContactDaoImpl")
public class ContactDaoImpl extends GenericDaoHibernateBase<Contact, String> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Autowired
    private SchemaNames schemaNames;

    public Contact findByContactId(String ContactId) {
        String hql ="from Contact c where c.contactid = :ContactId";
        Parameter parameter = new ParameterString("ContactId",ContactId);
        Contact contact = find(hql, parameter);
        return contact;
    }
    
    public List<Contact> findByContactName(String name) {
    	String hql = "from Contact c where c.name = :name";
        @SuppressWarnings("rawtypes")
		Parameter parameter = new ParameterString("name", name);
        return findList(hql, parameter);
    }

    public String GetContactId(){
        Query q = getSession().createSQLQuery("select "+ schemaNames.getAgentSchema()+"seqcontact.nextval from dual");
        //q.setString("skuCode",skuCode);
        return  q.list().get(0).toString();
        //if q.list().size() > 0 )
        //return q.list().size()>0;
    }
    
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<Contact> getContactFromIds(List<String> contactIdList)
    {
        List<Contact> contactList=new ArrayList<Contact>();
        BulkListSplitter<String> bulkListSplitter=new BulkListSplitter<String>(50,"Contact","contactid");
        List<List<String>> bulkIdList = bulkListSplitter.splitList(contactIdList);
        for(List<String> itemList:bulkIdList)
        {
            Map<String,String> mapParms =new HashMap<String, String>();
            String hql = bulkListSplitter.getHql(contactIdList, mapParms);
            contactList.addAll(getContacts(hql,mapParms));
        }

        return contactList;
    }

    private List<Contact> getContacts(String hql, Map<String,String> mapParms)
    {
        Query query = this.getSession().createQuery(hql);
        for(Map.Entry<String, String> entry : mapParms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return query.list();
    }
}
