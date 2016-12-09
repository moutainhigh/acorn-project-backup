package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.dao.PotentialContactPhoneDao;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-10
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PotentialContactPhoneDaoImpl extends GenericDaoHibernate<PotentialContactPhone, Long> implements PotentialContactPhoneDao {
    public PotentialContactPhoneDaoImpl() {
        super(PotentialContactPhone.class);
    }

    public void clearPrimePotentialContactPhone(String potentialContactId) {
        String hql = " update PotentialContactPhone p set p.prmphn ='N' where p.potentialContactId=:potentialContactId and p.prmphn='Y' ";
        Query query = this.getSession().createQuery(hql);
        query.setString("potentialContactId", potentialContactId);
        query.executeUpdate();
    }

    public void setPrimePotentialContactPhone(String potentialContactId, String potentialContactPhoneId) {
        String hql = " update PotentialContactPhone p set p.prmphn ='Y' where p.potentialContactId=:potentialContactId and p.id=:potentialContactPhoneId ";
        Query query = this.getSession().createQuery(hql);
        query.setString("potentialContactId", potentialContactId);
        query.setString("potentialContactPhoneId", potentialContactPhoneId);
        query.executeUpdate();
    }

    public List<PotentialContactPhone> getPotentialContactPhones(Long potentialContactId){
        String hql = "from PotentialContactPhone a where a.potentialContactId=:potentialContactId";
        Query query = this.getSession().createQuery(hql);
        query.setParameter("potentialContactId",String.valueOf(potentialContactId));
        return query.list();
    }

    public List<PotentialContactPhone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum) {
        return findByPhoneFullNumAndPotentialContactId(areaCode, phoneNum, childNum, null);
    }

    public List<PotentialContactPhone> findByPhoneFullNumAndPotentialContactId(String areaCode, String phoneNum, String childNum, String potentialContactId) {
        StringBuffer hql = new StringBuffer();
        hql.append("from PotentialContactPhone a where a.phone2=:phoneNum");
        if (StringUtils.isNotBlank(areaCode)) {
            hql.append(" and a.phone1=:areaCode");
        }
        if (StringUtils.isNotBlank(childNum)) {
            hql.append(" and a.phone3=:childNum");
        }
        if (StringUtils.isNotBlank(potentialContactId)) {
            hql.append(" and a.potentialContactId=:potentialContactId");
        }
        Query query = this.getSession().createQuery(hql.toString());
        query.setParameter("phoneNum", phoneNum);
        if (StringUtils.isNotBlank(areaCode)) {
            query.setString("areaCode", areaCode);
        }
        if (StringUtils.isNotBlank(childNum)) {
            query.setString("childNum", childNum);
        }
        if (StringUtils.isNotBlank(potentialContactId)) {
            query.setString("potentialContactId", potentialContactId);
        }
        return query.list();
    }

    public PotentialContactPhone findPrimePhone(String customerId) {
        String hql = "from PotentialContactPhone p where p.prmphn ='Y' and p.potentialContactId=:potentialContactId";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactId", customerId);
        return (PotentialContactPhone)query.uniqueResult();
    }

    public int findCountByPotentialContactId(String potentialContactId) {
        String hql = "select count(p.id) from PotentialContactPhone p where p.potentialContactId=:potentialContactId";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactId", potentialContactId);
        return Integer.valueOf(query.list().get(0).toString());
    }

    public List<PotentialContactPhone> findByPotentialContactId(String potentialContactId, int startRow, int rows) {
        String hql = "from PotentialContactPhone p where p.potentialContactId=:potentialContactId order by p.id";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactId", potentialContactId);
        query.setFirstResult(startRow);
        query.setMaxResults(rows);
        return query.list();

    }

    @Override
    public void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto) {
        StringBuffer hql = new StringBuffer();
        hql.append("update PotentialContactPhone p set p.lastCallDate=:lastCallDate where p.potentialContactId=:contactId ");
        if (StringUtils.isNotBlank(updateLastCallDateDto.getPhn2())) hql.append(" and p.phone2=:phn2 ");
        if (updateLastCallDateDto.getCustomerPhoneId() != null) hql.append(" and p.id=:phoneId");
        Query query= this.getSession().createQuery(hql.toString());
        query.setDate("lastCallDate", updateLastCallDateDto.getLastCallDate());
        query.setString("contactId", updateLastCallDateDto.getCustomerId());
        if (StringUtils.isNotBlank(updateLastCallDateDto.getPhn2()))
            query.setString("phn2", updateLastCallDateDto.getPhn2());
        if (updateLastCallDateDto.getCustomerPhoneId() != null)
            query.setLong("phoneId", updateLastCallDateDto.getCustomerPhoneId());
        query.executeUpdate();
    }

    @Override
    public void addToBlackList(Long phoneId) {
        String hql = "update PotentialContactPhone p set p.black=1 where p.id=:phoneId";
        Query query= this.getSession().createQuery(hql);
        query.setLong("phoneId", phoneId);
        query.executeUpdate();
    }

    @Override
    public void removeFromBlackList(Long phoneId) {
        String hql = "update PotentialContactPhone p set p.black=0 where p.id=:phoneId";
        Query query= this.getSession().createQuery(hql);
        query.setLong("phoneId", phoneId);
        query.executeUpdate();
    }

    @Override
    public List<PotentialContactPhone> findBackupPCPhoneByPContactId(String potentialContactId, String phoneId) {
        String hql = "from PotentialContactPhone p where p.prmphn='B' and p.potentialContactId=:potentialContactId and p.id!=:phoneId order by p.id";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactId", potentialContactId);
        query.setString("phoneId", phoneId);
        return query.list();
    }

    @Override
    public void unsetBackupPCPhone(String potentialContactPhoneId) {
        String hql = " update PotentialContactPhone p set p.prmphn ='N' where p.id=:potentialContactPhoneId and p.prmphn !='Y' ";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactPhoneId", potentialContactPhoneId);
        query.executeUpdate();
    }

    @Override
    public void setBackupPCPhone(String potentialContactPhoneId) {
        String hql = " update PotentialContactPhone p set p.prmphn ='B' where p.id=:potentialContactPhoneId and p.prmphn !='Y' ";
        Query query= this.getSession().createQuery(hql);
        query.setString("potentialContactPhoneId", potentialContactPhoneId);
        query.executeUpdate();
    }
}
