package com.chinadrtv.erp.sales.core.test;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.model.GrpOrderType;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.utils.OrderhistUtil;
import com.chinadrtv.erp.test.SpringTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@TransactionConfiguration( defaultRollback = false)
public class OrderServiceTest extends SpringTest {

    @Autowired
    private OrderhistService orderhistService;

    //@Test
    public void testAmortisationDao() throws ErpException
    {
        List<Amortisation> amortisationList= orderhistService.queryCardAmortisation("008");
        if(amortisationList!=null)
        {
            for(Amortisation amortisation:amortisationList)
            {
                System.out.println(amortisation.getUprice());
            }
        }
    }

    @Autowired
    private OrderhistDao orderhistDao;
    //@Test
    public void testAudiDao()
    {
        OrderAuditQueryDto orderAuditQueryDto=new OrderAuditQueryDto();
        orderAuditQueryDto.setStartPos(0);
        orderAuditQueryDto.setPageSize(10);
        List<OrderAuditExtDto> orderAuditExtDtoList=orderhistDao.queryAuditOrder(orderAuditQueryDto);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date dtBegin=null;
        try
        {dtBegin=simpleDateFormat.parse("2011-05-28");}
        catch (Exception exp)
        {

        }
        orderAuditQueryDto.setBeginCrdt(dtBegin);
        int nub=orderhistDao.getAuditTotalCount(orderAuditQueryDto);
        System.out.println(orderAuditExtDtoList.size());
        System.out.println(nub);
    }

    //@Test
    public void testGrpOrderType() throws Exception
    {
        List<GrpOrderType> grpOrderTypeList=orderhistService.queryGrpOderType("1");
        if(grpOrderTypeList!=null)
        {
            for(GrpOrderType grpOrderType:grpOrderTypeList)
            {
                System.out.println(grpOrderType.getOrderType());
            }
        }
    }

    //@Test
    public void testMailprice()
    {
        //Order order=orderhistService.getOrderhist(50000018050L);
        Order order=new Order();
        order.setMailtype("3");
        order.setOrdertype("1");
        order.setPaytype("1");
        order.setGrpid("3");
        order.setContactid("945027217");
        AddressExt addressExt=new AddressExt();
        addressExt.setAddressId("28468828");
        order.setAddress(addressExt);
        order.setCrusr("12650");

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProdid("1130175900");
        orderDetail.setSoldwith("1");
        orderDetail.setProdname("瑞蒂妮丝2012新款加大码玫瑰提花修身连衣裙（配腰带）");
        orderDetail.setUpnum(1);
        orderDetail.setUprice(new BigDecimal("299"));
        orderDetail.setSpnum(null);
        orderDetail.setSprice(null);

        order.getOrderdets().add(orderDetail);
        try{
            BigDecimal bigDecimal=orderhistService.calcOrderMailPrice(order,"1");
            System.out.println(bigDecimal.toString());
        }catch (Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

    //@Test
    public void testDeleteOrder() throws Exception
    {
        orderhistService.saveOrderCancelRequest("22142008","test","14594");
    }

    //@Test
    public void testCreateOrder()  throws Exception
    {
        Order order=orderhistService.getOrderHistByOrderid("920969272");
        Order orderNew=new Order();
        OrderhistUtil.copy(orderNew, order);
        orderNew.setId(null);
        orderNew.setOrderid(null);
        orderNew.setStatus("1");
        orderNew.setJifen("8");

        for(OrderDetail orderDetail:orderNew.getOrderdets())
        {
            orderDetail.setId(null);
            orderDetail.setOrderid(null);
            orderDetail.setOrderdetid(null);
            orderDetail.setStatus("1");
            orderDetail.setJifen("8");
        }
        orderNew.setOrdertype("35");
        orderhistService.createOrder(orderNew,null);
    }


    //@Test
    public void testQueryOrder()  throws Exception
    {
        OrderQueryDto orderQueryDto=new OrderQueryDto();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        orderQueryDto.setBeginCrdt(simpleDateFormat.parse("2013-12-26 11:00:00"));
        orderQueryDto.setEndCrdt(simpleDateFormat.parse("2014-01-02 14:00:00"));
        orderQueryDto.setSortCrDate(-1);
        orderQueryDto.setCheckResult(2);
        orderQueryDto.setTrackUsr("20429");
        orderQueryDto.setCrUsr("20429");
        orderQueryDto.setPageSize(100);
        orderQueryDto.setStartPos(0);
        AddressExt addressExt=new AddressExt();
        addressExt.setProvince(new Province());
        addressExt.getProvince().setProvinceid("18");
        orderQueryDto.setAddressExt(addressExt);

        //List<Order> orderList=orderhistDao.findList("from Order where (crusr='20429' or trackUsr='20429') and (crdt>=to_date('2013-12-26','yyyy-MM-dd') and crdt<=to_date('2014-01-02','yyyy-MM-dd'))");
        List<Order> orderList=orderhistDao.queryOrder(orderQueryDto);
        System.out.println(orderList.size());
    }

    @Test
    public void testJJJ()  throws Exception
    {
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("1","xxx");
        map.put("2","yyy");
        list.add(map);

        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("3","hhh");

        list.add(map1);

        System.out.println(list.getClass().getName());
        ObjectMapper objectMapper=new ObjectMapper();

        String str=objectMapper.writeValueAsString(list);

        System.out.println(str);
    }

    @Test
    public void test_getOrderType(){

        List<String> n400List = orderhistService.queryN400ByDnis("33385375");

        List<GrpOrderType> value = orderhistService.queryOrderType(n400List, "021");

        Assert.assertNotNull(value);


    }

}
