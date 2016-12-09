package com.chinadrtv.erp.model.marketing;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询Callback规格(条件)
 * User: gaudi.gao
 * Date: 13-8-1
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class CallbackSpecification implements Serializable {
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
    
    private String startDateString;
    /**
     * 呼入结束时间
     */
    private Date endDate;
    
    private String endDateString;
    /**
     * 工作组
     */
    private String workGroup;
    /**
     * acd组
     */
    private String acdGroup;
    /**
     * i
     */
    private String ani;
    /**
     * dnis
     */
    private String dnis;
    /**
     * 被叫
     */
    private String priority;
    /**
     * 通话结果
     */
    private String result;
    /**
     * 数据分配状态
     */
    private Long allocatedNumber;
    /**
     * 咨询订单产品
     */
    private String orderId;
    /**
     * 咨询订单产品
     */
    private String productId;
    /**
     * 坐席工号
     */
    private String agentUser;
    
    /**
     * 用于批量查询
     */
    private List<String> agentUserIds;
    /**
     * 通话ID(caseId or connId)
     */
    private String callId;
    /**
     * 分配批次号
     */
    private String batchId;
    
    private boolean queryExecuted;

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

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    public String getAcdGroup() {
        return acdGroup;
    }

    public void setAcdGroup(String acdGroup) {
        this.acdGroup = acdGroup;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Long getAllocatedNumber() {
        return allocatedNumber;
    }

    public void setAllocatedNumber(Long allocatedNumber) {
        this.allocatedNumber = allocatedNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(String agentUser) {
        this.agentUser = agentUser;
    }

    public List<String> getAgentUserIds() {
		return agentUserIds;
	}

	public void setAgentUserIds(List<String> agentUserIds) {
		this.agentUserIds = agentUserIds;
	}

	public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    public boolean isQueryExecuted() {
		return queryExecuted;
	}

	public void setQueryExecuted(boolean queryExecuted) {
		this.queryExecuted = queryExecuted;
	}

	public Map<String, Object> GetParameters(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(callType)){
            map.put("callType", callType);
        }

        if(StringUtils.isNotBlank(callDuration)){
            map.put("callDuration", callDuration);
        }

        if(startDate != null){
            map.put("startDate", startDate);
            map.put("startDateString", formatter.format(startDate));
        }
        if(endDate != null){
            map.put("endDate", endDate);
            map.put("endDateString", formatter.format(endDate));
        }
        if(StringUtils.isNotBlank(workGroup)){
            map.put("workGroup", workGroup);
            map.put("workGroups", Arrays.asList(workGroup.split(",")));
        }
        if(StringUtils.isNotBlank(acdGroup)){
            map.put("acdGroup", acdGroup);
            map.put("acdGroups", Arrays.asList(acdGroup.split(",")));
        }

        if(StringUtils.isNotBlank(ani)){
            map.put("ani", ani);
            map.put("anis", Arrays.asList(ani.split(",")));
        }

        if(StringUtils.isNotBlank(dnis)){
            map.put("dnis", dnis);
        }
        if(StringUtils.isNotBlank(priority)){
            map.put("priority", priority);
            map.put("priorities", Arrays.asList(priority.split(",")));
        }
        if(StringUtils.isNotBlank(result)){
            map.put("result", result);
        }
        if(StringUtils.isNotBlank(orderId)){
            map.put("orderId", orderId);
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
