package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.uc.dao.PotentialContactDao;
import com.chinadrtv.erp.uc.dto.CustomerDto;

@Repository
public class PotentialContactDaoImpl extends
		GenericDaoHibernate<PotentialContact, Long> implements
		PotentialContactDao {

	public PotentialContactDaoImpl() {
		super(PotentialContact.class);
	}

	public Long savePotentialContact(PotentialContact potentialContact) {
		Session session = getSession();
		session.save(potentialContact);
		return potentialContact.getId();
	}

	public void updatePotentialContact(PotentialContact potentialContact) {
		Session session = getSession();
		session.saveOrUpdate(potentialContact);
		session.flush();
	}

	public List<CustomerDto> findByBaseCondition(String potentialContactId,
			String name, String phoneNum, String phone1, String phone2,
			String phone3, int index, int rows) {
		StringBuffer hql = new StringBuffer();
		hql.append("select p.id, p.name, p.gender, p.productId, pp.phoneNum, p.call_Length, pp.phone1, pp.phone2, pp.phone3, pp.phoneTypeId, p.comments ");
		hql.append(" from PotentialContact p, PotentialContactPhone pp ");
		hql.append(" where p.id=pp.potentialContactId ");
		if (StringUtils.isNotBlank(potentialContactId)) {
			hql.append(" and p.id=:potentialContactId");
		}
		if (StringUtils.isNotBlank(name)) {
			hql.append(" and p.name=:name");
		}
		if (StringUtils.isNotBlank(phoneNum)) {
			hql.append(" and pp.phoneNum=:phoneNum");
		}
		if (StringUtils.isNotBlank(phone1)) {
			hql.append(" and pp.phone1=:phone1");
		}
		if (StringUtils.isNotBlank(phone2)) {
			hql.append(" and pp.phone2=:phone2");
		}
		if (StringUtils.isNotBlank(phone3)) {
			hql.append(" and pp.phone3=:phone3");
		}
		hql.append(" order by p.id, pp.id");
		Query hqlQuery = this.getSession().createQuery(hql.toString());
		hqlQuery.setFirstResult(index);
		hqlQuery.setMaxResults(rows);
		if (StringUtils.isNotBlank(potentialContactId)) {
			hqlQuery.setParameter("potentialContactId",
					Long.valueOf(potentialContactId));
		}
		if (StringUtils.isNotBlank(name)) {
			hqlQuery.setParameter("name", name);
		}
		if (StringUtils.isNotBlank(phoneNum)) {
			hqlQuery.setParameter("phoneNum", phoneNum);
		}
		if (StringUtils.isNotBlank(phone1)) {
			hqlQuery.setParameter("phone1", phone1);
		}
		if (StringUtils.isNotBlank(phone2)) {
			hqlQuery.setParameter("phone2", phone2);
		}
		if (StringUtils.isNotBlank(phone3)) {
			hqlQuery.setParameter("phone3", phone3);
		}
		return convertToCustomerDtoList(hqlQuery.list());
	}

	private List<CustomerDto> convertToCustomerDtoList(List objList) {
		List<CustomerDto> result = new ArrayList<CustomerDto>();
		Object[] obj = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			CustomerDto customerDto = new CustomerDto();
			customerDto.setPotentialContactId(obj[0] != null ? obj[0]
					.toString() : null);
			customerDto.setName(obj[1] != null ? obj[1].toString() : null);
			customerDto.setSex(obj[2] != null ? obj[2].toString() : null);
			customerDto.setProductId(obj[3] != null ? obj[3].toString() : null);
			customerDto.setPhoneNum(obj[4] != null ? obj[4].toString() : null);
			customerDto.setCall_Length(obj[5] != null ? Long.valueOf(obj[5]
					.toString()) : null);
			customerDto.setPhone1(obj[6] != null ? obj[6].toString() : null);
			customerDto.setPhone2(obj[7] != null ? obj[7].toString() : null);
			customerDto.setPhone3(obj[8] != null ? obj[8].toString() : null);
			customerDto.setPhoneType(obj[9] != null ? obj[9].toString() : null);
			customerDto
					.setComments(obj[10] != null ? obj[10].toString() : null);
			result.add(customerDto);
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: getByContactId</p> <p>Description: </p>
	 * 
	 * @param contactId
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.uc.dao.PotentialContactDao#getByContactId(java.lang
	 * .Long)
	 */
	@Override
	public PotentialContact getByContactId(Long contactId) {
		// TODO Auto-generated method stub
		String hql = "from PotentialContact where  contactId =?";
		List<PotentialContact> list = (List<PotentialContact>) getHibernateTemplate()
				.find(hql, new Object[] { contactId });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
