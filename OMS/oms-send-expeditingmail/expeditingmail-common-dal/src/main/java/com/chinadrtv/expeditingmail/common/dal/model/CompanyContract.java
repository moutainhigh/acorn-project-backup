package com.chinadrtv.expeditingmail.common.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-7-23
 * Time: 上午10:51
 * To change this template use File | Settings | File Templates.
 * 承运商邮件列表VO
 */
public class CompanyContract implements java.io.Serializable {
    private Integer id;         //承运商Id
    private String name;         //承运商名称
    private String informEmail;         //承运商负责人邮件列表
    private String optsEmail;           //承运商运营邮件列表
    private String acornInformEmail;   //橡果负责人邮件列表
    private String acornOptsEmail;     //想国运营邮件列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
