package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "SYS_MESSAGE", schema = "IAGENT")
@Entity
public class SysMessage implements Serializable {
    private Long id;

    private String content;

    private String receiverId;

    private String recivierGroup;

    @Column(name = "IS_CHECKED")
    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    private Integer checked;

    private String createUser;

    private Date createDate;

    private String linkAction;

    private Date checkDate;

    private String sourceTypeId;

    @Column(name = "DEPARTMENT_ID", length = 20)
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    private String departmentId;//DEPARTMENT_ID

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYS_MESSAGE_SEQ")
    @SequenceGenerator(name = "SYS_MESSAGE_SEQ", sequenceName = "IAGENT.SYS_MESSAGE_SEQ", allocationSize=1)
    public Long getId() {
        return id;
    }

    @Column(name = "CHECK_DATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCheckDate() {
        return checkDate;
    }

    @Column(name = "CONTENT", length = 200)
    public String getContent() {
        return content;
    }

    @Column(name = "CREATE_DATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "CREATE_USER", length = 20)
    public String getCreateUser() {
        return createUser;
    }

    @Column(name = "LINK_ACTION", length = 100)
    public String getLinkAction() {
        return linkAction;
    }

    @Column(name = "RECEIVER_ID", length = 20)
    public String getReceiverId() {
        return receiverId;
    }

    @Column(name = "RECIVIER_GROUP", length = 20)
    public String getRecivierGroup() {
        return recivierGroup;
    }

    @Column(name = "SOURCE_TYPE_ID", length = 20)
    public String getSourceTypeId() {
        return sourceTypeId;
    }

    /*@Column(name = "IS_CHECKED")
    public Integer getChecked() {
        return isChecked;
    }

    public void setChecked(Integer checked) {
        isChecked = checked;
    }*/

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLinkAction(String linkAction) {
        this.linkAction = linkAction;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setRecivierGroup(String recivierGroup) {
        this.recivierGroup = recivierGroup;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysMessage that = (SysMessage) o;

        if (checkDate != null ? !checkDate.equals(that.checkDate) : that.checkDate != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //if (isChecked != null ? !isChecked.equals(that.isChecked) : that.isChecked != null) return false;
        if (linkAction != null ? !linkAction.equals(that.linkAction) : that.linkAction != null) return false;
        if (receiverId != null ? !receiverId.equals(that.receiverId) : that.receiverId != null) return false;
        if (recivierGroup != null ? !recivierGroup.equals(that.recivierGroup) : that.recivierGroup != null)
            return false;
        if (sourceTypeId != null ? !sourceTypeId.equals(that.sourceTypeId) : that.sourceTypeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (receiverId != null ? receiverId.hashCode() : 0);
        result = 31 * result + (recivierGroup != null ? recivierGroup.hashCode() : 0);
        //result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (linkAction != null ? linkAction.hashCode() : 0);
        result = 31 * result + (checkDate != null ? checkDate.hashCode() : 0);
        result = 31 * result + (sourceTypeId != null ? sourceTypeId.hashCode() : 0);
        return result;
    }
}
