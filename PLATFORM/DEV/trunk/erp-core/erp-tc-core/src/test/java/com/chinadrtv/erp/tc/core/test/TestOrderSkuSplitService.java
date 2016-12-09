package com.chinadrtv.erp.tc.core.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.test.SpringTest;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-3-9
 * Time: 上午11:05
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */

public class TestOrderSkuSplitService extends SpringTest {
    @Autowired
    private OrderSkuSplitService orderSkuSplitService;

    //@Test
    public void testFindIagentProduct() throws Exception {
        System.out.println("find iagent product by scmid test begin ******");

        Map<String, Object> resultMap = orderSkuSplitService.findIagentProduct("114010030001");

        System.out.println(resultMap.get("prodid"));
        System.out.println(resultMap.get("prodtype"));

        System.out.println("find iagent product by scmid test end ******");

        Assert.assertNotNull(resultMap.get("prodtype"));
    }

    @Test
    public void testOrderSkuSplit() throws Exception {

        System.out.println("商品拆分测试***************");

        Order orderhist = new Order();
        Set<OrderDetail> orderdetSet = new HashSet<OrderDetail>();
        OrderDetail orderdet = new OrderDetail();

        orderhist.setOrderid("928308242");
        orderhist.setMailtype("3");
        orderhist.setOrdertype("36");
        orderhist.setMailprice(new BigDecimal(39));
        orderhist.setPaytype("1");
        orderhist.setStatus("1");
        orderhist.setResult("1");
        orderhist.setProdprice(new BigDecimal(717));
        orderhist.setTotalprice(new BigDecimal(756));
        orderhist.setUrgent("0");
        orderhist.setCrusr("sa");
        orderhist.setCallid("442554783");
        orderhist.setContactid("943492257");
        orderhist.setPaycontactid("943492257");
        orderhist.setGetcontactid("943492257");
        orderhist.setBill("1");
        orderhist.setStarttm(new Date());
        orderhist.setEndtm(new Date());
        orderhist.setJifen("0");
        orderhist.setTicket(0);
        orderhist.setAreacode("010");
        orderhist.setCityid("33CN");
        orderhist.setProvinceid("14");
        orderhist.setSpellid(47);
        orderdet.setOrderhist(orderhist);

        //产品金额大于成本
        orderdet.setProdid("1140102400");
        orderdet.setProdname("AOYA奥雅丝柔粉底液35ml 保湿遮瑕强 控油隔离美白 正品专柜彩妆");
        orderdet.setProdscode("LWYTZGT1");
        orderdet.setProducttype("710");
        orderdet.setUpnum(0);
        orderdet.setUprice(new BigDecimal(98.00));
        orderdet.setSpnum(2);
        orderdet.setSprice(new BigDecimal(76.82));
        orderdet.setOrderdetid("34208413");
        orderdet.setSoldwith("2");
        orderdetSet.add(orderdet);
        orderhist.setOrderdets(orderdetSet);

        orderdet=new OrderDetail();
        orderdet.setProdid("1140102001");
        orderdet.setProdname("AOYA奥雅无瑕亮采粉底霜 保湿遮瑕防水粉底膏 自然简单无瑕 正品");
        orderdet.setProdscode("YEJZYT10");
        orderdet.setProducttype("31852");
        orderdet.setUpnum(0);
        orderdet.setUprice(new BigDecimal(98.00));
        orderdet.setSpnum(3);
        orderdet.setSprice(new BigDecimal(57.12));
        orderdet.setOrderdetid("33289768");
        orderdet.setSoldwith("1");
        orderdet.setFreight(new BigDecimal(0));
        orderdetSet.add(orderdet);

        //产品金额小于成本
        /*orderdet.setProdid("1110401110");
        orderdet.setProdname("益尔健活力健身器（卓越版）三台+紫环远红外记忆枕三个");
        orderdet.setProdscode("YEJZYT10");
        orderdet.setProducttype("32092");
        orderdet.setUpnum(1);
        orderdet.setUprice(new BigDecimal(136.00));
        orderdet.setSpnum(0);
        orderdet.setSprice(new BigDecimal(0));
        orderdet.setOrderdetid("33289768");
        orderdet.setSoldwith("1");
        orderdet.setFreight(new BigDecimal(50.00));
        orderdetSet.add(orderdet);*/

        orderhist.setOrderdets(orderdetSet);

        List<Map<String, Object>> s = orderSkuSplitService.orderSkuSplit(orderhist);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    //@Test
    public void testOnlyProduct() throws Exception {
        System.out.println("测试非组合商品*********************");
        Order orderhist = new Order();
        Set<OrderDetail> orderdetSet = new HashSet<OrderDetail>();
        OrderDetail orderdet = new OrderDetail();

        orderhist.setOrderid("928308242");
        orderhist.setMailtype("3");
        orderhist.setOrdertype("36");
        orderhist.setMailprice(new BigDecimal(39));
        orderhist.setPaytype("1");
        orderhist.setStatus("1");
        orderhist.setResult("1");
        orderhist.setProdprice(new BigDecimal(717));
        orderhist.setTotalprice(new BigDecimal(756));
        orderhist.setUrgent("0");
        orderhist.setCrusr("sa");
        orderhist.setCallid("442554783");
        orderhist.setContactid("943492257");
        orderhist.setPaycontactid("943492257");
        orderhist.setGetcontactid("943492257");
        orderhist.setBill("1");
        orderhist.setStarttm(new Date());
        orderhist.setEndtm(new Date());
        orderhist.setJifen("0");
        orderhist.setTicket(0);
        orderhist.setAreacode("010");
        orderhist.setCityid("33CN");
        orderhist.setProvinceid("14");
        orderhist.setSpellid(47);

        orderdet.setOrderhist(orderhist);

        //非组合商品
        orderdet.setProdid("1140103000");
        orderdet.setProdname("彩妆专柜 AOYA/奥雅鸡尾酒唇彩6g/支 韩国唇蜜裸色润唇膏保湿");
        orderdet.setProdscode("YEJZYT7");
        orderdet.setProducttype("1727");
        orderdet.setUpnum(0);
        orderdet.setUprice(new BigDecimal(69.00));
        orderdet.setSpnum(1);
        orderdet.setSprice(new BigDecimal(138.00));
        orderdet.setOrderdetid("33336516");
        orderdet.setSoldwith("2");
        orderdet.setFreight(new BigDecimal(50.00));
        orderdetSet.add(orderdet);

        orderhist.setOrderdets(orderdetSet);

        List<Map<String, Object>> s = orderSkuSplitService.orderSkuSplit(orderhist);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void test1()
    {
        try
        {
        Map<String, Object> agentProdInfo = orderSkuSplitService.findIagentProduct("114010240001");
            System.out.println(agentProdInfo.keySet().size());
        }catch (Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }

}
