package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 订单修改请求 (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "MODIFY_ORDERHIST_EXT", schema = "IAGENT")
@Entity
public class ModifyOrderhistExt implements Serializable{

    @Embeddable
    public static class ModifyOderhistExtKey implements Serializable
    {
        private String modorderid;

        private String orderid;

        public ModifyOderhistExtKey()
        {}

        public ModifyOderhistExtKey(String modorderid,String orderid)
        {
            this.modorderid=modorderid;
            this.orderid=orderid;
        }

        @Column(name = "MODORDERID", length = 20)
        public String getModorderid() {
            return modorderid;
        }

        public void setModorderid(String modorderid) {
            this.modorderid = modorderid;
        }

        @Column(name = "ORDERID", length = 20)
        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }

    public ModifyOrderhistExt()
    {}

    private ModifyOderhistExtKey modifyOderhistExtKey;

    private String contactid;

    @EmbeddedId
    public ModifyOderhistExtKey getModifyOderhistExtKey() {
        return modifyOderhistExtKey;
    }

    public void setModifyOderhistExtKey(ModifyOderhistExtKey modifyOderhistExtKey) {
        this.modifyOderhistExtKey = modifyOderhistExtKey;
    }

    @Column(name = "CONTACTID", length = 20)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifyOrderhistExt that = (ModifyOrderhistExt) o;

        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if(modifyOderhistExtKey!=null? !modifyOderhistExtKey.equals(that.modifyOderhistExtKey) : that.modifyOderhistExtKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modifyOderhistExtKey != null ? modifyOderhistExtKey.hashCode() : 0;
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        return result;
    }
}
