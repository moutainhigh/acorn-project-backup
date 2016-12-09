package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * EDI数据导入历史表
 * User: liyu
 * Date: 13-1-18
 * Time: 下午3:47
 */
@Entity
@Table(name = "OMS_EDICLEAR", schema = "ACOAPP_OMS")
public class EdiClear implements java.io.Serializable {

    private Long clearid;       //ID
    private String orderid;     //订单号
    private String mailid;      //邮件号
    private Date senddt;        //交寄时间
    private String status;      //反馈状态
    private String reason;      //导入备注信息
    private Double totalprice;  //应收金额
    private Double nowmoney;    //实际结算金额
    private Date impdt;          //结账时间
    private String impusr;       //结算人
    private Integer type;        //结账类型
    private String remark;       //结算描述
    private Double postFee;    //投递费


    @Id
    @Column(name = "CLEARID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQEDICLEAR")
    @SequenceGenerator(name = "SEQEDICLEAR", sequenceName = "IAGENT.SEQEDICLEAR")
    public Long getClearid() {
        return this.clearid;
    }

    public void setClearid(Long clearid) {
        this.clearid = clearid;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "MAILID", length = 20)
    public String getMailid() {
        return this.mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SENDDT", length = 7)
    public Date getSenddt() {
        return this.senddt;
    }

    public void setSenddt(Date senddt) {
        this.senddt = senddt;
    }

    @Column(name = "STATUS", length = 10)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "REASON", length = 10)
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "TOTALPRICE", precision = 10)
    public Double getTotalprice() {
        return this.totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    @Column(name = "NOWMONEY", precision = 10)
    public Double getNowmoney() {
        return this.nowmoney;
    }

    public void setNowmoney(Double nowmoney) {
        this.nowmoney = nowmoney;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMPDT", length = 7)
    public Date getImpdt() {
        return this.impdt;
    }

    public void setImpdt(Date impdt) {
        this.impdt = impdt;
    }

    @Column(name = "IMPUSR", length = 10)
    public String getImpusr() {
        return this.impusr;
    }

    public void setImpusr(String impusr) {
        this.impusr = impusr;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    @Column(name = "POSTFEE", precision = 10)
    public Double getPostFee() {
        return postFee;
    }

    public void setPostFee(Double postFee) {
        this.postFee = postFee;
    }
}
