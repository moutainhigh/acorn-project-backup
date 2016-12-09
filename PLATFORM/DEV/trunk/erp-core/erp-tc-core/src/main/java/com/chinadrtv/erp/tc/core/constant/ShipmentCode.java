package com.chinadrtv.erp.tc.core.constant;

/**
 * 运单相关处理代码 (TC).
 * User: 徐志凯
 * Date: 13-2-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public final class ShipmentCode {
    public final static String SUCC="000";
    public final static String FIELD_INVALID="001";
    /**
     * 没有明细
     */
    public final static String NOT_DETAILS="002";
    /**
     * 未找到发运单
     */
    public final static String SHIPMENT_NOT_FOUND="003";
    /**
     * 未找到对应订单
     */
    public final static String ORDER_NOT_FOUND="004";
    /**
     * 订单已发生修改
     */
    public final static String ORDER_HAVE_MODIFY="005";
    /**
     * 订单不符合扫描出库条件
     */
    public final static String ORDER_SHOULD_NOT_SCANOUT="006";
    /**
     * 存在加订
     */
    public final static String ORDER_EXIT_ACORDER="007";
    /**
     * 订单存在修改请求
     */
    public final static String ORDER_EXIT_MODIFY="008";
    /**
     * 订单已经取消
     */
    public final static String ORDER_HAVE_CANCELED="009";
    /**
     * 订单已经出库
     */
    public final static String ORDER_IS_OUT="010";
    /**
     * 订单已经拒收
     */
    public final static String ORDER_HAVE_REJECTED="011";
    /**
     * 订单收货人或者电话没有设置
     */
    public final static String ORDER_NOT_CONTACT_INFO="012";

    /**
     * 订单商品缺少库存
     */
    public final static String ORDER_NOT_PRODUCT_NUM="013";
    /**
     * 订单商品缺少库存
     */
    public final static String ORDER_MINUS_PRODUCT_NUM="014";

    public final static String ORDER_OLD_ENTITY_NOT_MATCH="020";
    public final static String ORDER_OLD_PRICE_NOT_MATCH="021";

    public final static String SYSTEM_ERROR="100";
}
