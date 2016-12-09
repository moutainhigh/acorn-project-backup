package com.chinadrtv.erp.tc.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentException;
import com.chinadrtv.erp.tc.core.dao.*;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.service.OrderhistService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;
import com.chinadrtv.erp.test.SpringTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-17
 */
public class TCTest extends SpringTest {

    @Autowired
    private OrderhistDao orderhistDao;

    @Autowired
    private ModifyOrderhistDao modifyOrderhistDao;

    @Autowired
    private AcorderlistDao acorderlistDao;

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private OrderhistService orderhistService;

    @Resource(name = "dataSource")
    public void setDataSource(final DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Autowired
    private AddressExtDao addressExtDao;

    @Autowired
    private CompanyDao companyDao;
    //@Test
    public void testInit(){
        Company company= companyDao.getCompany("-22");
    	logger.debug("Init test case");
    }

    //@Test
    public void testDaoAndJson()
    {
        AddressExt addressExt=addressExtDao.get("815769035");
        if(addressExt!=null)
        {
            AreaAll areaAll=addressExt.getArea();
            System.out.println(areaAll.getAreaid()+areaAll.getAreaname());
            CountyAll countyAll=addressExt.getCounty();
            System.out.println(countyAll.getCountyid());
            CityAll cityAll=addressExt.getCity();
            System.out.println(cityAll.getCityid());
            Province province=addressExt.getProvince();
            System.out.println(province.getProvinceid())  ;

            areaAll=addressExt.getArea();
            System.out.println(areaAll.getAreaid());

            try
            {
                ObjectMapper objectMapper=new ObjectMapper();
                addressExt.setArea(null);
                addressExt.setProvince(null);
                addressExt.setCounty(null);
                addressExt.setCity(null);
                String str=objectMapper.writeValueAsString(addressExt);
                System.out.println(str);
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
        String orderId="49996573744";
        List<Order> orderhistList=orderhistDao.findList("from Order where orderId='"+orderId+"'");
        if(orderhistList.size()==1)
        {
            try
            {
                ObjectMapper objectMapper=new ObjectMapper();
                //ObjectWriter writer=ObjectWriter.with;//ObjectWriter.writer();
                /*Order orderhist=orderhistList.get(0);
                for(OrderDetail orderdet: orderhist.getOrderdets())
                {
                    System.out.println("OrderDetail ID:"+orderdet.getOrderdetid());
                } */
                /*addressExt=orderhistList.get(0).getAddress();
                if(addressExt!=null)
                {
                    System.out.println(addressExt.getArea());
                } */
                addressExt=orderhistList.get(0).getAddress();
                addressExt.setCity(null);
                addressExt.setCounty(null);
                addressExt.setProvince(null);
                addressExt.setArea(null);
                //orderhistList.get(0).setOrderdets(null);
                orderhistList.get(0).setAddress(null);
                //orderhistList.get(0).setOrderdets(null);
                for(OrderDetail orderdet:orderhistList.get(0).getOrderdets())
                {

                    if(orderdet.getProduct()!=null)
                    {
                        Product product=orderdet.getProduct();
                        //product.get
                        System.out.println(orderdet.getProduct().getProdname());
                        //orderdet.setProduct(null);
                    }
                }
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
    public void testOrderModel()
    {
        Order orderhist=orderhistDao.getOrderHistByOrderid("49996573744");
        if(orderhist==null)
        {
            System.out.println("order id is null");
        }
        else
        {
            for(OrderDetail orderdet:orderhist.getOrderdets())
            {
                if(orderdet.getProduct()==null)
                {
                    System.out.println("orderdet id:"+orderdet.getOrderdetid()+" have no product");
                }
                else
                {
                    System.out.println("orderdet id:"+orderdet.getOrderdetid()+" product id:"+ orderdet.getProduct().getProdid());
                }
            }
        }
    }

    //@Test
    public void testAcorderlistDao()
    {
        System.out.println(acorderlistDao.findAcorderListFromOrderId("1").size());
    }

    //@Test
    public void testModifyDao()
    {
        List<ModifyOrderhist> modifyOrderhistList= modifyOrderhistDao.findModifyFromOrderId("333");
        if(modifyOrderhistList==null||modifyOrderhistList.size()==0)
        {
            System.out.println("modify list is null");
        }
        else
        {
            for(ModifyOrderhist modifyOrderhist:modifyOrderhistList)
            {
                System.out.println("modify id:"+modifyOrderhist.getModorderid());
            }
        }
    }

    @Test
    public void testScanOutService() throws Exception
    {
        try{
        RequestScanOutInfo requestScanOutInfo=new RequestScanOutInfo();
        requestScanOutInfo.setMailId("acv123");
        requestScanOutInfo.setOrderId("928805222");
        requestScanOutInfo.setUser("123");
        requestScanOutInfo.setRevision(1L);
        //requestScanOutInfo.setCarrier("");
        //requestScanOutInfo.setMailType("");
        //requestScanOutInfo.setProdPrice("1014");
        //requestScanOutInfo.setTotalPrice("1164");

        shipmentHeaderService.scanOutShipment(requestScanOutInfo);
        }catch (ShipmentException shipExp)
        {
            System.out.println(shipExp.getShipmentReturnCode().getCode());
        }
    }

    //@Test
    public void testMok()
    {
        //String str=shipmentHeaderService.getSmpMessage("10","12","MX123");
        //System.out.println(str);
    }

    //@Test
    /*public void testOrderhistService() throws Exception
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

        orderhist.getOrderdets().add(orderdet);

        try
        {
        orderhistService.updateOrderhist(orderhist);
        }
        catch (OrderException orderExp)
        {
            throw orderExp;
        }

    }*/

    //@Test
    public void testOrderhistService() throws Exception
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
        orderhistService.updateOrderhist(orderhist);
        }
        catch (Exception exp)
        {
            throw exp;
        }

    }

    @Autowired
    private AddressExtService addressExtService;

  /*  @Autowired
    private OrderhistCoreService orderhistCoreService;*/

    //@Test
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
    }

    //@Test
    public void testCreateOrderFromPreTrade() throws Exception
    {
        //获取前置订单，然后插入
        PreTrade preTrade;
        Long preTradeId=243050L;
        preTrade=(PreTrade)addressExtDao.getJustSession().get(PreTrade.class,preTradeId);
        if(preTrade!=null)
        {
            preTrade.setPreTradeLot(null);
            PreTradeRest preTradeRest=new PreTradeRest();
            BeanUtils.copyProperties(preTrade,preTradeRest);
            preTradeRest.setCrusr("xzk");
            /*Set<PreTradeDetail> preTradeDetailSet=preTrade.getPreTradeDetails();
            preTradeRest.getPreTradeDetails().clear();
            for(PreTradeDetail preTradeDetail:preTradeDetailSet)
            {
                preTradeRest.getPreTradeDetails().add(preTradeDetail);
            }  */

            try
            {
                orderhistService.addOrderhist(preTradeRest);
                System.out.println("********************* ok *********************");
            }catch (Exception exp)
            {
                exp.printStackTrace();
                throw exp;
            }


        }
    }

    //@Test
    public void testCreateOrderFromPreTrade2() throws Exception
    {
        PreTradeRest preTradeRest=new PreTradeRest();
        preTradeRest.setTradeId("6654321");
        preTradeRest.setAlipayTradeId("");
        preTradeRest.setBuyerAlipayId("");
        preTradeRest.setTradeType("0");
        preTradeRest.setPayType("1");
        preTradeRest.setTradeFrom("TAOBAO");
        preTradeRest.setTmsCode("107");
        preTradeRest.setOutCrdt(new Date());
        preTradeRest.setInvoiceComment("just test");
        preTradeRest.setInvoiceTitle("test");
        preTradeRest.setBuyerNick("xzk");
        preTradeRest.setReceiverName("xzk");
        preTradeRest.setReceiverProvince("江苏");
        preTradeRest.setReceiverCity("常州市");
        preTradeRest.setReceiverCounty("金坛市");
        preTradeRest.setReceiverArea("");
        preTradeRest.setReceiverAddress("测试地址xxxxy");
        preTradeRest.setReceiverMobile("15811111112");
        preTradeRest.setReceiverPostCode("213200");
        preTradeRest.setCrusr("test02");
        preTradeRest.setUserid("2709423751");
        preTradeRest.setName("test02");
        preTradeRest.setUserMobile("15911111122");
        preTradeRest.setShippingFee(6D);
        preTradeRest.setPayment(612D);
        preTradeRest.setBuyerMessage("官网(无)");
        preTradeRest.setUserPhone("150265993");

        PreTradeDetail preTradeDetail=new PreTradeDetail();
        preTradeDetail.setShippingFee(6D);
        preTradeDetail.setPrice(398D);
        preTradeDetail.setPayment(379.23D);
        preTradeDetail.setTradeId("7654321");
        preTradeDetail.setOutSkuId("111040110401");
        preTradeDetail.setSkuId(181066L);
        preTradeDetail.setQty(1);
        preTradeDetail.setUpPrice(379.23D);
        preTradeRest.getPreTradeDetails().add(preTradeDetail);

        preTradeDetail=new PreTradeDetail();
        preTradeDetail.setShippingFee(6D);
        preTradeDetail.setPrice(238D);
        preTradeDetail.setPayment(226.77D);
        preTradeDetail.setTradeId("7654321");
        preTradeDetail.setOutSkuId("113033380007");
        preTradeDetail.setSkuId(181064L);
        preTradeDetail.setQty(1);
        preTradeDetail.setUpPrice(226.77D);
        preTradeRest.getPreTradeDetails().add(preTradeDetail);

        preTradeDetail=new PreTradeDetail();
        preTradeDetail.setShippingFee(6D);
        preTradeDetail.setPrice(29D);
        preTradeDetail.setPayment(0D);
        preTradeDetail.setTradeId("7654321");
        preTradeDetail.setOutSkuId("114027000000");
        preTradeDetail.setSkuId(181065L);
        preTradeDetail.setQty(1);
        preTradeDetail.setUpPrice(0D);
        preTradeRest.getPreTradeDetails().add(preTradeDetail);

        try
        {
            String orderId=orderhistService.addOrderhist(preTradeRest);
            System.out.println("********************* ok *********************"+orderId);
        }catch (Exception exp)
        {
            exp.printStackTrace();
            throw exp;
        }
    }

}
