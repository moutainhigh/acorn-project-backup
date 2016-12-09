package com.chinadrtv.erp.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@DiscriminatorValue("ORDERTYPE")
@Where(clause = "TID='ORDERTYPE'")
public class ChannelType extends BaseType implements java.io.Serializable {
    public ChannelType() {
    	super();
        //setTid("COMPANYTYPE");
        //setTdsc("公司类型");
    }
}
