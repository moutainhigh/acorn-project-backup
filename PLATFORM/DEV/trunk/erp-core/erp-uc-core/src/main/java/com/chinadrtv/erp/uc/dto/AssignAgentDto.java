package com.chinadrtv.erp.uc.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Title: AssignAgentDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class AssignAgentDto implements Serializable {
    private Integer assignCount;
    private String userId;
    private String workGrp;

    public Integer getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(Integer assignCount) {
        this.assignCount = assignCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkGrp() {
        return workGrp;
    }

    public void setWorkGrp(String workGrp) {
        this.workGrp = workGrp;
    }
}
