package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 运费规则配置
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "LOGISTICS_FEE_RULE", schema = "ACOAPP_OMS")
public class LogisticsFeeRule implements java.io.Serializable {

    private Integer id;                  //标识
    private CompanyContract contract;   //二级承运商ID(合约)
    private Integer version;            //版本号
    private Integer status;             //状态(启用/停用)
    private Date beginDate;            //开始时间
    private Date endDate;              //结束时间
    private String createUser;        //创建人
    private Date createDate;          //创建时间
    private String updateUser;        //更新人
    private Date updateDate;          //更新时间

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGISTICS_FEE_RULE_SEQ")
    @SequenceGenerator(name = "LOGISTICS_FEE_RULE_SEQ", sequenceName = "ACOAPP_OMS.LOGISTICS_FEE_RULE_SEQ")
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_ID")
    public CompanyContract getContract() {
        return contract;
    }

    public void setContract(CompanyContract contract) {
        this.contract = contract;
    }

    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "BEGIN_DATE")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
