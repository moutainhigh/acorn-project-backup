package com.chinadrtv.remindmail.common.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-7-23
 * Time: 上午10:51
 * To change this template use File | Settings | File Templates.
 * 承运商邮件列表VO
 */
public class CompanyContract implements java.io.Serializable {
    private String companyName;     //送货公司名称
    private String companyId;         //送货公司Id
    private String informEmail;         //承运商负责人邮件列表
    private String optsEmail;           //承运商运营邮件列表
    private String acornInformEmail;   //橡果负责人邮件列表
    private String acornOptsEmail;     //想国运营邮件列表

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInformEmail() {
        return informEmail;
    }

    public void setInformEmail(String informEmail) {
        this.informEmail = informEmail;
    }

    public String getOptsEmail() {
        return optsEmail;
    }

    public void setOptsEmail(String optsEmail) {
        this.optsEmail = optsEmail;
    }

    public String getAcornInformEmail() {
        return acornInformEmail;
    }

    public void setAcornInformEmail(String acornInformEmail) {
        this.acornInformEmail = acornInformEmail;
    }

    public String getAcornOptsEmail() {
        return acornOptsEmail;
    }

    public void setAcornOptsEmail(String acornOptsEmail) {
        this.acornOptsEmail = acornOptsEmail;
    }
}
