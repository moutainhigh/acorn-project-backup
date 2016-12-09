package com.chinadrtv.erp.tc.test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.chinadrtv.erp.model.Ems;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.AddressExtDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.tc.service.OrderhistService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-24
 */
//@ContextConfiguration("classpath:applicationContext-*.xml")
public class OrderRESTClientTest extends OrderRESTClient<Order,OrderReturnCode> {
    //@Autowired
    //private OrderRESTClient client;
    private final static String url = "http://localhost:8080/erp-tc/order/1";

    /**
     * 用于客户端测试
     */
    @Test
    public void mokTest()
    {

    }

    //@Test
    public void testOrderhistService() throws Exception
    {
        Order orderhist=new Order();
        orderhist.setOrderid("997700222");
        orderhist.setMdusr("xxx");

        OrderDetail orderdet=new OrderDetail();
        orderdet.setOrderhist(orderhist);
        orderdet.setOrderdetid("101018");
        orderdet.setProdname("xxx1");

        orderhist.getOrderdets().add(orderdet);


        orderdet=new OrderDetail();
        orderdet.setOrderhist(orderhist);
        orderdet.setOrderdetid("999999999");
        orderdet.setProdname("xxx2");
        orderdet.setStatus("1");
        orderdet.setOrderid("997700222");

        orderhist.getOrderdets().add(orderdet);

        try
        {
            orderhistService.updateOrderhist(orderhist);
        }
        catch (OrderException orderExp)
        {
            throw orderExp;
        }
        catch (ConstraintViolationException constraintExp)
        {
            //目前先只返回一个错误提示
            String strDesc=this.getConstraintErrorDesc(constraintExp);
            throw new Exception(strDesc);
        }

    }

    private String getConstraintErrorDesc(ConstraintViolationException constraintExp)
    {
        Set<ConstraintViolation<?>> errorSet= constraintExp.getConstraintViolations();
        if(errorSet!=null&&errorSet.size()>0)
        {
            for(ConstraintViolation<?> error: errorSet)
            {
                return error.getPropertyPath()+":" +error.getMessage();
            }
        }
        return "";
    }

    /*@Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Test
    public void testShipment()
    {
        System.out.println(shipmentHeaderService.getShipmentHeader(1L));
    } */

    //@Test
    public void testAddOrder()
    {
        /*Order orderhist=new Order();
        orderhist.setOrderid("11111");
        orderhist.setCrusr("徐志凯");
        orderhist.setStatus("1");
        orderhist.setRevision(0);
        orderhist.setTotalprice(new BigDecimal("333"));
        OrderDetail orderdet=new OrderDetail();
        orderdet.setOrderdetid("111111");
        orderdet.setOrderhist(orderhist);
        orderdet.setRevision(0);
        orderhist.getOrderdets().add(orderdet);*/
        Order orderhist= orderhistDao.getOrderHistByOrderid("123");
        OrderDetail orderdet=new OrderDetail();
        AddressExt addressExt=orderhist.getAddress();
        orderhist.setAddress(null);
        orderhist.setMdusr("xxxx");
        orderhist.setStatus("1");
        for(OrderDetail orderdet1:orderhist.getOrderdets())
        {
            orderdet1.setProduct(null);
        }
        orderdet.setOrderid("123");
        orderdet.setOrderdetid("1235");
        orderdet.setOrderhist(orderhist);
        orderdet.setSpnum(1);
        orderdet.setUpnum(1);
        orderdet.setUprice(new BigDecimal("11.0"));
        orderdet.setSprice(new BigDecimal("10.0"));
        orderdet.setProducttype("20051");
        orderdet.setProdid("1060715900");
        orderdet.setStatus("1");
        orderhist.getOrderdets().add(orderdet);

        OrderReturnCode code=this.postData(url + "/save", orderhist);
        System.out.println(code.getCode()+"-"+code.getDesc());

    }

    //@Test
    public void testDaoAndJson()
    {
        /*AddressExt addressExt=addressExtDao.get("815769035");
        if(addressExt!=null)
        {
            System.out.println(addressExt);
        }*/
        String orderId="123";
        List<Order> orderhistList=orderhistDao.findList("from Order where orderId='"+orderId+"'");
        if(orderhistList.size()==1)
        {
            try
            {
                ObjectMapper objectMapper=new ObjectMapper();
                //ObjectWriter writer=ObjectWriter.with;//ObjectWriter.writer();
                Order orderhist=orderhistList.get(0);
                OrderDetail orderdet=new OrderDetail();
                AddressExt addressExt=orderhist.getAddress();
                orderhist.setAddress(null);
                orderhist.setMdusr("xxxx");
                for(OrderDetail orderdet1:orderhist.getOrderdets())
                {
                    orderdet1.setProduct(null);
                }
                orderdet.setOrderid("123");
                orderdet.setOrderdetid("1235");
                orderdet.setOrderhist(orderhist);
                orderdet.setSpnum(1);
                orderdet.setUpnum(1);
                orderdet.setUprice(new BigDecimal("11.0"));
                orderdet.setSprice(new BigDecimal("10.0"));
                orderdet.setProducttype("20051");
                orderdet.setProdid("1060715900");
                orderdet.setStatus("1");
                orderhist.getOrderdets().add(orderdet);

                for(OrderDetail orderdet1: orderhist.getOrderdets())
                {
                    orderdet1.setProduct(null);
                }
                /*AddressExt addressExt=orderhistList.get(0).getAddress();
                if(addressExt!=null)
                {
                    System.out.println(addressExt.getArea());
                }*/
                orderhistList.get(0).setAddress(null);
                String json=objectMapper.writeValueAsString(orderhistList.get(0));
                System.out.println(json);


            }catch (Exception exp)
            {
                System.out.println(exp.getMessage());
            }
        }
        else
        {
            System.out.println("get order size:"+orderhistList.size());
        }
    }

    //@Test
    public void testFieldOper()
    {
        Order orderhist=new Order();
        orderhist.setOrderid("9999");
        orderhist.setCrusr("123");
        orderhist.setStatus("1");
        orderhist.setRevision(0);
        orderhist.setTotalprice(new BigDecimal("333"));
        OrderDetail orderdet=new OrderDetail();
        orderdet.setOrderdetid("99991");
        orderdet.setOrderhist(orderhist);
        orderdet.setRevision(0);
        orderhist.getOrderdets().add(orderdet);

        //orderhistDao.save(orderhist);


        /*OrderhistUtil orderhistUtil=new OrderhistUtil();
        orderhistUtil.init();

        Order orderhist2=new Order();
        orderhistUtil.CopyNotNullValue(orderhist,orderhist2);
        System.out.println(orderhist2.getOrderid());*/
    }

    //@Test
    public void testJsonRead()
    {
        //String json="{\"@id\":1,\"orderid\":\"9913690909\",\"address\":{\"addressId\":\"815769034\"},\"mailtype\":\"3\",\"ordertype\":\"36\",\"mailprice\":\"39\",\"paytype\":\"1\",\"status\":\"1\",\"result\":\"1\",\"prodprice\":\"2580\",\"totalprice\":\"2619\",\"urgent\":\"0\",\"note\":\"\",\"crdt\":\"2013-02-21 11:11:34\",\"crusr\":\"sa\",\"callid\":\"44399180\",\"contactid\":\"935448176\",\"paycontactid\":\"935448176\",\"getcontactid\":\"935448176\",\"bill\":\"0\",\"media\":\"\",\"starttm\":\"2013-02-21 11:02:10\",\"endtm\":\"2013-02-21 11:11:34\",\"parentid\":\"\",\"callbackid\":\"\",\"dnis\":\"\",\"cbcrusr\":\"\",\"jifen\":\"0\",\"ticket\":\"0\",\"areacode\":\"010\",\"cityid\":\"JXLS\",\"provinceid\":\"23\",\"spellid\":\"1748\",\"nowmoney\":\"2619\",\"grpid\":\"Test\",\"companyid\":\"260\",\"spid\":\"\",\"scratchcard\":\"\",\"sccardamount\":\"0\",\"invoicetitle\":\"个人\",\"netorderid\":\"\",\"alipayid\":\"\",\"orderdets\":[{\"orderhist\":1,\"orderdetid\":\"29205767\",\"orderid\":\"9913690909\",\"prodid\":\"1200101500\",\"prodname\":\"激情与梦想08残奥电话卡册\",\"prodscode\":\"CADHK\",\"uprice\":\"2580\",\"upnum\":\"1\",\"sprice\":\"0\",\"spnum\":\"0\",\"soldwith\":\"1\",\"status\":\"1\",\"orderdt\":\"2013-02-21 11:11:34\",\"reckoning\":\"N\",\"contactid\":\"935448176\",\"state\":\"江西\",\"provinceid\":\"23\",\"city\":\"九江市\",\"freight\":\"39\",\"payment\":\"2580\",\"producttype\":\"none\",\"catalogno\":\"\",\"promotionsdocno\":\"\",\"promotionsdetruid\":\"\",\"num1\":\"\",\"num2\":\"\",\"baleprodid\":\"\",\"jifen\":\"0\",\"ticket\":\"0\",\"oldprod\":\"\",\"accountingcost\":\"0\",\"spid\":\"\",\"scratchcard\":\"\",\"sccardamount\":\"0\"}]}";
        String json="{\"@id\":1,\"orderid\":\"928109282\",\"mailtype\":\"3\",\"ordertype\":\"36\",\"mailprice\":\"39\",\"paytype\":\"1\",\"status\":\"1\",\"result\":\"1\",\"prodprice\":\"4680\",\"totalprice\":\"4719\",\"urgent\":\"0\",\"note\":\"\",\"crdt\":\"2013-02-22 15:51:22\",\"crusr\":\"sa\",\"callid\":\"442554561\",\"contactid\":\"943398257\",\"paycontactid\":\"943398257\",\"getcontactid\":\"943398257\",\"bill\":\"1\",\"media\":\"\",\"starttm\":\"2013-02-22 15:47:22\",\"endtm\":\"2013-02-22 15:51:22\",\"parentid\":\"\",\"callbackid\":\"\",\"dnis\":\"\",\"cbcrusr\":\"\",\"jifen\":\"0\",\"ticket\":\"0\",\"areacode\":\"010\",\"address\":{\"addressId\":\"28096074\"},\"cityid\":\"11CP\",\"provinceid\":\"01\",\"spellid\":\"5\",\"nowmoney\":\"4719\",\"grpid\":\"Test\",\"companyid\":\"104\",\"spid\":\"\",\"scratchcard\":\"\",\"sccardamount\":\"0\",\"invoicetitle\":\"个人\",\"orderdets\":[{\"orderhist\":1,\"orderdetid\":\"33336163\",\"orderid\":\"928109282\",\"prodid\":\"1200101000\",\"prodname\":\"中国与奥运邮币卡大型典藏册（珍藏版）\",\"prodscode\":\"AYYBK2\",\"uprice\":\"4680\",\"upnum\":\"1\",\"sprice\":\"0\",\"spnum\":\"0\",\"soldwith\":\"1\",\"status\":\"1\",\"orderdt\":\"2013-02-22 15:51:22\",\"reckoning\":\"N\",\"contactid\":\"943398257\",\"state\":\"北京\",\"provinceid\":\"01\",\"city\":\"北京市\",\"freight\":\"39\",\"payment\":\"4680\",\"producttype\":\"none\",\"catalogno\":\"\",\"promotionsdocno\":\"\",\"promotionsdetruid\":\"\",\"num1\":\"\",\"num2\":\"\",\"baleprodid\":\"\",\"jifen\":\"0\",\"ticket\":\"0\",\"oldprod\":\"\",\"accountingcost\":\"1075\",\"spid\":\"\",\"scratchcard\":\"\",\"sccardamount\":\"0\"}]}";
        ObjectMapper objectMapper=new ObjectMapper();
        //ObjectWriter writer=ObjectWriter.with;//ObjectWriter.writer();
        try
        {
            Order orderhist=(Order)objectMapper.readValue(json,Order.class);
            if(orderhist.getAddress()!=null)
            {
                System.out.println(orderhist.getAddress().getAddressId());
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }
    //@Test
    public void testJson()
    {
        Order orderhist=new Order();
        orderhist.setOrderid("123");
        orderhist.setOrdertype("98");
        orderhist.setPaytype("4");
        AddressExt addressExt=new AddressExt();
        CityAll cityAll=new CityAll();
        cityAll.setCityid(42);
        addressExt.setCity(cityAll);
        CountyAll countyAll=new CountyAll();
        countyAll.setCountyid(465);
        addressExt.setCounty(countyAll);
        AreaAll areaAll=new AreaAll();
        areaAll.setAreaid(1005755);
        addressExt.setArea(areaAll);
        Province province=new Province();
        province.setProvinceid("6");
        addressExt.setProvince(province);
        orderhist.setAddress(addressExt);
        addressExt.setAddressId("1");
        orderhist.setCrusr("xzk");

        //设置明细
        OrderDetail orderdet=new OrderDetail();
        orderdet.setOrderdetid("1234");
        orderdet.setOrderid("123");
        orderdet.setOrderhist(orderhist);
        orderdet.setProdid("1120846600");
        orderdet.setProducttype("6002");
        orderdet.setUpnum(1);
        orderdet.setUprice(new BigDecimal("10.0"));
        orderdet.setSpnum(1);
        orderdet.setSprice(new BigDecimal("9.0"));

        orderhist.getOrderdets().add(orderdet);

        try
        {
            ObjectMapper objectMapper=new ObjectMapper();
            //ObjectWriter writer=ObjectWriter.with;//ObjectWriter.writer();
            String json=objectMapper.writeValueAsString(orderhist);
            System.out.println(json);


            //orderhistService.updateOrderhist(orderhist);


            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }catch (Exception exp)
        {
              System.out.println(exp.getMessage());
        }

    }

    @Autowired
    private OrderhistDao orderhistDao;

    @Autowired
    private OrderhistService orderhistService;

    //@Test
    public void testDao()
    {
        try
        {
            Order orderhist=new Order();
            orderhist.setOrderid("9999");
            orderhist.setCrusr("123");
            orderhist.setMdusr("223");
            orderhist.setStatus("1");
            orderhist.setRevision(0);
            orderhist.setTotalprice(new BigDecimal("333"));
            OrderDetail orderdet=new OrderDetail();
            orderdet.setOrderdetid("99991");
            orderdet.setOrderhist(orderhist);
            orderdet.setRevision(0);
            orderhist.getOrderdets().add(orderdet);

            orderhistService.updateOrderhist(orderhist);
            //Order orderhist=orderhistService.setOrderhistFromId("8888");
            System.out.println(orderhist.getOrderid());
        }
        catch (Exception exp)
        {
                                                            System.out.println("************");
              System.out.println(exp.getMessage());
        }
        /*if(orderhistList.size()==0)
        {
            System.out.println("not find order");
        }
        else
        {
            Order orderhist=orderhistList.get(0);
            orderhist.setTotalprice(new BigDecimal("1222"));
            System.out.println("****************************");
            try
            {
            orderhistDao.saveOrder(orderhist);
            }
            catch (ConstraintViolationException cExp)
            {
                Set<ConstraintViolation<?>> errorSet= cExp.getConstraintViolations();
                for(ConstraintViolation<?> item: errorSet)
                {
                    System.out.println(item.getMessage());
                }
                System.out.println(cExp.getMessage());
            }
            catch (ValidationException validExp)
            {

                System.out.println(validExp.getMessage());
            }

        } */

    }

    @Autowired
    private AddressExtDao addressExtDao;

    //@Test
    public void testAddressDao()
    {
        /*AddressExt addressExt=new AddressExt();
        addressExt.setAddressId(addressExtDao.GetAddressId());
        //addressExt.setCity(new CityAll());
        //addressExt.setCounty(new CountyAll());
        //Province province=new Province();
        //province.setProvinceid("01");
       // addressExt.setProvince(province);
        addressExt.setArea(new AreaAll());
        addressExt.setUptime(new Date());

        addressExt.setContactId("1");
        addressExt.setAddressDesc("just test");

        System.out.println("************ address id:"+addressExt.getAddressId());
        //addressExtDao.save(addressExt);*/




    }

    //@Test
    public void testCreateOrderFromPreTrade() throws Exception
    {
         //获取前置订单，然后插入
        PreTrade preTrade;
        Long preTradeId=232491270545635L;
        preTrade=(PreTrade)addressExtDao.getJustSession().get(PreTrade.class,preTradeId);
        if(preTrade!=null)
        {
            /*try
            {
            orderhistService.addOrderhist(preTrade);
            }catch (Exception e)
            {
                throw e;
            } */
            preTrade.setPreTradeLot(null);

            ObjectMapper objectMapper=new ObjectMapper();
            //ObjectWriter writer=ObjectWriter.with;//ObjectWriter.writer();
            String json=objectMapper.writeValueAsString(preTrade);

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println(json);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
    }

    @Autowired
    private ShipmentHeaderService shipmentService;

    //@Test
    public void testAddShipment() throws Exception
    {
        ShipmentHeader shipmentHeader=new ShipmentHeader();
        shipmentHeader.setLogisticsStatusId("2");
        shipmentHeader.setMailId("2");
        shipmentHeader.setAccountStatusId("2");
        shipmentHeader.setAccountStatusRemark("2");
        shipmentHeader.setAccountType("2");
        //shipmentHeader.setOrderRefHisId(2L);
        shipmentHeader.setOrderId("123");
        shipmentHeader.setEntityId("2");
        shipmentHeader.setCrusr("2");
        shipmentHeader.setLogisticsStatusRemark("2");
        shipmentHeader.setOrderRefRevisionId(1L);

        ShipmentDetail shipmentDetail=new ShipmentDetail();
        shipmentDetail.setCarrier(new BigDecimal("12.3"));
        shipmentDetail.setShipmentLineNum(1L);
        shipmentDetail.setItemId("1");
        shipmentDetail.setItemDesc("111");
        shipmentDetail.setTotalQty(1L);
        shipmentDetail.setUnitPrice(new BigDecimal("1.1"));
        shipmentDetail.setQuantity("1");
        shipmentDetail.setShipmentHeader(shipmentHeader);

        shipmentHeader.getShipmentDetails().add(shipmentDetail);

        ShipmentDetail shipmentDetail2=new ShipmentDetail();
        shipmentDetail2.setCarrier(new BigDecimal("22.3"));
        shipmentDetail2.setShipmentLineNum(2L);
        shipmentDetail2.setItemId("2");
        shipmentDetail2.setItemDesc("222");
        shipmentDetail2.setTotalQty(2L);
        shipmentDetail2.setUnitPrice(new BigDecimal("2.2"));
        shipmentDetail2.setQuantity("2");
        shipmentDetail2.setShipmentHeader(shipmentHeader);

        shipmentHeader.getShipmentDetails().add(shipmentDetail2);

        shipmentService.addShipmentHeader(shipmentHeader);
    }

    @Autowired
    private AddressExtService addressExtService;

    @Test
    public void testAddressExt()
    {
        /*List<AddressExt> addressExtList= addressExtDao.findList("from AddressExt where city.cityname=:cityname",new ParameterString("cityname","烟台市"));
        System.out.println(addressExtList.size());*/
        AddressExt addressExt= addressExtService.findMatchAddressFromNames("河北","张家口市","宣化区","","小东门永辉超市一层","");
        if(addressExt==null)
        {
            System.out.println("address is null");
        }
        else
        {
            System.out.println("prov id:"+addressExt.getProvince().getProvinceid()+" prov name:"+addressExt.getProvince().getChinese());
            System.out.println("city id:"+addressExt.getCity().getCityid()+" city name:"+addressExt.getCity().getCityname());
            System.out.println("county id:"+addressExt.getCounty().getCountyid()+ "county name:"+addressExt.getCounty().getCountyname());
            System.out.println("area id:"+addressExt.getArea().getAreaid()+"  area name:"+addressExt.getArea().getAreaname());
        }
        addressExt.setAddressDesc("test");
        addressExt.setContactId("943398247");

        Ems ems=null;
        addressExtService.createAddressExt(addressExt,ems);
    }
}
