package com.chinadrtv.erp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 承运商基础资料(OMS).
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "COMPANY_CONTRACT", schema = "ACOAPP_OMS")
public class CompanyContract implements java.io.Serializable {

    private Integer id;                     //承运商二级ID
    private String name;                   //承运商二级名称
    private String companyType;           //承运商一级类型
    private String nccompanyId;           //NC承运商
    private String nccompanyName;         //NC承运商名

    private String mailType;            //干线运输/落地配
    private CompanyContract parent;     //上级合约
    private Warehouse warehouse;        //默认发货仓库
    private OrderChannel channel;       //订单渠道
    private Integer status;             //状态
    private Boolean isManual;           //是否手工挑单
    private Integer freightMethod;     //运费计算方式(按金额/重量计算)

    private Date beginDate;             //开始日期
    private Date endDate;              //结束日期

    private Double marginRatio;
    private Double performanceBondAmount;    //定金
    private Double assertMarginAmount;
    private Double creditMarginAmount;       //信用控制

    private String informEmail;         //承运商负责人邮件列表
    private String optsEmail;           //承运商运营邮件列表
    private String acornInformEmail;   //橡果负责人邮件列表
    private String acornOptsEmail;     //想国运营邮件列表

    private Integer matchingRatio;     //配比系数（1-10分）
    private Integer carryCapacity;   //配送能力（最大单量）

    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private BigDecimal amountCapacity;//配送能力（最大金额）
    //新增字段
    private Integer packTempLateId; //发包清单模板编号
    private String suretyType;      //担保形式(names.tid=Surety_Type）
    private String distributionRegion;  //配送区域（names.tid=Distribution_Region）
    private Double dailyAmount;     //平均每日发包金额
    private String riskEMAIL;   //保证额度超标邮件列表
    private String riskOptsEmail;   //保证额度超标可选列表
    private Double actualRiskFactor;    //实际风险系数(BI)
    private Double deliverySuccessRate; //送货成功率(BI)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_CONTRACT_SEQ")
    @SequenceGenerator(name = "COMPANY_CONTRACT_SEQ", sequenceName = "ACOAPP_OMS.COMPANY_CONTRACT_SEQ")
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MAIL_TYPE")
    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public CompanyContract getParent() {
        return parent;
    }

    public void setParent(CompanyContract parent) {
        this.parent = parent;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WAREHOUSE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANNEL_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public OrderChannel getChannel() {
        return channel;
    }

    public void setChannel(OrderChannel channel) {
        this.channel = channel;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "IS_MANUAL")
    public Boolean getIsManual() {
        return isManual;
    }

    public void setIsManual(Boolean isManual) {
        this.isManual = isManual;
    }

    @Column(name = "INFORM_EMAIL")
    public String getInformEmail() {
        return informEmail;
    }

    public void setInformEmail(String informEmail) {
        this.informEmail = informEmail;
    }

    @Column(name = "OPTS_EMAIL")
    public String getOptsEmail() {
        return optsEmail;
    }

    public void setOptsEmail(String optsEmail) {
        this.optsEmail = optsEmail;
    }

    @Column(name = "ACORN_INFORM_EMAIL")
    public String getAcornInformEmail() {
        return acornInformEmail;
    }

    public void setAcornInformEmail(String acornInformEmail) {
        this.acornInformEmail = acornInformEmail;
    }

    @Column(name = "ACORN_OPTS_EMAIL")
    public String getAcornOptsEmail() {
        return acornOptsEmail;
    }

    public void setAcornOptsEmail(String acornOptsEmail) {
        this.acornOptsEmail = acornOptsEmail;
    }

    @Column(name = "COMPANY_TYPE")
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Column(name = "NCCOMPANY_ID")
    public String getNccompanyId() {
        return nccompanyId;
    }

    public void setNccompanyId(String nccompanyId) {
        this.nccompanyId = nccompanyId;
    }

    @Column(name = "NCCOMPANY_NAME")
    public String getNccompanyName() {
        return nccompanyName;
    }

    public void setNccompanyName(String nccompanyName) {
        this.nccompanyName = nccompanyName;
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

    @Column(name = "MARGIN_RATIO")
    public Double getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(Double marginRatio) {
        this.marginRatio = marginRatio;
    }

    @Column(name = "PERFORMANCE_BOND_AMOUNT")
    public Double getPerformanceBondAmount() {
        return performanceBondAmount;
    }

    public void setPerformanceBondAmount(Double performanceBondAmount) {
        this.performanceBondAmount = performanceBondAmount;
    }

    @Column(name = "ASSERT_MARGIN_AMOUNT")
    public Double getAssertMarginAmount() {
        return assertMarginAmount;
    }

    public void setAssertMarginAmount(Double assertMarginAmount) {
        this.assertMarginAmount = assertMarginAmount;
    }

    @Column(name = "CREDIT_MARGIN_AMOUNT")
    public Double getCreditMarginAmount() {
        return creditMarginAmount;
    }

    public void setCreditMarginAmount(Double creditMarginAmount) {
        this.creditMarginAmount = creditMarginAmount;
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

    @Column(name = "FREIGHT_METHOD")
    public Integer getFreightMethod() {
        return freightMethod;
    }

    public void setFreightMethod(Integer freightMethod) {
        this.freightMethod = freightMethod;
    }

    @Column(name = "MATCHING_RATIO")
    public Integer getMatchingRatio() {
        return matchingRatio;
    }

    public void setMatchingRatio(Integer matchingRatio) {
        this.matchingRatio = matchingRatio;
    }

    @Column(name = "CARRY_CAPACITY")
    public Integer getCarryCapacity() {
        return carryCapacity;
    }

    public void setCarryCapacity(Integer carryCapacity) {
        this.carryCapacity = carryCapacity;
    }

    @Column(name = "Pack_TEMPLATE_ID")
    public Integer getPackTempLateId() {
        return packTempLateId;
    }

    public void setPackTempLateId(Integer packTempLateId) {
        this.packTempLateId = packTempLateId;
    }
    @Column(name = "Surety_Type")
    public String getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(String suretyType) {
        this.suretyType = suretyType;
    }
    @Column(name = "Distribution_Region")
    public String getDistributionRegion() {
        return distributionRegion;
    }

    public void setDistributionRegion(String distributionRegion) {
        this.distributionRegion = distributionRegion;
    }
    @Column(name = "Daily_Amount")
    public Double getDailyAmount() {
        return dailyAmount;
    }

    public void setDailyAmount(Double dailyAmount) {
        this.dailyAmount = dailyAmount;
    }
    @Column(name = "Risk_EMAIL")
    public String getRiskEMAIL() {
        return riskEMAIL;
    }

    public void setRiskEMAIL(String riskEMAIL) {
        this.riskEMAIL = riskEMAIL;
    }
    @Column(name = "Risk_OPTS_EMAIL")
    public String getRiskOptsEmail() {
        return riskOptsEmail;
    }

    public void setRiskOptsEmail(String riskOptsEmail) {
        this.riskOptsEmail = riskOptsEmail;
    }
    @Column(name = "amount_capacity")
    public BigDecimal getAmountCapacity() {
        return amountCapacity;
    }

    public void setAmountCapacity(BigDecimal amountCapacity) {
        this.amountCapacity = amountCapacity;
    }
    @Column(name = "actual_risk_factor")
    public Double getActualRiskFactor() {
        return actualRiskFactor;
    }

    public void setActualRiskFactor(Double actualRiskFactor) {
        this.actualRiskFactor = actualRiskFactor;
    }
    @Column(name = "Delivery_success_rate")
    public Double getDeliverySuccessRate() {
        return deliverySuccessRate;
    }

    public void setDeliverySuccessRate(Double deliverySuccessRate) {
        this.deliverySuccessRate = deliverySuccessRate;
    }

    @Override
    public String toString() {
        return id!=null?id.toString():"0";
    }
}
