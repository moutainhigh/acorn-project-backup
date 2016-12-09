package com.chinadrtv.erp.tc.core.constant;

/**
 * 代码常量
 * Date: 13-1-28
 */
public final class OrderCode {
    public final static String SUCC="000";
    public final static String FIELD_INVALID="001";
    public final static String NOT_ORDERDET="002";
    public final static String ORDER_NOT_FOUND="003";
    public final static String COMPANY_NOT_FOUND="004";
    public final static String ADDRESS_NOT_MATCH="005";
    public final static String ORDER_PRICE_ZERO="006";
    public final static String ORDER_HAVE_MODIFY="007";
    public final static String ORDER_SHIPMENT_STATUS_INVALID="008";
    public final static String ORDER_STATUS_NOT_FOR_TRANS="009";
    public final static String PRETRADE_PHONE_NOT_FOUND="010";
    public final static String PRETRADE_CONTACT_NAME_INVALID="011";
    public final static String PRETRADE_ADDRESS_DSC_INVALID="012";
    public final static String PRETRADE_HAVE_CREATED="013";
    public final static String PRETRADE_ADDRESS_ERROR="014";
    public final static String PRETRADE_PROD_INVALID="015";

    public final static String PRETRADE_IDCARD_INVALID="016";
    public final static String PRETRADE_CREDITCARD_INVALID="017";
    public final static String PRETRADE_CREDITCARDTYPE_INVALID="018";
    public final static String PRETRADE_CREDITCARDEXPIRE_INVALID="019";
    public final static String PRETRADE_CREDITCARD_IMPORT_INVALID="020";
    public final static String PRETRADE_CREDITCARDCYCLES_INVALID="021";

    public final static String SYSTEM_ERROR="100";
    public final static String INPUT_PARM_ERROR="101";



    /** 操作状态码 */
    public static final String OPERATE_CODE = "code";
    /** 操作描述  */
    public static final String OPERATE_DESC = "desc";

    /** 订单支持类型，信用卡支付  */
    public static final String PAY_TYPE_CREDITCARD = "2";

    /** 订单出库状态  -> 正常 */
    public static final String CUSTOMIZE_STATUS_NORMAL = "0";
}
