package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.AddressExtDao;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-28
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.AddressExtDaoImpl")
public class AddressExtDaoImpl extends GenericDaoHibernateBase<AddressExt, String>
    implements AddressExtDao {

    @Autowired
    private SchemaNames schemaNames;

    public AddressExtDaoImpl() {
        super(AddressExt.class);
    }
    
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public void saveAddressExt(AddressExt addressExt, Address address)
    {
        AreaAll areaAll=(AreaAll)this.getSession().get(AreaAll.class,addressExt.getArea().getAreaid());
        if(areaAll==null)
        {
            this.getSession().save(addressExt.getArea());
        }
        else
        {
            addressExt.setArea(areaAll);
        }

        CityAll cityAll=(CityAll)this.getSession().get(CityAll.class,addressExt.getCity().getCityid());
        if(cityAll==null)
        {
            this.getSession().save(addressExt.getCity());
        }
        else
        {
            addressExt.setCity(cityAll);
        }

        CountyAll countyAll=(CountyAll)this.getSession().get(CountyAll.class,addressExt.getCounty().getCountyid());
        if(countyAll==null)
        {
            this.getSession().save(addressExt.getCounty());
        }
        else
        {
            addressExt.setCounty(countyAll);
        }

        Province province= (Province)this.getSession().get(Province.class,addressExt.getProvince().getProvinceid());
        if(province==null)
        {
            this.getSession().save(addressExt.getProvince());
        }
        else
        {
            addressExt.setProvince(province);
        }

        addressExt.setAddressId(getAddressId());

        //Address address=new Address();
        address.setAddressid(addressExt.getAddressId());
        //address.setAddress(addressExt.getAddressDesc());
        //address.setContactid(addressExt.getContactId()); --外部维护contactId逻辑，因为address表不允许联系人重复

        this.getSession().save(address);

        if(addressExt.getUptime()==null)
            addressExt.setUptime(new Date());
        this.save(addressExt);


    }
    public  void saveOrUpdate(AddressExt addressExt){
        getSession().saveOrUpdate(addressExt);
    }

    public String getAddressId(){
        Query q = getSession().createSQLQuery("select "+ schemaNames.getAgentSchema()+"seqaddress.nextval from dual");
        return  q.list().get(0).toString();
    }

    public AddressExt getAddressExtFromContact(String contactId)
    {
        return this.find("from AddressExt where contactId=:contactId",new ParameterString("contactId",contactId));
    }

    public List<AddressExt> findMatchAddressExtsFromNames(String provinceName,String cityName,String countyName,String areaName)
    {
        AddressExt addressExt=new AddressExt();
        if(StringUtils.isNotEmpty(provinceName))
        {
            Province province=new Province();
            province.setChinese(provinceName);
            addressExt.setProvince(province);
        }
        if(StringUtils.isNotEmpty(cityName))
        {
            CityAll cityAll=new CityAll();
            cityAll.setCityname(cityName);
            addressExt.setCity(cityAll);
        }
        if(StringUtils.isNotEmpty(countyName))
        {
            CountyAll countyAll=new CountyAll();
            countyAll.setCountyname(countyName);
            addressExt.setCounty(countyAll);
        }
        if(StringUtils.isNotEmpty(areaName))
        {
            AreaAll areaAll=new AreaAll();
            areaAll.setAreaname(areaName);
            addressExt.setArea(areaAll);
        }

        return this.hibernateTemplate.findByExample(addressExt);
    }

    public AddressExt findMatchAddress(AddressExt addressExt,String address, String strContactId)
    {
        if(address==null)
            address="";
        String hql="";
        Map<String,Object> parmsMap=new HashMap<String, Object>();
        //省
        if(addressExt.getProvince()!=null&&StringUtils.isNotEmpty(addressExt.getProvince().getProvinceid()))
        {
            if(StringUtils.isNotEmpty(hql))
            {
                hql+=" and province.provinceid=:provinceid";
            }
            else
            {
                hql+=" province.provinceid=:provinceid";
            }
            parmsMap.put("provinceid",addressExt.getProvince().getProvinceid());
        }
        //市
        if(addressExt.getCity()!=null&&addressExt.getCity().getCityid()!=null)
        {
            if(StringUtils.isNotEmpty(hql))
            {
                hql+=" and city.cityid=:cityid";
            }
            else
            {
                hql+=" city.cityid=:cityid";
            }
            parmsMap.put("cityid",addressExt.getCity().getCityid());
        }
        //区
        if(addressExt.getCounty()!=null&&addressExt.getCounty().getCountyid()!=null)
        {
            if(StringUtils.isNotEmpty(hql))
            {
                hql+=" and county.countyid=:countyid";
            }
            else
            {
                hql+=" county.countyid=:countyid";
            }
            parmsMap.put("countyid",addressExt.getCounty().getCountyid());
        }
        if(addressExt.getArea()!=null&&addressExt.getArea().getAreaid()!=null)
        {
            if(StringUtils.isNotEmpty(hql))
            {
                hql+=" and area.areaid=:areaid";
            }
            else
            {
                hql+=" area.areaid=:areaid";
            }
            parmsMap.put("areaid",addressExt.getArea().getAreaid());
        }
        //增加联系人信息
        if(StringUtils.isNotEmpty(strContactId))
        {
            if(StringUtils.isNotEmpty(hql))
            {
                hql+=" and contactId=:contactId";
            }
            else
            {
                hql+=" contactId=:contactId";
            }
            parmsMap.put("contactId", strContactId);
        }
        //增加审核状态
        hql+=" and (auditState='2' or auditState is null)";
        //String hql="from AddressExt where province.provinceid=:provinceid";
        if(StringUtils.isEmpty(hql))
            return null;
        else
        {
            Query query=this.getSession().createQuery("from AddressExt where "+hql);
            for(Map.Entry<String,Object> entry:parmsMap.entrySet())
            {
                query.setParameter(entry.getKey(),entry.getValue());
            }
            List<AddressExt> addressExtList=query.list();
            if(addressExtList!=null&&addressExtList.size()>0)
            {
                for(AddressExt addressExt1:addressExtList)
                {
                    if(StringUtils.isEmpty(address))
                    {
                        if(StringUtils.isEmpty(addressExt1.getAddressDesc()))
                        {
                            if(StringUtils.isNotEmpty(strContactId))
                            {
                                if(strContactId.equals(addressExt1.getContactId()))
                                    return addressExt1;
                            }
                            else
                                return addressExt1;
                        }
                    }
                    else
                    {
                        if(address.equals(addressExt1.getAddressDesc()))
                        {
                            if(StringUtils.isNotEmpty(strContactId))
                            {
                                if(strContactId.equals(addressExt1.getContactId()))
                                    return addressExt1;
                            }
                            else
                                return addressExt1;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Session getJustSession()
    {
        return this.getSession();
    }

    public Province getProviceFromName(String provinceName)
    {
        Query query=this.getSession().createQuery("from Province where chinese=:chinese");
        query.setParameter("chinese",provinceName);
        List<Province> provinceList=query.list();
        if(provinceList!=null&&provinceList.size()>0)
            return provinceList.get(0);
        return null;
    }

    public CityAll getCityAllFromName(String provinceId,String cityName)
    {
        Query query=this.getSession().createQuery("from CityAll where provid=:provid and cityname=:cityname");
        query.setParameter("provid", provinceId);
        query.setParameter("cityname",cityName);
        List<CityAll> cityAllList=query.list();
        if(cityAllList!=null&&cityAllList.size()>0)
        {
            return cityAllList.get(0);
        }
        return null;
    }

    public List<CountyAll> getCountyAllFromName(String provinceId,Integer cityId,String countyName)
    {
        Query query=this.getSession().createQuery("from CountyAll where provid=:provid and cityid=:cityid and countyname=:countyname");
        query.setParameter("provid", provinceId);
        query.setParameter("cityid",cityId);
        query.setParameter("countyname",countyName);
        List<CountyAll> countyAllList=query.list();
        return countyAllList;
    }

    public List<AreaAll> getAreaAllFromName(String provinceId,Integer cityId,Integer countyId, String areaName)
    {
        Query query=this.getSession().createQuery("from AreaAll where provid=:provid and cityid=:cityid and countyid=:countyid and areaname=:areaname");
        query.setParameter("provid", provinceId);
        query.setParameter("cityid",cityId);
        query.setParameter("countyid",countyId);
        query.setParameter("areaname",areaName);

        List<AreaAll> areaAllList=query.list();
        return areaAllList;
    }

    
}

