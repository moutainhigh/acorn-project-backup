package com.chinadrtv.erp.oms.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public final class ShipmentRefundErrorCode {
    public final static String FIELD_NOT_VALID="001";
    public final static String ORDER_NOT_FOUND="002";
    public final static String PHONE_NOT_FOUND="003";
    public final static String CONTACT_NOT_FOUND="004";
    public final static String CAN_NOT_REFUND="005";
    public final static String SHIPMENT_NOT_FOUND="006";
    public final static String HAVE_NOT_REFUND_DETAIL="007";
    public final static String REFUND_HAVE_EXIST="008";
    public final static String CORRESPONDING_SHIPMENT_DETAIL_NOT_EXIST="009";
    public final static String CHECKINPAYMENT_NOT_EXIST="010";
    public final static String REFUND_QUANTITY_MORETHAN="011";
    public final static String REFUND_PRICE_LESSTHAN="012";
    public final static String REFUND_REFUSE_ALL="013";
    public final static String REFUND_TOTAL_MORETHAN="014";
    public final static String MAIL_NOT_EQUAL_FROM_ORDER="015";
    public final static String RECONCIL_CONFIRM="016";
    
    

    public final static Map<String,String> codeMap=new HashMap<String, String>()
    {{
            put(FIELD_NOT_VALID,"输入参数不能为空或者无效");
            put(ORDER_NOT_FOUND,"无法找到订单信息，请检查订单或运单号是否正确");
            put(PHONE_NOT_FOUND,"无法找到手机信息，请确认手机信息是否正确");
            put(CONTACT_NOT_FOUND,"无法找到联系人信息，请确认联系人是否正确");
            put(CAN_NOT_REFUND,"当前订单无法进行半拒收登记，请确认订单相关状态");
            put(SHIPMENT_NOT_FOUND,"当前订单没有对应发运单");
            put(HAVE_NOT_REFUND_DETAIL,"半拒收登记没有提供相关明细信息");
            put(REFUND_HAVE_EXIST,"订单已经做了半拒收登记");
            put(CORRESPONDING_SHIPMENT_DETAIL_NOT_EXIST,"没有找到对应的运单明细行");
            put(CHECKINPAYMENT_NOT_EXIST,"没有行合计金额");
            put(REFUND_QUANTITY_MORETHAN,"半签收的数量不能大于订单数量");
            put(REFUND_PRICE_LESSTHAN,"半签收单价不能小于订单单价");
            put(REFUND_REFUSE_ALL,"不能全拒收");
            put(REFUND_TOTAL_MORETHAN,"半签收金额不能大于订单总金额");
            put(MAIL_NOT_EQUAL_FROM_ORDER,"邮件号与订单中的不符");
            put(RECONCIL_CONFIRM,"结算金额已确认，不能编辑");
        }};
}
