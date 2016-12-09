package com.chinadrtv.erp.uc.dto;
import java.io.Serializable;

/**
 *
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-31
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public class GroupDto implements Serializable {
    private String grpid;//组id
    private String grpname; //组名
    private String deptid; //部门id
    private String deptname; //部门
    private Integer grpqty = 0;

    public String getGrpid() {
        return grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    public String getGrpname() {
        return grpname;
    }

    public void setGrpname(String grpname) {
        this.grpname = grpname;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void stDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Integer getGrpqty() {
        return grpqty;
    }

    public void setGrpqty(Integer grpqty) {
        this.grpqty = grpqty;
    }
}
