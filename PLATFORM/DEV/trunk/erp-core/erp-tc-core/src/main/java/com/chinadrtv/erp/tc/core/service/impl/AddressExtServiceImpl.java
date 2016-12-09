package com.chinadrtv.erp.tc.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.Ems;
import com.chinadrtv.erp.tc.core.dao.AddressDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.tc.core.dao.AddressExtDao;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.utils.OrderAddressChecker;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("com.chinadrtv.erp.tc.core.service.impl.AddressExtServiceImpl")
public class AddressExtServiceImpl extends GenericServiceImpl<AddressExt,String> implements AddressExtService {

    private static final String anyCountyName="其他区";
    private static final String anyAreaName="其他";

    private static final String[] municipalityCitys=new String[]{"上海","北京","重庆","天津"};

    @Autowired
    private AddressExtDao addressExtDao;

    @Autowired
    private AddressDao addressDao;

    private class AddressNames
    {
        public String provinceName;
        public String cityName;
        public String countyName;
        public String areaName;
    }

    private List<AddressNames> getToFindNames(String provinceName, String cityName, String countyName, String areaName)
    {
        List<AddressNames> list=new ArrayList<AddressNames>();

        //省和城市必须存在
        if(StringUtils.isEmpty(provinceName)||StringUtils.isEmpty(cityName))
        {
            return list;
        }
        //四级地址
        if(StringUtils.isNotEmpty(countyName))
        {
            if(StringUtils.isNotEmpty(areaName))
            {
                AddressNames addressNames=new AddressNames();
                addressNames.provinceName=provinceName;
                addressNames.cityName=cityName;
                addressNames.countyName=countyName;
                addressNames.areaName=areaName;

                list.add(addressNames);
            }
            AddressNames addressNames=new AddressNames();
            addressNames.provinceName=provinceName;
            addressNames.cityName=cityName;
            addressNames.countyName=countyName;
            addressNames.areaName=anyAreaName;

            list.add(addressNames);
        }

        AddressNames addressNames=new AddressNames();
        addressNames.provinceName=provinceName;
        addressNames.cityName=cityName;
        addressNames.countyName=anyCountyName;
        addressNames.areaName=anyAreaName;

        list.add(addressNames);

        return list;
    }


    /**
     * 根据地址名称来查找匹配的地址
     * @param provinceName
     * @param cityName
     * @param countyName
     * @param areaName
     * @param address
     * @return
     * 如果没有匹配的地址，那么使用“通配符”来匹配
     */
    public AddressExt findMatchAddressFromNames(String provinceName, String cityName, String countyName, String areaName,String address, String strContactId) {
        //省和城市必须存在
        if(StringUtils.isEmpty(provinceName)||StringUtils.isEmpty(cityName))
        {
            return null;
        }
        if(address==null)
        {
            address="";
        }
        //首先根据四级地址来匹配
        //然后根据三级地址来匹配
        //最后根据两级地址来匹配
        Province province=this.addressExtDao.getProviceFromName(provinceName);
        if(province==null)
            return null;
        CityAll cityAll=this.addressExtDao.getCityAllFromName(province.getProvinceid(),cityName);
        if(cityAll==null)
            return null;
        CountyAll countyAll=null;
        if(StringUtils.isNotEmpty(countyName))
        {
            List<CountyAll> countyAllList=this.addressExtDao.getCountyAllFromName(province.getProvinceid(),cityAll.getCityid(),countyName);
            if(countyAllList!=null&&countyAllList.size()>0)
            {
                countyAll=countyAllList.get(0);
            }
        }
        if(countyAll==null)
        {
            List<CountyAll> countyAllList=this.addressExtDao.getCountyAllFromName(province.getProvinceid(),cityAll.getCityid(),anyCountyName);
            if(countyAllList!=null&&countyAllList.size()>0)
            {
                //找最大的id
                for(CountyAll countyAll1:countyAllList)
                {
                    if(countyAll==null)
                    {
                        countyAll=countyAll1;
                    }
                    else
                    {
                        if(countyAll1.getCountyid().intValue()>countyAll.getCountyid().intValue())
                        {
                            countyAll=countyAll1;
                        }
                    }
                }
            }
        }

        if(countyAll==null)
        {
            return null;
        }
        AreaAll areaAll=null;
        if(StringUtils.isNotEmpty(areaName))
        {
            List<AreaAll> areaAllList=this.addressExtDao.getAreaAllFromName(province.getProvinceid(),cityAll.getCityid(),countyAll.getCountyid(),areaName);
            if(areaAllList!=null&&areaAllList.size()>0)
            {
                areaAll=areaAllList.get(0);
            }
        }
        if(areaAll==null)
        {
            List<AreaAll> areaAllList=this.addressExtDao.getAreaAllFromName(province.getProvinceid(),cityAll.getCityid(),countyAll.getCountyid(),anyAreaName);
            if(areaAllList!=null&&areaAllList.size()>0)
            {
                for(AreaAll areaAll1:areaAllList)
                {
                    if(areaAll==null)
                    {
                        areaAll=areaAll1;
                    }
                    else
                    {
                        if(areaAll1.getAreaid().intValue()>areaAll.getAreaid().intValue())
                        {
                            areaAll=areaAll1;
                        }
                    }
                }
            }
        }
        if(areaAll==null)
        {
            return null;
        }

        AddressExt addressExt=new AddressExt();
        addressExt.setProvince(province);
        addressExt.setCity(cityAll);
        addressExt.setCounty(countyAll);
        addressExt.setArea(areaAll);
        //最后检查地址是否存在
        AddressExt addressExtMatch=this.addressExtDao.findMatchAddress(addressExt,address,strContactId);
        if(addressExtMatch!=null)
            return addressExtMatch;
        else
            return addressExt;

        /*AddressExt addressExtMostMatch=null;
        List<AddressNames> addressNamesList=this.getToFindNames(provinceName,cityName,countyName,areaName);
        for(AddressNames addressNames:addressNamesList)
        {
            List<AddressExt> addressExtList=this.addressExtDao.findMatchAddressExtsFromNames(addressNames.provinceName,addressNames.cityName,addressNames.countyName,addressNames.areaName);
            if(addressExtList!=null&&addressExtList.size()>0)
            {
                AddressExt addressExt=getMostMatchAddressExt(addressExtList,address);
                if(addressExt!=null)
                    return addressExt;
            }
            //获取相应的四级地址id
            if(addressExtMostMatch==null)
            {
                addressExtMostMatch=getMostMatchAddressExt(addressExtList,null);
            }

        }
        return addressExtMostMatch;*/
    }

    public AddressExt getAddressExt(String addressId)
    {
        return this.addressExtDao.get(addressId);
    }

    /**
     * 查找最匹配的地址
     * @param provinceName
     * @param cityName
     * @param countyName
     * @param areaName
     * @param address
     * @return
     */
    public AddressExt findAddressFromNames(String provinceName,String cityName,String countyName,String areaName,String address)
    {
        if(address==null)
        {
            address="";
        }
        List<AddressExt> addressExtList= this.addressExtDao.findMatchAddressExtsFromNames(provinceName,cityName,countyName,areaName);
        if(addressExtList!=null&&addressExtList.size()>0)
        {
            for(AddressExt addressExt:addressExtList)
            {
                 if(address.equals(addressExt.getAddressDesc()))
                 {
                     return addressExt;
                 }
            }
        }
        return null;
    }

    public void createAddressExt(AddressExt addressExt, Ems ems)
    {
        //根据spellId来找到address表里面的CITY、AREA和
        Address address=new Address();
        if(ems!=null)
        {
            address.setArea(ems.getSpellid().toString());
        }
        else
        {
            address.setArea("0");
        }

        address.setState(addressExt.getProvince().getProvinceid());
        address.setContactid(addressExt.getContactId());
        //将四级地址连接到地址详细信息中
        String addressDesc="";
        //判断是否直辖市
        boolean bMunicipalityCity = false;
        for(String municipalityCity : municipalityCitys)
        {
            if(municipalityCity.equals(addressExt.getProvince().getChinese()))
            {
                bMunicipalityCity=true;
                break;
            }
        }
        if(bMunicipalityCity==false)
        {
            addressDesc+=addressExt.getProvince().getChinese();
            addressDesc+="省";
        }

        addressDesc+=addressExt.getCity().getCityname();
        if(addressExt.getCounty()!=null)
        {
            if(!anyCountyName.equals(addressExt.getCounty().getCountyname()))
            {
                addressDesc+=addressExt.getCounty().getCountyname();
            }
        }
        if(addressExt.getArea()!=null)
        {
            if(!anyAreaName.equals(addressExt.getArea().getAreaname()))
            {
                addressDesc+=addressExt.getArea().getAreaname();
            }
            addressExt.setAreaName(addressExt.getArea().getAreaname());
        }
        addressDesc+=addressExt.getAddressDesc();
        address.setAddress(addressDesc);
        if(ems!=null)
        {
            address.setCity(ems.getCityid());
            address.setZip(ems.getZip());
        }
        address.setAddrtypid("1");
        address.setAdditionalinfo("0");
        address.setAddrconfirm("0");

        address.setIsdefault("-1");

        addressExt.setAddressType("0");
        //首先检查给定地址里面的联系人的地址是否存在，如果存在，那么修改联系人id
        if(address!=null)
            this.resetAddressContactId(address);
        this.addressExtDao.saveAddressExt(addressExt, address);
    }

    private void resetAddressContactId(Address address)
    {
        //首先检查给定地址里面的联系人的地址是否存在，如果存在，那么修改联系人id
        if(StringUtils.isNotEmpty(address.getContactid()))
        {
            Address address1 = addressDao.find("from Address where contactid=:contactid", new ParameterString("contactid",address.getContactid()));
            if(address1!=null)
            {
                String newContactId=addressDao.getNewContactId();
                address.setContactid("A"+newContactId);
                address.setIsdefault("0");
            }
        }
    }

    private static AddressExt getMostMatchAddressExt(List<AddressExt> addressExtList, String address)
    {
        //目前算法，如果有多个，那么找到最大值
        AddressExt addressExtMatch=null;
        for(AddressExt addressExt:addressExtList)
        {
            if(OrderAddressChecker.isJustValidAddress(addressExt))
            {
                if(addressExtMatch==null)
                {
                    if(address!=null)
                    {
                        if(address.equals(addressExt.getAddressDesc()))
                        {
                            addressExtMatch=addressExt;
                        }
                    }
                    else
                    {
                        addressExtMatch=addressExt;
                    }
                }
                else
                {
                    if(addressExt.getCounty().getCountyid().intValue()>addressExtMatch.getCounty().getCountyid().intValue())
                    {
                        if(address!=null)
                        {
                            if(address.equals(addressExt.getAddressDesc()))
                            {
                                addressExtMatch=addressExt;
                            }
                        }
                        else
                        {
                            addressExtMatch=addressExt;
                        }
                    }
                    else if(addressExt.getCounty().getCountyid().intValue()==addressExtMatch.getCounty().getCountyid().intValue())
                    {
                        if(addressExt.getArea().getAreaid().intValue()>addressExtMatch.getArea().getAreaid().intValue())
                        {
                            if(address!=null)
                            {
                                if(address.equals(addressExt.getAddressDesc()))
                                {
                                    addressExtMatch=addressExt;
                                }
                            }
                            else
                            {
                                addressExtMatch=addressExt;
                            }
                        }
                    }
                }
            }
        }
        return addressExtMatch;
    }

    @Override
    protected GenericDao<AddressExt, String> getGenericDao() {
        return addressExtDao;
    }

    public void createAddressExt(AddressExt addressExt, Address address)
    {
        if(address!=null)
            this.resetAddressContactId(address);
        this.addressExtDao.saveAddressExt(addressExt, address);
    }
}
