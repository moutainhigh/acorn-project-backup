package com.chinadrtv.erp.admin.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import com.chinadrtv.erp.model.BaseType;
/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@DiscriminatorValue("COMPANYTYPE")
@Where(clause = "TID='COMPANYTYPE'")
public class CompanyType extends BaseType implements java.io.Serializable {
    public CompanyType() {
        super();
        //setTid("COMPANYTYPE");
        //setTdsc("公司类型");
    }
}
