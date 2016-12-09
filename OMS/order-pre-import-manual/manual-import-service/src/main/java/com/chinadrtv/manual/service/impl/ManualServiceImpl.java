package com.chinadrtv.manual.service.impl;

import com.chinadrtv.manual.bean.ItemCsv;
import com.chinadrtv.manual.service.ManualService;
import com.chinadrtv.manual.service.PreTradeCompanyService;
import com.chinadrtv.model.constant.TradeSourceConstants;
import com.chinadrtv.model.oms.PreTradeCard;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeLotService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-5
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Service("manualService")
public class ManualServiceImpl implements ManualService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ManualServiceImpl.class);
    private static DateFormat expireDateFormat=new SimpleDateFormat( "yyyy-MM");
    private static Pattern expireDatePattern = Pattern.compile("[0-9]{4}-[0-9]{2}");

    @Autowired
     private PreTradeCompanyService preTradeCompanyService;
    @Autowired
    private PreTradeLotService preTradeLotService;

    @Override
    public HSSFWorkbook doProcessXLS(InputStream inputStream) {
        //String fileToBeRead="D:/ESB/manual-3.xls";
        try{
            // 创建对Excel工作簿文件的引用
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // 创建对工作表的引用。
            // 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
            HSSFSheet sheet = workbook.getSheetAt(0);
            this.parseXls(sheet);
            return workbook;
        }catch (Exception ex){
            logger.info("ManualServiceImpl doProcessXLS Exception: ",ex);
        }
        return null;
    }
    /**
     * 解析支付宝总账单（Excel）
     * @param sheet
     */
    private void parseXls(Sheet sheet) {
        logger.info("parseXLs begin...........");
        //外部订单号（银行或CMS）	订单类型	订单来源	银行编码	授权号	订购人姓名	订购人别名	收货人姓名	身份证
        // 卡号	卡有效期	期数	区号1	联系电话1	分机1	区号2	联系电话2	分机2	省份	城市	区县	乡镇
        // 详细地址	支付方式	销售方式	指定送货公司	发票类型	发票抬头	产品编码	商品规格	产品单价
        // 数量	产品总价	买家备注	卖家备注	佣金率	订单生成日	制表人员  运费
        String[] fields = new String[] {"ops_trade_id",
                "trade_type","trade_from","bank_code","auth_code","buyer_nick","buyer_id",
                "receiver_name","id_card_number","credit_card_number","credit_card_expire",
                "credit_card_cycles","long_distance_code1","telephone1","extension1",
                "long_distance_code2","telephone2","extension2","province","city","county","area",
                "address","pay_type","mail_type","tms_code","invoice_comment","invoice_title",
                "sku_id","sku_name","price","qty","amount","buyer_message","seller_message",
                "commission_rate","created_on","created_by","shipping_fee"};

        List<ItemCsv> items = new ArrayList<ItemCsv>();
        for(int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows();rowIndex++)
        {
            Row row = sheet.getRow(rowIndex);
            if(row != null){
                ItemCsv csv = new ItemCsv();
                for(int i = 0; i < fields.length; i++){
                    Cell cell = row.getCell(i); //业务类型
                    if(cell != null){
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                            setItemCsv(csv, fields[i], cell.getStringCellValue());
                        }
                        else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if(DateUtil.isCellDateFormatted(cell))
                            {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                setItemCsv(csv, fields[i], sdf.format(cell.getDateCellValue()));
                            }
                            else
                            {
                                String fieldValue = String.valueOf(cell.getNumericCellValue());
                                if(fieldValue.contains("E")){
                                    DecimalFormat df = new DecimalFormat("0");
                                    setItemCsv(csv, fields[i], df.format(cell.getNumericCellValue()));
                                }
                                else if(fieldValue.endsWith(".0"))
                                {
                                    setItemCsv(csv, fields[i], fieldValue.substring(0, fieldValue.length() - 2));
                                }
                                else
                                {
                                    setItemCsv(csv, fields[i], fieldValue);
                                }
                            }
                        }
                    }
                }
                items.add(csv);
            }

        }
        doHandle(items, 0);  //没有头
    }
    private void doHandle( List<ItemCsv> items, int startAt)
    {
        List<String> errors = new ArrayList<String>();
        List<PreTradeDto> trades = new ArrayList<PreTradeDto>();
        //第一行为头提示，忽略掉index从1开始
        for(int index = startAt; index < items.size(); index++){
            handle(items.get(index), index, trades, errors);
        }

        if(trades.size() > 0 || errors.size() > 0){
            //生成批次
            doPack(trades, errors);
        }
    }
    //转换结果集PreTradeDto，保存到数据库
    private void doPack(List<PreTradeDto> trades, List<String> errors)
    {
        PreTradeLotDto lot = new PreTradeLotDto();
        lot.setCrdt(new Date());
        lot.setSourceId(TradeSourceConstants.CSV_SOURCE_ID);
        lot.setValidCount(Long.parseLong(String.valueOf(trades.size())));
        lot.setErrCount(Long.parseLong(String.valueOf(errors.size())));
        lot.setTotalCount(Long.parseLong(String.valueOf(trades.size() + errors.size())));
        lot.setStatus(Long.parseLong(String.valueOf("0"))); //未处理

        for(PreTradeDto trade : trades){
            trade.setSourceId(preTradeCompanyService.getPreTradeSourceId(trade.getTradeType()));
            lot.getPreTrades().add(trade);
        }
        //TODO:待处理  导入Excl文件名
        String fileName = "";
        lot.setLotDsc("导入文件名");

        if(errors.size() > 0) {
            lot.setLotDsc(lot.getLotDsc() + ":");
            for(String error : errors){
                lot.setLotDsc(lot.getLotDsc() + "\n" + error);
            }
        }
        //TODO:待处理 sendLog(lot.getLotDsc()); 日志服务暂不处理 EsbAuditLog
        //调用前置订单服务保存数据 message.getBody().add("preTradeLot", lot);
        preTradeLotService.insert(lot);
    }
    private void handle(ItemCsv item, int index, List<PreTradeDto> trades, List<String> errors)  {
        if(check(item, index+1, errors)){
            EnsurePreTrade(item, trades);
        }
    }
    //数据检查校验
    private boolean check(ItemCsv item, int index, List<String> errors)  {
        boolean result = true;
        //外部订单号不能为空，不能超过50个字符，不能含有汉字
        if(item.getOpsTradeId() == null || "".equals(item.getOpsTradeId())){
            errors.add(String.format("行{%d}:外部订单号不能为空!", index));
            result = false;
        }
        else if(item.getOpsTradeId().length() > 50){
            errors.add(String.format("行%d:外部订单号不能超过50个字符!", index));
            result = false;
        }
        else if(checkHz(item.getOpsTradeId())) {
            errors.add(String.format("行%d:外部订单号不能有汉字!", index));
            result = false;
        }
        //订单类型不能为空，并且在可导入列表中
        if(item.getTradeType() == null || "".equals(item.getTradeType())){
            errors.add(String.format("行%d:订单类型不能为空!", index));
            result = false;
        }
        else if(preTradeCompanyService.getPreTradeSourceId(item.getTradeType()) == -1)
        {
            logger.info("wodedingyi错误:"+item.getTradeType());
            errors.add(String.format("行%d:订单类型(%s)不存在!", index, item.getTradeType()));
            result = false;
        }else if(preTradeCompanyService.getPreTradeSourceId(item.getTradeType()) == 17){
            //校验银行编码	授权号	身份证	卡号	卡有效期	期数 不为空
            if(StringUtils.isBlank(item.getBankCode()) || StringUtils.isBlank(item.getAuthCode()) || StringUtils.isBlank(item.getIdCardNumber())
                    || StringUtils.isBlank(item.getCreditCardNumber()) || StringUtils.isBlank(item.getCreditCardExpire())
                    || StringUtils.isBlank(item.getCreditCardCycles())){
                errors.add(String.format("行%d:订单类型(%s)银行编码,授权号,身份证,卡号,卡有效期,期数不能为空!", index, item.getTradeType()));
                result = false;

            }else {
                //校验身份证、行用卡期数、信用卡有效期格式是否正确
                if(item.getIdCardNumber().length() > 50){
                    errors.add(String.format("行%d:身份证长度大于50位了!", index));
                    result = false;
                }
                Integer cycles = Integer.parseInt(item.getCreditCardCycles());
                if(cycles.intValue() < 0){
                    errors.add(String.format("行%d:信用卡期数不是有效数字!", index));
                    result = false;
                }
                if(!isValidCreditExpire(item.getCreditCardExpire())){
                    errors.add(String.format("行%d:信用卡有效期日期格式不正确（yyyy-MM)!", index));
                    result = false;
                }
            }
        }

        //收货人姓名不能为空
        else if(item.getReceiverName() == null || "".equals(item.getReceiverName())){
            errors.add(String.format("行%d:收货人(%s)为空!", index, item.getReceiverName()));
            result = false;
        }

        //联系电话1、2不能同时为空
        if((item.getTelephone1() == null || "".equals(item.getTelephone1()))
                && (item.getTelephone2() == null || "".equals(item.getTelephone2()))){
            errors.add(String.format("行%d:联系电话1和联系电话2不能都为空!", index));
            result = false;
        }

        //省、市、区、详细地址不能为空
        if(item.getProvince() == null || "".equals(item.getProvince())){
            errors.add(String.format("行%d:省份不能为空!", index));
            result = false;
        }

        if(item.getCity() == null || "".equals(item.getCity())){
            errors.add(String.format("行%d:城市不能为空!", index));
            result = false;
        }

        if(item.getCounty() == null || "".equals(item.getCounty())){
            errors.add(String.format("行%d:区县不能为空!", index));
            result = false;
        }

        if(item.getAddress() == null || "".equals(item.getAddress())){
            errors.add(String.format("行%d:地址不能为空!", index));
            result = false;
        }

        /*
        //去掉支付方式、销售方式的必填项控制
        //支付方式不能为空
        if(item.getPayType() == null || "".equals(item.getPayType())){
            errors.add(String.format("行%d:支付方式不能为空!", index));
            result = false;
        }
        //销售方式不能为空
        if(item.getMailType() == null || "".equals(item.getMailType())){
            errors.add(String.format("行%d:销售方式不能为空!", index));
            result = false;
        }
        */

        //发票类型不能为空  0-不需要，1-需要，2-特殊需求
        if(item.getInvoiceComment() == null || "".equals(item.getInvoiceComment())){
            errors.add(String.format("行%d:发票类型不能为空!", index));
            result = false;
        }
        if(item.getInvoiceTitle() == null || "".equals(item.getInvoiceTitle())){
            item.setInvoiceTitle("个人");
        }

        //产品编码不能为空，且必须为数字
        if(item.getSkuId() == null || "".equals(item.getSkuId())){
            errors.add(String.format("行%d:产品编码不能为空!", index));
            result = false;
        }
        else
        {
            try
            {
                Long.parseLong(item.getSkuId());
            }
            catch (NumberFormatException ex){
                errors.add(String.format("行%d:产品编码必须是数字!", index));
                result = false;
            }
        }
        //产品名称不能为空
        /*
        if(item.getSkuName() == null || "".equals(item.getSkuName())){
            errors.add(String.format("行%d:商品规格不能为空!", index));
            result = false;
        }
        */
        //产品价格不能为空，且必须是数字
        if(item.getPrice() == null || "".equals(item.getPrice())){
            errors.add(String.format("行%d:产品单价不能为空!", index));
            result = false;
        }
        else
        {
            try
            {
                Double.parseDouble(item.getPrice());
            }
            catch (NumberFormatException ex){
                errors.add(String.format("行%d:产品单价必须是数字!", index));
                result = false;
            }
        }
        //产品数量不能为空，且必须是数字
        if(item.getQty() == null || "".equals(item.getQty())){
            errors.add(String.format("行%d:订购数量不能为空!", index));
            result = false;
        }
        else
        {
            try
            {
                Double.parseDouble(item.getQty());
            }
            catch (NumberFormatException ex){
                errors.add(String.format("行%d:订购数量必须是数字!", index));
                result = false;
            }
        }
        //产品总额不嫩为空，且必须是数字
        if(item.getAmount() == null || "".equals(item.getAmount())){
            errors.add(String.format("行%d:产品总价不能为空!", index));
            result = false;
        }
        else
        {
            try
            {
                Double.parseDouble(item.getAmount());
            }
            catch (NumberFormatException ex){
                errors.add(String.format("行%d:产品总价必须是数字!", index));
                result = false;
            }
        }

        //佣金率默认为0
        if(item.getCommissionRate() == null || "".equals(item.getCommissionRate())){
            item.setCommissionRate("0");
        }

        //订单生成日期不能为空，且有效
        if(item.getCreatedOn() == null || "".equals(item.getCreatedOn())){
            errors.add(String.format("行%d:订单生成日期不能为空!", index));
            result = false;
        }
        else
        {
            Date date = parseDate(item.getCreatedOn());
            if(date == null){
                errors.add(String.format("行%d:订单生成日非日期格式!", index));
                result = false;
            }
        }

        //检查运费是否存在，存在的话必须是数值型
        if(item.getShippingFee() == null || "".equals(item.getShippingFee())){
            item.setShippingFee("0");
        }
        else
        {
            try
            {
                Double.parseDouble(item.getShippingFee());
            }
            catch (NumberFormatException ex){
                errors.add(String.format("行%d:运费必须是数字!", index));
                result = false;
            }
        }

        return result;
    }
    //验证信用卡日期有效性
    private boolean isValidCreditExpire(String expire){
        try
        {
            expireDateFormat.parse(expire);
        } catch (ParseException exp)
        {
            return false;
        }

        if(expireDatePattern.matcher(expire).matches())
        {
            return true;
        }

        return false;
    }
    //日期转换
    private static Date parseDate(String dateStr) {
        String patterns = "yyyy-MM-dd;yyyy-MM-dd HH:mm:ss;yyyy-M-d;yyyy-M-d hh:mm;yyyy/MM/dd;dd/MM/yyyy;yyyy/M/d;yyyy/M/d hh:mm";
        dateStr = dateStr.replaceAll("\\s\\s", " "); //两个空格替换为一个空格
        Date date = null;
        ParseException err = null;
        String[] arr = patterns.split(";");
        for(int i=0;i<arr.length;i++){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(arr[i]);
                date = sdf.parse(dateStr);
                if(arr[i].length() == dateStr.length()){
                    break;
                } else {
                    continue;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        return date;
    }
    private static boolean checkHz(String str){
        char[] chars=str.toCharArray();
        boolean isGB2312=false;
        for(int i=0;i<chars.length;i++){
            byte[] bytes=(""+chars[i]).getBytes();
            if(bytes.length==2){
                int[] ints=new int[2];
                ints[0]=bytes[0]& 0xff;
                ints[1]=bytes[1]& 0xff;
                if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){
                    isGB2312=true;
                    break;
                }
            }
        }
        return isGB2312;
    }
    //匹配opsTradeId
    private PreTradeDto GetTrade(String opsTradeId,  List<PreTradeDto> trades){
        for(PreTradeDto trade : trades) {
            if(opsTradeId.equals(trade.getOpsTradeId())){
                return trade;
            }
        }
        return null;
    }
    //添加明细
    private void EnsurePreTrade(ItemCsv item, List<PreTradeDto> trades)  {
        PreTradeDto trade = GetTrade(item.getOpsTradeId(), trades);
        if(trade == null){
            trade = new PreTradeDto();
            trade.setMailType(item.getMailType());
            trade.setImpUser(item.getCreatedBy());
            trade.setImpDate(new Date());

            trade.setTradeId(item.getOpsTradeId());
            trade.setOpsTradeId(item.getOpsTradeId());
            trade.setTradeFrom(item.getTradeFrom());
            trade.setTradeType(item.getTradeType());
            trade.setTmsCode(item.getTmsCode());
            trade.setCrdt(new Date());
            trade.setOutCrdt(parseDate(item.getCreatedOn()));
            trade.setInvoiceComment(item.getInvoiceComment());
            trade.setInvoiceTitle(item.getInvoiceTitle());
            trade.setBuyerId(item.getBuyerId());
            trade.setBuyerNick(item.getBuyerNick());
            trade.setReceiverName(item.getReceiverName());
            trade.setReceiverProvince(item.getProvince());
            trade.setReceiverCity(item.getCity());
            trade.setReceiverArea(item.getArea());
            trade.setReceiverCounty(item.getCounty());
            trade.setReceiverAddress(item.getAddress());

            String telephone1 = item.getTelephone1();
            if(item.getLongDistanceCode1() != null &&
                    !"".equals(item.getLongDistanceCode1())){
                telephone1 = item.getLongDistanceCode1() +"-" +telephone1;
            }
            if(item.getExtension1() != null &&
                    !"".equals(item.getExtension1())){
                telephone1 = telephone1 +"-"+item.getExtension1();
            }
            trade.setReceiverPhone(telephone1);

            trade.setReceiverMobile(item.getTelephone2());
            trade.setBuyerMessage(item.getBuyerMessage());
            trade.setSellerMessage(item.getSellerMessage());
            trade.setPaytype(item.getPayType());
            trade.setShippingFee(Double.parseDouble(item.getShippingFee())) ;
            try
            {
                if(item.getCommissionRate() != null){
                    trade.setComisssionFee(Double.parseDouble(item.getCommissionRate()));
                }
            }
            catch (NumberFormatException ex) { /* do nothing */ }
            try
            {
                trade.setPayment(0.0);
            }
            catch (NumberFormatException ex) { /* do nothing */ }
            //添加PRE_TRADE_CARD
            if(preTradeCompanyService.getPreTradeSourceId(item.getTradeType()) == 17){
                List<PreTradeCard> cards = trade.getPreTradeCards();
                PreTradeCard preTradeCard = new PreTradeCard();
                preTradeCard.setTradeId(item.getOpsTradeId());
                //preTradeCard.setPreTrade(trade);
                preTradeCard.setBankCode(item.getBankCode());
                preTradeCard.setAuthCode(item.getAuthCode());
                preTradeCard.setIdCardNumber(item.getIdCardNumber());
                preTradeCard.setCreditCardNumber(item.getCreditCardNumber());
                preTradeCard.setCreditCardExpire(item.getCreditCardExpire());
                preTradeCard.setCreditCardCycles(Long.parseLong(item.getCreditCardCycles()));
                preTradeCard.setCreateUser(item.getCreatedBy());
                preTradeCard.setCreateDate(new Date());
                preTradeCard.setUpdateUser(item.getCreatedBy());
                preTradeCard.setUpdateDate(new Date());
                cards.add(preTradeCard);
            }

            trades.add(trade);
        }

        //添加明细
        List<PreTradeDetail> details = trade.getPreTradeDetails();
        PreTradeDetail detail = new PreTradeDetail();
        //detail.setPreTrade(trade);
        detail.setTradeId(trade.getTradeId());
        try
        {
            if(item.getSkuId() != null){
                detail.setSkuId(Long.parseLong(item.getSkuId()));
                detail.setOutSkuId(item.getSkuId());
            }
        }
        catch (NumberFormatException ex) { /* do nothing */ }
        try
        {
            if(item.getQty() != null){
                detail.setQty(Integer.parseInt(item.getQty()));
            }
        }
        catch (NumberFormatException ex) { /* do nothing */ }
        detail.setSkuName(item.getSkuName());
        try
        {
            if(item.getPrice() != null){
                detail.setPrice(Double.parseDouble(item.getPrice()));
            }
        }
        catch (NumberFormatException ex) { /* do nothing */ }
        details.add(detail);
        //重新计算订单总金额
        try
        {
            if(item.getAmount() != null){
                trade.setPayment(trade.getPayment() + Double.parseDouble(item.getAmount()));
            }
        }
        catch (NumberFormatException ex) { /* do nothing */ }
    }

    //解析Excel数据生成临时对象 ItemCsv
    private void setItemCsv(ItemCsv csv, String fieldName, String fieldValue){

        if(fieldName.equals("ops_trade_id")){
            csv.setOpsTradeId(fieldValue);
        }
        else if(fieldName.equals("trade_type")){
            csv.setTradeType(fieldValue);
        }
        else if(fieldName.equals("trade_from")){
            csv.setTradeFrom(fieldValue);
        }
        else if(fieldName.equals("bank_code")){
            csv.setBankCode(fieldValue);
        }
        else if(fieldName.equals("auth_code")){
            csv.setAuthCode(fieldValue);
        }
        else if(fieldName.equals("buyer_nick")){
            csv.setBuyerNick(fieldValue);
        }
        else if(fieldName.equals("buyer_id")){
            csv.setBuyerId(fieldValue);
        }
        else if(fieldName.equals("receiver_name")){
            csv.setReceiverName(fieldValue);
        }
        else if(fieldName.equals("id_card_number")){
            csv.setIdCardNumber(fieldValue);
        }
        else if(fieldName.equals("credit_card_number")){
            csv.setCreditCardNumber(fieldValue);
        }
        else if(fieldName.equals("credit_card_expire")){
            csv.setCreditCardExpire(fieldValue);
        }
        else if(fieldName.equals("credit_card_cycles")){
            csv.setCreditCardCycles(fieldValue);
        }
        else if(fieldName.equals("long_distance_code1")){
            csv.setLongDistanceCode1(fieldValue);
        }
        else if(fieldName.equals("telephone1")){
            csv.setTelephone1(fieldValue);
        }
        else if(fieldName.equals("extension1")){
            csv.setExtension1(fieldValue);
        }
        else if(fieldName.equals("long_distance_code2")){
            csv.setLongDistanceCode2(fieldValue);
        }
        else if(fieldName.equals("telephone2")){
            csv.setTelephone2(fieldValue);
        }
        else if(fieldName.equals("extension2")){
            csv.setExtension2(fieldValue);
        }
        else if(fieldName.equals("province")){
            csv.setProvince(fieldValue);
        }
        else if(fieldName.equals("city")){
            csv.setCity(fieldValue);
        }
        else if(fieldName.equals("county")){
            csv.setCounty(fieldValue);
        }
        else if(fieldName.equals("area")){
            csv.setArea(fieldValue);
        }
        else if(fieldName.equals("address")){
            csv.setAddress(fieldValue);
        }
        else if(fieldName.equals("pay_type")){
            csv.setPayType(fieldValue);
        }
        else if(fieldName.equals("mail_type")){
            csv.setMailType(fieldValue);
        }
        else if(fieldName.equals("tms_code")){
            csv.setTmsCode(fieldValue);
        }
        else if(fieldName.equals("invoice_comment")){
            csv.setInvoiceComment(fieldValue);
        }
        else if(fieldName.equals("invoice_title")){
            csv.setInvoiceTitle(fieldValue);
        }
        else if(fieldName.equals("sku_id")){
            csv.setSkuId(fieldValue);
        }
        else if(fieldName.equals("sku_name")){
            csv.setSkuName(fieldValue);
        }
        else if(fieldName.equals("price")){
            csv.setPrice(fieldValue);
        }
        else if(fieldName.equals("qty")){
            csv.setQty(fieldValue);
        }
        else if(fieldName.equals("amount")){
            csv.setAmount(fieldValue);
        }
        else if(fieldName.equals("buyer_message")){
            csv.setBuyerMessage(fieldValue);
        }
        else if(fieldName.equals("seller_message")){
            csv.setSellerMessage(fieldValue);
        }
        else if(fieldName.equals("commission_rate")){
            csv.setCommissionRate(fieldValue);
        }
        else if(fieldName.equals("created_on")){
            csv.setCreatedOn(fieldValue);
        }
        else if(fieldName.equals("created_by")){
            csv.setCreatedBy(fieldValue);
        }
        else if(fieldName.equals("shipping_fee")){
            csv.setShippingFee(fieldValue);
        }
    }


    @Override
    public void doProcessCSV() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
