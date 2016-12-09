package com.chinadrtv.erp.admin.model;

import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
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
@DiscriminatorValue("ORDERTYPE")
@Where(clause = "TID='ORDERTYPE'")
//订单类型--来源Names
public class OrderType extends BaseType implements java.io.Serializable {

}
