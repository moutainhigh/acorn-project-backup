package com.chinadrtv.erp.uc.dto;

/**
 * Created with IntelliJ IDEA.
 * User: gaodj
 * Date: 14-1-8
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class IvrGrpNumCti implements java.io.Serializable {
    private String deptId;
    private String acdId;
    private String areaCode;
    private String showAllPhone;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getAcdId() {
        return acdId;
    }

    public void setAcdId(String acdId) {
        this.acdId = acdId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getShowAllPhone() {
        return showAllPhone;
    }

    public void setShowAllPhone(String showAllPhone) {
        this.showAllPhone = showAllPhone;
    }
}
