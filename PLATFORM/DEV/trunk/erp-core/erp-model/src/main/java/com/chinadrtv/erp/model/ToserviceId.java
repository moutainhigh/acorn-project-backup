package com.chinadrtv.erp.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: ToserviceId
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Embeddable
public class ToserviceId implements Serializable {
    private String contactId;
    private Date crtm;

    public ToserviceId() {
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Date getCrtm() {
        return crtm;
    }

    public void setCrtm(Date crtm) {
        this.crtm = crtm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToserviceId that = (ToserviceId) o;

        if (!contactId.equals(that.contactId)) return false;
        if (!crtm.equals(that.crtm)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contactId.hashCode();
        result = 31 * result + crtm.hashCode();
        return result;
    }
}
