package com.chinadrtv.erp.sales.dto;


import com.chinadrtv.erp.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-14
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */

public class SessionDto {
    private String uid;
    private String ip;
    private Date lastTime;
    private String sessionId;
    private String server;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionDto)) return false;

        SessionDto that = (SessionDto) o;

        if (!uid.equals(that.uid)) return false;

        return true;
    }


    public boolean equals2(Object obj) {
        if(obj instanceof SessionDto == false) return false;
        if(this == obj) return true;
        SessionDto other = (SessionDto)obj;

        EqualsBuilder eb = new EqualsBuilder();
        if(!StringUtil.isNullOrBank(other.getUid())){
            eb.append(getUid(),other.getUid());
        }
        if(!StringUtil.isNullOrBank(other.getServer())){
            eb.append(getServer(),other.getServer());
        }

        if(!StringUtil.isNullOrBank(other.getIp())){
            eb.append(getIp(),other.getIp());
        }
        return eb.isEquals();
    }



    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
