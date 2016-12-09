package com.chinadrtv.erp.model;

import javax.persistence.*;

import java.util.*;

//import com.google.code.ssm.api.CacheKeyMethod;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.hibernate.annotations.Cache;  
import org.hibernate.annotations.CacheConcurrencyStrategy;  


@Entity 
@Table(name = "COMPANY", schema = "IAGENT")
public class Company implements java.io.Serializable{
	
	//columns START
	@NotBlank @Length(max=5)
	private java.lang.String companyid;
	@Length(max=4)
	private java.lang.String typeid;
	@Length(max=80)
	private java.lang.String name;
	@Length(max=120)
	private java.lang.String address;
	@Length(max=10)
	private java.lang.String zip;
	@Length(max=24)
	private java.lang.String fax;
	@Length(max=80)
	private java.lang.String description;
	@Length(max=200)
	private java.lang.String remark;
	@Length(max=10)
	private java.lang.String areaCode;
	@Length(max=24)
	private java.lang.String phone;
	@Length(max=20)
	private java.lang.String subphone;
	@Length(max=6)
	private java.lang.String shortCode;
	@Length(max=5)
	private java.lang.String spellid;
	@Length(max=60)
	private java.lang.String cityName;
	@Length(max=15)
	private java.lang.String mailCode;
	@Length(max=36)
	private java.lang.String email;
	@Length(max=10)
	private java.lang.String mailtype;
	
	private Long postfee;
	@Length(max=20)
	private java.lang.String isreseller;
	@Length(max=20)
	private java.lang.String provinceid;
	
	private Long refpostfee;
	@Length(max=10)
	private java.lang.String bill;
	@NotBlank @Length(max=50)
	private java.lang.String nccompanyid;
	@Length(max=10)
	private java.lang.String billpostfee;
	@Length(max=4)
	private java.lang.String nousebill;
	@Length(max=50)
	private java.lang.String toWarehouse;

    private Long warehouseId;
	//columns END


	public Company(){
	}


    //@CacheKeyMethod
	@Id
	@Column(name = "COMPANYID", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
	public java.lang.String getCompanyid() {
		return this.companyid;
	}
	
	public void setCompanyid(java.lang.String value) {
		this.companyid = value;
	}
	
	@Column(name = "TYPEID", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getTypeid() {
		return this.typeid;
	}
	
	public void setTypeid(java.lang.String value) {
		this.typeid = value;
	}
	
	@Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "ADDRESS", unique = false, nullable = true, insertable = true, updatable = true, length = 120)
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	@Column(name = "ZIP", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getZip() {
		return this.zip;
	}
	
	public void setZip(java.lang.String value) {
		this.zip = value;
	}
	
	@Column(name = "FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
	public java.lang.String getFax() {
		return this.fax;
	}
	
	public void setFax(java.lang.String value) {
		this.fax = value;
	}
	
	@Column(name = "DESCRIPTION", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	@Column(name = "AREA_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	
	public void setAreaCode(java.lang.String value) {
		this.areaCode = value;
	}
	
	@Column(name = "PHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	@Column(name = "SUBPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getSubphone() {
		return this.subphone;
	}
	
	public void setSubphone(java.lang.String value) {
		this.subphone = value;
	}
	
	@Column(name = "SHORT_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getShortCode() {
		return this.shortCode;
	}
	
	public void setShortCode(java.lang.String value) {
		this.shortCode = value;
	}
	
	@Column(name = "SPELLID", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.String getSpellid() {
		return this.spellid;
	}
	
	public void setSpellid(java.lang.String value) {
		this.spellid = value;
	}
	
	@Column(name = "CITY_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public java.lang.String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}
	
	@Column(name = "MAIL_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public java.lang.String getMailCode() {
		return this.mailCode;
	}
	
	public void setMailCode(java.lang.String value) {
		this.mailCode = value;
	}
	
	@Column(name = "EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	@Column(name = "MAILTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getMailtype() {
		return this.mailtype;
	}
	
	public void setMailtype(java.lang.String value) {
		this.mailtype = value;
	}
	
	@Column(name = "POSTFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getPostfee() {
		return this.postfee;
	}
	
	public void setPostfee(Long value) {
		this.postfee = value;
	}
	
	@Column(name = "ISRESELLER", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getIsreseller() {
		return this.isreseller;
	}
	
	public void setIsreseller(java.lang.String value) {
		this.isreseller = value;
	}
	
	@Column(name = "PROVINCEID", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getProvinceid() {
		return this.provinceid;
	}
	
	public void setProvinceid(java.lang.String value) {
		this.provinceid = value;
	}
	
	@Column(name = "REFPOSTFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getRefpostfee() {
		return this.refpostfee;
	}
	
	public void setRefpostfee(Long value) {
		this.refpostfee = value;
	}
	
	@Column(name = "BILL", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getBill() {
		return this.bill;
	}
	
	public void setBill(java.lang.String value) {
		this.bill = value;
	}
	
	@Column(name = "NCCOMPANYID", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public java.lang.String getNccompanyid() {
		return this.nccompanyid;
	}
	
	public void setNccompanyid(java.lang.String value) {
		this.nccompanyid = value;
	}
	
	@Column(name = "BILLPOSTFEE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getBillpostfee() {
		return this.billpostfee;
	}
	
	public void setBillpostfee(java.lang.String value) {
		this.billpostfee = value;
	}
	
	@Column(name = "NOUSEBILL", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getNousebill() {
		return this.nousebill;
	}
	
	public void setNousebill(java.lang.String value) {
		this.nousebill = value;
	}
	
	@Column(name = "TO_WAREHOUSE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getToWarehouse() {
		return this.toWarehouse;
	}
	
	public void setToWarehouse(java.lang.String value) {
		this.toWarehouse = value;
	}

    @Column(name = "WAREHOUSEID")
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Companyid",getCompanyid())
			.append("Typeid",getTypeid())
			.append("Name",getName())
			.append("Address",getAddress())
			.append("Zip",getZip())
			.append("Fax",getFax())
			.append("Description",getDescription())
			.append("Remark",getRemark())
			.append("AreaCode",getAreaCode())
			.append("Phone",getPhone())
			.append("Subphone",getSubphone())
			.append("ShortCode",getShortCode())
			.append("Spellid",getSpellid())
			.append("CityName",getCityName())
			.append("MailCode",getMailCode())
			.append("Email",getEmail())
			.append("Mailtype",getMailtype())
			.append("Postfee",getPostfee())
			.append("Isreseller",getIsreseller())
			.append("Provinceid",getProvinceid())
			.append("Refpostfee",getRefpostfee())
			.append("Bill",getBill())
			.append("Nccompanyid",getNccompanyid())
			.append("Billpostfee",getBillpostfee())
			.append("Nousebill",getNousebill())
			.append("ToWarehouse",getToWarehouse())
            .append("WarehouseId",getWarehouseId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Company == false) return false;
		if(this == obj) return true;
		Company other = (Company)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

