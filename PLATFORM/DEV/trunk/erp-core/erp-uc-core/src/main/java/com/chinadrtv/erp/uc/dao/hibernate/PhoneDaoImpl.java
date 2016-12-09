package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;
import com.chinadrtv.erp.uc.service.SchemaNames;
import com.chinadrtv.erp.user.aop.Mask;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PhoneDaoImpl extends GenericDaoHibernateBase<Phone, Long>
        implements PhoneDao {
	
	@Autowired
	private SchemaNames schemaNames;

    public PhoneDaoImpl() {
        super(Phone.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public void clearPrimePhone(String contactId) {
        String hql = " update Phone p set p.prmphn ='N' where p.contactid=:contactId and p.prmphn='Y' ";
        Query query= this.getSession().createQuery(hql);
        query.setString("contactId", contactId);
        query.executeUpdate();
    }

    public void setPrimePhone(String contactId, String phoneId) {
        String hql = " update Phone p set p.prmphn ='Y' where p.contactid=:contactId and p.phoneid=:phoneId ";
        Query query= this.getSession().createQuery(hql);
        query.setString("contactId", contactId);
        query.setString("phoneId", phoneId);
        query.executeUpdate();
    }

    public void unsetBackupPhone(String phoneId) {
        String hql = " update Phone p set p.prmphn ='N' where p.phoneid=:phoneId and p.prmphn !='Y' ";
        Query query= this.getSession().createQuery(hql);
        query.setString("phoneId", phoneId);
        query.executeUpdate();
    }
    public void setBackupPhone(String phoneId) {
        String hql = " update Phone p set p.prmphn ='B' where p.phoneid=:phoneId and p.prmphn !='Y' ";
        Query query= this.getSession().createQuery(hql);
        query.setString("phoneId", phoneId);
        query.executeUpdate();
    }

    @Override
    public boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn2, String contactId) {
        String sql = "select p.phoneid from iagent.contact c inner join iagent.phone p on c.CONTACTID=p.CONTACTID where c.NAME=:name and p.phn2=:phn2 and p.CONTACTID=:contactId";
        Query query = this.getSession().createSQLQuery(sql);
        query.setString("name", name);
        query.setString("phn2", phn2);
        query.setString("contactId", contactId);
        List<String> phoneIds = query.list();
        if (null == phoneId) return query.list().size() > 0 ? true : false;
        else {
            for (String cId : phoneIds) {
                if (!StringUtils.equals(phoneId.toString(), cId)) return true;
            }
            return false;
        }

    }

    @Override
    public boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn2, String contactId, String phoneType, String phn1, String phn3) {
        StringBuffer sql = new StringBuffer();
        sql.append("select p.phoneid from iagent.contact c inner join iagent.phone p on c.CONTACTID=p.CONTACTID where c.NAME=:name and p.phn2=:phn2 and p.CONTACTID=:contactId ");
        if (StringUtils.isBlank(phn1)) sql.append(" and p.phn1 is null ");
        if (StringUtils.isBlank(phn3)) sql.append(" and p.phn3 is null ");
        if (StringUtils.isNotBlank(phn1) && !StringUtils.equals("0", phn1)) sql.append(" and p.phn1 =:phn1 ");
        if (StringUtils.isNotBlank(phn3)) sql.append(" and p.phn3 =:phn3 ");
            Query query = this.getSession().createSQLQuery(sql.toString());
        query.setString("name", name);
        query.setString("phn2", phn2);
        query.setString("contactId", contactId);
        if (StringUtils.isNotBlank(phn1) && !StringUtils.equals("0", phn1)) query.setString("phn1", phn1);
        if (StringUtils.isNotBlank(phn3)) query.setString("phn3", phn3);
        List<String> phoneIds = query.list();
        if (null == phoneId) return query.list().size() > 0 ? true : false;
        else {
            for (String cId : phoneIds) {
                if (!StringUtils.equals(phoneId.toString(), cId)) return true;
            }
            return false;
        }

    }

    @Override
    public boolean checkExistPhone(Phone phone) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select p.phoneid from iagent.phone p where p.CONTACTID=:contactId and (p.state is null or p.state = 2 or p.state = 4)  and p.phn2=:phn2 ");
        if (StringUtils.isNotBlank(phone.getPhn1()) && !StringUtils.equals("0", phone.getPhn1())) sqlBuffer.append(" and p.phn1=:phn1 ");
        if (StringUtils.isBlank(phone.getPhn1())) sqlBuffer.append(" and p.phn1 is null ");
        if (StringUtils.isNotBlank(phone.getPhn3())) sqlBuffer.append(" and p.phn3=:phn3 ");
        if (StringUtils.isBlank(phone.getPhn3())) sqlBuffer.append(" and p.phn3 is null ");
        Query query = this.getSession().createSQLQuery(sqlBuffer.toString());
        query.setString("contactId", phone.getContactid());
        query.setString("phn2", phone.getPhn2());
        if (StringUtils.isNotBlank(phone.getPhn1()) && !StringUtils.equals("0", phone.getPhn1())) query.setString("phn1", phone.getPhn1());
        if (StringUtils.isNotBlank(phone.getPhn3())) query.setString("phn3", phone.getPhn3());
        return query.list().size() > 0 ? true : false;
    }


    public List<Phone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum) {
        return findByPhoneFullNumAndContactId(areaCode, phoneNum, childNum, null);
    }

    public List<Phone> findByPhoneFullNumAndContactId(String areaCode, String phoneNum, String childNum, String contactId) {
        StringBuffer hql = new StringBuffer();
        hql.append("from Phone p where p.phn2=:phoneNum");
        if (StringUtils.isNotBlank(areaCode) && !StringUtils.equals("0", areaCode)) {
            hql.append(" and p.phn1=:areaCode");
        }
        if (StringUtils.isBlank(areaCode)) {
            hql.append(" and p.phn1 is null");
        }
        if (StringUtils.isNotBlank(childNum)) {
            hql.append(" and p.phn3=:childNum");
        }
        if (StringUtils.isBlank(childNum)) {
            hql.append(" and p.phn3 is null");
        }
        if (StringUtils.isNotBlank(contactId)) {
            hql.append(" and p.contactid=:contactId");
        }
        Query query = this.getSession().createQuery(hql.toString());
        query.setString("phoneNum", phoneNum);
        if (StringUtils.isNotBlank(areaCode) && !StringUtils.equals("0", areaCode)) {
            query.setString("areaCode", areaCode);
        }
        if (StringUtils.isNotBlank(childNum)) {
            query.setString("childNum", childNum);
        }
        if (StringUtils.isNotBlank(contactId)) {
            query.setString("contactId", contactId);
        }
        return query.list();
    }

    public Phone findPrimePhone(String contactId) {
        String hql = "from Phone p where p.prmphn ='Y' and p.contactid=:contactId";
        Query query= this.getSession().createQuery(hql);
        query.setString("contactId", contactId);
        return (Phone)query.uniqueResult();
    }

    public List<Phone> findByContactId(String contactId, int startRow, int rows) {
        String hql = "from Phone p where p.contactid=:contactId and p.phn2 is not null order by p.phoneid";
        Query query= this.getSession().createQuery(hql);
        query.setString("contactId", contactId);
        query.setFirstResult(startRow);
        query.setMaxResults(rows);
        return query.list();
    }
    
	public Phone findByPhoneId(long phoneId) {
		Phone phone = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ")
		  .append(AddressConstant.IAGENT_SCHEMA)
		  .append(".phone p where p.phoneid=:phoneId");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("phoneId", String.valueOf(phoneId));
		List<Phone> objList = sqlQuery.addEntity(Phone.class).list();
		if(objList!=null && objList.size()>0) {
			phone = objList.get(0);
		}
		return phone;
	}

    @Override
    public boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn1, String phn2, String phn3) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select p.phoneid from iagent.contact c inner join iagent.phone p on c.CONTACTID=p.CONTACTID where c.NAME=:name and p.phn2=:phn2 ");
        if (StringUtils.isNotBlank(phn1) && !StringUtils.equals("0", phn1)) sqlBuffer.append(" and p.phn1=:phn1 ");
        if (StringUtils.isNotBlank(phn3)) sqlBuffer.append(" and p.phn3=:phn3 ");
        Query query = this.getSession().createSQLQuery(sqlBuffer.toString());
        query.setString("name", name);
        query.setString("phn2", phn2);
        if (StringUtils.isNotBlank(phn1) && !StringUtils.equals("0", phn1)) query.setString("phn1", phn1);
        if (StringUtils.isNotBlank(phn3)) query.setString("phn3", phn3);
        List<String> phoneIds = query.list();
        if (null == phoneId) return query.list().size() > 0 ? true : false;
        else {
            for (String cId : phoneIds) {
                if (!StringUtils.equals(phoneId.toString(), cId)) return true;
            }
            return false;
        }

    }

    public int findCountByContactId(String contactId) {
        String hql = "select count(p.phoneid) from Phone p where p.contactid=:contactId and p.phn2 is not null";
        Query query= this.getSession().createQuery(hql);
        query.setString("contactId", contactId);
        return Integer.valueOf(query.list().get(0).toString());
    }

	/** 
	 * <p>Title: getSequence</p>
	 * <p>Description: 手动获取Sequence</p>
	 * @return Long
	 * @see com.chinadrtv.erp.uc.dao.PhoneDao#getSequence()
	 */ 
	@Override
	public Long getSequence() {
		Query q = getSession().createSQLQuery("select " + schemaNames.getAgentSchema() + "SEQPHONE.nextval from dual");
		Long seq = Long.parseLong(q.list().get(0).toString());
		return seq;
	}

    @Override
    public void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto) {
        StringBuffer hql = new StringBuffer();
        hql.append("update Phone p set p.lastCallDate=:lastCallDate where p.contactid=:contactId ");
        if (StringUtils.isNotBlank(updateLastCallDateDto.getPhn2())) hql.append(" and p.phn2=:phn2 ");
        if (updateLastCallDateDto.getCustomerPhoneId() != null) hql.append(" and p.phoneid=:phoneId");
        Query query= this.getSession().createQuery(hql.toString());
        query.setDate("lastCallDate", updateLastCallDateDto.getLastCallDate());
        query.setString("contactId", updateLastCallDateDto.getCustomerId());
        if (StringUtils.isNotBlank(updateLastCallDateDto.getPhn2()))
            query.setString("phn2", updateLastCallDateDto.getPhn2());
        if (updateLastCallDateDto.getCustomerPhoneId() != null)
            query.setLong("phoneId", updateLastCallDateDto.getCustomerPhoneId());
        query.executeUpdate();
    }

	/** 
	 * <p>Title: queryMobilePhoneByContact</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<Phone>
	 * @see com.chinadrtv.erp.uc.dao.PhoneDao#queryMobilePhoneByContact(java.lang.String)
	 */ 
	@Override
	public List<Phone> queryMobilePhoneByContact(String contactId) {
		String hql = "select p from Phone p where p.contactid=:contactId and p.phonetypid='4'";
		return this.findList(hql, new ParameterString("contactId", contactId));
	}

    @Override
    public void addToBlackList(Long phoneId) {
        String hql = "update Phone p set p.black=1 where p.phoneid=:phoneId";
        Query query= this.getSession().createQuery(hql);
        query.setString("phoneId", phoneId.toString());
        query.executeUpdate();
    }

    @Override
    public void removeFromBlackList(Long phoneId) {
        String hql = "update Phone p set p.black=0 where p.phoneid=:phoneId";
        Query query= this.getSession().createQuery(hql);
        query.setString("phoneId", phoneId.toString());
        query.executeUpdate();
    }

	/** 
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#get(java.io.Serializable)
	 */ 
	@Override
	public Phone get(Long id) {
		String hql = "select p from Phone p where p.id=:id";
		return this.find(hql, new ParameterString("id", id + ""));
	}

	/** 
	 * <p>Title: updatePhone</p>
	 * <p>Description: </p>
	 * @param phone
	 * @see com.chinadrtv.erp.uc.dao.PhoneDao#updatePhone(com.chinadrtv.erp.model.Phone)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public void updatePhone(Phone phone) {
		String hql = "update Phone set contactid=:contactid, phn1=:phn1, phn2=:phn2, phn3=:phn3, entityid=:entityid, " +
					 " phonetypid=:phonetypid, prmphn=:prmphn, remark=:remark, contactRowid=:contactRowid, phoneNum=:phoneNum, state=:state" +
					 " where phoneid=:phoneid";
		this.getSession().evict(phone);
		Map<String, Parameter> paraMap = new HashMap<String, Parameter>();
		paraMap.put("contactid", new ParameterString("contactid", phone.getContactid()));
		paraMap.put("phn1", new ParameterString("phn1", phone.getPhn1()));
		paraMap.put("phn2", new ParameterString("phn2", phone.getPhn2()));
		paraMap.put("phn3", new ParameterString("phn3", phone.getPhn3()));
		paraMap.put("entityid", new ParameterString("entityid", phone.getEntityid()));
		paraMap.put("phonetypid", new ParameterString("phonetypid", phone.getPhonetypid()));
		paraMap.put("prmphn", new ParameterString("prmphn", phone.getPrmphn()));
		paraMap.put("remark", new ParameterString("remark", phone.getRemark()));
		paraMap.put("contactRowid", new ParameterString("contactRowid", phone.getContactRowid()));
		paraMap.put("phoneNum", new ParameterString("phoneNum", phone.getPhoneNum()));
		paraMap.put("state", new ParameterInteger("state", phone.getState()));
		paraMap.put("phoneid", new ParameterString("phoneid", phone.getPhoneid()+""));
		
		this.execUpdate(hql, paraMap);
	}

	/** 
	 * <p>Title: remove</p>
	 * <p>Description: </p>
	 * @param id
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#remove(java.io.Serializable)
	 */ 
	@Override
	public void remove(Long id) {
		String hql = "delete from Phone where phoneid=:phoneid";
		this.execUpdate(hql, new ParameterString("phoneid", id+""));
	}

    @Override
    public List<Phone> findBackupPhoneByContactId(String contactId, String phoneId) {
        SQLQuery q = getSession().createSQLQuery("select * from " + schemaNames.getAgentSchema() + "PHONE p where p.CONTACTID=:contactId and p.PHONEID !=:phoneId and p.PRMPHN='B' order by p.PHONEID");
        q.setString("contactId", contactId + "");
        q.setString("phoneId", phoneId + "");
        return q.addEntity(Phone.class).list();
    }

	@Override
	public void saveOrUpdate(Phone p) {
		if(null != p && p.getPhoneid() != null){
			this.updatePhone(p);
		}else{
			super.save(p);
		}
	}
}
