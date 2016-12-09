package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackPhone
 * Description: 电话黑名单
 * User: xieguoqiang
 *
 * @version 1.0
 */

@javax.persistence.Table(name = "BLACK_PHONE", schema = "IAGENT")
@Entity
public class BlackPhone implements java.io.Serializable {
    private Long id;
    private Integer status; // 1为非黑 2为黑 3为已去黑，当状态为3，则次数重置为0，且不再更新，而是另起新增，这样可以加黑去黑记录
    private String phoneNum;
    private String approveManager;
    private Date approveDate;
    private String reuseManager;
    private Date reuseDate;
    private Integer addTimes;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLACK_PHONE_SEQ")
    @SequenceGenerator(name = "BLACK_PHONE_SEQ", sequenceName = "IAGENT.BLACK_PHONE_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", length = 1)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "PHONE_NUM", length = 20)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name = "APPROVE_MANAGER", length = 20)
    public String getApproveManager() {
        return approveManager;
    }

    public void setApproveManager(String approveManager) {
        this.approveManager = approveManager;
    }

    @Column(name = "APPROVE_DATE", length = 20)
    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    @Column(name = "REUSE_MANAGER", length = 20)
    public String getReuseManager() {
        return reuseManager;
    }

    public void setReuseManager(String reuseManager) {
        this.reuseManager = reuseManager;
    }

    @Column(name = "REUSE_DATE", length = 20)
    public Date getReuseDate() {
        return reuseDate;
    }

    public void setReuseDate(Date reuseDate) {
        this.reuseDate = reuseDate;
    }

    @Column(name = "ADD_TIMES", length = 3)
    public Integer getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(Integer addTimes) {
        this.addTimes = addTimes;
    }
}