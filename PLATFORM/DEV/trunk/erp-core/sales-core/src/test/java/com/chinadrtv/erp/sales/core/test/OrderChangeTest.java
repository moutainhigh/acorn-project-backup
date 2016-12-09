package com.chinadrtv.erp.sales.core.test;

import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.sales.core.dao.OrderChangeDao;
import com.chinadrtv.erp.sales.core.model.OrderExtDto;
import com.chinadrtv.erp.sales.core.service.OrderChangeService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.tc.core.dao.AddressExtDao;
import com.chinadrtv.erp.tc.core.dao.ContactDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dao.PhoneDao;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@TransactionConfiguration( defaultRollback = false)
//@TransactionConfiguration( defaultRollback = false)
public class OrderChangeTest extends SpringTest {

    @Test
    public void testMok()
    {
        Map<String,List<String>> map=new Hashtable<String, List<String>>();
        List<String> list=new ArrayList<String>();
        list.add("aaa");
        map.put("1",list);

        List<String> list1=map.get("1");
        List<String> list2=new ArrayList<String>();
        list2.add("bb");
        list1.addAll(list2);
        map.put("1",list1);
    }

    @Autowired
    private OrderChangeService orderChangeService;

    @Autowired
    private OrderChangeDao orderChangeDao;

    @Autowired
    private OrderhistDao orderhistDao;

    @Test
    public void testOrderChangeSave()
    {
        OrderChange orderChange=new OrderChange();
        orderChange.setEntityid("444");
        orderChange.setOrderid("444");
        orderChange.setContactid("1");
        AddressExtChange addressExtChange=new AddressExtChange();
        addressExtChange.setOrderChange(orderChange);
        orderChange.setAddress(addressExtChange);
        addressExtChange.setAddressId("222");
        addressExtChange.setAddressDesc("test");
        AddressChange addressChange=new AddressChange();
        addressChange.setAddressid("222");
        addressChange.setAddressExtChange(addressExtChange);
        addressExtChange.setAddressChange(addressChange);
        /*ContactChange contactChange=new ContactChange();
        contactChange.setContactid("1");
        contactChange.setAges("11");
        orderChange.setGetContactChange(contactChange);*/
        OrderdetChange orderdetChange=new OrderdetChange();
        orderdetChange.setOrderdetid("1");
        orderdetChange.setOrderid("444");
        orderChange.getOrderdetChanges().add(orderdetChange);
        orderdetChange.setOrderChange(orderChange);
        orderChangeDao.save(orderChange);
    }

    @Test
    public void testOrderChangeRead()
    {
        OrderChange orderChange= orderChangeDao.get(5150L);
        if(orderChange!=null)
        {
            System.out.println(orderChange.getOrderid());
        }
    }

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private ContactDao contactDao;

    @Test
    public void testOrderService() throws Exception
    {
        OrderQueryDto orderQueryDto=new OrderQueryDto();
        orderQueryDto.setOrderId("22083195");
        //orderQueryDto.setProdId("111");
        AddressExt addressExt=new AddressExt();
        Province province=new Province();
        province.setProvinceid("16");
        /*CityAll cityAll=new CityAll();
        cityAll.setCityid(1);
        CountyAll countyAll=new CountyAll();
        countyAll.setCountyid(1);
        AreaAll areaAll=new AreaAll();
        areaAll.setAreaid(1); */

        addressExt.setProvince(province);
        //addressExt.setCity(cityAll);
        //addressExt.setCounty(countyAll);
        //addressExt.setArea(areaAll);

        orderQueryDto.setAddressExt(addressExt);
        Date dt=new Date();
        Date dtBegin=new Date(dt.getTime()-29*24*3600*1000L);
        orderQueryDto.setBeginCrdt(dtBegin);

        List<OrderExtDto> orderExtDtoList= orderhistService.queryOrder(orderQueryDto);
        int count=orderhistService.queryOrderTotalCount(orderQueryDto);
        if(orderExtDtoList!=null)
        {
            for(OrderExtDto orderExtDto:orderExtDtoList)
            {
                System.out.println(orderExtDto.getOrder().getId());
            }
        }
    }

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private AddressExtDao addressExtDao;

    @Test
    public void testOrderChangeService() throws Exception
    {
        Order order=orderhistDao.getOrderHistByOrderid("22142008");

        Order orderNew=new Order();
        BeanUtils.copyProperties(order,orderNew);
        orderNew.setMdusr("12650");

        /*AddressExt addressExt=new AddressExt();
        addressExt.setAddressId("28096312");
        BeanUtils.copyProperties(order,orderNew);
        orderNew.setAddress(addressExt);
        orderNew.setJifen("");*/
        //orderNew.setNote("test");
        //orderNew.setMailprice(new BigDecimal("123"));
        /*AddressExt addressExt=new AddressExt();
        //BeanUtils.copyProperties(order.getAddress(),addressExt);
        Province province=new Province();
        province.setProvinceid("1");
        addressExt.setProvince(province);

        addressExt.setAddressId(order.getAddress().getAddressId());
        addressExt.setContactId(order.getAddress().getContactId());
        addressExt.setArea(order.getAddress().getArea());
        addressExt.setAreaName(order.getAddress().getAreaName());
        addressExt.setCity(order.getAddress().getCity());
        addressExt.setCounty(order.getAddress().getCounty());
        addressExt.setUptime(order.getAddress().getUptime());
        addressExt.setAddressDesc(order.getAddress().getAddressDesc());
        addressExt.setAddressType(order.getAddress().getAddressType());


        orderNew.setAddress(addressExt);*/
        /*orderNew.setOrderdets(new HashSet<OrderDetail>());
        orderNew.setTotalprice(orderNew.getTotalprice().add(new BigDecimal(93.42D)));
        orderNew.setProdprice(orderNew.getTotalprice());
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            if(orderDetail.getId()==109357L)
            {
            OrderDetail orderDetail1=new OrderDetail();
            BeanUtils.copyProperties(orderDetail,orderDetail1);
            orderDetail1.setOrderhist(orderNew);
            orderDetail1.setSpnum(orderDetail1.getSpnum()+1);

            orderNew.getOrderdets().add(orderDetail1);
            }
            else
            {
                OrderDetail orderDetail1=new OrderDetail();
                BeanUtils.copyProperties(orderDetail,orderDetail1);
                orderDetail1.setOrderhist(orderNew);

                orderNew.getOrderdets().add(orderDetail1);
            }
        }

        Contact contact=contactDao.get(order.getGetcontactid());
        Contact contactNew=contactDao.get("910698284");*/
        /*Contact contactNew=new Contact();
        BeanUtils.copyProperties(contact,contactNew);
        contactNew.setAges("22");
        contactNew.setName("testxzk");
        orderNew.setMdusr("testxzk"); */
        /*Phone phone=phoneDao.get(2045887675L);
        Phone phone1=new Phone();
        BeanUtils.copyProperties(phone,phone1);
        phone1.setPhn1("333");

        List<Phone> phoneList=new ArrayList<Phone>();
        phoneList.add(phone1); */
        Map<String,List<String>> map= orderhistService.saveOrderModifyRequest(orderNew,null,null,null,null,null,null,"test");
        System.out.println(map.entrySet().size());
    }

    @Test
    public void testOderModifySave() throws Exception
    {
        //Order order=orderhistDao.getOrderHistByOrderid("22082537");
        orderhistService.updateOrderFromChangeRequest("391901", AuditTaskType.ORDERCHANGE,null, "22082537","123","test");
    }

    @Test
    public void testCorrelativeOrder() throws Exception
    {
        List<Order> orderList=this.orderhistService.checkCorrelativeOrderByContact("28097207",null);
        if(orderList!=null)
        {
            System.out.println(orderList.size());
        }
    }

    @Test
    public void testCreateOrder() throws Exception
    {
       Order order=new Order();
        order.setCrusr("1");
        order.setCrdt(new Date());
        AddressExt addressExt=new AddressExt();
        Province province=new Province();
        province.setProvinceid("16");
        CityAll cityAll=new CityAll();
        cityAll.setCityid(146);
        CountyAll countyAll=new CountyAll();
        countyAll.setCountyid(1235);
        AreaAll areaAll=new AreaAll();
        areaAll.setAreaid(42200);
        addressExt.setProvince(province);
        addressExt.setCity(cityAll);
        addressExt.setCounty(countyAll);
        addressExt.setArea(areaAll);
        addressExt.setAddressId("28096736");
        addressExt.setAddressDesc("江苏常州市金坛市测试地址");
        order.setAddress(addressExt);
        order.setContactid("27935027");
        order.setGetcontactid("27935027");
        order.setPaycontactid("27935027");
        order.setTotalprice(new BigDecimal("379"));
        order.setProdprice(new BigDecimal("379.23"));
        order.setMailprice(new BigDecimal("0"));
        order.setOrdertype("1");
        order.setPaytype("1");
        order.setMailtype("1");

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setOrderhist(order);
        orderDetail.setProdid("1110401104");
        orderDetail.setProdscode("YEJZYT4");
        orderDetail.setProdname("益尔健活力健身器（卓越版）+紫环远红外记忆枕");
        orderDetail.setSoldwith("1");
        orderDetail.setProducttype("31792");
        orderDetail.setSprice(new BigDecimal("379.23"));
        orderDetail.setSpnum(1);
        orderDetail.setUpnum(0);
        orderDetail.setUprice(new BigDecimal("398"));
        orderDetail.setOrderdt(new Date());
        orderDetail.setFreight(new BigDecimal("0"));

        order.getOrderdets().add(orderDetail);
        order.setGrpid("1");

        orderhistService.createOrder(order,null);
    }

    @Test
    public void testQueryAuditOrder()
    {
        OrderAuditQueryDto orderAuditQueryDto=new OrderAuditQueryDto();
        orderAuditQueryDto.setProdId("1140102001");
        orderAuditQueryDto.setPageSize(10000);
        orderAuditQueryDto.setStartPos(0);
        List<OrderAuditExtDto> orderAuditExtDtoList=orderhistDao.queryAuditOrder(orderAuditQueryDto);
        if(orderAuditExtDtoList!=null)
        {
            for(OrderAuditExtDto orderAuditExtDto:orderAuditExtDtoList)
            {
                System.out.println(orderAuditExtDto.getOrder().getOrderid());
            }
        }
        System.out.println(orderhistDao.getAuditTotalCount(orderAuditQueryDto) );
        //Order order=orderhistDao.find("from Order a inner join fetch  a.orderdets b where b.prodid='1140102001'");
    }

}
