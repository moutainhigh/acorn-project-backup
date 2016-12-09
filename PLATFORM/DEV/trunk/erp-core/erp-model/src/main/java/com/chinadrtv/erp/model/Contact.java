package com.chinadrtv.erp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "CONTACT", schema = "IAGENT")
public class Contact implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String contactid;  //客户编号
	@Length(max=30)
	private java.lang.String name;  //姓名  
	@Length(max=10)
	private java.lang.String sex;  //性别  
	@Length(max=10)
	private java.lang.String title;  //职位  
	@Length(max=10)
	private java.lang.String dept;  //部门  
	@Length(max=2)
	private java.lang.String contacttype;  //客户类型  
	@Length(max=128)
	private java.lang.String email;  //电子邮件
    @Length(max=50)
    private java.lang.String weixin;  //微信号码
	@Length(max=128)
	private java.lang.String webaddr;  //网址  
	
	private java.util.Date crdt;  //记录创建日期  
	
	private java.util.Date crtm;  //记录创建时间  
	@Length(max=10)
	private java.lang.String crusr;  //记录创建人  
	
	private java.util.Date mddt;  //记录修改日期  
	
	private java.util.Date mdtm;  //记录修改时间  
	@Length(max=10)
	private java.lang.String mdusr;  //记录修改人  
	@Length(max=128)
	private java.lang.String entityid;  //客户所属公司  
	
	private java.util.Date birthday;  //生日  
	@Length(max=2)
	private java.lang.String ages;  //年龄段  
	@Length(max=10)
	private java.lang.String education;  //教育情况  
	@Length(max=10)
	private java.lang.String income;  //收入  
	@Length(max=10)
	private java.lang.String marriage;  //婚姻  
	@Length(max=10)
	private java.lang.String occupation;  //职业  
	@Length(max=2)
	private java.lang.String consumer;  //客户评价(X:使用黑卡)  
	@Length(max=20)
	private java.lang.String pin;  //会员号码  
	
	private Long total;  //购货总额  
	@Length(max=10)
	private java.lang.String purpose;  //购买目的  
	@Length(max=50)
	private java.lang.String netcontactid;  //网上客户号  
	@Length(max=10)
	private java.lang.String jifen;  //客户积分  
	@Length(max=6)
	private java.lang.String areacode;  //客户所属区域  
	
	private Long ticketvalue;  //返券总金额  
	@Length(max=10)
	private java.lang.String fancy;  //fancy  
	@Length(max=3)
	private java.lang.String idcardFlag;  //idcardFlag  
	@Length(max=10)
	private java.lang.String attitude;  //客户态度  
	@Length(max=20)
	private java.lang.String membertype;  //会员类型  
	@Length(max=20)
	private java.lang.String memberlevel;  //会员级别  
	
	private java.util.Date lastdate;  //lastdate  
	@Max(32767)
	private Integer totalfrequency;  //totalfrequency  
	
	private Long totalmonetary;  //totalmonetary  
	@Length(max=10)
	private java.lang.String sundept;  //sundept  
	@Max(32767)
	private Integer credit;  //credit  
	@Length(max=8)
	private java.lang.String customersource;  //?？???？  
	@Length(max=8)
	private java.lang.String datatype;  //？?？？  
	@Length(max=5)
	private java.lang.String car;  //car  
	@Length(max=5)
	private java.lang.String creditcard;  //creditcard  
	@Length(max=5)
	private java.lang.String children;  //children  
	
	private java.util.Date childrenage;  //childrenage  
	
	private java.lang.Integer carmoney1;  //carmoney1  
	
	private java.lang.Integer carmoney2;  //carmoney2  
	@Length(max=32)
	private java.lang.String dnis;  //dnis  
	@Length(max=64)
	private java.lang.String caseid;  //caseid

    private String isHasElder;

    private java.util.Date elderBirthday;

    private String occupationStatus;
    
    private Integer state;
    //columns END


	public Contact(){
	}

	public Contact(
		java.lang.String contactid
	){
		this.contactid = contactid;
	}

	

	public void setContactid(java.lang.String value) {
		this.contactid = value;
	}
	
	@Id
    @GeneratedValue(generator = "contactIdGenerator")
    @GenericGenerator(
            name = "contactIdGenerator",
            strategy = "com.chinadrtv.erp.model.ContactIdGenerator")
	@Column(name = "CONTACTID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getContactid() {
		return this.contactid;
	}
	
	@Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "SEX", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	@Column(name = "TITLE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	@Column(name = "DEPT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getDept() {
		return this.dept;
	}
	
	public void setDept(java.lang.String value) {
		this.dept = value;
	}
	
	@Column(name = "CONTACTTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getContacttype() {
		return this.contacttype;
	}
	
	public void setContacttype(java.lang.String value) {
		this.contacttype = value;
	}
	
	@Column(name = "EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}

    @Column(name = "WEIXIN", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public java.lang.String getWeixin() {
        return this.weixin;
    }

    public void setWeixin(java.lang.String value) {
        this.weixin = value;
    }
	
	@Column(name = "WEBADDR", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getWebaddr() {
		return this.webaddr;
	}
	
	public void setWebaddr(java.lang.String value) {
		this.webaddr = value;
	}
	
	@Transient
	public String getCrdtString() {
        if(getCrdt()==null)
            return null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getCrdt());
		return df.format(getCrdt());
	}
	public void setCrdtString(String value)  {
        if(value==null)
            return;
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
        if(getCrtm()==null)
            return null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getCrtm());
		return df.format(getCrtm());
	}
	public void setCrtmString(String value)  {
        if(value==null)
            return ;
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
	
	@Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCrusr() {
		return this.crusr;
	}
	
	public void setCrusr(java.lang.String value) {
		this.crusr = value;
	}
	
	@Transient
	public String getMddtString() {
        if(getMddt()==null)
            return null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getMddt());
		return df.format(getMddt());
	}
	public void setMddtString(String value)  {
        if(value==null)
            return;
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
        if(getMdtm()==null)
            return null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getMdtm());
		return df.format(getMdtm());
	}
	public void setMdtmString(String value)  {
        if(value==null)
            return;
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
	
	@Column(name = "MDUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getMdusr() {
		return this.mdusr;
	}
	
	public void setMdusr(java.lang.String value) {
		this.mdusr = value;
	}
	
	@Column(name = "ENTITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getEntityid() {
		return this.entityid;
	}
	
	public void setEntityid(java.lang.String value) {
		this.entityid = value;
	}

//	@Transient
//	public String getBirthdayString() {
//        if(getBirthday()==null)
//            return null;
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		df.format(getBirthday());
//		return df.format(getBirthday());
//	}
//	public void setBirthdayString(String value)  {
//        if(value==null)
//            return;
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		try {
//		setBirthday(df.parse(value));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Column(name = "BIRTHDAY", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
	@Column(name = "AGES", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getAges() {
		return this.ages;
	}
	
	public void setAges(java.lang.String value) {
		this.ages = value;
	}
	
	@Column(name = "EDUCATION", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getEducation() {
		return this.education;
	}
	
	public void setEducation(java.lang.String value) {
		this.education = value;
	}
	
	@Column(name = "INCOME", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getIncome() {
		return this.income;
	}
	
	public void setIncome(java.lang.String value) {
		this.income = value;
	}
	
	@Column(name = "MARRIAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getMarriage() {
		return this.marriage;
	}
	
	public void setMarriage(java.lang.String value) {
		this.marriage = value;
	}
	
	@Column(name = "OCCUPATION", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getOccupation() {
		return this.occupation;
	}
	
	public void setOccupation(java.lang.String value) {
		this.occupation = value;
	}
	
	@Column(name = "CONSUMER", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getConsumer() {
		return this.consumer;
	}
	
	public void setConsumer(java.lang.String value) {
		this.consumer = value;
	}
	
	@Column(name = "PIN", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getPin() {
		return this.pin;
	}
	
	public void setPin(java.lang.String value) {
		this.pin = value;
	}
	
	@Column(name = "TOTAL", unique = false, nullable = true, insertable = true, updatable = true, length = 9)
	public Long getTotal() {
		return this.total;
	}
	
	public void setTotal(Long value) {
		this.total = value;
	}
	
	@Column(name = "PURPOSE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getPurpose() {
		return this.purpose;
	}
	
	public void setPurpose(java.lang.String value) {
		this.purpose = value;
	}
	
	@Column(name = "NETCONTACTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getNetcontactid() {
		return this.netcontactid;
	}
	
	public void setNetcontactid(java.lang.String value) {
		this.netcontactid = value;
	}
	
	@Column(name = "JIFEN", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getJifen() {
		return this.jifen;
	}
	
	public void setJifen(java.lang.String value) {
		this.jifen = value;
	}
	
	@Column(name = "AREACODE", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getAreacode() {
		return this.areacode;
	}
	
	public void setAreacode(java.lang.String value) {
		this.areacode = value;
	}
	
	@Column(name = "TICKETVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Long getTicketvalue() {
		return this.ticketvalue;
	}
	
	public void setTicketvalue(Long value) {
		this.ticketvalue = value;
	}
	
	@Column(name = "FANCY", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getFancy() {
		return this.fancy;
	}
	
	public void setFancy(java.lang.String value) {
		this.fancy = value;
	}
	
	@Column(name = "IDCARD_FLAG", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public java.lang.String getIdcardFlag() {
		return this.idcardFlag;
	}
	
	public void setIdcardFlag(java.lang.String value) {
		this.idcardFlag = value;
	}
	
	@Column(name = "ATTITUDE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAttitude() {
		return this.attitude;
	}
	
	public void setAttitude(java.lang.String value) {
		this.attitude = value;
	}
	
	@Column(name = "MEMBERTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMembertype() {
		return this.membertype;
	}
	
	public void setMembertype(java.lang.String value) {
		this.membertype = value;
	}
	
	@Column(name = "MEMBERLEVEL", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMemberlevel() {
		return this.memberlevel;
	}
	
	public void setMemberlevel(java.lang.String value) {
		this.memberlevel = value;
	}
	
	@Transient
	public String getLastdateString() {
        if(getLastdate()==null)
            return  null;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getLastdate());
		return df.format(getLastdate());
	}
	public void setLastdateString(String value)  {
        if(value==null)
            return;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setLastdate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "LASTDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getLastdate() {
		return this.lastdate;
	}
	
	public void setLastdate(java.util.Date value) {
		this.lastdate = value;
	}
	
	@Column(name = "TOTALFREQUENCY", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public Integer getTotalfrequency() {
		return this.totalfrequency;
	}
	
	public void setTotalfrequency(Integer value) {
		this.totalfrequency = value;
	}
	
	@Column(name = "TOTALMONETARY", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getTotalmonetary() {
		return this.totalmonetary;
	}
	
	public void setTotalmonetary(Long value) {
		this.totalmonetary = value;
	}
	
	@Column(name = "SUNDEPT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSundept() {
		return this.sundept;
	}
	
	public void setSundept(java.lang.String value) {
		this.sundept = value;
	}
	
	@Column(name = "CREDIT", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public Integer getCredit() {
		return this.credit;
	}
	
	public void setCredit(Integer value) {
		this.credit = value;
	}
	
	@Column(name = "CUSTOMERSOURCE", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	public java.lang.String getCustomersource() {
		return this.customersource;
	}
	
	public void setCustomersource(java.lang.String value) {
		this.customersource = value;
	}
	
	@Column(name = "DATATYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	public java.lang.String getDatatype() {
		return this.datatype;
	}
	
	public void setDatatype(java.lang.String value) {
		this.datatype = value;
	}
	
	@Column(name = "CAR", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getCar() {
		return this.car;
	}
	
	public void setCar(java.lang.String value) {
		this.car = value;
	}
	
	@Column(name = "CREDITCARD", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getCreditcard() {
		return this.creditcard;
	}
	
	public void setCreditcard(java.lang.String value) {
		this.creditcard = value;
	}
	
	@Column(name = "CHILDREN", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getChildren() {
		return this.children;
	}
	
	public void setChildren(java.lang.String value) {
		this.children = value;
	}
	
//	@Transient
//	public String getChildrenageString() {
//        if(getChildrenage()==null)
//            return null;
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		df.format(getChildrenage());
//		return df.format(getChildrenage());
//	}
//
//	public void setChildrenageString(String value)  {
//        if(value==null)
//            return;
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		try {
//		setChildrenage(df.parse(value));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Column(name = "CHILDRENAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getChildrenage() {
		return this.childrenage;
	}
	
	public void setChildrenage(java.util.Date value) {
		this.childrenage = value;
	}
	
	@Column(name = "CARMONEY1", unique = false, nullable = true, insertable = true, updatable = true, length = 9)
	public java.lang.Integer getCarmoney1() {
		return this.carmoney1;
	}
	
	public void setCarmoney1(java.lang.Integer value) {
		this.carmoney1 = value;
	}
	
	@Column(name = "CARMONEY2", unique = false, nullable = true, insertable = true, updatable = true, length = 9)
	public java.lang.Integer getCarmoney2() {
		return this.carmoney2;
	}
	
	public void setCarmoney2(java.lang.Integer value) {
		this.carmoney2 = value;
	}
	
	@Column(name = "DNIS", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDnis() {
		return this.dnis;
	}
	
	public void setDnis(java.lang.String value) {
		this.dnis = value;
	}
	
	@Column(name = "CASEID", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getCaseid() {
		return this.caseid;
	}
	
	public void setCaseid(java.lang.String value) {
		this.caseid = value;
	}

    @Column(name = "IS_HAS_ELDER")
    public String getHasElder() {
        return isHasElder;
    }

    public void setHasElder(String hasElder) {
        isHasElder = hasElder;
    }

    @Column(name = "ELDER_BIRTHDAY")
    public java.util.Date getElderBirthday() {
        return elderBirthday;
    }

    public void setElderBirthday(java.util.Date elderBirthday) {
        this.elderBirthday = elderBirthday;
    }

    @Column(name = "OCCUPATION_STATUS")
    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

	@Column(name = "STATE", length = 2)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Contactid",getContactid())
			.append("Name",getName())
			.append("Sex",getSex())
			.append("Title",getTitle())
			.append("Dept",getDept())
			.append("Contacttype",getContacttype())
			.append("Email",getEmail())
			.append("Weixin",getWeixin())
			.append("Webaddr",getWebaddr())
			.append("Crdt",getCrdt())
			.append("Crtm",getCrtm())
			.append("Crusr",getCrusr())
			.append("Mddt",getMddt())
			.append("Mdtm",getMdtm())
			.append("Mdusr",getMdusr())
			.append("Entityid",getEntityid())
			.append("Birthday",getBirthday())
			.append("Ages",getAges())
			.append("Education",getEducation())
			.append("Income",getIncome())
			.append("Marriage",getMarriage())
			.append("Occupation",getOccupation())
			.append("Consumer",getConsumer())
			.append("Pin",getPin())
			.append("Total",getTotal())
			.append("Purpose",getPurpose())
			.append("Netcontactid",getNetcontactid())
			.append("Jifen",getJifen())
			.append("Areacode",getAreacode())
			.append("Ticketvalue",getTicketvalue())
			.append("Fancy",getFancy())
			.append("IdcardFlag",getIdcardFlag())
			.append("Attitude",getAttitude())
			.append("Membertype",getMembertype())
			.append("Memberlevel",getMemberlevel())
			.append("Lastdate",getLastdate())
			.append("Totalfrequency",getTotalfrequency())
			.append("Totalmonetary",getTotalmonetary())
			.append("Sundept",getSundept())
			.append("Credit",getCredit())
			.append("Customersource",getCustomersource())
			.append("Datatype",getDatatype())
			.append("Car",getCar())
			.append("Creditcard",getCreditcard())
			.append("Children",getChildren())
			.append("Childrenage",getChildrenage())
			.append("Carmoney1",getCarmoney1())
			.append("Carmoney2",getCarmoney2())
			.append("Dnis",getDnis())
			.append("Caseid",getCaseid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getContactid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Contact == false) return false;
		if(this == obj) return true;
		Contact other = (Contact)obj;
		return new EqualsBuilder()
			.append(getContactid(),other.getContactid())
			.isEquals();
	}
}

