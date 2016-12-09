/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.service.model;

/**
 * 
 * @author xieen
 * @version $Id: Scheduler.java, v 0.1 2013-8-7 下午4:07:04 xieen Exp $
 */
public class Scheduler {
    private int    id;
    private String jobName;
    private String jobTopic;
    private String jobCronExpression;
    private int    jobStatus;
    private String jobDescription;
    private String jobSystem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobTopic() {
        return jobTopic;
    }

    public void setJobTopic(String jobTopic) {
        this.jobTopic = jobTopic;
    }

    public String getJobCronExpression() {
        return jobCronExpression;
    }

    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobSystem() {
        return jobSystem;
    }

    public void setJobSystem(String jobSystem) {
        this.jobSystem = jobSystem;
    }

}
