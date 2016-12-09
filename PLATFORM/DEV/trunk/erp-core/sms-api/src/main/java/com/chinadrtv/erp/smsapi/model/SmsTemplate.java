package com.chinadrtv.erp.smsapi.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SmsTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SMS_TEMPLATE", schema = "ACOAPP_MARKETING")
public class SmsTemplate implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 793380893261801691L;

	private Long id;
	private String code;
	private String name;
	private String content;
	private BigDecimal cost;
	private String status;
	private String deptid;
	private Date crttime;
	private String crtuid;
	private Date updtime;
	private String upduid;
	private Integer isdel;
	private String themeTemplate;
	private String dynamicTemplate;
	private String exeDepartment;

	// Constructors

	/** default constructor */
	public SmsTemplate() {
	}

	/** minimal constructor */
	public SmsTemplate(String code) {
		this.code = code;
	}

	/** full constructor */
	public SmsTemplate(String code, String name, String content,
			BigDecimal cost, String status, String deptid, Date crttime,
			String crtuid, Date updtime, String upduid, Integer isdel) {
		this.code = code;
		this.name = name;
		this.content = content;
		this.cost = cost;
		this.status = status;
		this.deptid = deptid;
		this.crttime = crttime;
		this.crtuid = crtuid;
		this.updtime = updtime;
		this.upduid = upduid;
		this.isdel = isdel;
	}

	// Property accessors
	@SequenceGenerator(name = "seq", sequenceName = "ACOAPP_MARKETING.SEQ_SMS_TEMPLATE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CODE", nullable = false, length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONTENT", length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "COST", precision = 22, scale = 0)
	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DEPTID", precision = 22, scale = 0)
	public String getDeptid() {
		return this.deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRTTIME", length = 7)
	public Date getCrttime() {
		return this.crttime;
	}

	public void setCrttime(Date crttime) {
		this.crttime = crttime;
	}

	@Column(name = "CRTUID", precision = 22, scale = 0)
	public String getCrtuid() {
		return this.crtuid;
	}

	public void setCrtuid(String crtuid) {
		this.crtuid = crtuid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDTIME", length = 7)
	public Date getUpdtime() {
		return this.updtime;
	}

	public void setUpdtime(Date updtime) {
		this.updtime = updtime;
	}

	@Column(name = "UPDUID", precision = 22, scale = 0)
	public String getUpduid() {
		return this.upduid;
	}

	public void setUpduid(String upduid) {
		this.upduid = upduid;
	}

	@Column(name = "ISDEL", precision = 22, scale = 0)
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	/**
	 * @return the themeTemplate
	 */
	@Column(name = "THEME_TEMPLATE")
	public String getThemeTemplate() {
		return themeTemplate;
	}

	/**
	 * @param themeTemplate
	 *            the themeTemplate to set
	 */
	public void setThemeTemplate(String themeTemplate) {
		this.themeTemplate = themeTemplate;
	}

	/**
	 * @return the dynamicTemplate
	 */
	@Column(name = "DYNAMIC_TEMPLATE")
	public String getDynamicTemplate() {
		return dynamicTemplate;
	}

	/**
	 * @param dynamicTemplate
	 *            the dynamicTemplate to set
	 */
	public void setDynamicTemplate(String dynamicTemplate) {
		this.dynamicTemplate = dynamicTemplate;
	}

	/**
	 * @return the exeDepartment
	 */
	@Column(name = "EXE_DEPARTMENT")
	public String getExeDepartment() {
		return exeDepartment;
	}

	/**
	 * @param exeDepartment
	 *            the exeDepartment to set
	 */
	public void setExeDepartment(String exeDepartment) {
		this.exeDepartment = exeDepartment;
	}

}