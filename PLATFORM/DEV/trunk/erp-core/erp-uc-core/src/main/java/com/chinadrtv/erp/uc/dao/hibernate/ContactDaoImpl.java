package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.ContactDao;
import com.chinadrtv.erp.uc.dao.OldContactexDao;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.uc.service.SchemaNames;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_ORDER;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-3
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ContactDaoImpl extends GenericDaoHibernateBase<Contact, String>
        implements ContactDao {

    @Autowired
    private SchemaNames schemaNames;
    @Autowired
    private OldContactexDao oldContactexDao;

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public Contact findByOrderId(String orderId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c");
        hql.append(" from Contact c, Order o ");
        hql.append(" where c.contactid=o.contactid ");
        hql.append(" and o.orderid=:orderId ");
        return this.find(hql.toString(), new ParameterString("orderId", orderId));
    }

    public Contact findByShipmentId(String shipmentId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c");
        hql.append(" from Contact c, Order o, ShipmentHeader s ");
        hql.append(" where c.contactid=o.contactid and o.orderid=s.orderId ");
        hql.append(" and s.shipmentId=:shipmentId ");
        return this.find(hql.toString(), new ParameterString("shipmentId", shipmentId));
    }

    public Contact findByMailId(String mailId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c");
        hql.append(" from Contact c, Order o");
        hql.append(" where c.contactid=o.contactid");
        hql.append(" and o.mailid=:mailId");
        return this.find(hql.toString(), new ParameterString("mailId", mailId));
    }

    private List<CustomerDto> convertToCustomerDtoList(List objList) {
        List<CustomerDto> result = new ArrayList<CustomerDto>();
        Object[] obj = null;
        for (int i = 0; i < objList.size(); i++) {
            obj = (Object[]) objList.get(i);
            CustomerDto customerDto = new CustomerDto();
            customerDto.setContactId(obj[0] != null ? obj[0].toString() : null);
            customerDto.setName(obj[1] != null ? obj[1].toString() : null);
            customerDto.setBirthday(obj[2] != null ? obj[2].toString() : null);
            customerDto.setSex(obj[3] != null ? obj[3].toString() : null);
            customerDto.setContactType(obj[4] != null ? obj[4].toString() : null);
            customerDto.setCustomerSource(obj[5] != null ? obj[5].toString() : null);
            customerDto.setDataType(obj[6] != null ? obj[6].toString() : null);
            customerDto.setCrdt(obj[7] != null ? obj[7].toString() : null);
            customerDto.setCrtm(obj[8] != null ? obj[8].toString() : null);
            customerDto.setCrusr(obj[9] != null ? obj[9].toString() : null);
            customerDto.setLevel(obj[10] != null ? obj[10].toString() : null);
            customerDto.setContactType(obj[11].toString());
            result.add(customerDto);
        }
        return result;
    }

    public List<CustomerDto> findByPhoneAndNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto, int index, int rows) {
        String provinceId = customerBaseSearchDto.getProvinceId();
        String cityId = customerBaseSearchDto.getCityId();
        String countyId = customerBaseSearchDto.getCountyId();
        String areaId = customerBaseSearchDto.getAreaId();
        String name = customerBaseSearchDto.getName();
        String phone = customerBaseSearchDto.getPhone();
        StringBuilder hql = new StringBuilder();
        hql.append(" select c.contactid,c.name,c.birthday,c.sex,c.contacttype,c.customersource,c.datatype,c.crdt,c.crtm,c.crusr,c.memberlevel,'1' ");
        hql.append(createSqlForPhoneAndNameAndAddress(name, provinceId, cityId, countyId, areaId));
        hql.append(" order by c.contactid ");
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setParameter("phone", phone);
        hqlQuery.setParameter("phoneWithZero", "0"+phone);
        hqlQuery.setFirstResult(index);
        hqlQuery.setMaxResults(rows);
        if (StringUtils.isNotBlank(name)) {
            hqlQuery.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(provinceId)) {
            hqlQuery.setParameter("provinceId", provinceId);
        }
        if (StringUtils.isNotBlank(cityId)) {
            hqlQuery.setParameter("cityId", Integer.valueOf(cityId));
        }
        if (StringUtils.isNotBlank(countyId)) {
            hqlQuery.setParameter("countyId", Integer.valueOf(countyId));
        }
        if (StringUtils.isNotBlank(areaId)) {
            hqlQuery.setParameter("areaId", Integer.valueOf(areaId));
        }
        return convertToCustomerDtoList(hqlQuery.list());
    }

    private String createSqlForPhoneAndNameAndAddress(String name, String provinceId, String cityId, String countyId, String areaId) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from Contact c, Phone p, AddressExt ae, Address a ");
        hql.append(" where c.contactid=p.contactid ");
        hql.append(" and a.contactid=c.contactid and a.addressid=ae.addressId ");
        hql.append(" and (p.phn2=:phone or p.phn2=:phoneWithZero) ");
        if (StringUtils.isNotBlank(name)) {
            hql.append(" and c.name=:name ");
        }
        if (StringUtils.isNotBlank(provinceId)) {
            hql.append(" and ae.province.provinceid=:provinceId ");
        }
        if (StringUtils.isNotBlank(cityId)) {
            hql.append(" and ae.city.cityid=:cityId ");
        }
        if (StringUtils.isNotBlank(countyId)) {
            hql.append(" and ae.county.countyid=:countyId ");
        }
        if (StringUtils.isNotBlank(areaId)) {
            hql.append(" and ae.area.areaid=:areaId ");
        }
        return hql.toString();
    }


    public List<CustomerDto> findByNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto, int index, int rows) {
        String provinceId = customerBaseSearchDto.getProvinceId();
        String cityId = customerBaseSearchDto.getCityId();
        String countyId = customerBaseSearchDto.getCountyId();
        String areaId = customerBaseSearchDto.getAreaId();
        String name = customerBaseSearchDto.getName();
        StringBuilder hql = new StringBuilder();
        hql.append(" select c.contactid,c.name,c.birthday,c.sex,c.contacttype,c.customersource,c.datatype,c.crdt,c.crtm,c.crusr,c.memberlevel,'1' ");
        hql.append(createSqlForNameAndAddress(provinceId, cityId, countyId, areaId));
        hql.append(" order by c.contactid ");
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setParameter("name", name);
        hqlQuery.setFirstResult(index);
        hqlQuery.setMaxResults(rows);
        if (StringUtils.isNotBlank(provinceId)) {
            hqlQuery.setParameter("provinceId", provinceId);
        }
        if (StringUtils.isNotBlank(cityId)) {
            hqlQuery.setParameter("cityId", Integer.valueOf(cityId));
        }
        if (StringUtils.isNotBlank(countyId)) {
            hqlQuery.setParameter("countyId", Integer.valueOf(countyId));
        }
        if (StringUtils.isNotBlank(areaId)) {
            hqlQuery.setParameter("areaId", Integer.valueOf(areaId));
        }
        return convertToCustomerDtoList(hqlQuery.list());
    }

    private String createSqlForNameAndAddress(String provinceId, String cityId, String countyId, String areaId) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from Contact c, AddressExt ae, Address a ");
        hql.append(" where a.contactid=c.contactid and a.addressid=ae.addressId ");
        hql.append(" and c.name=:name ");
        if (StringUtils.isNotBlank(provinceId)) {
            hql.append(" and ae.province.provinceid=:provinceId ");
        }
        if (StringUtils.isNotBlank(cityId)) {
            hql.append(" and ae.city.cityid=:cityId ");
        }
        if (StringUtils.isNotBlank(countyId)) {
            hql.append(" and ae.county.countyid=:countyId ");
        }
        if (StringUtils.isNotBlank(areaId)) {
            hql.append(" and ae.area.areaid=:areaId ");
        }
        return hql.toString();
    }


    public Double findJiFenByContactId(String contactId) {
    	String hsql="select iagent.fun_calpoint('"+contactId+"') from dual"; 
		 Query query = this.getSession().createSQLQuery(hsql.toString());
		 List list = query.list();
		 Double result=null;
		 if(list != null){
			 result= Double.valueOf(list.get(0).toString());
		 }
	     return result;
	}

    public String findLevelByContactId(String contactId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select m.memberlevel ");
        sql.append(" from " + schemaNames.getAgentSchema() + "vm_customer t, ");
        sql.append(schemaNames.getAgentSchema() + "memberservice m ");
        sql.append(" where t.memberlevelid=m.memberlevelid and t.CONTACTID=:contactId ");
        Query q = this.getSession().createSQLQuery(sql.toString());
        q.setString("contactId", contactId);
        return (String)q.uniqueResult();
    }

    public List<CustomerDto> findByName(String name, int index, int rows) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.contactid,t2.name,t2.birthday,t2.sex,t2.contacttype,t2.customersource,t2.datatype,t2.crdt,t2.crtm,t2.crusr,t2.memberlevel,t2.customerType from ( ");
        sql.append(createSqlForName());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("name", name);
        sqlQuery.setMaxResults(rows);
        sqlQuery.setFirstResult(index);
        return convertToCustomerDtoList(sqlQuery.list());
    }

    private String createSqlForName() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" where c.name=:name ");
        sql.append(" union ");
        sql.append(" select p.id||'' contactid,p.name name,p.birthday birthday,p.gender sex,p.contact_type contacttype,p.customer_source customersource,'' datatype,p.create_date crdt,null crtm,p.create_user crusr,'' memberlevel,'2' customerType ");
        sql.append(" from " + schemaNames.getCrmmarketingSchema() + "potential_contact p ");
        sql.append(" where p.contact_id is null and p.name=:name ");
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        return sql.toString();
    }

    public List<CustomerDto> findByPhoneAndName(String phone, String name, int index, int rows) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.contactid,t2.name,t2.birthday,t2.sex,t2.contacttype,t2.customersource,t2.datatype,t2.crdt,t2.crtm,t2.crusr,t2.memberlevel,t2.customerType from ( ");
        sql.append(createSqlForPhoneAndName(name));
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone", phone);
        sqlQuery.setParameter("phoneWithZero", "0"+phone);
        if (StringUtils.isNotBlank(name)) {
            sqlQuery.setParameter("name", name);
        }
        sqlQuery.setMaxResults(rows);
        sqlQuery.setFirstResult(index);
        return convertToCustomerDtoList(sqlQuery.list());
    }

    private String createSqlForPhoneAndName(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" inner join " + schemaNames.getAgentSchema() + "phone phone on c.contactid=phone.contactid ");
        sql.append(" where (phone.phn2=:phone or phone.phn2=:phoneWithZero) ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and c.name=:name ");
        }
        sql.append(" union ");
        sql.append(" select p.id||'' contactid,p.name name,p.birthday birthday,p.gender sex,p.contact_type contacttype,p.customer_source customersource,'' datatype,p.create_date crdt,null crtm,p.create_user crusr,'' memberlevel,'2' customerType ");
        sql.append(" from " + schemaNames.getCrmmarketingSchema() + "potential_contact p ");
        sql.append(" inner join " + schemaNames.getCrmmarketingSchema() + "potential_contact_phone pcphone on p.id=pcphone.potential_contact_id ");
        sql.append(" where p.contact_id is null and (pcphone.phone2=:phone or pcphone.phone2=:phoneWithZero) ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and p.name=:name ");
        }
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        return sql.toString();
    }

    private String createSqlForPhone() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select distinct c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" inner join " + schemaNames.getAgentSchema() + "phone phone on c.contactid=phone.contactid ");
        sql.append(" where phone.phn2=:phone ");
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        return sql.toString();
    }

    public List<CustomerDto> findByFixedPhone(String phn1, String phn2, int startRow, int rows) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.contactid,t2.name,t2.birthday,t2.sex,t2.contacttype,t2.customersource,t2.datatype,t2.crdt,t2.crtm,t2.crusr,t2.memberlevel,t2.customerType from ( ");
        sql.append(createSqlForFixedPhone());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phn1", phn1);
        sqlQuery.setParameter("phn2", phn2);
        sqlQuery.setMaxResults(rows);
        sqlQuery.setFirstResult(startRow);
        return convertToCustomerDtoList(sqlQuery.list());
    }

    @Override
    public int findContactCountByFixPhone(String phoneNum) {
        String[] phoneParams = phoneNum.split("-");
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(distinct t2.contactid) from ( ");
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select distinct c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" inner join " + schemaNames.getAgentSchema() + "phone phone on c.contactid=phone.contactid ");
        sql.append(" where phone.phn2=:phone2 ");
        sql.append(" and phone.phn1=:phone1 ");
        if (phoneParams.length > 2) sql.append(" and phone.phn3=:phone3 ");
        else sql.append(" and phone.phn3 is null ");
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone2", phoneParams[1]);
        sqlQuery.setParameter("phone1", phoneParams[0]);
        if (phoneParams.length > 2) sqlQuery.setParameter("phone3", phoneParams[2]);
        return Integer.valueOf(sqlQuery.list().get(0).toString());
    }

    @Override
    public List<CustomerDto> findContactByFixPhone(String phoneNum, int startRow, int rows) {
        String[] phoneParams = phoneNum.split("-");
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.contactid,t2.name,t2.birthday,t2.sex,t2.contacttype,t2.customersource,t2.datatype,t2.crdt,t2.crtm,t2.crusr,t2.memberlevel,t2.customerType from ( ");
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select distinct c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" inner join " + schemaNames.getAgentSchema() + "phone phone on c.contactid=phone.contactid ");
        sql.append(" where phone.phn2=:phone2 ");
        sql.append(" and phone.phn1=:phone1 ");
        if (phoneParams.length > 2) sql.append(" and phone.phn3=:phone3 ");
        else sql.append(" and phone.phn3 is null ");
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone2", phoneParams[1]);
        sqlQuery.setParameter("phone1", phoneParams[0]);
        if (phoneParams.length > 2) sqlQuery.setParameter("phone3", phoneParams[2]);
        sqlQuery.setMaxResults(rows);
        sqlQuery.setFirstResult(startRow);
        return convertToCustomerDtoList(sqlQuery.list());
    }

    public List<CustomerDto> findByPhone(String phone, int index, int rows) {
        return this.findByPhoneAndName(phone, null, index, rows);
    }

    public List<CustomerDto> findContactByPhone(String phone, int index, int rows) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.contactid,t2.name,t2.birthday,t2.sex,t2.contacttype,t2.customersource,t2.datatype,t2.crdt,t2.crtm,t2.crusr,t2.memberlevel,t2.customerType from ( ");
        sql.append(createSqlForPhone());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone", phone);
        sqlQuery.setMaxResults(rows);
        sqlQuery.setFirstResult(index);
        return convertToCustomerDtoList(sqlQuery.list());
    }

    public String getSequence(){
        Query q = getSession().createSQLQuery("select "+schemaNames.getAgentSchema()+"SEQCONTACT.nextval from dual");
        return  q.list().get(0).toString();
    }

    public int findCountByPhone(String phone) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(t2.contactid) from ( ");
        sql.append(createSqlForPhoneAndName(null));
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone", phone);
        sqlQuery.setParameter("phoneWithZero", "0"+phone);
        return Integer.valueOf(sqlQuery.list().get(0).toString());
    }

    public int findCountByFixedPhone(String phn1, String phn2) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(t2.contactid) from ( ");
        sql.append(createSqlForFixedPhone());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phn1", phn1);
        sqlQuery.setParameter("phn2", phn2);
        return Integer.valueOf(sqlQuery.list().get(0).toString());
    }

    private String createSqlForFixedPhone() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select rownum rnum,t1.* from ( ");
        sql.append(" select c.contactid contactid,c.name name,c.birthday birthday,c.sex sex,c.contacttype contacttype,c.customersource customersource,c.datatype datatype,c.crdt crdt,c.crtm crtm,c.crusr crusr,c.memberlevel memberlevel,'1' customerType  ");
        sql.append(" from " + schemaNames.getAgentSchema() + "contact c ");
        sql.append(" inner join " + schemaNames.getAgentSchema() + "phone phone on c.contactid=phone.contactid ");
        sql.append(" where phone.phn2=:phn2 and phone.phn1=:phn1 ");

        sql.append(" union ");
        sql.append(" select p.id||'' contactid,p.name name,p.birthday birthday,p.gender sex,p.contact_type contacttype,p.customer_source customersource,'' datatype,p.create_date crdt,null crtm,p.create_user crusr,'' memberlevel,'2' customerType ");
        sql.append(" from " + schemaNames.getCrmmarketingSchema() + "potential_contact p ");
        sql.append(" inner join " + schemaNames.getCrmmarketingSchema() + "potential_contact_phone pcphone on p.id=pcphone.potential_contact_id ");
        sql.append(" where p.contact_id is null and pcphone.phone2=:phn2 and pcphone.phone1=:phn1 ");
        sql.append(" ) t1 order by t1.contactid ) t2 ");
        return sql.toString();
    }

    public int findContactCountByPhone(String phone) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(distinct t2.contactid) from ( ");
        sql.append(createSqlForPhone());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone", phone);
        return Integer.valueOf(sqlQuery.list().get(0).toString());
    }

    public int findCountByName(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(t2.contactid) from ( ");
        sql.append(createSqlForName());
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("name", name);
        return Integer.valueOf(sqlQuery.list().get(0).toString());
    }

    public int findCountByPhoneAndName(String phone, String name) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(t2.contactid) from ( ");
        sql.append(createSqlForPhoneAndName(name));
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("phone", phone);
        sqlQuery.setParameter("phoneWithZero", "0"+phone);
        sqlQuery.setParameter("name", name);
        return Integer.valueOf(sqlQuery.list().get(0).toString());

    }

    public int findCountByNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto) {
        String provinceId = customerBaseSearchDto.getProvinceId();
        String cityId = customerBaseSearchDto.getCityId();
        String countyId = customerBaseSearchDto.getCountyId();
        String areaId = customerBaseSearchDto.getAreaId();
        String name = customerBaseSearchDto.getName();
        StringBuilder hql = new StringBuilder();
        hql.append(" select count(c) ");
        hql.append(createSqlForNameAndAddress(provinceId, cityId, countyId, areaId));
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setParameter("name", name);
        if (StringUtils.isNotBlank(provinceId)) {
            hqlQuery.setParameter("provinceId", provinceId);
        }
        if (StringUtils.isNotBlank(cityId)) {
            hqlQuery.setParameter("cityId", Integer.valueOf(cityId));
        }
        if (StringUtils.isNotBlank(countyId)) {
            hqlQuery.setParameter("countyId", Integer.valueOf(countyId));
        }
        if (StringUtils.isNotBlank(areaId)) {
            hqlQuery.setParameter("areaId", Integer.valueOf(areaId));
        }
        return Integer.valueOf(hqlQuery.list().get(0).toString());
    }

    public int findCountByPhoneAndNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto) {
        String provinceId = customerBaseSearchDto.getProvinceId();
        String cityId = customerBaseSearchDto.getCityId();
        String countyId = customerBaseSearchDto.getCountyId();
        String areaId = customerBaseSearchDto.getAreaId();
        String name = customerBaseSearchDto.getName();
        String phone = customerBaseSearchDto.getPhone();
        StringBuilder hql = new StringBuilder();
        hql.append(" select count(c) ");
        hql.append(createSqlForPhoneAndNameAndAddress(name, provinceId, cityId, countyId, areaId));
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setParameter("phone", phone);
        hqlQuery.setParameter("phoneWithZero", "0"+phone);
        if (StringUtils.isNotBlank(name)) {
            hqlQuery.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(provinceId)) {
            hqlQuery.setParameter("provinceId", provinceId);
        }
        if (StringUtils.isNotBlank(cityId)) {
            hqlQuery.setParameter("cityId", Integer.valueOf(cityId));
        }
        if (StringUtils.isNotBlank(countyId)) {
            hqlQuery.setParameter("countyId", Integer.valueOf(countyId));
        }
        if (StringUtils.isNotBlank(areaId)) {
            hqlQuery.setParameter("areaId", Integer.valueOf(areaId));
        }
        return Integer.valueOf(hqlQuery.list().get(0).toString());
    }


    
    
    
    
    
    
    
    
    /*===================================Separator==============================================*/
    
    
    
    
    
    
    
    
	/** 
	 * <p>Title: 查询成单客户行数</p>
	 * <p>Description: </p>
	 * @param obCustomerDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.ContactDao#queryPageCount(com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	public int queryPageCount(ObCustomerDto cDto) {
		StringBuffer sb =  new StringBuffer();
		
		sb.append("select count(*) from ( ");
		
		String sql = this.querySql(cDto);
		sb.append(sql);
		
		sb.append(" )");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, cDto);
		BigDecimal rs = (BigDecimal) query.list().get(0);
		
		return rs.intValue();
	}


	/** 
	 * <p>Title: 分页查询成单客户</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param obCustomerDto
	 * @return List<ObCustomerDto>
	 * @see com.chinadrtv.erp.uc.dao.ContactDao#queryPageList(com.chinadrtv.erp.uc.common.DataGridModel, com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	@SuppressWarnings("unchecked")
	public List<ObCustomerDto> queryPageList(DataGridModel dataModel, ObCustomerDto cDto) {
		
		StringBuffer sb =  new StringBuffer();
		
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		
		String sql = this.querySql(cDto);
		
		sb.append(sql);
		
		sb.append("  ) row_ where rownum <= :endRow ) where rownum_ > :beginRow ");
		
		Integer beginRow = dataModel.getStartRow();
		Integer endRow = dataModel.getRows() + beginRow;
		
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, cDto);
		query.setParameter("endRow", endRow);
		query.setParameter("beginRow", beginRow);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> mapList = query.list();
		List<ObCustomerDto> dtoList = new ArrayList<ObCustomerDto>();
		
		AgentUser user = SecurityHelper.getLoginUser();
		for(Map<String, Object> map : mapList){	
			ObCustomerDto dto = oldContactexDao.convert2ObCustomerDto(map);
			dto.setCustomerFrom(OB_CUSTOMER_TYPE_ORDER);
			dto.setCustomerOwner(oldContactexDao.queryCustomerOwner(dto, user.getUserId()));
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	/**
	 * <p>拼接where 子句 </p>
	 * @param cDto
	 * @return String
	 */
	private String querySql(ObCustomerDto cDto) {
		String contactId = cDto.getContactid();
		String name = cDto.getName();
		String memberType = cDto.getMembertype();
		String memberLevel = cDto.getMemberlevel();
		String phoneType = cDto.getPhoneType();
		String phoneNo = cDto.getPhoneNo();
		String agentId = cDto.getAgentId();
		
		StringBuffer sb = new StringBuffer();
		
		String sql = " select distinct c.*," +
				 " mt.dsc MEMBERTYPE_LABEL, m.memberlevel MEMBERLEVEL_LABEL " +
				 " from iagent.contact c " +
				 " inner join iagent.orderhist oh on oh.contactid = c.contactid  "+
				 " left join iagent.phone p on c.contactid = p.contactid "+
				 " left join iagent.membertype mt on c.membertype=mt.id and mt.status='-1' " +
				 " left join iagent.vm_customer vc on c.contactid = vc.contactid " +
				 " left join iagent.memberservice m on vc.memberlevelid=m.memberlevelid " +
				 " where 1=1 ";
		
		sb.append(sql);
		
		sb.append(" and oh.status in ('1', '2', '5', '7') ");
		sb.append(" and trunc(oh.crdt) >= trunc(to_date(:beginDateStr , 'yyyy-mm-dd')) ");
		sb.append(" and trunc(oh.crdt) <= trunc(to_date(:endDateStr, 'yyyy-mm-dd'))");
		
		if(null!=contactId && !"".equals(contactId)){
			sb.append(" and c.contactid=:contactId ");
		}
		if(null!=name && !"".equals(name)){
			sb.append(" and c.name like :name ");
		}
		if(null!=memberType && !"".equals(memberType)){
			sb.append(" and c.membertype=:memberType ");
		}
		if(null!=memberLevel && !"".equals(memberLevel)){
			sb.append(" and m.memberlevelid=:memberLevel ");
		}
		if(null!=phoneNo && !"".equals(phoneNo)){
			Integer _phoneType = Integer.parseInt(phoneType);
			if(_phoneType == CustomerConstant.PHONE_TYPE_CELL){
				sb.append(" and p.phonetypid='4' and p.phone_num=:phoneNo ");
			}else{
				sb.append(" and ((p.phonetypid='1' or " +
						"p.phonetypid='2') and p.phone_num=:phoneNo )");
			}
		}
		if(null!=agentId && !"".equals(agentId)){
			sb.append( " and oh.crusr=:agentId ");
		}
		
		sb.append(" order by c.crdt desc");
		
		return sb.toString();
	}
	
	/**
	 * <p>参数化query</p>
	 * @param query
	 * @param cDto
	 */
	private void parameterizedQuery(Query query, ObCustomerDto cDto) {
		String contactId = cDto.getContactid();
		String name = cDto.getName();
		String memberType = cDto.getMembertype();
		String memberLevel = cDto.getMemberlevel();
		Date beginDate = cDto.getBeginDate();
		Date endDate = cDto.getEndDate();
		String phoneType = cDto.getPhoneType();
		String phoneNo = cDto.getPhoneNo();
		String agentId = cDto.getAgentId();
		String resultCode = cDto.getResultCode();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = sdf.format(beginDate);
		String endDateStr = sdf.format(endDate);
		StringBuffer sb = new StringBuffer();
		
		sb.append(" and oh.status in ('1', '2', '5', '7') ");
		sb.append(" and trunc(oh.crdt) >= trunc(to_date(:beginDateStr , 'yyyy-mm-dd')) ");
		sb.append(" and trunc(oh.crdt) <= trunc(to_date(:endDateStr, 'yyyy-mm-dd'))");
		
		query.setParameter("beginDateStr", beginDateStr);
		query.setParameter("endDateStr", endDateStr);
		if(null!=resultCode && !"".equals(resultCode)){
			query.setParameter("resultCode", resultCode);
		}
		if(null!=contactId && !"".equals(contactId)){
			query.setParameter("contactId", contactId);
		}
		if(null!=name && !"".equals(name)){
			query.setParameter("name", "%"+name+"%");
		}
		if(null!=memberType && !"".equals(memberType)){
			query.setParameter("memberType", memberType);
		}
		if(null!=memberLevel && !"".equals(memberLevel)){
			query.setParameter("memberLevel", memberLevel);
		}
		if(null!=phoneNo && !"".equals(phoneNo)){
			Integer _phoneType = Integer.parseInt(phoneType);
			if(_phoneType == CustomerConstant.PHONE_TYPE_CELL){
				query.setParameter("phoneNo", phoneNo);
			}else{
				query.setParameter("phoneNo", phoneNo);
			}
		}
		if(null!=agentId && !"".equals(agentId)){
			query.setParameter("agentId", agentId);
		}
	}

	/** 
	 * <p>Title: queryAuditPageCount</p>
	 * <p>Description: </p>
	 * @param customerDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.ContactDao#queryAuditPageCount(com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	public Integer queryAuditPageCount(ObCustomerDto dto) {

		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		String contactId = dto.getContactid();
		Date beginDate = dto.getBeginDate();
		Date endDate = dto.getEndDate();
		String sql = " select distinct c.*, ub.id batch_id, ub.status batch_status"+
				 " from iagent.contact c "+
				 " inner join "+schemaNames.getAgentSchema()+"contact_change cc on c.contactid = cc.contactid "+
				 " inner join "+schemaNames.getCrmmarketingSchema()+"user_bpm ub on ub.contact_id = c.contactid "+
				 " where 1=1 ";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select count(*) from ( ");
		sb.append(sql);
		if(null!=contactId && !"".equals(contactId)){
			sb.append(" and c.contactid=:contactId ");
		}
		if(null!=beginDate){
			sb.append(" and trunc(ub.create_date)>=trunc(to_date(:beginDate, 'yyyy-mm-dd')) ");
		}
		if(null!=endDate){
			sb.append(" and trunc(ub.create_date)<=trunc(to_date(:endDate, 'yyyy-mm-dd')) ");
		}
		sb.append(" and ub.create_user=:userId");
		sb.append(" )");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		if(null!=contactId && !"".equals(contactId)){
			query.setParameter("contactId", contactId);
		}
		if(null!=beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(null!=endDate){
			query.setParameter("endDate", endDate);
		}
		query.setParameter("userId", userId);
		
		Integer total = Integer.parseInt(query.list().get(0).toString());
		return total;
	}

	/** 
	 * <p>Title: queryAuditPageList</p>
	 * <p>Description: </p>
	 * @param dataGrid
	 * @param customerDto
	 * @return List<ObCustomerDto>
	 * @see com.chinadrtv.erp.uc.dao.ContactDao#queryAuditPageList(com.chinadrtv.erp.constant.DataGridModel, com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	@SuppressWarnings("unchecked")
	public List<ObCustomerDto> queryAuditPageList(DataGridModel dataGrid, ObCustomerDto dto) {
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		String contactId = dto.getContactid();
		Date beginDate = dto.getBeginDate();
		Date endDate = dto.getEndDate();
		
		String sql = " select distinct c.*, ub.id batch_id, ub.status batch_status"+
				 " from iagent.contact c "+
				 " inner join "+schemaNames.getAgentSchema()+"contact_change cc on c.contactid = cc.contactid "+
				 " inner join "+schemaNames.getCrmmarketingSchema()+"user_bpm ub on ub.contact_id = c.contactid "+
				 " where 1=1 ";
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		sb.append(sql);
		if(null!=contactId && !"".equals(contactId)){
			sb.append(" and c.contactid=:contactId ");
		}
		if(null!=beginDate){
			sb.append(" and trunc(ub.create_date)>=trunc(to_date(:beginDate, 'yyyy-mm-dd')) ");
		}
		if(null!=endDate){
			sb.append(" and trunc(ub.create_date)<=trunc(to_date(:endDate, 'yyyy-mm-dd')) ");
		}
		sb.append(" and ub.create_user=:userId");
		Integer beginRow = dataGrid.getStartRow();
		Integer endRow = dataGrid.getRows() + beginRow;
		
		sb.append("  ) row_ where rownum <= "+endRow+") where rownum_ > "+beginRow);
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(null!=contactId && !"".equals(contactId)){
			query.setParameter("contactId", contactId);
		}
		if(null!=beginDate){
			query.setParameter("beginDate", beginDate);
		}
		if(null!=endDate){
			query.setParameter("endDate", endDate);
		}
		query.setParameter("userId", userId);
		
		List<Map<String, Object>> mapList = query.list();
		List<ObCustomerDto> dtoList = new ArrayList<ObCustomerDto>();
		for(int i=0; i<mapList.size(); i++){
			Map<String, Object> map = mapList.get(i);
			ObCustomerDto obCustomerDto = oldContactexDao.convert2ObCustomerDto(map);
			dtoList.add(obCustomerDto);
		}
		
		return dtoList;
	}

    public List<Contact> getContactFromIds(List<String> contactIdList)
    {
        if(contactIdList==null||contactIdList.size()==0)
        {
            return new ArrayList<Contact>();
        }
        StringBuilder stringBuilder=new StringBuilder("from Contact where ");
        Map<String,Parameter>  mapParm=new Hashtable<String, Parameter>();
        for(int i=0;i<contactIdList.size();i++)
        {
            String name="contactid"+i;
            if(i==0)
            {
                stringBuilder.append("contactid=:"+name);
            }
            else
            {
                stringBuilder.append(" or contactid=:"+name);
            }
            mapParm.put(name,new ParameterString(name,contactIdList.get(i)));
        }

        return this.findList(stringBuilder.toString(),mapParm);
    }

    public Integer findContactCountByPhoneList(List<String> phoneNumList) {
        Query hqlQuery = getSession().createQuery("from Contact c  where c.contactid in (select p.contactid  from Phone p where p.phn2 in (:phoneNumList) ) ");
        hqlQuery.setParameterList("phoneNumList", phoneNumList);
        List result = hqlQuery.list();
        return result==null?0:result.size();
    }

    public List<Contact> findContactByPhoneList(List<String> phoneNumList, int startRow, int rows) {
        Query hqlQuery = getSession().createQuery("from Contact c  where c.contactid in (select p.contactid  from Phone p where p.phn2 in (:phoneNumList) ) ");
        hqlQuery.setParameterList("phoneNumList", phoneNumList);
        hqlQuery.setFirstResult(startRow);
        hqlQuery.setMaxResults(rows);
        List<Contact> result = hqlQuery.list();
        return result;
    }
}
