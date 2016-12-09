package com.chinadrtv.erp.oms.dto;

import java.util.Date;


/**
 * Created with myeclipse IDEA.
 * User: zhangguosheng
 * Date: 13-4-22
 * Time: 下午3:40
 * 应收应付款核对表
 */
public class ReceiptPaymentDto {
	
	private Long id;

    private Date fbdt;    			//结账反馈日期
    private String shipmentId;    	//发运单号
    private String mailId;    	 	//邮件号
    
    private Double a;				//原始应收款（A）
    private Double b;				//已登记的免运费(B)
    private Double c;				//已登记的半拒收收金额(C)
    private Double d;				//实际应收款D=（A-B-C）
    private Double e;				//基准投递费（E）
    private Double f;				//成功/拒收服务费(F)
    private Double g;				//反馈导入应收款(G)
    private Double h;				//反馈的免运费(H)
    private Double i;				//应收款差异I=(D-G)
    private Double j;				//代收手续费(J)
    private Double k;				//总投递费(K)
    
    private Double weight;			//重量
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFbdt() {
		return fbdt;
	}
	public void setFbdt(Date fbdt) {
		this.fbdt = fbdt;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public Double getA() {
		return a;
	}
	public void setA(Double a) {
		this.a = a;
	}
	public Double getB() {
		return b;
	}
	public void setB(Double b) {
		this.b = b;
	}
	public Double getC() {
		return c;
	}
	public void setC(Double c) {
		this.c = c;
	}
	public Double getD() {
		return d;
	}
	public void setD(Double d) {
		this.d = d;
	}
	public Double getE() {
		return e;
	}
	public void setE(Double e) {
		this.e = e;
	}
	public Double getF() {
		return f;
	}
	public void setF(Double f) {
		this.f = f;
	}
	public Double getG() {
		return g;
	}
	public void setG(Double g) {
		this.g = g;
	}
	public Double getH() {
		return h;
	}
	public void setH(Double h) {
		this.h = h;
	}
	public Double getI() {
		return i;
	}
	public void setI(Double i) {
		this.i = i;
	}
	public Double getJ() {
		return j;
	}
	public void setJ(Double j) {
		this.j = j;
	}
	public Double getK() {
		return k;
	}
	public void setK(Double k) {
		this.k = k;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
