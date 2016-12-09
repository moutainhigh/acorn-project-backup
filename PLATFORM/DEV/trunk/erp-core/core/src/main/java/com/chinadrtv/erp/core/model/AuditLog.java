package com.chinadrtv.erp.core.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "AUDIT_LOG", schema = "ACOAPP_OMS")
public class AuditLog implements java.io.Serializable {
    private Long id;
    private String appName;
    private String funcName;
    private String userId;
    private String sessionId;
    private String url;
    private String logValue;
    private Date   logDate;
    private Long duration;
    private String treadid;

       @Column(name = "TEST_FIELD", length = 100)
    public String getTest() {
        return test;
    }


    public void setTest(String test) {
        this.test = test;
    }

    private String test;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_LOG_SEQ")
    @SequenceGenerator(name = "AUDIT_LOG_SEQ", sequenceName = "ACOAPP_OMS.AUDIT_LOG_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "APP_NAME", length = 100)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(name = "FUNC_NAME", length = 100)
    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "SESSION_ID", length = 100)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = "URL", length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "LOG_VALUE", length = 1000)
    public String getLogValue() {
        return logValue;
    }

    public void setLogValue(String logValue) {
        this.logValue = logValue;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOG_DATE", length = 7)
    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    @Column(name = "DURATION")
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Column(name = "TREAD_ID")
	public String getTreadid() {
		return treadid;
	}


	public void setTreadid(String treadid) {
		this.treadid = treadid;
	}
    
    
}
