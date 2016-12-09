package com.chinadrtv.erp.sales.core.model;

import com.chinadrtv.erp.marketing.core.common.AuditTaskType;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderCreateResult {
    public OrderCreateResult()
    {
        isAudit=false;
    }
    /**
     * 是否需要审批
     */
    private boolean isAudit;
    /**
     * 审批类型
     */
    private AuditTaskType auditTaskType;

    public boolean isAudit() {
        return isAudit;
    }

    public void setAudit(boolean audit) {
        isAudit = audit;
    }

    public AuditTaskType getAuditTaskType() {
        return auditTaskType;
    }

    public void setAuditTaskType(AuditTaskType auditTaskType) {
        this.auditTaskType = auditTaskType;
    }
}
