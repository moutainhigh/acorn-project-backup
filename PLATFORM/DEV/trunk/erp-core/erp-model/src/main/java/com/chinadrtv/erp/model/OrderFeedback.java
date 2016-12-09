package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.GenericGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "ORDER_FEEDBACK", schema = "IAGENT")
public class OrderFeedback implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String orderid;  //iagent_orderid  
	@Length(max=20)
	private java.lang.String mailid;  //邮件号  
	
	private java.util.Date contactDate;  //交寄时间  
	@Length(max=10)
	private java.lang.String feedbackStatus;  //反馈状态  
	@Length(max=100)
	private java.lang.String rejectionDesc;  //失败原因  
	
	private Double nowmoney;  //应收金额  
	
	private Double clearfee;  //实际结算金额  
	
	private Double mailprice;  //投递费  
	
	private Double handlingfee;  //手续费  
	
	private java.lang.Long id;  //ID   
	
	private java.util.Date importDate;  //导入日期  
	//columns END


	public OrderFeedback(){
	}

	public OrderFeedback(
		java.lang.Long id
	){
		this.id = id;
	}

	

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_FEEDBACK_SEQ")
    @SequenceGenerator(name = "ORDER_FEEDBACK_SEQ", sequenceName = "IAGENT.ORDER_FEEDBACK_SEQ")
	@Column(name = "ID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getId() {
		return this.id;
	}
	
	@Column(name = "ORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	
	@Column(name = "MAILID", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMailid() {
		return this.mailid;
	}
	
	public void setMailid(java.lang.String value) {
		this.mailid = value;
	}
	
	@Transient
	public String getContactDateString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getContactDate());
		return df.format(getContactDate());
	}
	public void setContactDateString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setContactDate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CONTACT_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getContactDate() {
		return this.contactDate;
	}
	
	public void setContactDate(java.util.Date value) {
		this.contactDate = value;
	}
	
	@Column(name = "FEEDBACK_STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getFeedbackStatus() {
		return this.feedbackStatus;
	}
	
	public void setFeedbackStatus(java.lang.String value) {
		this.feedbackStatus = value;
	}
	
	@Column(name = "REJECTION_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getRejectionDesc() {
		return this.rejectionDesc;
	}
	
	public void setRejectionDesc(java.lang.String value) {
		this.rejectionDesc = value;
	}
	
	@Column(name = "NOWMONEY", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getNowmoney() {
		return this.nowmoney;
	}
	
	public void setNowmoney(Double value) {
		this.nowmoney = value;
	}
	
	@Column(name = "CLEARFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getClearfee() {
		return this.clearfee;
	}
	
	public void setClearfee(Double value) {
		this.clearfee = value;
	}
	
	@Column(name = "MAILPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getMailprice() {
		return this.mailprice;
	}
	
	public void setMailprice(Double value) {
		this.mailprice = value;
	}
	
	@Column(name = "HANDLINGFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getHandlingfee() {
		return this.handlingfee;
	}
	
	public void setHandlingfee(Double value) {
		this.handlingfee = value;
	}
	
	@Transient
	public String getImportDateString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getImportDate());
		return df.format(getImportDate());
	}
	public void setImportDateString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setImportDate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "IMPORT_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 11)
	public java.util.Date getImportDate() {
		return this.importDate;
	}
	
	public void setImportDate(java.util.Date value) {
		this.importDate = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Orderid",getOrderid())
			.append("Mailid",getMailid())
			.append("ContactDate",getContactDate())
			.append("FeedbackStatus",getFeedbackStatus())
			.append("RejectionDesc",getRejectionDesc())
			.append("Nowmoney",getNowmoney())
			.append("Clearfee",getClearfee())
			.append("Mailprice",getMailprice())
			.append("Handlingfee",getHandlingfee())
			.append("Id",getId())
			.append("ImportDate",getImportDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrderFeedback == false) return false;
		if(this == obj) return true;
		OrderFeedback other = (OrderFeedback)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

