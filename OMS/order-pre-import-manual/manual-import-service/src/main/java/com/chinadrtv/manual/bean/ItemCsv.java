package com.chinadrtv.manual.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘宽
 * Date: 13-12-5
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class ItemCsv implements Serializable {

    private String ops_trade_id;  //外部订单号（银行或CMS）
    private String trade_type;    //订单类型
    private String trade_from;    //订单来源
    private String bank_code;     //银行编码
    private String auth_code;     //授权号
    private String buyer_nick;    //订购人姓名
    private String buyer_id;     //订购人别名
    private String receiver_name;   //收货人姓名
    private String id_card_number;  //身份证
    private String credit_card_number ;     //卡号
    private String credit_card_expire;     //卡有效期
    private String credit_card_cycles;     //期数
    private String long_distance_code1;     //区号1
    private String telephone1;     //联系电话1
    private String extension1;     //分机1
    private String long_distance_code2;     //区号2
    private String telephone2;     //联系电话2
    private String extension2;     //分机2
    private String province;     //省份
    private String city;     //城市
    private String area;     //区县 county
    private String county;     //乡镇 town
    private String address;     //详细地址
    private String pay_type;     //支付方式
    private String mail_type;     //销售方式
    private String tms_code;     //指定送货公司
    private String invoice_comment;   //发票类型
    private String invoice_title;     //发票抬头
    private String sku_id;     //产品编码
    private String sku_name;     //商品规格(商品名称)
    private String price;     //产品单价
    private String qty;     //数量
    private String amount;     //产品总价
    private String buyer_message;     //买家备注
    private String seller_message;     //卖家备注
    private String commission_rate;     //佣金率
    private String created_on;     //订单生成日
    private String created_by;     //制表人员
    private String shippingFee;    //运费

    public String getOpsTradeId(){
         return this.ops_trade_id;
    }

    public void setOpsTradeId(String value){
        this.ops_trade_id = value;
    }

    public String getTradeType(){
        return this.trade_type;
    }

    public void setTradeType(String value){
        this.trade_type = value;
    }

    public String getTradeFrom(){
        return this.trade_from;
    }

    public void setTradeFrom(String value){
        this.trade_from = value;
    }

    public String getBankCode(){
        return this.bank_code;
    }

    public void setBankCode(String value){
        this.bank_code = value;
    }

    public String getAuthCode(){
        return this.auth_code;
    }

    public void setAuthCode(String value){
        this.auth_code = value;
    }

    public String getBuyerNick(){
        return this.buyer_nick;
    }

    public void setBuyerNick(String value){
        this.buyer_nick = value;
    }

    public String getBuyerId(){
        return this.buyer_id;
    }

    public void setBuyerId(String value){
        this.buyer_id = value;
    }

    public String getReceiverName(){
        return this.receiver_name;
    }

    public void setReceiverName(String value){
        this.receiver_name = value;
    }

    public String getIdCardNumber(){
        return this.id_card_number;
    }

    public void setIdCardNumber(String value){
        this.id_card_number = value;
    }

    public String getCreditCardNumber(){
        return this.credit_card_number;
    }

    public void setCreditCardNumber(String value){
        this.credit_card_number = value;
    }

    public String getCreditCardExpire(){
        return this.credit_card_expire;
    }

    public void setCreditCardExpire(String value){
        this.credit_card_expire = value;
    }

    public String getCreditCardCycles(){
        return this.credit_card_cycles;
    }

    public void setCreditCardCycles(String value){
        this.credit_card_cycles = value;
    }

    public String getLongDistanceCode1(){
        return this.long_distance_code1;
    }

    public void setLongDistanceCode1(String value){
        this.long_distance_code1 = value;
    }

    public String getTelephone1(){
        return this.telephone1;
    }

    public void setTelephone1(String value){
        this.telephone1 = value;
    }

    public String getExtension1(){
        return this.extension1;
    }

    public void setExtension1(String value){
        this.extension1 = value;
    }

    public String getLongDistanceCode2(){
        return this.long_distance_code2;
    }

    public void setLongDistanceCode2(String value){
        this.long_distance_code2 = value;
    }

    public String getTelephone2(){
        return this.telephone2;
    }

    public void setTelephone2(String value){
        this.telephone2 = value;
    }

    public String getExtension2(){
        return this.extension2;
    }

    public void setExtension2(String value){
        this.extension2 = value;
    }

    public String getProvince(){
        return this.province;
    }

    public void setProvince(String value){
        this.province = value;
    }

    public String getCity(){
        return this.city;
    }

    public void setCity(String value){
        this.city = value;
    }

    public String getCounty(){
        return this.county;
    }

    public void setCounty(String value){
        this.county = value;
    }

    public String getArea(){
        return this.area;
    }

    public void setArea(String value){
        this.area = value;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String value){
        this.address = value;
    }

    public String getPayType(){
        return this.pay_type;
    }

    public void setPayType(String value){
        this.pay_type = value;
    }

    public String getMailType(){
        return this.mail_type;
    }

    public void setMailType(String value){
        this.mail_type = value;
    }

    public String getTmsCode(){
        return this.tms_code;
    }

    public void setTmsCode(String value){
        this.tms_code = value;
    }

    public String getInvoiceComment(){
        return this.invoice_comment;
    }

    public void setInvoiceComment(String value){
        this.invoice_comment = value;
    }

    public String getInvoiceTitle(){
        return this.invoice_title;
    }

    public void setInvoiceTitle(String value){
        this.invoice_title = value;
    }

    public String getSkuId(){
        return this.sku_id;
    }

    public void setSkuId(String value){
        this.sku_id = value;
    }

    public String getSkuName(){
        return this.sku_name;
    }

    public void setSkuName(String value){
        this.sku_name = value;
    }

    public String getPrice(){
        return this.price;
    }

    public void setPrice(String value){
        this.price = value;
    }

    public String getQty(){
        return this.qty;
    }

    public void setQty(String value){
        this.qty = value;
    }

    public String getAmount(){
        return this.amount;
    }

    public void setAmount(String value){
        this.amount = value;
    }

    public String getBuyerMessage(){
        return this.buyer_message;
    }

    public void setBuyerMessage(String value){
        this.buyer_message = value;
    }

    public String getSellerMessage(){
        return this.seller_message;
    }

    public void setSellerMessage(String value){
        this.seller_message = value;
    }

    public String getCommissionRate(){
        return this.commission_rate;
    }

    public void setCommissionRate(String value){
        this.commission_rate = value;
    }

    public String getCreatedOn(){
        return this.created_on;
    }

    public void setCreatedOn(String value){
        this.created_on = value;
    }

    public String getCreatedBy(){
        return this.created_by;
    }

    public void setCreatedBy(String value){
        this.created_by = value;
    }


    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }
}
