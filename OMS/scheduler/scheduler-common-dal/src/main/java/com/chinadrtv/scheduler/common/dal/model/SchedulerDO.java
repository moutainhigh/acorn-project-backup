package com.chinadrtv.scheduler.common.dal.model;

/**
 * 
 * @author xieen
 * @version $Id: Scheduler.java, v 0.1 2013-7-23 上午11:05:18 xieen Exp $
 */
public class SchedulerDO {
    private int    id;
    private String jobName;
    private String jobTopic;
    private String jobCronExpression;
    private int    jobStatus;
    private String jobDescription;
    private String jobSystem;
    private String jobStyle;

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

    /**
     * Getter method for property <tt>jobStyle</tt>.
     * 
     * @return property value of jobStyle
     */
    public String getJobStyle() {
        return jobStyle;
    }

    /**
     * Setter method for property <tt>jobStyle</tt>.
     * 
     * @param jobStyle value to be assigned to property jobStyle
     */
    public void setJobStyle(String jobStyle) {
        this.jobStyle = jobStyle;
    }

}
