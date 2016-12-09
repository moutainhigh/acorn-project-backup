package com.chinadrtv.erp.distribution.dto;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class CallDetailSpecification implements java.io.Serializable {
    /**
     * 分配类型
     */
    private String callType;
    /**
     * 呼入开始时间
     */
    private Date startDate;
    /**
     * 呼入结束时间
     */
    private Date endDate;
    /**
     * acd组
     */
    private String acdGroup;
    /**
     * dnis
     */
    private String dnis;
    /**
     * 工作组
     */
    private String workGroup;
    /**
     * 坐席工号
     */
    private String agentUser;
    /**
     * 分配次数
     */
    private Long allocatedNumbers;

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAcdGroup() {
        return acdGroup;
    }

    public void setAcdGroup(String acdGroup) {
        this.acdGroup = acdGroup;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    public String getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(String agentUser) {
        this.agentUser = agentUser;
    }

    public Long getAllocatedNumbers() {
        return allocatedNumbers;
    }

    public void setAllocatedNumbers(Long allocatedNumbers) {
        this.allocatedNumbers = allocatedNumbers;
    }

    public Map<String, Object> GetParameters(){
        Map<String, Object> map = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(callType)){
            map.put("callType", callType);
        }

        if(startDate != null){
            map.put("startDate", startDate);
        }
        if(endDate != null){
            map.put("endDate", endDate);
        }

        if(StringUtils.isNotBlank(acdGroup)){
            map.put("acdGroup", acdGroup);
        }

        if(StringUtils.isNotBlank(dnis)){
            map.put("dnis", dnis);
        }

        if(StringUtils.isNotBlank(workGroup)){
            map.put("workGroup", workGroup);
        }

        if(StringUtils.isNotBlank(agentUser)){
            map.put("agentUser", agentUser);
        }

        return map;
    }
}
