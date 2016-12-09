package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *  事件
 * @author haoleitao
 * @date 2013-5-9 上午11:25:34
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "CASES", schema = "IAGENT")
public class Cases implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String caseid;  //事件编号 PK  
	//@Length(max=16)
	//private java.lang.String casetypeid;  //事件类型编号 CASETYPE表 FK  
	
	private Casetype casetype;
	@Length(max=16)
	private java.lang.String category;  //事件子类CATEGORY表 FK  
	@Length(max=16)
	private java.lang.String priorityid;  //优先级编号 PRIORITY表 FK  
	@Length(max=16)
	private java.lang.String contactid;  //联系人编号 CONTACT表 FK  
	@Length(max=16)
	private java.lang.String entityid;  //单位编号 ENTITY表 FK  
	@Length(max=100)
	private java.lang.String briefdsc;  //事件问题摘要  
	@Length(max=2000)
	private java.lang.String probdsc;  //事件问题详细描述  
	
	private java.util.Date reqtime;  //要求响应日  
	@Length(max=200)
	private java.lang.String reqdsc;  //要求响应时  
	@Length(max=100)
	private java.lang.String delivered;  //事件解决方法  
	@Length(max=100)
	private java.lang.String origin;  //事件来源  
	@Length(max=2000)
	private java.lang.String external;  //事件解决描述  
	@Length(max=16)
	private java.lang.String callid;  //通话编号  
	@Length(max=16)
	private java.lang.String status;  //当前状态  
	
	private java.util.Date crdt;  //创建日期  
	
	private java.util.Date crtm;  //创建时间  
	@Length(max=16)
	private java.lang.String crusr;  //创建人  
	
	private java.util.Date mddt;  //修改日期  
	
	private java.util.Date mdtm;  //修改时间  
	@Length(max=16)
	private java.lang.String mdusr;  //修改人  
	@Length(max=16)
	private java.lang.String orderid;  //订单编号  
	@Length(max=100)
	private java.lang.String scode;  //保留字段  
	@Length(max=100)
	private java.lang.String prodfittings;  //产品配件  
	@Length(max=20)
	private java.lang.String prodnum;  //产品或者配件数量  
	@Length(max=20)
	private java.lang.String mediacompany;  //信息来源  
	@Length(max=20)
	private java.lang.String reason;  //投诉原因  
	@Length(max=4)
	private java.lang.String buychannel;  //购买渠道  
	@Length(max=4)
	private java.lang.String satisfaction;  //处理方案调查  
	@Length(max=20)
	private java.lang.String casesource;  //事件信息来源  
	@Length(max=16)
	private java.lang.String chargeusr;  //主管处理人  
	
	private java.util.Date chargedt;  //主管处理时间  
	@Length(max=10)
	private java.lang.String prodsurvey;  //产品使用调查  
	@Length(max=16)
	private java.lang.String processtime;  //processtime  
	//columns END


	public Cases(){
	}

    public Cases(String caseid ) {
        this.caseid = caseid;
    }


    public void setCaseid(java.lang.String value) {
		this.caseid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CASES_SEQ")
    @SequenceGenerator(name = "CASES_SEQ", sequenceName = "IAGENT.CASES_SEQ", allocationSize = 1)
	@Column(name = "CASEID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getCaseid() {
		return this.caseid;
	}
	
//	@Column(name = "CASETYPEID")
//	public java.lang.String getCasetypeid() {
//		return this.casetypeid;
//	}
//	
//	public void setCasetypeid(java.lang.String value) {
//		this.casetypeid = value;
//	}
	
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "CASETYPEID")
	public Casetype getCasetype() {
		return casetype;
	}

	public void setCasetype(Casetype casetype) {
		this.casetype = casetype;
	}

	@Column(name = "CATEGORY", unique = false, nullable = true, insertable = false, updatable = false, length = 16)
	public java.lang.String getCategory() {
		return this.category;
	}
	
	public void setCategory(java.lang.String value) {
		this.category = value;
	}
	
	@Column(name = "PRIORITYID", unique = false, nullable = true, insertable = false, updatable = false, length = 16)
	public java.lang.String getPriorityid() {
		return this.priorityid;
	}
	
	public void setPriorityid(java.lang.String value) {
		this.priorityid = value;
	}
	
	@Column(name = "CONTACTID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getContactid() {
		return this.contactid;
	}
	
	public void setContactid(java.lang.String value) {
		this.contactid = value;
	}
	
	@Column(name = "ENTITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getEntityid() {
		return this.entityid;
	}
	
	public void setEntityid(java.lang.String value) {
		this.entityid = value;
	}
	
	@Column(name = "BRIEFDSC", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getBriefdsc() {
		return this.briefdsc;
	}
	
	public void setBriefdsc(java.lang.String value) {
		this.briefdsc = value;
	}
	
	@Column(name = "PROBDSC", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public java.lang.String getProbdsc() {
		return this.probdsc;
	}
	
	public void setProbdsc(java.lang.String value) {
		this.probdsc = value;
	}
	
	@Transient
	public String getReqtimeString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getReqtime());
		return df.format(getReqtime());
	}
	public void setReqtimeString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setReqtime(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "REQTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getReqtime() {
		return this.reqtime;
	}
	
	public void setReqtime(java.util.Date value) {
		this.reqtime = value;
	}
	
	@Column(name = "REQDSC", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getReqdsc() {
		return this.reqdsc;
	}
	
	public void setReqdsc(java.lang.String value) {
		this.reqdsc = value;
	}
	
	@Column(name = "DELIVERED", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getDelivered() {
		return this.delivered;
	}
	
	public void setDelivered(java.lang.String value) {
		this.delivered = value;
	}
	
	@Column(name = "ORIGIN", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getOrigin() {
		return this.origin;
	}
	
	public void setOrigin(java.lang.String value) {
		this.origin = value;
	}
	
	@Column(name = "EXTERNAL", unique = false, nullable = true, insertable = true, updatable = true, length = 2000)
	public java.lang.String getExternal() {
		return this.external;
	}
	
	public void setExternal(java.lang.String value) {
		this.external = value;
	}
	
	@Column(name = "CALLID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getCallid() {
		return this.callid;
	}
	
	public void setCallid(java.lang.String value) {
		this.callid = value;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	@Transient
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
	public String getCrtmString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getCrtm());
		return df.format(getCrtm());
	}
	public void setCrtmString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setCrtm(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CRTM", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getCrtm() {
		return this.crtm;
	}
	
	public void setCrtm(java.util.Date value) {
		this.crtm = value;
	}
	
	@Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getCrusr() {
		return this.crusr;
	}
	
	public void setCrusr(java.lang.String value) {
		this.crusr = value;
	}
	
	@Transient
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
	
	@Transient
	public String getMdtmString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getMdtm());
		return df.format(getMdtm());
	}
	public void setMdtmString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setMdtm(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "MDTM", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getMdtm() {
		return this.mdtm;
	}
	
	public void setMdtm(java.util.Date value) {
		this.mdtm = value;
	}
	
	@Column(name = "MDUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getMdusr() {
		return this.mdusr;
	}
	
	public void setMdusr(java.lang.String value) {
		this.mdusr = value;
	}
	
	@Column(name = "ORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	
	@Column(name = "SCODE", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getScode() {
		return this.scode;
	}
	
	public void setScode(java.lang.String value) {
		this.scode = value;
	}
	
	@Column(name = "PRODFITTINGS", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getProdfittings() {
		return this.prodfittings;
	}
	
	public void setProdfittings(java.lang.String value) {
		this.prodfittings = value;
	}
	
	@Column(name = "PRODNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getProdnum() {
		return this.prodnum;
	}
	
	public void setProdnum(java.lang.String value) {
		this.prodnum = value;
	}
	
	@Column(name = "MEDIACOMPANY", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMediacompany() {
		return this.mediacompany;
	}
	
	public void setMediacompany(java.lang.String value) {
		this.mediacompany = value;
	}
	
	@Column(name = "REASON", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getReason() {
		return this.reason;
	}
	
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	@Column(name = "BUYCHANNEL", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getBuychannel() {
		return this.buychannel;
	}
	
	public void setBuychannel(java.lang.String value) {
		this.buychannel = value;
	}
	
	@Column(name = "SATISFACTION", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getSatisfaction() {
		return this.satisfaction;
	}
	
	public void setSatisfaction(java.lang.String value) {
		this.satisfaction = value;
	}
	
	@Column(name = "CASESOURCE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCasesource() {
		return this.casesource;
	}
	
	public void setCasesource(java.lang.String value) {
		this.casesource = value;
	}
	
	@Column(name = "CHARGEUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getChargeusr() {
		return this.chargeusr;
	}
	
	public void setChargeusr(java.lang.String value) {
		this.chargeusr = value;
	}
	
	@Transient
	public String getChargedtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getChargedt());
		return df.format(getChargedt());
	}
	public void setChargedtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setChargedt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CHARGEDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getChargedt() {
		return this.chargedt;
	}
	
	public void setChargedt(java.util.Date value) {
		this.chargedt = value;
	}
	
	@Column(name = "PRODSURVEY", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getProdsurvey() {
		return this.prodsurvey;
	}
	
	public void setProdsurvey(java.lang.String value) {
		this.prodsurvey = value;
	}
	
	@Column(name = "PROCESSTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getProcesstime() {
		return this.processtime;
	}
	
	public void setProcesstime(java.lang.String value) {
		this.processtime = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Caseid",getCaseid())
			//.append("Casetypeid",getCasetypeid())
			.append("Category",getCategory())
			.append("Priorityid",getPriorityid())
			.append("Contactid",getContactid())
			.append("Entityid",getEntityid())
			.append("Briefdsc",getBriefdsc())
			.append("Probdsc",getProbdsc())
			.append("Reqtime",getReqtime())
			.append("Reqdsc",getReqdsc())
			.append("Delivered",getDelivered())
			.append("Origin",getOrigin())
			.append("External",getExternal())
			.append("Callid",getCallid())
			.append("Status",getStatus())
			.append("Crdt",getCrdt())
			.append("Crtm",getCrtm())
			.append("Crusr",getCrusr())
			.append("Mddt",getMddt())
			.append("Mdtm",getMdtm())
			.append("Mdusr",getMdusr())
			.append("Orderid",getOrderid())
			.append("Scode",getScode())
			.append("Prodfittings",getProdfittings())
			.append("Prodnum",getProdnum())
			.append("Mediacompany",getMediacompany())
			.append("Reason",getReason())
			.append("Buychannel",getBuychannel())
			.append("Satisfaction",getSatisfaction())
			.append("Casesource",getCasesource())
			.append("Chargeusr",getChargeusr())
			.append("Chargedt",getChargedt())
			.append("Prodsurvey",getProdsurvey())
			.append("Processtime",getProcesstime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCaseid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Cases == false) return false;
		if(this == obj) return true;
		Cases other = (Cases)obj;
		return new EqualsBuilder()
			.append(getCaseid(),other.getCaseid())
			.isEquals();
	}
}

