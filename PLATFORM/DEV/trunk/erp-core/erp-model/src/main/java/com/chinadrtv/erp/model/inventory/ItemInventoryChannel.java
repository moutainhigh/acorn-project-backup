package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * User: gaodejian Date: 13-1-28 Time: 下午2:50 To
 * change this template use File | Settings | File Templates.
 */
@Table(name = "ITEM_INVENTORY_CHANNEL", schema = "ACOAPP_OMS")
@Entity
public class ItemInventoryChannel implements java.io.Serializable {
	private Long id;
	private String channel;
	private String warehouse;
	private String locationType;
	private Long productId;
	private String productCode;
	private Double onHandQty;
	private Double frozenQty;
	private Double allocatedQty;
    private Double allocatedDistributionQty;
    private Double allocatedOtherQty;
	private Double inTransitPurchaseQty;
	private Double inTransitMovementQty;
	private Double inTransitOtherQty;
	private String createdBy;
	private String modifiedBy;
	private Date created;
	private Date modified;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INVENTORY_CHANNEL_SEQ")
	@SequenceGenerator(name = "ITEM_INVENTORY_CHANNEL_SEQ", sequenceName = "ACOAPP_OMS.ITEM_INVENTORY_CHANNEL_SEQ" , allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	@Column(name = "CHANNEL")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "WAREHOUSE")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "LOCATION_TYPE")
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	@Column(name = "PRODUCT_ID")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_CODE")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name = "ON_HAND_QTY")
	public Double getOnHandQty() {
		return onHandQty;
	}

	public void setOnHandQty(Double onHandQty) {
		this.onHandQty = onHandQty;
	}

	@Column(name = "FROZEN_QTY")
	public Double getFrozenQty() {
		return frozenQty;
	}

	public void setFrozenQty(Double frozenQty) {
		this.frozenQty = frozenQty;
	}

	@Column(name = "ALLOCATED_QTY")
	public Double getAllocatedQty() {
		return allocatedQty;
	}

	public void setAllocatedQty(Double allocatedQty) {
		this.allocatedQty = allocatedQty;
	}

    @Column(name = "ALLOCATED_DISTRIBUTION_QTY")
    public Double getAllocatedDistributionQty() {
        return allocatedDistributionQty;
    }

    public void setAllocatedDistributionQty(Double allocatedDistributionQty) {
        this.allocatedDistributionQty = allocatedDistributionQty;
    }

    @Column(name = "ALLOCATED_OTHER_QTY")
    public Double getAllocatedOtherQty() {
        return allocatedOtherQty;
    }

    public void setAllocatedOtherQty(Double allocatedOtherQty) {
        this.allocatedOtherQty = allocatedOtherQty;
    }

	@Column(name = "IN_TRANSIT_PURCHASE_QTY")
	public Double getInTransitPurchaseQty() {
		return inTransitPurchaseQty;
	}

	public void setInTransitPurchaseQty(Double inTransitPurchaseQty) {
		this.inTransitPurchaseQty = inTransitPurchaseQty;
	}

	@Column(name = "IN_TRANSIT_MOVEMENT_QTY")
	public Double getInTransitMovementQty() {
		return inTransitMovementQty;
	}

	public void setInTransitMovementQty(Double inTransitMovementQty) {
		this.inTransitMovementQty = inTransitMovementQty;
	}

	@Column(name = "IN_TRANSIT_OTHER_QTY")
	public Double getInTransitOtherQty() {
		return inTransitOtherQty;
	}

	public void setInTransitOtherQty(Double inTransitOtherQty) {
		this.inTransitOtherQty = inTransitOtherQty;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "MODIFIED")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param channel
	 * @param warehouse
	 * @param locationType
	 * @param productId
	 */
	public ItemInventoryChannel(String channel, String warehouse,
			String locationType, Long productId) {
		super();
		this.channel = channel;
		this.warehouse = warehouse;
		this.locationType = locationType;
		this.productId = productId;
	}

	/**
	 * 库存渠道构造函数
	 * 
	 * @param channel
	 * @param warehouse
	 * @param locationType
	 * @param productId
	 * @param productCode
	 */
	public ItemInventoryChannel(String channel, String warehouse,
			String locationType, Long productId, String productCode) {
		super();
		this.channel = channel;
		this.warehouse = warehouse;
		this.locationType = locationType;
		this.productId = productId;
		this.productCode = productCode;
	}

	/*
	 * (非 Javadoc) <p>Title: toString</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return channel + warehouse + locationType + productId;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public ItemInventoryChannel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
