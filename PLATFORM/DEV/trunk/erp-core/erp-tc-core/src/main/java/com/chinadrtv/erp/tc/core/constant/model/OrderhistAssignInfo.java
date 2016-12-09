package com.chinadrtv.erp.tc.core.constant.model;


import com.chinadrtv.erp.model.agent.Order;

/**
 * 订单地址匹配结果数据
 * User: 徐志凯
 * Date: 12-12-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderhistAssignInfo {
    /**
     * 地址匹配是否成功
     */
    private boolean isSucc;
    /**
     * 匹配错误代码
     */
    private String errorId;
    /**
     * 匹配错误信息
     */
    private String errorMsg;
    /**
     * 匹配的仓库Id
     */
    private Long warehouseId;
    /**
     *  匹配的仓库名称
     */
    private String warehouseName;
    /**
     * 匹配的送货公司
     */
    private String entityId;
    /**
     * 匹配送货公司订购方式
     */
    private String mailType;
    /**
     * 匹配规则Id
     */
    private String ruleId;
    /**
     * 匹配规则名称
     */
    private String ruleName;

    private Order order;

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean succ) {
        isSucc = succ;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
