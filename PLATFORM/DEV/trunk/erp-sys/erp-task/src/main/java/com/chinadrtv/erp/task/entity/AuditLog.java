package com.chinadrtv.erp.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "AUDIT_LOG", schema = "ACOAPP_OMS")
public class AuditLog extends BaseEntity {

	private static final long serialVersionUID = -3909242315673925646L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_LOG_SEQ")
    @SequenceGenerator(name = "AUDIT_LOG_SEQ", sequenceName = "ACOAPP_OMS.AUDIT_LOG_SEQ")
    @Column(name = "ID")
	private Long id;
    
    @Column(name = "APP_NAME", length = 100)
    private String appName;
    
    @Column(name = "FUNC_NAME", length = 100)
    private String funcName;
    
    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "SESSION_ID", length = 100)
    private String sessionId;
    
    @Column(name = "URL", length = 200)
    private String url;
    
    @Column(name = "LOG_VALUE", length = 1000)
    private String logValue;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOG_DATE", length = 7)
    private Date   logDate;
    
    @Column(name = "DURATION")
    private Long duration;
    
    @Column(name = "TREAD_ID")
    private String treadid;
    
    @Column(name = "TEST_FIELD", length = 100)
    private String test;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogValue() {
		return logValue;
	}

	public void setLogValue(String logValue) {
		this.logValue = logValue;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getTreadid() {
		return treadid;
	}

	public void setTreadid(String treadid) {
		this.treadid = treadid;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
    
}
