package com.chinadrtv.erp.tc.core.model;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-3-11
 * Time: 下午12:18
 * 拆分订单明细商品信息
 */
public class OrderdetProducts {
    private String orderid;//订单编号
    private String orderdetid;//订单明细编号
    private String plucode;//商品（12）编码
    private String pluname;//商品名称
    private Long  npnum;//原价订购个数  
	private Long spnum;  //优惠价订购个数 
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    public String getPlucode() {
        return plucode;
    }

    public void setPlucode(String plucode) {
        this.plucode = plucode;
    }

    public String getPluname() {
        return pluname;
    }

    public void setPluname(String pluname) {
        this.pluname = pluname;
    }
    public Long getNpnum() {
		return npnum;
	}

	public void setNpnum(Long npnum) {
		this.npnum = npnum;
	}

	public Long getSpnum() {
		return spnum;
	}

	public void setSpnum(Long spnum) {
		this.spnum = spnum;
	}
   
}
