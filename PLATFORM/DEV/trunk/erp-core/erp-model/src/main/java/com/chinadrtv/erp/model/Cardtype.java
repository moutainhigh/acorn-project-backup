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
@Table(name = "CARDTYPE", schema = "IAGENT")
public class Cardtype implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=10)
	private java.lang.String cardtypeid;  //编号  
	@Length(max=20)
	private java.lang.String type1;  //类型一  
	@Length(max=20)
	private java.lang.String type2;  //类型二  
	@Length(max=20)
	private java.lang.String type3;  //类型三  
	@Length(max=50)
	private java.lang.String cardname;  //卡名称  
	@Length(max=2048)
	private java.lang.String svalue;  //校验值一  
	@Length(max=50)
	private java.lang.String evalue;  //校验值二  
	@Length(max=50)
	private java.lang.String reserved1;  //保留字段  
	@Length(max=30)
	private java.lang.String bankPhn;  //银行电话  
	@Length(max=30)
	private java.lang.String bankFax;  //银行传真  
	@Length(max=30)
	private java.lang.String bankCode;  //商户编号  
	@Length(max=30)
	private java.lang.String bankName;  //信用卡所属银行名称  
	@Length(max=100)
	private java.lang.String bankStr;  //一些特别字符  
	@Length(max=30)
	private java.lang.String bankAccounts;  //商务帐号  
	@Length(max=256)
	private java.lang.String prompt;  //prompt  
	@Length(max=2)
	private java.lang.String numbernullable;  //卡号是否可不用填写  
	@Length(max=2)
	private java.lang.String purviewctrl;  //需要权限控制  
	@Length(max=2)
	private java.lang.String valid;  //是否有效  
	@Length(max=2)
	private java.lang.String showextracode;  //是否显示附加码  
	//columns END


	public Cardtype(){
	}

	public Cardtype(
		java.lang.String cardtypeid
	){
		this.cardtypeid = cardtypeid;
	}

	

	public void setCardtypeid(java.lang.String value) {
		this.cardtypeid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARDTYPE_SEQ")
    @SequenceGenerator(name = "CARDTYPE_SEQ", sequenceName = "IAGENT.CARDTYPE_SEQ")
	@Column(name = "CARDTYPEID", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.String getCardtypeid() {
		return this.cardtypeid;
	}
	
	@Column(name = "TYPE1", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getType1() {
		return this.type1;
	}
	
	public void setType1(java.lang.String value) {
		this.type1 = value;
	}
	
	@Column(name = "TYPE2", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getType2() {
		return this.type2;
	}
	
	public void setType2(java.lang.String value) {
		this.type2 = value;
	}
	
	@Column(name = "TYPE3", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getType3() {
		return this.type3;
	}
	
	public void setType3(java.lang.String value) {
		this.type3 = value;
	}
	
	@Column(name = "CARDNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getCardname() {
		return this.cardname;
	}
	
	public void setCardname(java.lang.String value) {
		this.cardname = value;
	}
	
	@Column(name = "SVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 2048)
	public java.lang.String getSvalue() {
		return this.svalue;
	}
	
	public void setSvalue(java.lang.String value) {
		this.svalue = value;
	}
	
	@Column(name = "EVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getEvalue() {
		return this.evalue;
	}
	
	public void setEvalue(java.lang.String value) {
		this.evalue = value;
	}
	
	@Column(name = "RESERVED1", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getReserved1() {
		return this.reserved1;
	}
	
	public void setReserved1(java.lang.String value) {
		this.reserved1 = value;
	}
	
	@Column(name = "BANK_PHN", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBankPhn() {
		return this.bankPhn;
	}
	
	public void setBankPhn(java.lang.String value) {
		this.bankPhn = value;
	}
	
	@Column(name = "BANK_FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBankFax() {
		return this.bankFax;
	}
	
	public void setBankFax(java.lang.String value) {
		this.bankFax = value;
	}
	
	@Column(name = "BANK_CODE", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBankCode() {
		return this.bankCode;
	}
	
	public void setBankCode(java.lang.String value) {
		this.bankCode = value;
	}
	
	@Column(name = "BANK_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	@Column(name = "BANK_STR", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getBankStr() {
		return this.bankStr;
	}
	
	public void setBankStr(java.lang.String value) {
		this.bankStr = value;
	}
	
	@Column(name = "BANK_ACCOUNTS", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getBankAccounts() {
		return this.bankAccounts;
	}
	
	public void setBankAccounts(java.lang.String value) {
		this.bankAccounts = value;
	}
	
	@Column(name = "PROMPT", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public java.lang.String getPrompt() {
		return this.prompt;
	}
	
	public void setPrompt(java.lang.String value) {
		this.prompt = value;
	}
	
	@Column(name = "NUMBERNULLABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getNumbernullable() {
		return this.numbernullable;
	}
	
	public void setNumbernullable(java.lang.String value) {
		this.numbernullable = value;
	}
	
	@Column(name = "PURVIEWCTRL", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getPurviewctrl() {
		return this.purviewctrl;
	}
	
	public void setPurviewctrl(java.lang.String value) {
		this.purviewctrl = value;
	}
	
	@Column(name = "VALID", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getValid() {
		return this.valid;
	}
	
	public void setValid(java.lang.String value) {
		this.valid = value;
	}
	
	@Column(name = "SHOWEXTRACODE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getShowextracode() {
		return this.showextracode;
	}
	
	public void setShowextracode(java.lang.String value) {
		this.showextracode = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Cardtypeid",getCardtypeid())
			.append("Type1",getType1())
			.append("Type2",getType2())
			.append("Type3",getType3())
			.append("Cardname",getCardname())
			.append("Svalue",getSvalue())
			.append("Evalue",getEvalue())
			.append("Reserved1",getReserved1())
			.append("BankPhn",getBankPhn())
			.append("BankFax",getBankFax())
			.append("BankCode",getBankCode())
			.append("BankName",getBankName())
			.append("BankStr",getBankStr())
			.append("BankAccounts",getBankAccounts())
			.append("Prompt",getPrompt())
			.append("Numbernullable",getNumbernullable())
			.append("Purviewctrl",getPurviewctrl())
			.append("Valid",getValid())
			.append("Showextracode",getShowextracode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCardtypeid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Cardtype == false) return false;
		if(this == obj) return true;
		Cardtype other = (Cardtype)obj;
		return new EqualsBuilder()
			.append(getCardtypeid(),other.getCardtypeid())
			.isEquals();
	}
}

