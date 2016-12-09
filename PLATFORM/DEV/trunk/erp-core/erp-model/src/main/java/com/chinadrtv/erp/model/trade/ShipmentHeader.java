package com.chinadrtv.erp.model.trade;

import com.chinadrtv.erp.model.agent.OrderHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 发运单
 * User: 徐志凯
 * Date: 13-2-4
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "SHIPMENT_HEADER", schema = "ACOAPP_OMS")
@Entity
public class ShipmentHeader implements java.io.Serializable{
   
	private static final long serialVersionUID = -6770558548684024599L;

	/**
     * 内部ID
     */
    private Long id;

    /**
     * 发运单号
     */
    private String shipmentId;

    /**
     * 发运单记账类型（正向单，红冲单）
     */
    private String accountType;


    /**
     * 订单历史表头外键
     */
    //private Long orderRefHisId;

    private OrderHistory orderHistory;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 版本
     */
    private Long revision;

    /**
     * 对应订单版本
     */
    private Long orderRefRevisionId;

    /**
     * 邮件编号
     */
    private String mailId;

    /**
     * 送货公司
     */
    private String entityId;

    /**
     * 创建人
     */
    private String crusr;

    /**
     * 修改人
     */
    private String mdusr;

    /**
     * 修改日期
     */
    private Date mddt;

    /**
     * 订单结算反馈状态
     */
    private String accountStatusId;

    /**
     * 结算状态描述
     */
    private String accountStatusRemark;

    /**
     * 创建日期
     */
    private Date crdt;

    /**
     * 交寄日期
     */
    private Date senddt;

    /**
     * 反馈日期
     */
    private Date fbdt;

    /**
     * 放单日期
     */
    private Date outdt;

    /**
     * 结帐日期
     */
    private Date accdt;

    /**
     * 订单物流节点状态
     */
    private String logisticsStatusId;

    /**
     * 物流状态描述
     */
    private String logisticsStatusRemark;

    /**
     * 发货时间
     */
    private Date rfoutdat;

    /**
     * （航空禁运，易碎,是否计泡件等）
     */
    private String itemFlag;

    /**
     * 货主
     */
    private String owner;

    /**
     * 体积
     */
    private String volume;

    /**
     * 包裹重量
     */
    private String weight;

    /**
     * 发货仓库
     */
    private String warehouseId;

    /*
     * 合计金额(OrderHistory.totalPrice)
     */
    private BigDecimal totalPrice;
    /*
      临时对象
     */
    @Transient
    private String additionalMemo;
  
    private Long associatedId;
    private BigDecimal prodPrice;
    private BigDecimal mailPrice;
    private BigDecimal postFee1;
    private BigDecimal postFee2;
    private BigDecimal postFee3;
    private BigDecimal clearFee;
    private Integer reconcilFlag;
    private String reconcilUser;
    private Date reconcilDate;
    private BigDecimal clearPostFee;
    private String account;
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_HEADER_SEQ")
    @SequenceGenerator(name = "SHIPMENT_HEADER_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_HEADER_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPMENT_ID", length = 16)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "ACCOUNT_TYPE", length = 16)
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /*@Column(name = "ORDER_REF_HIS_ID", length = 16)
    public Long getOrderRefHisId() {
        return orderRefHisId;
    }

    public void setOrderRefHisId(Long orderRefHisId) {
        this.orderRefHisId = orderRefHisId;
    }*/

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_REF_HIS_ID")
    @JsonIgnore
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }


    @Column(name = "ORDER_ID", length = 16)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "REVISION", length = 16)
    @Version
    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    @Column(name = "ORDER_REF_REVISION")
    @NotNull
    public Long getOrderRefRevisionId() {
        return orderRefRevisionId;
    }

    public void setOrderRefRevisionId(Long orderRefRevisionId) {
        this.orderRefRevisionId = orderRefRevisionId;
    }

    @Column(name = "MAIL_ID", length = 20)
    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    @Column(name = "ENTITY_ID", length = 16)
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Column(name = "CRUSR", length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "MDDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "ACCOUNT_STATUS_ID", length = 10)
    public String getAccountStatusId() {
        return accountStatusId;
    }

    public void setAccountStatusId(String accountStatusId) {
        this.accountStatusId = accountStatusId;
    }

    @Column(name = "ACCOUNT_STATUS_REMARK", length = 100)
    public String getAccountStatusRemark() {
        return accountStatusRemark;
    }

    public void setAccountStatusRemark(String accountStatusRemark) {
        this.accountStatusRemark = accountStatusRemark;
    }

    @Column(name = "CRDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "SENDDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSenddt() {
        return senddt;
    }

    public void setSenddt(Date senddt) {
        this.senddt = senddt;
    }

    @Column(name = "FBDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFbdt() {
        return fbdt;
    }

    public void setFbdt(Date fbdt) {
        this.fbdt = fbdt;
    }

    @Column(name = "OUTDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOutdt() {
        return outdt;
    }

    public void setOutdt(Date outdt) {
        this.outdt = outdt;
    }

    @Column(name = "ACCDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAccdt() {
        return accdt;
    }

    public void setAccdt(Date accdt) {
        this.accdt = accdt;
    }

    @Column(name = "LOGISTICS_STATUS_ID", length = 10)
    public String getLogisticsStatusId() {
        return logisticsStatusId;
    }

    public void setLogisticsStatusId(String logisticsStatusId) {
        this.logisticsStatusId = logisticsStatusId;
    }

    @Column(name = "LOGISTICS_STATUS_REMARK", length = 100)
    public String getLogisticsStatusRemark() {
        return logisticsStatusRemark;
    }

    public void setLogisticsStatusRemark(String logisticsStatusRemark) {
        this.logisticsStatusRemark = logisticsStatusRemark;
    }

    @Column(name = "RFOUTDAT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRfoutdat() {
        return rfoutdat;
    }

    public void setRfoutdat(Date rfoutdat) {
        this.rfoutdat = rfoutdat;
    }

    @Column(name = "ITEM_FLAG", length = 10)
    public String getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(String itemFlag) {
        this.itemFlag = itemFlag;
    }

    @Column(name = "OWNER", length = 10)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "VOLUME", length = 10)
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Column(name = "WEIGHT", length = 10)
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Column(name = "WAREHOUSE_ID", length = 10)
    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Formula("(0)")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Transient
    public String getAdditionalMemo() {
        return additionalMemo;
    }

    public void setAdditionalMemo(String additionalMemo) {
        this.additionalMemo = additionalMemo;
    }

    private Set<ShipmentDetail> shipmentDetails=new HashSet<ShipmentDetail>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shipmentHeader")
    public Set<ShipmentDetail> getShipmentDetails() {
        return shipmentDetails;
    }

    public void setShipmentDetails(Set<ShipmentDetail> shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }

    @Column(name = "ASSOCIATED_ID")
	public Long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	@Column(name = "PROD_PRICE", precision = 15, scale = 2)
	public BigDecimal getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(BigDecimal prodPrice) {
		this.prodPrice = prodPrice;
	}

	@Column(name = "MAIL_PRICE", precision = 15, scale = 2)
	public BigDecimal getMailPrice() {
		return mailPrice;
	}

	public void setMailPrice(BigDecimal mailPrice) {
		this.mailPrice = mailPrice;
	}

	@Column(name = "POST_FEE1", precision = 15, scale = 2)
	public BigDecimal getPostFee1() {
		return postFee1;
	}

	public void setPostFee1(BigDecimal postFee1) {
		this.postFee1 = postFee1;
	}

	@Column(name = "POST_FEE2", precision = 15, scale = 2)
	public BigDecimal getPostFee2() {
		return postFee2;
	}

	public void setPostFee2(BigDecimal postFee2) {
		this.postFee2 = postFee2;
	}

    @Column(name = "POST_FEE3", precision = 15, scale = 2)
    public BigDecimal getPostFee3() {
        return postFee3;
    }

    public void setPostFee3(BigDecimal postFee3) {
        this.postFee3 = postFee3;
    }

	@Column(name = "CLEAR_FEE", precision = 15, scale = 2)
	public BigDecimal getClearFee() {
		return clearFee;
	}

	public void setClearFee(BigDecimal clearFee) {
		this.clearFee = clearFee;
	}

	@Column(name = "RECONCIL_FLAG")
	public Integer getReconcilFlag() {
		return reconcilFlag;
	}

	public void setReconcilFlag(Integer reconcilFlag) {
		this.reconcilFlag = reconcilFlag;
	}

	@Column(name = "RECONCIL_USER", length=20)
	public String getReconcilUser() {
		return reconcilUser;
	}

	public void setReconcilUser(String reconcilUser) {
		this.reconcilUser = reconcilUser;
	}

	@Column(name = "RECONCIL_DATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getReconcilDate() {
		return reconcilDate;
	}

	public void setReconcilDate(Date reconcilDate) {
		this.reconcilDate = reconcilDate;
	}

	@Column(name = "CLEAR_POST_FEE", precision = 12, scale=2)
	public BigDecimal getClearPostFee() {
		return clearPostFee;
	}

	public void setClearPostFee(BigDecimal clearPostFee) {
		this.clearPostFee = clearPostFee;
	}

    @Column(name = "ACCOUNT", length=20)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accdt == null) ? 0 : accdt.hashCode());
		result = prime * result
				+ ((accountStatusId == null) ? 0 : accountStatusId.hashCode());
		result = prime
				* result
				+ ((accountStatusRemark == null) ? 0 : accountStatusRemark
						.hashCode());
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((additionalMemo == null) ? 0 : additionalMemo.hashCode());
		result = prime * result
				+ ((associatedId == null) ? 0 : associatedId.hashCode());
		result = prime * result
				+ ((clearFee == null) ? 0 : clearFee.hashCode());
		result = prime * result
				+ ((clearPostFee == null) ? 0 : clearPostFee.hashCode());
		result = prime * result + ((crdt == null) ? 0 : crdt.hashCode());
		result = prime * result + ((crusr == null) ? 0 : crusr.hashCode());
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result + ((fbdt == null) ? 0 : fbdt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemFlag == null) ? 0 : itemFlag.hashCode());
		result = prime
				* result
				+ ((logisticsStatusId == null) ? 0 : logisticsStatusId
						.hashCode());
		result = prime
				* result
				+ ((logisticsStatusRemark == null) ? 0 : logisticsStatusRemark
						.hashCode());
		result = prime * result + ((mailId == null) ? 0 : mailId.hashCode());
		result = prime * result
				+ ((mailPrice == null) ? 0 : mailPrice.hashCode());
		result = prime * result + ((mddt == null) ? 0 : mddt.hashCode());
		result = prime * result + ((mdusr == null) ? 0 : mdusr.hashCode());
		/*result = prime * result
				+ ((orderHistory == null) ? 0 : orderHistory.hashCode());*/
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime
				* result
				+ ((orderRefRevisionId == null) ? 0 : orderRefRevisionId
						.hashCode());
		result = prime * result + ((outdt == null) ? 0 : outdt.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((postFee1 == null) ? 0 : postFee1.hashCode());
		result = prime * result
				+ ((postFee2 == null) ? 0 : postFee2.hashCode());
		result = prime * result
				+ ((prodPrice == null) ? 0 : prodPrice.hashCode());
		result = prime * result
				+ ((reconcilDate == null) ? 0 : reconcilDate.hashCode());
		result = prime * result
				+ ((reconcilFlag == null) ? 0 : reconcilFlag.hashCode());
		result = prime * result
				+ ((reconcilUser == null) ? 0 : reconcilUser.hashCode());
		result = prime * result
				+ ((revision == null) ? 0 : revision.hashCode());
        result = prime * result
                + ((account == null) ? 0 : account.hashCode());
		result = prime * result
				+ ((rfoutdat == null) ? 0 : rfoutdat.hashCode());
		result = prime * result + ((senddt == null) ? 0 : senddt.hashCode());
		result = prime * result
				+ ((shipmentDetails == null) ? 0 : shipmentDetails.hashCode());
		result = prime * result
				+ ((shipmentId == null) ? 0 : shipmentId.hashCode());
		result = prime * result
				+ ((totalPrice == null) ? 0 : totalPrice.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		result = prime * result
				+ ((warehouseId == null) ? 0 : warehouseId.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentHeader other = (ShipmentHeader) obj;
		if (accdt == null) {
			if (other.accdt != null)
				return false;
		} else if (!accdt.equals(other.accdt))
			return false;
		if (accountStatusId == null) {
			if (other.accountStatusId != null)
				return false;
		} else if (!accountStatusId.equals(other.accountStatusId))
			return false;
		if (accountStatusRemark == null) {
			if (other.accountStatusRemark != null)
				return false;
		} else if (!accountStatusRemark.equals(other.accountStatusRemark))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (additionalMemo == null) {
			if (other.additionalMemo != null)
				return false;
		} else if (!additionalMemo.equals(other.additionalMemo))
			return false;
		if (associatedId == null) {
			if (other.associatedId != null)
				return false;
		} else if (!associatedId.equals(other.associatedId))
			return false;
		if (clearFee == null) {
			if (other.clearFee != null)
				return false;
		} else if (!clearFee.equals(other.clearFee))
			return false;
		if (clearPostFee == null) {
			if (other.clearPostFee != null)
				return false;
		} else if (!clearPostFee.equals(other.clearPostFee))
			return false;
		if (crdt == null) {
			if (other.crdt != null)
				return false;
		} else if (!crdt.equals(other.crdt))
			return false;
		if (crusr == null) {
			if (other.crusr != null)
				return false;
		} else if (!crusr.equals(other.crusr))
			return false;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (fbdt == null) {
			if (other.fbdt != null)
				return false;
		} else if (!fbdt.equals(other.fbdt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemFlag == null) {
			if (other.itemFlag != null)
				return false;
		} else if (!itemFlag.equals(other.itemFlag))
			return false;
		if (logisticsStatusId == null) {
			if (other.logisticsStatusId != null)
				return false;
		} else if (!logisticsStatusId.equals(other.logisticsStatusId))
			return false;
		if (logisticsStatusRemark == null) {
			if (other.logisticsStatusRemark != null)
				return false;
		} else if (!logisticsStatusRemark.equals(other.logisticsStatusRemark))
			return false;
		if (mailId == null) {
			if (other.mailId != null)
				return false;
		} else if (!mailId.equals(other.mailId))
			return false;
		if (mailPrice == null) {
			if (other.mailPrice != null)
				return false;
		} else if (!mailPrice.equals(other.mailPrice))
			return false;
		if (mddt == null) {
			if (other.mddt != null)
				return false;
		} else if (!mddt.equals(other.mddt))
			return false;
		if (mdusr == null) {
			if (other.mdusr != null)
				return false;
		} else if (!mdusr.equals(other.mdusr))
			return false;
		/*if (orderHistory == null) {
			if (other.orderHistory != null)
				return false;
		}*/ else if (!orderHistory.equals(other.orderHistory))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderRefRevisionId == null) {
			if (other.orderRefRevisionId != null)
				return false;
		} else if (!orderRefRevisionId.equals(other.orderRefRevisionId))
			return false;
		if (outdt == null) {
			if (other.outdt != null)
				return false;
		} else if (!outdt.equals(other.outdt))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (postFee1 == null) {
			if (other.postFee1 != null)
				return false;
		} else if (!postFee1.equals(other.postFee1))
			return false;
		if (postFee2 == null) {
			if (other.postFee2 != null)
				return false;
		} else if (!postFee2.equals(other.postFee2))
			return false;
		if (prodPrice == null) {
			if (other.prodPrice != null)
				return false;
		} else if (!prodPrice.equals(other.prodPrice))
			return false;
		if (reconcilDate == null) {
			if (other.reconcilDate != null)
				return false;
		} else if (!reconcilDate.equals(other.reconcilDate))
			return false;
		if (reconcilFlag == null) {
			if (other.reconcilFlag != null)
				return false;
		} else if (!reconcilFlag.equals(other.reconcilFlag))
			return false;
		if (reconcilUser == null) {
			if (other.reconcilUser != null)
				return false;
		} else if (!reconcilUser.equals(other.reconcilUser))
			return false;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		if (rfoutdat == null) {
			if (other.rfoutdat != null)
				return false;
		} else if (!rfoutdat.equals(other.rfoutdat))
			return false;
		if (senddt == null) {
			if (other.senddt != null)
				return false;
		} else if (!senddt.equals(other.senddt))
			return false;
		if (shipmentDetails == null) {
			if (other.shipmentDetails != null)
				return false;
		} else if (!shipmentDetails.equals(other.shipmentDetails))
			return false;
		if (shipmentId == null) {
			if (other.shipmentId != null)
				return false;
		} else if (!shipmentId.equals(other.shipmentId))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		if (warehouseId == null) {
			if (other.warehouseId != null)
				return false;
		} else if (!warehouseId.equals(other.warehouseId))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

}
