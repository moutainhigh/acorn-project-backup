package com.chinadrtv.erp.tc.core.model;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class InventoryAssignInfo implements Serializable {
    public String getScmId() {
        return scmId;
    }

    public void setScmId(String scmId) {
        this.scmId = scmId;
    }

    public boolean isIcControl() {
        return isIcControl;
    }

    public void setIcControl(boolean icControl) {
        isIcControl = icControl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private String scmId;
    private boolean isIcControl;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryAssignInfo that = (InventoryAssignInfo) o;

        if (isIcControl != that.isIcControl) return false;
        if (scmId != null ? !scmId.equals(that.scmId) : that.scmId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scmId != null ? scmId.hashCode() : 0;
        result = 31 * result + (isIcControl ? 1 : 0);
        return result;
    }
}
