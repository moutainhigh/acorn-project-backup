package com.chinadrtv.logistics.model;

import java.io.Serializable;

/**
 * Created by Net on 14-12-24.
 */
public class ContactInfo implements Serializable {
    private String contactid;

    public String getContactId()
    {
        return this.contactid;
    }

    public void setContactId(String contactid) {
        this.contactid = contactid;
    }
}
