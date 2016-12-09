package com.chinadrtv.yuantong.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.chinadrtv.yuantong.common.dal.dao.WmsShipmentHeaderDao;
import com.chinadrtv.yuantong.common.dal.model.WmsShipmentDetail;
import com.chinadrtv.yuantong.common.dal.model.WmsShipmentHeader;
import com.chinadrtv.yuantong.service.CreateOrderService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-13
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
@Service("createOrderService")
public class CreateOrderServiceImpl implements CreateOrderService {
    private static Logger logger  = LoggerFactory.getLogger(CreateOrderServiceImpl.class);

    //加密id
    @Value("${parternId}")
    private String parternId;
    @Value("${sh_yt_kd_customerId}")
    private String sh_yt_kd_customerId; //上海圆通快递
    @Value("${cbg_yt_kd_customerId}")
    private String cbg_yt_kd_customerId; //CBG-圆通快递
    @Value("${sh_sh_yt_customerId}")
    private String sh_sh_yt_customerId; //上海售后-圆通
    @Value("${xyk_customerId}")
    private String xyk_customerId; //信用卡分期发货
    @Value("${sh_yt_customerId}")
    private String sh_yt_customerId; //上海圆通
    @Value("${api_url}")
    private String apiUrl;

    @Autowired
    private WmsShipmentHeaderDao wmsShipmentHeaderDao;

    private String clientId = ""; //客户编码
    private String customerId = ""; //客户编码
    
    @Override
    public void createOrder() {
    	
        OutputStreamWriter out = null;
        InputStream in = null;
        try{
            //从WMS库获取推送数据
            List<WmsShipmentHeader> wmsShipmentHeaders = wmsShipmentHeaderDao.findShipmentHeader();
            logger.info("get wmsShipmentHeaders size:"+wmsShipmentHeaders.size());
            if(wmsShipmentHeaders != null && wmsShipmentHeaders.size() > 0){
                    for(WmsShipmentHeader ws : wmsShipmentHeaders){     //遍历数据拼接xml数据推送圆通
                        this.checkCarrier(ws); //获取对应客户编码值
                        logger.info(ws.getCarrier()+";parternId:"+parternId+" clientId:"+clientId+" customerId:"+customerId);
                        //打开连接
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                       //获取订单明细
                        List<WmsShipmentDetail> shipmentDetailList = wmsShipmentHeaderDao.findDetails(ws.getShipmentId());
                        logger.info("get shipmentDetailList size :"+shipmentDetailList.size());
                        StringBuilder xmlBuilder = new StringBuilder();
                        xmlBuilder.append("<RequestOrder>");
                        xmlBuilder.append("    <clientID>"+clientId+"</clientID>");
                        xmlBuilder.append("    <logisticProviderID>YTO</logisticProviderID>");
                        xmlBuilder.append("    <customerId>"+customerId+"</customerId>");
                        xmlBuilder.append("    <txLogisticID>"+ws.getContainerId()+"</txLogisticID>");
                        xmlBuilder.append("    <tradeNo>1</tradeNo>");
                        xmlBuilder.append("    <mailNo>"+ws.getContainerId()+"</mailNo>");
                        xmlBuilder.append("    <type>1</type>");
                        xmlBuilder.append("    <orderType>"+ws.getOrderType()+"</orderType>");//普通订单
                        xmlBuilder.append("    <serviceType>1</serviceType>");
                        xmlBuilder.append("    <flag>0</flag>");
                        xmlBuilder.append("    <sender>");
                        xmlBuilder.append("        <name>橡果</name>");
                        xmlBuilder.append("        <postCode>200233</postCode>");
                        xmlBuilder.append("        <phone>400-6668888</phone>");
                        xmlBuilder.append("        <mobile></mobile>");
                        xmlBuilder.append("        <prov>上海</prov>");
                        xmlBuilder.append("        <city>上海</city>");
                        xmlBuilder.append("        <address>上海市青浦区华新镇华卫路18号</address>");
                        xmlBuilder.append("    </sender>");
                        xmlBuilder.append("    <receiver>");
                        xmlBuilder.append("        <name>"+ws.getCustomer()+"</name>");
                        xmlBuilder.append("        <postCode></postCode>");
                        //客户联系电话处理
                        String customerPhoneNum = ws.getCustomerPhoneNum();  //获取用户联系方式
                        if(customerPhoneNum != null && !customerPhoneNum.equals(""))
                        {
                            String[] phones = customerPhoneNum.split("   ");
                            for(String p : phones){    //电话号码
                                if(p.indexOf("-") > 0){
                                    xmlBuilder.append("        <phone>"+p+"</phone>");
                                }else if(p.length() == 11){      //手机号码
                                    xmlBuilder.append("        <mobile>"+p+"</mobile>");
                                }
                            }
                        }
                        xmlBuilder.append("        <prov>"+ws.getShipToProvinceName()+"</prov>");
                        xmlBuilder.append("        <city>"+ws.getShipToCityName()+"</city>");
                        //处理地址不规范字符
                        String address = ws.getShipToAddress();
                        address = address.replaceAll("<","(");
                        address = address.replaceAll(">",")");
                        address = address.replaceAll("&"," ");
                        xmlBuilder.append("        <address>"+address+"</address>");
                        xmlBuilder.append("    </receiver>");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z");
                        String time = df.format(ws.getActualShipDate());
                        xmlBuilder.append("    <sendStartTime>"+time+"</sendStartTime>");
                        xmlBuilder.append("    <sendEndTime>"+time+"</sendEndTime>");
                        //计算商品总金额
                      // double totalMoney = 0.00;
                       //if(shipmentDetailList != null && shipmentDetailList.size() > 0){
                            /*for(WmsShipmentDetail d : shipmentDetailList){
                                int qty = d.getTotalQty();
                                double price = d.getUnitPrice();
                                totalMoney += qty*price;
                            }*/
                            xmlBuilder.append("    <goodsValue>"+ws.getGoodsValue()+"</goodsValue>");
                            xmlBuilder.append("    <totalValue>0</totalValue>");
                            xmlBuilder.append("    <itemsValue>"+ws.getTotalValue()+"</itemsValue>");
                            xmlBuilder.append("    <itemsWeight>"+ws.getWeight()+"</itemsWeight>");
                            xmlBuilder.append("    <items>");
                            for(WmsShipmentDetail detail:shipmentDetailList){
                                xmlBuilder.append("        <item>");
                                xmlBuilder.append("            <itemName>"+detail.getKits()+"</itemName>");
                                xmlBuilder.append("            <number>"+detail.getTotalQty()+"</number>");
                                xmlBuilder.append("            <itemValue>"+detail.getUnitPrice()+"</itemValue>");
                                xmlBuilder.append("        </item>");
                            }
                      //}
                        xmlBuilder.append("    </items>");
                        xmlBuilder.append("    <special>1</special>");
                        xmlBuilder.append("    <remark>1</remark>");
                        xmlBuilder.append("    <insuranceValue>1</insuranceValue>");
                        xmlBuilder.append("    <totalServiceFee>1</totalServiceFee>");
                        xmlBuilder.append("    <codSplitFee>1</codSplitFee>");
                        xmlBuilder.append("</RequestOrder>");

                        //加密
                        MessageDigest messagedigest = MessageDigest.getInstance("MD5");
                        messagedigest.update((xmlBuilder.toString() + parternId).getBytes("UTF-8"));
                        byte[] abyte0 = messagedigest.digest();
                        String data_digest = new String(Base64.encodeBase64(abyte0));
                        //最终的xml
                        String queryString = "logistics_interface=" + URLEncoder.encode(xmlBuilder.toString(), "UTF-8")
                                + "&data_digest="+ URLEncoder.encode(data_digest,"UTF-8")
                                + "&clientId=" + URLEncoder.encode(clientId, "UTF-8");
                        out.write(queryString);
                        out.flush();

                        //获取服务端的反馈
                        String responseString = "";
                        String strLine = "";
                        in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while ((strLine = reader.readLine()) != null) {
                            responseString += strLine + "\n";
                        }

                        logger.info("responseString xml:"+responseString);
                        this.processResponseResult(responseString,ws.getShipmentId());
                    }
            }
        }catch (Exception e){
             logger.error("CreateOrderServiceImpl is createOrder error:"+e.getMessage());
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                logger.error("out.close, in.close error:"+e);
            }
        }
    }
    //获取对应的客户编码
    private void checkCarrier(WmsShipmentHeader ws){
        String carrier = ws.getCarrier();
        if(carrier.equals("上海圆通快递")){
            clientId = sh_yt_kd_customerId;
            customerId = sh_yt_kd_customerId;
        }else if(carrier.equals("CBG-圆通快递")){
            clientId = cbg_yt_kd_customerId;
            customerId = cbg_yt_kd_customerId;
        }else if(carrier.equals("上海售后-圆通")){
            clientId = sh_sh_yt_customerId;
            customerId = sh_sh_yt_customerId;
        }else if(carrier.equals("信用卡分期发货")){
            clientId = xyk_customerId;
            customerId = xyk_customerId;
        }else if(carrier.equals("上海圆通")) {
            clientId = sh_yt_customerId;
            customerId = sh_yt_customerId;
        }
    }
    //处理响应消息
    private void processResponseResult(String xml,String shipmentId){
        //String xml = "<Response><logisticProviderID>YTO</logisticProviderID><txLogisticID>1000009450</txLogisticID><success>false</success></Response>";
        //String errorXml = "<Response><logisticProviderID>YTO</logisticProviderID><success>false</success><reason>S01</reason></Response>"
        String reason = "";
        String success = "";
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            Element root = doc.getDocumentElement();
            success =   root.getElementsByTagName("success").item(0).getTextContent();
            if(success.equals("true")){   //成功 处理  回写数据标记
                int result = wmsShipmentHeaderDao.updateShipmentHeader(shipmentId);
                logger.info(shipmentId+":update wms ShipmentHeader success! affected count :" + result);
            }else{
                reason = root.getElementsByTagName("reason").item(0).getTextContent();
                logger.info(shipmentId+":response error reason="+reason);
            }
        }
        catch(Exception e)
        {
            logger.error("process response xml error:"+e.getMessage());
        }
    }

}
