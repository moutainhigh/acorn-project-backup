package com.chinadrtv.erp.model;

import javax.persistence.*;

import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.GenericGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "ORDERDET", schema = "IAGENT")
public class Orderdet implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String orderdetid;  //订单详细ID  
	@Length(max=16)
	private java.lang.String orderid;  //订单ID  
	@Length(max=16)
	private java.lang.String prodid;  //产品ID  

	private Contact contact;  //订购人  
	@Length(max=10)
	private java.lang.String prodscode;  //产品代码  
	@Length(max=100)
	private java.lang.String prodname;  //产品名称  
	@Length(max=10)
	private java.lang.String soldwith;  //是否搭销(NAMES.TID='SOLDWITH')  
	@Length(max=10)
	private java.lang.String status;  //产品状态(NAMES.TID='ORDERSTATUS')  
	@Length(max=10)
	private java.lang.String reckoning;  //是否结账  
	
	private java.util.Date reckoningdt;  //结账日期  
	
	private java.util.Date fbdt;  //反馈日期  
	
	private Long uprice;  //产品原价  
	
	private java.lang.Long upnum;  //原价订购个数  
	
	private Long sprice;  //产品优惠价  
	
	private java.lang.Long spnum;  //优惠价订购个数  
	
	private Long payment;  //实际付款  
	
	private Long freight;  //产品运费  
	
	private Long postfee;  //投递费  
	
	private Long clearfee;  //实际结算  
	
	private java.util.Date orderdt;  //订购日期  
	@Length(max=10)
	private java.lang.String provinceid;  //省份编码  
	@Length(max=10)
	private java.lang.String state;  //订购人省份  
	@Length(max=22)
	private java.lang.String city;  //城市  
	@Length(max=10)
	private java.lang.String mdusr;  //修改人  
	@Length(max=10)
	private java.lang.String breason;  //退货原因  
	@Length(max=10)
	private java.lang.String feedback;  //产品反馈结果(NAMES.TID='ORDERFEEDBACK')  
	@Length(max=10)
	private java.lang.String goodsback;  //退货地点  
	@Length(max=16)
	private java.lang.String producttype;  //产品规格  
	
	private java.util.Date backdt;  //退货日期  
	
	private Long backmoney;  //退货金额  
	@Length(max=30)
	private java.lang.String oldprod;  //原产品(只对换货产品有效)  
	
	private Long compensate;  //补差价  
	@Length(max=20)
	private java.lang.String purpose;  //购买目的  
	@Length(max=10)
	private java.lang.String jifen;  //当前产品使用积分金额  
	
	private java.lang.Long ticket;  //当前产品使用返券金额  
	@Length(max=16)
	private java.lang.String num1;  //num1  
	@Length(max=30)
	private java.lang.String num2;  //num2  
	@Length(max=30)
	private java.lang.String baleprodid;  //baleprodid  
	@Length(max=20)
	private java.lang.String cardrightnum;  //cardrightnum  
	
	private Long accountingcost;  //提成基数（product.discount）  
	@Length(max=10)
	private java.lang.String spid;  //促销活动ID  
	@Length(max=30)
	private java.lang.String prodbankid;  //银行产品编码  
	@Length(max=30)
	private java.lang.String scratchcard;  //scratchcard  
	
	private Long sccardamount;  //sccardamount  
	@Length(max=20)
	private java.lang.String catalogno;  //期刊号  
	@Length(max=20)
	private java.lang.String promotionsdocno;  //促销单号  
	
	private Long promotionsdetruid;  //满赠明细号  
	
	private Long lastLockSeqid;  //lastLockSeqid  
	
	private Long lastUpdateSeqid;  //lastUpdateSeqid  
	
	private java.util.Date lastUpdateTime;  //lastUpdateTime  
	//columns END


	public Orderdet(){
	}

	public Orderdet(
		java.lang.String orderdetid
	){
		this.orderdetid = orderdetid;
	}

	

	public void setOrderdetid(java.lang.String value) {
		this.orderdetid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERDET_SEQ")
    @SequenceGenerator(name = "ORDERDET_SEQ", sequenceName = "IAGENT.ORDERDET_SEQ")
	@Column(name = "ORDERDETID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getOrderdetid() {
		return this.orderdetid;
	}
	
	@Column(name = "ORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	
	@Column(name = "PRODID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getProdid() {
		return this.prodid;
	}
	
	public void setProdid(java.lang.String value) {
		this.prodid = value;
	}
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "contactid", referencedColumnName = "contactid", unique = true,insertable=false,updatable=false)
	public Contact getContact() {
		return this.contact;
	}
	
	public void setContact(Contact value) {
		this.contact = value;
	}
	
	@Column(name = "PRODSCODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getProdscode() {
		return this.prodscode;
	}
	
	public void setProdscode(java.lang.String value) {
		this.prodscode = value;
	}
	
	@Column(name = "PRODNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getProdname() {
		return this.prodname;
	}
	
	public void setProdname(java.lang.String value) {
		this.prodname = value;
	}
	
	@Column(name = "SOLDWITH", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSoldwith() {
		return this.soldwith;
	}
	
	public void setSoldwith(java.lang.String value) {
		this.soldwith = value;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	@Column(name = "RECKONING", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getReckoning() {
		return this.reckoning;
	}
	
	public void setReckoning(java.lang.String value) {
		this.reckoning = value;
	}
	
	@Transient
	public String getReckoningdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getReckoningdt());
		return df.format(getReckoningdt());
	}
	public void setReckoningdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setReckoningdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "RECKONINGDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getReckoningdt() {
		return this.reckoningdt;
	}
	
	public void setReckoningdt(java.util.Date value) {
		this.reckoningdt = value;
	}
	
	@Transient
	public String getFbdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getFbdt());
		return df.format(getFbdt());
	}
	public void setFbdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setFbdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "FBDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getFbdt() {
		return this.fbdt;
	}
	
	public void setFbdt(java.util.Date value) {
		this.fbdt = value;
	}
	
	@Column(name = "UPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getUprice() {
		return this.uprice;
	}
	
	public void setUprice(Long value) {
		this.uprice = value;
	}
	
	@Column(name = "UPNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getUpnum() {
		return this.upnum;
	}
	
	public void setUpnum(java.lang.Long value) {
		this.upnum = value;
	}
	
	@Column(name = "SPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getSprice() {
		return this.sprice;
	}
	
	public void setSprice(Long value) {
		this.sprice = value;
	}
	
	@Column(name = "SPNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getSpnum() {
		return this.spnum;
	}
	
	public void setSpnum(java.lang.Long value) {
		this.spnum = value;
	}
	
	@Column(name = "PAYMENT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getPayment() {
		return this.payment;
	}
	
	public void setPayment(Long value) {
		this.payment = value;
	}
	
	@Column(name = "FREIGHT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getFreight() {
		return this.freight;
	}
	
	public void setFreight(Long value) {
		this.freight = value;
	}
	
	@Column(name = "POSTFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getPostfee() {
		return this.postfee;
	}
	
	public void setPostfee(Long value) {
		this.postfee = value;
	}
	
	@Column(name = "CLEARFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getClearfee() {
		return this.clearfee;
	}
	
	public void setClearfee(Long value) {
		this.clearfee = value;
	}
	
	@Transient
	public String getOrderdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getOrderdt());
		return df.format(getOrderdt());
	}
	public void setOrderdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setOrderdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "ORDERDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getOrderdt() {
		return this.orderdt;
	}
	
	public void setOrderdt(java.util.Date value) {
		this.orderdt = value;
	}
	
	@Column(name = "PROVINCEID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getProvinceid() {
		return this.provinceid;
	}
	
	public void setProvinceid(java.lang.String value) {
		this.provinceid = value;
	}
	
	@Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getState() {
		return this.state;
	}
	
	public void setState(java.lang.String value) {
		this.state = value;
	}
	
	@Column(name = "CITY", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public java.lang.String getCity() {
		return this.city;
	}
	
	public void setCity(java.lang.String value) {
		this.city = value;
	}
	
	@Column(name = "MDUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getMdusr() {
		return this.mdusr;
	}
	
	public void setMdusr(java.lang.String value) {
		this.mdusr = value;
	}
	
	@Column(name = "BREASON", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getBreason() {
		return this.breason;
	}
	
	public void setBreason(java.lang.String value) {
		this.breason = value;
	}
	
	@Column(name = "FEEDBACK", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getFeedback() {
		return this.feedback;
	}
	
	public void setFeedback(java.lang.String value) {
		this.feedback = value;
	}
	
	@Column(name = "GOODSBACK", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getGoodsback() {
		return this.goodsback;
	}
	
	public void setGoodsback(java.lang.String value) {
		this.goodsback = value;
	}
	
	@Column(name = "PRODUCTTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getProducttype() {
		return this.producttype;
	}
	
	public void setProducttype(java.lang.String value) {
		this.producttype = value;
	}
	
	@Transient
	public String getBackdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getBackdt());
		return df.format(getBackdt());
	}
	public void setBackdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setBackdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "BACKDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getBackdt() {
		return this.backdt;
	}
	
	public void setBackdt(java.util.Date value) {
		this.backdt = value;
	}
	
	@Column(name = "BACKMONEY", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Long getBackmoney() {
		return this.backmoney;
	}
	
	public void setBackmoney(Long value) {
		this.backmoney = value;
	}
	
	@Column(name = "OLDPROD", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getOldprod() {
		return this.oldprod;
	}
	
	public void setOldprod(java.lang.String value) {
		this.oldprod = value;
	}
	
	@Column(name = "COMPENSATE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getCompensate() {
		return this.compensate;
	}
	
	public void setCompensate(Long value) {
		this.compensate = value;
	}
	
	@Column(name = "PURPOSE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getPurpose() {
		return this.purpose;
	}
	
	public void setPurpose(java.lang.String value) {
		this.purpose = value;
	}
	
	@Column(name = "JIFEN", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getJifen() {
		return this.jifen;
	}
	
	public void setJifen(java.lang.String value) {
		this.jifen = value;
	}
	
	@Column(name = "TICKET", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Long getTicket() {
		return this.ticket;
	}
	
	public void setTicket(java.lang.Long value) {
		this.ticket = value;
	}
	
	@Column(name = "NUM1", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getNum1() {
		return this.num1;
	}
	
	public void setNum1(java.lang.String value) {
		this.num1 = value;
	}
	
	@Column(name = "NUM2", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getNum2() {
		return this.num2;
	}
	
	public void setNum2(java.lang.String value) {
		this.num2 = value;
	}
	
	@Column(name = "BALEPRODID", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBaleprodid() {
		return this.baleprodid;
	}
	
	public void setBaleprodid(java.lang.String value) {
		this.baleprodid = value;
	}
	
	@Column(name = "CARDRIGHTNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCardrightnum() {
		return this.cardrightnum;
	}
	
	public void setCardrightnum(java.lang.String value) {
		this.cardrightnum = value;
	}
	
	@Column(name = "ACCOUNTINGCOST", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getAccountingcost() {
		return this.accountingcost;
	}
	
	public void setAccountingcost(Long value) {
		this.accountingcost = value;
	}
	
	@Column(name = "SPID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSpid() {
		return this.spid;
	}
	
	public void setSpid(java.lang.String value) {
		this.spid = value;
	}
	
	@Column(name = "PRODBANKID", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getProdbankid() {
		return this.prodbankid;
	}
	
	public void setProdbankid(java.lang.String value) {
		this.prodbankid = value;
	}
	
	@Column(name = "SCRATCHCARD", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getScratchcard() {
		return this.scratchcard;
	}
	
	public void setScratchcard(java.lang.String value) {
		this.scratchcard = value;
	}
	
	@Column(name = "SCCARDAMOUNT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getSccardamount() {
		return this.sccardamount;
	}
	
	public void setSccardamount(Long value) {
		this.sccardamount = value;
	}
	
	@Column(name = "CATALOGNO", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCatalogno() {
		return this.catalogno;
	}
	
	public void setCatalogno(java.lang.String value) {
		this.catalogno = value;
	}
	
	@Column(name = "PROMOTIONSDOCNO", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getPromotionsdocno() {
		return this.promotionsdocno;
	}
	
	public void setPromotionsdocno(java.lang.String value) {
		this.promotionsdocno = value;
	}
	
	@Column(name = "PROMOTIONSDETRUID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Long getPromotionsdetruid() {
		return this.promotionsdetruid;
	}
	
	public void setPromotionsdetruid(Long value) {
		this.promotionsdetruid = value;
	}
	
	@Column(name = "LAST_LOCK_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Long getLastLockSeqid() {
		return this.lastLockSeqid;
	}
	
	public void setLastLockSeqid(Long value) {
		this.lastLockSeqid = value;
	}
	
	@Column(name = "LAST_UPDATE_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Long getLastUpdateSeqid() {
		return this.lastUpdateSeqid;
	}
	
	public void setLastUpdateSeqid(Long value) {
		this.lastUpdateSeqid = value;
	}
	
	@Transient
	public String getLastUpdateTimeString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getLastUpdateTime());
		return df.format(getLastUpdateTime());
	}
	public void setLastUpdateTimeString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setLastUpdateTime(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "LAST_UPDATE_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Orderdetid",getOrderdetid())
			.append("Orderid",getOrderid())
			.append("Prodid",getProdid())
			.append("Contact",getContact())
			.append("Prodscode",getProdscode())
			.append("Prodname",getProdname())
			.append("Soldwith",getSoldwith())
			.append("Status",getStatus())
			.append("Reckoning",getReckoning())
			.append("Reckoningdt",getReckoningdt())
			.append("Fbdt",getFbdt())
			.append("Uprice",getUprice())
			.append("Upnum",getUpnum())
			.append("Sprice",getSprice())
			.append("Spnum",getSpnum())
			.append("Payment",getPayment())
			.append("Freight",getFreight())
			.append("Postfee",getPostfee())
			.append("Clearfee",getClearfee())
			.append("Orderdt",getOrderdt())
			.append("Provinceid",getProvinceid())
			.append("State",getState())
			.append("City",getCity())
			.append("Mdusr",getMdusr())
			.append("Breason",getBreason())
			.append("Feedback",getFeedback())
			.append("Goodsback",getGoodsback())
			.append("Producttype",getProducttype())
			.append("Backdt",getBackdt())
			.append("Backmoney",getBackmoney())
			.append("Oldprod",getOldprod())
			.append("Compensate",getCompensate())
			.append("Purpose",getPurpose())
			.append("Jifen",getJifen())
			.append("Ticket",getTicket())
			.append("Num1",getNum1())
			.append("Num2",getNum2())
			.append("Baleprodid",getBaleprodid())
			.append("Cardrightnum",getCardrightnum())
			.append("Accountingcost",getAccountingcost())
			.append("Spid",getSpid())
			.append("Prodbankid",getProdbankid())
			.append("Scratchcard",getScratchcard())
			.append("Sccardamount",getSccardamount())
			.append("Catalogno",getCatalogno())
			.append("Promotionsdocno",getPromotionsdocno())
			.append("Promotionsdetruid",getPromotionsdetruid())
			.append("LastLockSeqid",getLastLockSeqid())
			.append("LastUpdateSeqid",getLastUpdateSeqid())
			.append("LastUpdateTime",getLastUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrderdetid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Orderdet == false) return false;
		if(this == obj) return true;
		Orderdet other = (Orderdet)obj;
		return new EqualsBuilder()
			.append(getOrderdetid(),other.getOrderdetid())
			.isEquals();
	}
}

