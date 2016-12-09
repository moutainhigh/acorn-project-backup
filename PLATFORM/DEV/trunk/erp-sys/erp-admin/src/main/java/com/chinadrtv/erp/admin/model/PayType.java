package com.chinadrtv.erp.admin.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import com.chinadrtv.erp.model.BaseType;
/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@DiscriminatorValue("PAYTYPE")
@Where(clause = "TID='PAYTYPE'")
//订单类型--来源Names
public class PayType extends BaseType implements java.io.Serializable {

}
