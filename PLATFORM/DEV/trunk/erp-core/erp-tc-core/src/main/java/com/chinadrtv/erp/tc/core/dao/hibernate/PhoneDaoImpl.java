package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.tc.core.dao.PhoneDao;


/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-28
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.PhoneDaoImpl")
public class PhoneDaoImpl extends GenericDaoHibernateBase<Phone, Long> implements PhoneDao {

    public PhoneDaoImpl() {
        super(Phone.class);
    }
    
    @Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
    
    public Phone findByPhoneNum(String PhoneNum) {
        String hql ="from Phone p where p.phn2 =:phn";
        Parameter parameter = new ParameterString("phn",PhoneNum);
        Phone phone = find(hql,parameter);
        return phone;
    }

    public  void saveOrUpdate(Phone phone){
        getSession().saveOrUpdate(phone);
    }


	/* (非 Javadoc)
	* <p>Title: getByContactIdAndType</p>
	* <p>Description: </p>
	* @param contactId
	* @param phoneType
	* @return
	* @see com.chinadrtv.erp.tc.dao.PhoneDao#getByContactIdAndType(java.lang.String, int)
	*/ 
	public Phone getByContactIdAndType(String contactId, int phoneType) {
		String hql = "select p from Phone p where p.contactid=:contactId and p.phonetypid=:phoneType";
		//Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter<String> contactParam = new ParameterString("contactId", contactId);
		Parameter<Integer> phoneTypeParam = new ParameterInteger("phoneType", phoneType);
		Phone phone = this.find(hql, contactParam, phoneTypeParam);
		return phone;
	}

    public List<Phone> findPhoneListFromContactAndType(String contactId, int phoneType)
    {
        String hql = "select p from Phone p where p.contactid=:contactId and p.phonetypid=:phoneType";
        //Map<String, Parameter> params = new HashMap<String, Parameter>();
        Parameter<String> contactParam = new ParameterString("contactId", contactId);
        Parameter<Integer> phoneTypeParam = new ParameterInteger("phoneType", phoneType);
        return this.findList(hql, contactParam, phoneTypeParam);
    }
}
