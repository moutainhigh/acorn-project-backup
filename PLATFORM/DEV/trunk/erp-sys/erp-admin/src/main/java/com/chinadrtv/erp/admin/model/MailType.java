package com.chinadrtv.erp.admin.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import com.chinadrtv.erp.model.BaseType;
/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@DiscriminatorValue("BUYTYPE")
@Where(clause = "TID='BUYTYPE'")
public class MailType extends BaseType implements java.io.Serializable {
    public MailType() {
        super();
        //setTid("BUYTYPE");
        //setTdsc("订购类型");
    }
}
