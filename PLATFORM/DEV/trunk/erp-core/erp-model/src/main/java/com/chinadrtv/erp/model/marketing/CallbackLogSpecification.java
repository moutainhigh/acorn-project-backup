package com.chinadrtv.erp.model.marketing;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询CallbackLog规格(条件)
 * User: gaudi.gao
 * Date: 13-8-1
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class CallbackLogSpecification  implements Serializable {

    /**
     * 分配类型
     */
    private String callType;
    /**
     * 通话时长
     * A (0-20s)
     * B (21-180s)
     * C (>180s)
     */
    private String callDuration;
    /**
     * 呼入开始时间
     */
    private Date startDate;
    /**
     * 呼入结束时间
     */
    private Date endDate;
    /**
     * 优先级
     */
    private String priority;
    /**
     * acd组
     */
    private String acdGroup;
    /**
     * 被叫
     */
    private String dnis;
    /**
     * 坐席工号
     */
    private String agentUser;
    /**
     * 数据分配状态
     */
    private Long allocatedNumber;
    /**
     * 分配批次号
     */
    private String batchId;


    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(String agentUser) {
        this.agentUser = agentUser;
    }

    public Long getAllocatedNumber() {
        return allocatedNumber;
    }

    public void setAllocatedNumber(Long allocatedNumber) {
        this.allocatedNumber = allocatedNumber;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Map<String, Object> GetParameters(){
        Map<String, Object> map = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(callType)){
            map.put("callType", callType);
        }

        if(StringUtils.isNotBlank(callDuration)){
            map.put("callDuration", callDuration);
        }

        if(StringUtils.isNotBlank(priority)){
            map.put("priority", priority);
            map.put("priorities", Arrays.asList(priority.split(",")));
        }

        if(startDate != null){
            map.put("startDate", startDate);
        }

        if(endDate != null){
            map.put("endDate", endDate);
        }

        if(StringUtils.isNotBlank(acdGroup)){
            map.put("acdGroup", acdGroup);
            map.put("acdGroups", Arrays.asList(acdGroup.split(",")));
        }

        if(StringUtils.isNotBlank(dnis)){
            map.put("dnis", dnis);
        }

        if(allocatedNumber != null){
            map.put("allocatedNumber", allocatedNumber);
        }
        if(StringUtils.isNotBlank(agentUser)){
            map.put("agentUser", agentUser);
        }

        if(StringUtils.isNotBlank(batchId)){
            map.put("batchId", batchId);
        }

        return map;
    }
}
