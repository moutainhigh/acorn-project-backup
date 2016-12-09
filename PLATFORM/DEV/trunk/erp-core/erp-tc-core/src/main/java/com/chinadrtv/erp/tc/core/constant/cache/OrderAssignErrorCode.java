package com.chinadrtv.erp.tc.core.constant.cache;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
public class OrderAssignErrorCode {
    public final static String ORDER_TYPE_NOT_FOUND="02";
    public final static String ORDER_TYPE_NOT_FOUND_DSC="订单类型未找到";
    public final static String ORDER_PAY_NOT_FOUND="03";
    public final static String ORDER_PAY_NOT_FOUND_DSC="付款方式未找到";
    public final static String ADDRESS_NOT_FOUND="04";
    public final static String ADDRESS_NOT_FOUND_DSC="未找到匹配地址";
    public final static String RULE_NOT_FOUND="05";
    public final static String RULE_NOT_FOUND_DSC="未找到匹配规则";
    public final static String CHANNEL_NOT_FOUND="06";
    public final static String CHANNEL_NOT_FOUND_DSC="没有配置对应渠道";
    public final static String RULE_CONFIG_ERROR="07";
    public final static String RULE_CONFIG_ERROR_DSC="规则配置错误，有重复";
    public final static String COMPANY_NOT_FOUND="08";
    public final static String COMPANY_NOT_FOUND_DSC="没有找到匹配规则中的送货公司信息";
    public final static String RULE_COMPANY_CONFIG_ERROR="09";
    public final static String RULE_COMPANY_CONFIG_ERROR_DSC="匹配规则中的送货公司或者仓库未定义";
    public final static String STOCK_CHECK_NOT_SUPPORT="10";
    public final static String STOCK_CHECK_NOT_SUPPORT_DSC="目前版本不支持库存检查";
    public final static String ADDRESS_NOT_VALID="11";
    public final static String ADDRESS_NOT_VALID_DSC="订单地址信息不完整";
    public final static String MATCH_SPEC_CONDITION="12";
    public final static String MATCH_SPEC_CONDITION_DSC="满足金额或商品规则条件";
    public final static String OREDR_CHOOSE_SET_ERROR="13";
    public final static String OREDR_CHOOSE_SET_ERROR_DSC="设置订单为分拣时发生错误";

    //复杂匹配错误
    public final static String COMPANYCONTRACT_NOT_FOUND="14";
    public final static String COMPANYCONTRACT_NOT_FOUND_DSC="没有承运商配额信息";
    public final static String COMPANYCONTRACT_EXCEED_CARRYCAPACITY="15";
    public final static String COMPANYCONTRACT_EXCEED_CARRYCAPACITY_DSC="承运商超量";

    public final static String SYSTEM_ERROR="100";
}
