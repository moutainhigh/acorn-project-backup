package com.chinadrtv.erp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


@Entity
@Table(name = "EMS", schema = "IAGENT")
public class Ems implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	private java.lang.Integer spellid;  //唯一标识  
	@Length(max=16)
	private java.lang.String provinceid;  //省份编号  
	@Length(max=10)
	private java.lang.String spellCode;  //拼音简码  
	@Length(max=10)
	private java.lang.String cityid;  //城市编号  
	@Length(max=16)
	private java.lang.String wubiCode;  //五笔简码  
	@Length(max=60)
	private java.lang.String name;  //名称  
	@Length(max=60)
	private java.lang.String englishName;  //英文名称  
	@Length(max=6)
	private java.lang.String zip;  //邮政编码  
	@Length(max=2)
	private java.lang.String typeid;  //类型编码  
	@Length(max=6)
	private java.lang.String freight;  //FREIGHT  
	@Length(max=6)
	private java.lang.String shortening;  //短拼  
	@Length(max=16)
	private java.lang.String telephone;  //电话区号  
	@Length(max=16)
	private java.lang.String fax;  //传真  
	@Length(max=2)
	private java.lang.String agent;  //坐席  
	@Length(max=2)
	private java.lang.String ems;  //EMS  
	@Length(max=40)
	private java.lang.String remark;  //备注  
	
	private java.lang.Integer companyid;  //公司编码  
	@Length(max=30)
	private java.lang.String fullName;  //全称  
	//columns END


	public Ems(){
	}

	public Ems(
		java.lang.Integer spellid
	){
		this.spellid = spellid;
	}

	

	public void setSpellid(java.lang.Integer value) {
		this.spellid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMS_SEQ")
    @SequenceGenerator(name = "EMS_SEQ", sequenceName = "IAGENT.EMS_SEQ")
	@Column(name = "SPELLID", unique = true, nullable = false, insertable = true, updatable = true, length = 5)
	public java.lang.Integer getSpellid() {
		return this.spellid;
	}
	
	@Column(name = "PROVINCEID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getProvinceid() {
		return this.provinceid;
	}
	
	public void setProvinceid(java.lang.String value) {
		this.provinceid = value;
	}
	
	@Column(name = "SPELL_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getSpellCode() {
		return this.spellCode;
	}
	
	public void setSpellCode(java.lang.String value) {
		this.spellCode = value;
	}
	
	@Column(name = "CITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getCityid() {
		return this.cityid;
	}
	
	public void setCityid(java.lang.String value) {
		this.cityid = value;
	}
	
	@Column(name = "WUBI_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getWubiCode() {
		return this.wubiCode;
	}
	
	public void setWubiCode(java.lang.String value) {
		this.wubiCode = value;
	}
	
	@Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "ENGLISH_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public java.lang.String getEnglishName() {
		return this.englishName;
	}
	
	public void setEnglishName(java.lang.String value) {
		this.englishName = value;
	}
	
	@Column(name = "ZIP", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getZip() {
		return this.zip;
	}
	
	public void setZip(java.lang.String value) {
		this.zip = value;
	}
	
	@Column(name = "TYPEID", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getTypeid() {
		return this.typeid;
	}
	
	public void setTypeid(java.lang.String value) {
		this.typeid = value;
	}
	
	@Column(name = "FREIGHT", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getFreight() {
		return this.freight;
	}
	
	public void setFreight(java.lang.String value) {
		this.freight = value;
	}
	
	@Column(name = "SHORTENING", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public java.lang.String getShortening() {
		return this.shortening;
	}
	
	public void setShortening(java.lang.String value) {
		this.shortening = value;
	}
	
	@Column(name = "TELEPHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(java.lang.String value) {
		this.telephone = value;
	}
	
	@Column(name = "FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getFax() {
		return this.fax;
	}
	
	public void setFax(java.lang.String value) {
		this.fax = value;
	}
	
	@Column(name = "AGENT", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getAgent() {
		return this.agent;
	}
	
	public void setAgent(java.lang.String value) {
		this.agent = value;
	}
	
	@Column(name = "EMS", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getEms() {
		return this.ems;
	}
	
	public void setEms(java.lang.String value) {
		this.ems = value;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	@Column(name = "COMPANYID", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
	public java.lang.Integer getCompanyid() {
		return this.companyid;
	}
	
	public void setCompanyid(java.lang.Integer value) {
		this.companyid = value;
	}
	
	@Column(name = "FULL_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(java.lang.String value) {
		this.fullName = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Spellid",getSpellid())
			.append("Provinceid",getProvinceid())
			.append("SpellCode",getSpellCode())
			.append("Cityid",getCityid())
			.append("WubiCode",getWubiCode())
			.append("Name",getName())
			.append("EnglishName",getEnglishName())
			.append("Zip",getZip())
			.append("Typeid",getTypeid())
			.append("Freight",getFreight())
			.append("Shortening",getShortening())
			.append("Telephone",getTelephone())
			.append("Fax",getFax())
			.append("Agent",getAgent())
			.append("Ems",getEms())
			.append("Remark",getRemark())
			.append("Companyid",getCompanyid())
			.append("FullName",getFullName())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSpellid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Ems == false) return false;
		if(this == obj) return true;
		Ems other = (Ems)obj;
		return new EqualsBuilder()
			.append(getSpellid(),other.getSpellid())
			.isEquals();
	}
}

