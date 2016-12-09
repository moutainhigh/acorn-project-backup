package com.chinadrtv.erp.model;

import javax.persistence.*;
import javax.validation.constraints.Max;

import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "ORDERHIST", schema = "IAGENT")
public class Orderhist implements java.io.Serializable{
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String orderid;  //订单ID  
	@Length(max=20)
	private java.lang.String mailid;  //邮件编号(面单号)  
	@Length(max=16)
	private java.lang.String entityid;  //实际送货公司(COMPANY.COMPANYID)  
	
	private java.lang.Integer spellid;  //SPELLID(EMS.SPELLID)  
	@Length(max=16)
	private java.lang.String provinceid;  //省  
	@Length(max=10)
	private java.lang.String cityid;  //城市  

	private Contact contact;  //客户编号(CONTACT)  

	private Contact paycontact;  //付款人编号(CONTACT)  

	private Contact getcontact;  //收款人编号(CONTACT)  
	@Length(max=10)
	private java.lang.String crusr;  //创建人  
	@Length(max=10)
	private java.lang.String mdusr;  //修改人  
	
	private java.util.Date mddt;  //修改日期  
	@Length(max=10)
	private java.lang.String cbcrusr;  //CALLBACK创建者  
	@Length(max=10)
	private java.lang.String parcelnm;  //送货单号  
	@Length(max=10)
	private java.lang.String status;  //订单状态(NAMES.TID='ORDERSTATUS')  
	@Length(max=20)
	private java.lang.String account;  //账务信息('Y')  
	@Length(max=10)
	private java.lang.String result;  //订单反馈结果(NAMES.TID='ORDERFEEDBACK')  
	@Length(max=10)
	private java.lang.String ordertype;  //订单类型(NAMES.TID='ORDERTYPE')  
	@Length(max=20)
	private java.lang.String mailtype;  //订购方式(NAMES.TID='BUYTYPE')  
	@Length(max=10)
	private java.lang.String paytype;  //付款方式(NAMES.TID='PAYTYPE')  
	@Length(max=10)
	private java.lang.String urgent;  //紧急订单标志(0/1)  
	@Length(max=10)
	private java.lang.String confirm;  //索权标志(-1锁权)  
	
	private java.util.Date crdt;  //订购日期  
	
	private java.util.Date senddt;  //分拣日期  
	
	private java.util.Date fbdt;  //反馈日期  
	
	private java.util.Date outdt;  //outdt  
	
	private java.util.Date accdt;  //结帐日期  
	
	private Double totalprice;  //订单总价  
	
	private Double mailprice;  //总运费  
	
	private Double prodprice;  //商品总价  
	
	private Double nowmoney;  //已收货款  
	
	private Double postfee;  //投递费  
	
	private Double clearfee;  //实际结算费  
	@Length(max=10)
	private java.lang.String bill;  //需要发票(1需要)  
	@Length(max=100)
	private java.lang.String note;  //订单备注  
	@Length(max=20)
	private java.lang.String cardtype;  //信用卡类型（CARDTYPE）  
	@Length(max=20)
	private java.lang.String cardnumber;  //信用卡卡号  
	@Length(max=20)
	private java.lang.String media;  //媒体调查ID  
	@Length(max=16)
	private java.lang.String callid;  //通话编号  
	@Length(max=8)
	private java.lang.String callbackid;  //CALLBACK表ID  
	@Length(max=16)
	private java.lang.String parentid;  //父订单编号  
	@Length(max=100)
	private java.lang.String childid;  //子订单编号  
	
	private java.util.Date starttm;  //订单操作开始时间  
	
	private java.util.Date endtm;  //订单操作结束时间  
	@Length(max=10)
	private java.lang.String laststatus;  //订单上次状态  
	@Length(max=10)
	private java.lang.String remark;  //核单标志:为空表示未核,其它值(订单回访结果NAMES.TID='ADJUSTSTATUS')  
	@Length(max=20)
	private java.lang.String cardrightnum;  //索权号  
	@Length(max=10)
	private java.lang.String emsclearstatus;  //EMS结帐状态  
	@Length(max=1)
	private java.lang.String refuse;  //是否收到退包标志  
	
	private java.util.Date parcdt;  //邮寄包裹发出日期  
	@Length(max=40)
	private java.lang.String dnis;  //被叫号码  
	@Length(max=6)
	private java.lang.String areacode;  //订单所属电话中心  
	@Length(max=200)
	private java.lang.String netorderid;  //网上订单号  
	
	private java.util.Date netcrdt;  //网上订购日期  
	@Length(max=10)
	private java.lang.String jifen;  //当前订单使用的积分金额  
	
	private java.lang.Double ticket;  //当前订单使用的返券金额  
	
	private java.lang.Double ticketcount;  //当前订单获得的返券张数  
	@Length(max=32)
	private java.lang.String ani;  //主叫号码  
	@Length(max=10)
	private java.lang.String adusr;  //核单员  
	
	private java.util.Date addt;  //核单日期  
	@Length(max=10)
	private java.lang.String errcode;  //审核错误项  
	//@Length(max=100)
	//private java.lang.String addressid;  //送货地址ID  
	@Length(max=20)
	private java.lang.String grpid;  //坐席组ID（GRP）  

	private java.lang.String companyid;  //预配送货公司ID（COMPANY）  
	@Length(max=10)
	private java.lang.String spid;  //促销活动ID  
	@Length(max=50)
	private java.lang.String invoicetitle;  //发票抬头  
	@Length(max=10)
	private java.lang.String customizestatus;  //订单RF状态(NAMES.TID='CUSTOMIZESTATUS')  
	@Length(max=30)
	private java.lang.String scratchcard;  //刮刮卡卡号  
	
	private Long sccardamount;  //刮刮卡金额  
	
	private java.util.Date rfoutdat;  //出库扫描时间  
	@Max(127)
	private Integer expscm;  //expscm  
	@Max(127)
	private Integer expwms;  //expwms  
	
	private java.lang.Boolean issf;  //是否挑单  
	
	private java.util.Date confirmexpdt;  //确认出库时间  
	@Length(max=300)
	private java.lang.String alipayid;  //支付宝交易号  
	@Length(max=32)
	private java.lang.String istrans;  //是否放单  
	
	private java.util.Date transdate;  //放单日期  
	@Length(max=46)
	private java.lang.String transversion;  //订单版本  
	@Length(max=255)
	private java.lang.String carddouble;  //carddouble  
	
	private Long lastLockSeqid;  //lastLockSeqid  
	
	private Long lastUpdateSeqid;  //lastUpdateSeqid  
	
	private java.util.Date lastUpdateTime;  //lastUpdateTime  
	@Length(max=32)
	private java.lang.String isassign;  //isassign  
	//columns END

	private AddressExt address;
	
	
	private Company company;
	

	public Orderhist(){
		
	}

	public Orderhist(
		java.lang.String orderid
	){
		this.orderid = orderid;
	}

	

	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERHIST_SEQ")
    @SequenceGenerator(name = "ORDERHIST_SEQ", sequenceName = "IAGENT.ORDERHIST_SEQ")
	@Column(name = "ORDERID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	@Column(name = "MAILID", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMailid() {
		return this.mailid;
	}
	
	public void setMailid(java.lang.String value) {
		this.mailid = value;
	}
	
	@Column(name = "ENTITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getEntityid() {
		return this.entityid;
	}
	
	public void setEntityid(java.lang.String value) {
		this.entityid = value;
	}
	
	@Column(name = "SPELLID", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.Integer getSpellid() {
		return this.spellid;
	}
	
	public void setSpellid(java.lang.Integer value) {
		this.spellid = value;
	}
	
	@Column(name = "PROVINCEID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getProvinceid() {
		return this.provinceid;
	}
	
	public void setProvinceid(java.lang.String value) {
		this.provinceid = value;
	}
	
	@Column(name = "CITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCityid() {
		return this.cityid;
	}
	
	public void setCityid(java.lang.String value) {
		this.cityid = value;
	}
	

	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "PAYCONTACTID", referencedColumnName = "contactid", unique = true,insertable=false,updatable=false)
	public Contact getPaycontact() {
		return this.paycontact;
	}
	
	public void setPaycontact(Contact value) {
		this.paycontact = value;
	}
	@OneToOne(optional = false, fetch=FetchType.LAZY) 
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "GETCONTACTID", referencedColumnName = "contactid", unique = true,insertable=false,updatable=false)
	public Contact getGetcontact() {
		return this.getcontact;
	}
	
	public void setGetcontact(Contact value) {
		this.getcontact = value;
	}
	
	@Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCrusr() {
		return this.crusr;
	}
	
	public void setCrusr(java.lang.String value) {
		this.crusr = value;
	}
	
	@Column(name = "MDUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getMdusr() {
		return this.mdusr;
	}
	
	public void setMdusr(java.lang.String value) {
		this.mdusr = value;
	}
	
	@Transient
	@JsonIgnore
	public String getMddtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getMddt());
		return df.format(getMddt());
	}
	public void setMddtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setMddt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "MDDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getMddt() {
		return this.mddt;
	}
	
	public void setMddt(java.util.Date value) {
		this.mddt = value;
	}
	
	@Column(name = "CBCRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCbcrusr() {
		return this.cbcrusr;
	}
	
	public void setCbcrusr(java.lang.String value) {
		this.cbcrusr = value;
	}
	
	@Column(name = "PARCELNM", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getParcelnm() {
		return this.parcelnm;
	}
	
	public void setParcelnm(java.lang.String value) {
		this.parcelnm = value;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	@Column(name = "ACCOUNT", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getAccount() {
		return this.account;
	}
	
	public void setAccount(java.lang.String value) {
		this.account = value;
	}
	
	@Column(name = "RESULT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getResult() {
		return this.result;
	}
	
	public void setResult(java.lang.String value) {
		this.result = value;
	}
	
	@Column(name = "ORDERTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getOrdertype() {
		return this.ordertype;
	}
	
	public void setOrdertype(java.lang.String value) {
		this.ordertype = value;
	}
	
	@Column(name = "MAILTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMailtype() {
		return this.mailtype;
	}
	
	public void setMailtype(java.lang.String value) {
		this.mailtype = value;
	}
	
	@Column(name = "PAYTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getPaytype() {
		return this.paytype;
	}
	
	public void setPaytype(java.lang.String value) {
		this.paytype = value;
	}
	
	@Column(name = "URGENT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getUrgent() {
		return this.urgent;
	}
	
	public void setUrgent(java.lang.String value) {
		this.urgent = value;
	}
	
	@Column(name = "CONFIRM", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getConfirm() {
		return this.confirm;
	}
	
	public void setConfirm(java.lang.String value) {
		this.confirm = value;
	}
	
	@Transient
	@JsonIgnore
	public String getCrdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getCrdt());
		return df.format(getCrdt());
	}
	public void setCrdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setCrdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CRDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getCrdt() {
		return this.crdt;
	}
	
	public void setCrdt(java.util.Date value) {
		this.crdt = value;
	}
	
	@Transient
	@JsonIgnore
	public String getSenddtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getSenddt());
		return df.format(getSenddt());
	}
	public void setSenddtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setSenddt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "SENDDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getSenddt() {
		return this.senddt;
	}
	
	public void setSenddt(java.util.Date value) {
		this.senddt = value;
	}
	
	@Transient
	@JsonIgnore
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
	
	@Transient
	@JsonIgnore
	public String getOutdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getOutdt());
		return df.format(getOutdt());
	}
	public void setOutdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setOutdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "OUTDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getOutdt() {
		return this.outdt;
	}
	
	public void setOutdt(java.util.Date value) {
		this.outdt = value;
	}
	
	@Transient
	@JsonIgnore
	public String getAccdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getAccdt());
		return df.format(getAccdt());
	}
	public void setAccdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setAccdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "ACCDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getAccdt() {
		return this.accdt;
	}
	
	public void setAccdt(java.util.Date value) {
		this.accdt = value;
	}
	
	@Column(name = "TOTALPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getTotalprice() {
		return this.totalprice;
	}
	
	public void setTotalprice(Double value) {
		this.totalprice = value;
	}
	
	@Column(name = "MAILPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getMailprice() {
		return this.mailprice;
	}
	
	public void setMailprice(Double value) {
		this.mailprice = value;
	}
	
	@Column(name = "PRODPRICE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getProdprice() {
		return this.prodprice;
	}
	
	public void setProdprice(Double value) {
		this.prodprice = value;
	}
	
	@Column(name = "NOWMONEY", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getNowmoney() {
		return this.nowmoney;
	}
	
	public void setNowmoney(Double value) {
		this.nowmoney = value;
	}
	
	@Column(name = "POSTFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getPostfee() {
		return this.postfee;
	}
	
	public void setPostfee(Double value) {
		this.postfee = value;
	}
	
	@Column(name = "CLEARFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getClearfee() {
		return this.clearfee;
	}
	
	public void setClearfee(Double value) {
		this.clearfee = value;
	}
	
	@Column(name = "BILL", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getBill() {
		return this.bill;
	}
	
	public void setBill(java.lang.String value) {
		this.bill = value;
	}
	
	@Column(name = "NOTE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getNote() {
		return this.note;
	}
	
	public void setNote(java.lang.String value) {
		this.note = value;
	}
	
	@Column(name = "CARDTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCardtype() {
		return this.cardtype;
	}
	
	public void setCardtype(java.lang.String value) {
		this.cardtype = value;
	}
	
	@Column(name = "CARDNUMBER", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCardnumber() {
		return this.cardnumber;
	}
	
	public void setCardnumber(java.lang.String value) {
		this.cardnumber = value;
	}
	
	@Column(name = "MEDIA", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMedia() {
		return this.media;
	}
	
	public void setMedia(java.lang.String value) {
		this.media = value;
	}
	
	@Column(name = "CALLID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getCallid() {
		return this.callid;
	}
	
	public void setCallid(java.lang.String value) {
		this.callid = value;
	}
	
	@Column(name = "CALLBACKID", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	public java.lang.String getCallbackid() {
		return this.callbackid;
	}
	
	public void setCallbackid(java.lang.String value) {
		this.callbackid = value;
	}
	
	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getParentid() {
		return this.parentid;
	}
	
	public void setParentid(java.lang.String value) {
		this.parentid = value;
	}
	
	@Column(name = "CHILDID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getChildid() {
		return this.childid;
	}
	
	public void setChildid(java.lang.String value) {
		this.childid = value;
	}
	
	@Transient
	@JsonIgnore
	public String getStarttmString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getStarttm());
		return df.format(getStarttm());
	}
	public void setStarttmString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setStarttm(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "STARTTM", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getStarttm() {
		return this.starttm;
	}
	
	public void setStarttm(java.util.Date value) {
		this.starttm = value;
	}
	
	@Transient
	@JsonIgnore
	public String getEndtmString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getEndtm());
		return df.format(getEndtm());
	}
	public void setEndtmString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setEndtm(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "ENDTM", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getEndtm() {
		return this.endtm;
	}
	
	public void setEndtm(java.util.Date value) {
		this.endtm = value;
	}
	
	@Column(name = "LASTSTATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getLaststatus() {
		return this.laststatus;
	}
	
	public void setLaststatus(java.lang.String value) {
		this.laststatus = value;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	@Column(name = "CARDRIGHTNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCardrightnum() {
		return this.cardrightnum;
	}
	
	public void setCardrightnum(java.lang.String value) {
		this.cardrightnum = value;
	}
	
	@Column(name = "EMSCLEARSTATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getEmsclearstatus() {
		return this.emsclearstatus;
	}
	
	public void setEmsclearstatus(java.lang.String value) {
		this.emsclearstatus = value;
	}
	
	@Column(name = "REFUSE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.String getRefuse() {
		return this.refuse;
	}
	
	public void setRefuse(java.lang.String value) {
		this.refuse = value;
	}
	
	@Transient
	@JsonIgnore
	public String getParcdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getParcdt());
		return df.format(getParcdt());
	}
	public void setParcdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setParcdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "PARCDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getParcdt() {
		return this.parcdt;
	}
	
	public void setParcdt(java.util.Date value) {
		this.parcdt = value;
	}
	
	@Column(name = "DNIS", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
	public java.lang.String getDnis() {
		return this.dnis;
	}
	
	public void setDnis(java.lang.String value) {
		this.dnis = value;
	}
	
	@Column(name = "AREACODE", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getAreacode() {
		return this.areacode;
	}
	
	public void setAreacode(java.lang.String value) {
		this.areacode = value;
	}
	
	@Column(name = "NETORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getNetorderid() {
		return this.netorderid;
	}
	
	public void setNetorderid(java.lang.String value) {
		this.netorderid = value;
	}
	
	@Transient
	@JsonIgnore
	public String getNetcrdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getNetcrdt());
		return df.format(getNetcrdt());
	}
	public void setNetcrdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setNetcrdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "NETCRDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getNetcrdt() {
		return this.netcrdt;
	}
	
	public void setNetcrdt(java.util.Date value) {
		this.netcrdt = value;
	}
	
	@Column(name = "JIFEN", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getJifen() {
		return this.jifen;
	}
	
	public void setJifen(java.lang.String value) {
		this.jifen = value;
	}
	
	@Column(name = "TICKET", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Double getTicket() {
		return this.ticket;
	}
	
	public void setTicket(java.lang.Double value) {
		this.ticket = value;
	}
	
	@Column(name = "TICKETCOUNT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Double getTicketcount() {
		return this.ticketcount;
	}
	
	public void setTicketcount(java.lang.Double value) {
		this.ticketcount = value;
	}
	
	@Column(name = "ANI", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getAni() {
		return this.ani;
	}
	
	public void setAni(java.lang.String value) {
		this.ani = value;
	}
	
	@Column(name = "ADUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAdusr() {
		return this.adusr;
	}
	
	public void setAdusr(java.lang.String value) {
		this.adusr = value;
	}
	
	@Transient
	@JsonIgnore
	public String getAddtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getAddt());
		return df.format(getAddt());
	}
	public void setAddtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setAddt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "ADDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getAddt() {
		return this.addt;
	}
	
	public void setAddt(java.util.Date value) {
		this.addt = value;
	}
	
	@Column(name = "ERRCODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getErrcode() {
		return this.errcode;
	}
	
	public void setErrcode(java.lang.String value) {
		this.errcode = value;
	}
	
//	@Column(name = "ADDRESSID", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
//	public java.lang.String getAddressid() {
//		return this.addressid;
//	}
//	
//	public void setAddressid(java.lang.String value) {
//		this.addressid = value;
//	}
	
	@Column(name = "GRPID", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getGrpid() {
		return this.grpid;
	}
	
	public void setGrpid(java.lang.String value) {
		this.grpid = value;
	}
	
	
	public java.lang.String getCompanyid() {
		return this.companyid;
	}
	
	public void setCompanyid(java.lang.String value) {
		this.companyid = value;
	}
	
	@Column(name = "SPID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSpid() {
		return this.spid;
	}
	
	public void setSpid(java.lang.String value) {
		this.spid = value;
	}
	
	@Column(name = "INVOICETITLE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getInvoicetitle() {
		return this.invoicetitle;
	}
	
	public void setInvoicetitle(java.lang.String value) {
		this.invoicetitle = value;
	}
	
	@Column(name = "CUSTOMIZESTATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCustomizestatus() {
		return this.customizestatus;
	}
	
	public void setCustomizestatus(java.lang.String value) {
		this.customizestatus = value;
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
	
	@Transient
	@JsonIgnore
	public String getRfoutdatString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getRfoutdat());
		return df.format(getRfoutdat());
	}
	public void setRfoutdatString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setRfoutdat(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "RFOUTDAT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getRfoutdat() {
		return this.rfoutdat;
	}
	
	public void setRfoutdat(java.util.Date value) {
		this.rfoutdat = value;
	}
	
	@Column(name = "EXPSCM", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public Integer getExpscm() {
		return this.expscm;
	}
	
	public void setExpscm(Integer value) {
		this.expscm = value;
	}
	
	@Column(name = "EXPWMS", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public Integer getExpwms() {
		return this.expwms;
	}
	
	public void setExpwms(Integer value) {
		this.expwms = value;
	}
	
	@Column(name = "ISSF", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public java.lang.Boolean getIssf() {
		return this.issf;
	}
	
	public void setIssf(java.lang.Boolean value) {
		this.issf = value;
	}
	
	@Transient
	@JsonIgnore
	public String getConfirmexpdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getConfirmexpdt());
		return df.format(getConfirmexpdt());
	}
	public void setConfirmexpdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setConfirmexpdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CONFIRMEXPDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getConfirmexpdt() {
		return this.confirmexpdt;
	}
	
	public void setConfirmexpdt(java.util.Date value) {
		this.confirmexpdt = value;
	}
	
	@Column(name = "ALIPAYID", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public java.lang.String getAlipayid() {
		return this.alipayid;
	}
	
	public void setAlipayid(java.lang.String value) {
		this.alipayid = value;
	}
	
	@Column(name = "ISTRANS", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getIstrans() {
		return this.istrans;
	}
	
	public void setIstrans(java.lang.String value) {
		this.istrans = value;
	}
	
	@Transient
	@JsonIgnore
	public String getTransdateString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getTransdate());
		return df.format(getTransdate());
	}
	public void setTransdateString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setTransdate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "TRANSDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getTransdate() {
		return this.transdate;
	}
	
	public void setTransdate(java.util.Date value) {
		this.transdate = value;
	}
	
	@Column(name = "TRANSVERSION", unique = false, nullable = true, insertable = true, updatable = true, length = 46)
	public java.lang.String getTransversion() {
		return this.transversion;
	}
	
	public void setTransversion(java.lang.String value) {
		this.transversion = value;
	}
	
	@Column(name = "CARDDOUBLE", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public java.lang.String getCarddouble() {
		return this.carddouble;
	}
	
	public void setCarddouble(java.lang.String value) {
		this.carddouble = value;
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
	@JsonIgnore
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
	
	@Column(name = "ISASSIGN", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getIsassign() {
		return this.isassign;
	}
	
	public void setIsassign(java.lang.String value) {
		this.isassign = value;
	}
	
	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@JoinColumn(name = "addressid", referencedColumnName = "addressid", unique = true,insertable=false,updatable=false)  
	public AddressExt getAddress() {
		return address;
	}

	public void setAddress(AddressExt address) {
		this.address = address;
	}
	
	
	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "companyid", referencedColumnName = "companyid", unique = true,insertable=false,updatable=false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "contactid", referencedColumnName = "contactid", unique = false,insertable=false,updatable=false)
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Orderid",getOrderid())
			.append("Mailid",getMailid())
			.append("Entityid",getEntityid())
			.append("Spellid",getSpellid())
			.append("Provinceid",getProvinceid())
			.append("Cityid",getCityid())
			//.append("Contactid",getContactid())
			//.append("Paycontactid",getPaycontactid())
			//.append("Getcontactid",getGetcontactid())
			.append("Crusr",getCrusr())
			.append("Mdusr",getMdusr())
			.append("Mddt",getMddt())
			.append("Cbcrusr",getCbcrusr())
			.append("Parcelnm",getParcelnm())
			.append("Status",getStatus())
			.append("Account",getAccount())
			.append("Result",getResult())
			.append("Ordertype",getOrdertype())
			.append("Mailtype",getMailtype())
			.append("Paytype",getPaytype())
			.append("Urgent",getUrgent())
			.append("Confirm",getConfirm())
			.append("Crdt",getCrdt())
			.append("Senddt",getSenddt())
			.append("Fbdt",getFbdt())
			.append("Outdt",getOutdt())
			.append("Accdt",getAccdt())
			.append("Totalprice",getTotalprice())
			.append("Mailprice",getMailprice())
			.append("Prodprice",getProdprice())
			.append("Nowmoney",getNowmoney())
			.append("Postfee",getPostfee())
			.append("Clearfee",getClearfee())
			.append("Bill",getBill())
			.append("Note",getNote())
			.append("Cardtype",getCardtype())
			.append("Cardnumber",getCardnumber())
			.append("Media",getMedia())
			.append("Callid",getCallid())
			.append("Callbackid",getCallbackid())
			.append("Parentid",getParentid())
			.append("Childid",getChildid())
			.append("Starttm",getStarttm())
			.append("Endtm",getEndtm())
			.append("Laststatus",getLaststatus())
			.append("Remark",getRemark())
			.append("Cardrightnum",getCardrightnum())
			.append("Emsclearstatus",getEmsclearstatus())
			.append("Refuse",getRefuse())
			.append("Parcdt",getParcdt())
			.append("Dnis",getDnis())
			.append("Areacode",getAreacode())
			.append("Netorderid",getNetorderid())
			.append("Netcrdt",getNetcrdt())
			.append("Jifen",getJifen())
			.append("Ticket",getTicket())
			.append("Ticketcount",getTicketcount())
			.append("Ani",getAni())
			.append("Adusr",getAdusr())
			.append("Addt",getAddt())
			.append("Errcode",getErrcode())
			//.append("Addressid",getAddressid())
			.append("Grpid",getGrpid())
			.append("Companyid",getCompanyid())
			.append("Spid",getSpid())
			.append("Invoicetitle",getInvoicetitle())
			.append("Customizestatus",getCustomizestatus())
			.append("Scratchcard",getScratchcard())
			.append("Sccardamount",getSccardamount())
			.append("Rfoutdat",getRfoutdat())
			.append("Expscm",getExpscm())
			.append("Expwms",getExpwms())
			.append("Issf",getIssf())
			.append("Confirmexpdt",getConfirmexpdt())
			.append("Alipayid",getAlipayid())
			.append("Istrans",getIstrans())
			.append("Transdate",getTransdate())
			.append("Transversion",getTransversion())
			.append("Carddouble",getCarddouble())
			.append("LastLockSeqid",getLastLockSeqid())
			.append("LastUpdateSeqid",getLastUpdateSeqid())
			.append("LastUpdateTime",getLastUpdateTime())
			.append("Isassign",getIsassign())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrderid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Orderhist == false) return false;
		if(this == obj) return true;
		Orderhist other = (Orderhist)obj;
		return new EqualsBuilder()
			.append(getOrderid(),other.getOrderid())
			.isEquals();
	}
}

