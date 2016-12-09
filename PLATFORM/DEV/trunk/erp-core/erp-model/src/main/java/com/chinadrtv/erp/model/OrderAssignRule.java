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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

/**    分拣分配规则表-核心表，确定最终的“送货公司”和“发货仓库”
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "OMS_DELIVERY_REGULATION", schema = "ACOAPP_OMS")
public class OrderAssignRule implements java.io.Serializable{

    private static final long serialVersionUID = -1913516578741003646L;

    private Long id;

    private String name;

    private AreaGroup areaGroup;

    private OrderChannel orderChannel;

    private Boolean stockFlag;

    private Long entityId;

    private Long warehouseId;

    private String warehouseName;

    private Long secondEntityId;

    private Long secondWarehouseId;

    private String secondWarehouseName;

    private Long priority;

    private Date startDate;

    private Date endDate;

    private String crUser;

    private Date crDT;

    private String mdUser;

    private Date mdDT;

    private Boolean isActive;

    private String userDef1;

    private String userDef2;

    private String userDef3;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private String prodCode;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ASSIGN_RULE_SEQ")
    @SequenceGenerator(name = "ORDER_ASSIGN_RULE_SEQ", sequenceName = "ACOAPP_OMS.ORDER_ASSIGN_RULE_SEQ")
    @Column(name = "REGULATION_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "REGULATION_DSC", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANNEL_ID")
    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(OrderChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AREA_GROUP_ID")
    public AreaGroup getAreaGroup() {
        return areaGroup;
    }

    public void setAreaGroup(AreaGroup areaGroup) {
        this.areaGroup = areaGroup;
    }



    @Column(name = "STOCKFALG")
    @Type(type="yes_no")
    public Boolean isStockFlag() {
        return stockFlag;
    }

    public void setStockFlag(Boolean stockFlag) {
        this.stockFlag = stockFlag;
    }

    @Column(name = "COMPANY_ID")
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Column(name = "WAREHOUSE_ID")
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Column(name = "WAREHOUSE_NAME", length = 50)
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Column(name = "SECOND_COMPANY_ID")
    public Long getSecondEntityId() {
        return secondEntityId;
    }

    public void setSecondEntityId(Long secondEntityId) {
        this.secondEntityId = secondEntityId;
    }

    @Column(name = "SECOND_WAREHOUSE_ID")
    public Long getSecondWarehouseId() {
        return secondWarehouseId;
    }

    public void setSecondWarehouseId(Long secondWarehouseId) {
        this.secondWarehouseId = secondWarehouseId;
    }

    @Column(name = "SECOND_WAREHOUSE_NAME", length = 50)
    public String getSecondWarehouseName() {
        return secondWarehouseName;
    }

    public void setSecondWarehouseName(String secondWarehouseName) {
        this.secondWarehouseName = secondWarehouseName;
    }

    @Column(name = "PRIORITY")
    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Column(name = "START_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "END_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "CRUSER", length = 10)
    public String getCrUser() {
        return crUser;
    }

    public void setCrUser(String crUser) {
        this.crUser = crUser;
    }

    @Column(name = "CRDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrDT() {
        return crDT;
    }

    public void setCrDT(Date crDT) {
        this.crDT = crDT;
    }

    @Column(name = "MDUSER", length = 10)
    public String getMdUser() {
        return mdUser;
    }

    public void setMdUser(String mdUser) {
        this.mdUser = mdUser;
    }

    @Column(name = "MDDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMdDT() {
        return mdDT;
    }

    public void setMdDT(Date mdDT) {
        this.mdDT = mdDT;
    }

    @Column(name = "IS_ACTIVE")
    @Type(type = "yes_no")
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "USER_DEF1", length = 16)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USER_DEF2", length = 16)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USER_DEF3", length = 16)
    public String getUserDef3() {
        return userDef3;
    }

    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }

    @Column(name = "PROD_CODE", length = 50)
    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    @Column(name = "MIN_AMOUNT", precision = 10, scale = 2)
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    @Column(name = "MAX_AMOUNT", precision = 10, scale = 2)
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

}
