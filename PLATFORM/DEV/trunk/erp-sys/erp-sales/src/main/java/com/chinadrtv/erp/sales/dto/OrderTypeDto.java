package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.GrpOrderType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-25
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class OrderTypeDto {
    public GrpOrderType grpOrderType;
    public String orderTypeName;


    public  OrderTypeDto(GrpOrderType grpOrderType){
        this.grpOrderType = grpOrderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public Long getId() {
        return grpOrderType.getId();
    }


    public String getUpdateUser() {
        return grpOrderType.getUpdateUser();
    }

    public void setGrpid(String grpid) {
        grpOrderType.setGrpid(grpid);
    }

    public void setUpdateUser(String updateUser) {
        grpOrderType.setUpdateUser(updateUser);
    }

    public void setCreateUser(String createUser) {
        grpOrderType.setCreateUser(createUser);
    }

    public String getGrpname() {
        return grpOrderType.getGrpname();
    }

    public String getGrpid() {
        return grpOrderType.getGrpid();
    }

    public void setOrderType(String orderType) {
        grpOrderType.setOrderType(orderType);
    }

    public Date getUpdateDate() {
        return grpOrderType.getUpdateDate();
    }

    public void setGrpname(String grpname) {
        grpOrderType.setGrpname(grpname);
    }

    public void setUpdateDate(Date updateDate) {
        grpOrderType.setUpdateDate(updateDate);
    }


    public String getCreateUser() {
        return grpOrderType.getCreateUser();
    }

    public String getRemarks() {
        return grpOrderType.getRemarks();
    }

    public String getOrderType() {
        return grpOrderType.getOrderType();
    }

    public void setCreateDate(Date createDate) {
        grpOrderType.setCreateDate(createDate);
    }

    public Boolean getDefault() {
        return grpOrderType.getDefault();
    }

    public void setId(Long id) {
        grpOrderType.setId(id);
    }

    public Date getCreateDate() {
        return grpOrderType.getCreateDate();
    }

    public void setDefault(Boolean aDefault) {
        grpOrderType.setDefault(aDefault);
    }

    public void setRemarks(String remarks) {
        grpOrderType.setRemarks(remarks);
    }
}
