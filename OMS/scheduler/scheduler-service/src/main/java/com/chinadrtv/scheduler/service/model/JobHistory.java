package com.chinadrtv.scheduler.service.model;

import java.util.Date;

/**
 * 
 * @author xieen
 * @version $Id: JobHistory.java, v 0.1 2013-8-7 下午4:06:40 xieen Exp $
 */
public class JobHistory {
    private int    id;

    private int    jobId;

    private String message;

    private String jobSystem;

    private String jobName;

    private int    jobStatus;

    private Date   jobTime;

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getJobTime() {
        return jobTime;
    }

    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobSystem() {
        return jobSystem;
    }

    public void setJobSystem(String jobSystem) {
        this.jobSystem = jobSystem;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

}
