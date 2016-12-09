package com.chinadrtv.erp.model;

import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-9-6
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 * 行用卡索取导入复合主键
 */
@Embeddable
public class CardAuthorizationId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String orderid;
    private String cardrightnum;

    public CardAuthorizationId(){
        super();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCardrightnum() {
        return cardrightnum;
    }

    public void setCardrightnum(String cardrightnum) {
        this.cardrightnum = cardrightnum;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof CardAuthorizationId))
            return false;

        final CardAuthorizationId other = (CardAuthorizationId) object;

        return  orderid!=null&&
                other.getOrderid()!=null&&
                orderid==other.getOrderid()
                &&
                cardrightnum!=null&&
                other.getCardrightnum()!=null&&
                cardrightnum==other.getCardrightnum();

    }

    @Override
    public int hashCode() {
        return super.hashCode()+
                (orderid!=null?orderid.hashCode():0)+
                (cardrightnum!=null?cardrightnum.hashCode():0);
    }
}
