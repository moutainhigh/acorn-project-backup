package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-16
 * Time: 下午5:50
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class CallHistLeadInteractionId  implements java.io.Serializable {
    @Column(name="RID")
    private String id;
    @Column(name="TABLETYPE")
    private String tableType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallHistLeadInteractionId)) return false;

        CallHistLeadInteractionId that = (CallHistLeadInteractionId) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tableType != null ? !tableType.equals(that.tableType) : that.tableType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tableType != null ? tableType.hashCode() : 0);
        return result;
    }
}
