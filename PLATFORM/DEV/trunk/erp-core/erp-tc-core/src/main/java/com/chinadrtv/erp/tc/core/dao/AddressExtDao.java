package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import org.hibernate.Session;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-28
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public interface AddressExtDao extends GenericDao<AddressExt, String>{
    void saveOrUpdate(AddressExt addressExt);
    void saveAddressExt(AddressExt addressExt, Address address);
    AddressExt getAddressExtFromContact(String contactId);
    Session getJustSession();
    List<AddressExt> findMatchAddressExtsFromNames(String provinceName,String cityName,String countyName,String areaName);

    Province getProviceFromName(String provinceName);
    CityAll getCityAllFromName(String provinceId,String cityName);
    List<CountyAll> getCountyAllFromName(String provinceId,Integer cityId,String countyName);
    List<AreaAll> getAreaAllFromName(String provinceId,Integer cityId,Integer countyId, String areaName);
    AddressExt findMatchAddress(AddressExt addressExt,String address, String strContactId);
}
