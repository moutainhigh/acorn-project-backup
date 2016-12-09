package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * User: gaodejian Date: 13-1-28 Time: 下午2:50 To
 * change this template use File | Settings | File Templates.
 */
@Table(name = "ITEM_INVENTORY_SNAPSHOT", schema = "ACOAPP_OMS")
@Entity
public class ItemInventorySnapshot implements java.io.Serializable {
	private Long id; // NUMBER(19) not null,
    private Long snapshotId;
    private Date snapshotDate;  //快照日期
    private String snapshotBy;
    private Long refId;
	private String channel; // VARCHAR2(50) not null,
	private String warehouse; // VARCHAR2(50) not null,
	private String locationType; // VARCHAR2(50) not null,
	private Long productId; // NUMBER(19) not null,
	private String productCode; // VARCHAR2(50) not null,
	private Double onHandQty; // NUMBER(16,4),
	private Double frozenQty; // NUMBER(16,4),
	private Double allocatedQty; // NUMBER(16,4),
    private Double allocatedDistributionQty;
    private Double allocatedOtherQty;
	private Double inTransitPurchaseQty; // NUMBER(16,4),
	private Double inTransitMovementQty; // NUMBER(16,4),
	private Double inTransitOtherQty; // NUMBER(16,4),
	private String createdBy; // VARCHAR2(50) not null,
	private String modifiedBy; // VARCHAR2(50) not null,
	private Date created; // DATE,
	private Date modified; // DATE


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INVENTORY_SNAPSHOT_SEQ")
	@SequenceGenerator(name = "ITEM_INVENTORY_SNAPSHOT_SEQ", sequenceName = "ACOAPP_OMS.ITEM_INVENTORY_SNAPSHOT_SEQ", allocationSize = 1)
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

    @Column(name = "SNAPSHOT_DATE")
    public Date getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(Date snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    @Column(name = "REF_ID")
    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    @Column(name = "SNAPSHOT_ID")
    public Long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
    }

    @Column(name = "SNAPSHOT_BY")
    public String getSnapshotBy() {
        return snapshotBy;
    }

    public void setSnapshotBy(String snapshotBy) {
        this.snapshotBy = snapshotBy;
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
	public ItemInventorySnapshot(String channel, String warehouse,
                                 String locationType, Long productId) {
		super();
		this.channel = channel;
		this.warehouse = warehouse;
		this.locationType = locationType;
		this.productId = productId;
	}

    /**
     * 库存渠道构造函数
     * @param channel
     * @param warehouse
     * @param locationType
     * @param productId
     * @param productCode
     */
    public ItemInventorySnapshot(String channel, String warehouse,
                                 String locationType, Long productId, String productCode) {
        super();
        this.channel = channel;
        this.warehouse = warehouse;
        this.locationType = locationType;
        this.productId = productId;
        this.productCode = productCode;
    }

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public ItemInventorySnapshot() {
		super();
		// TODO Auto-generated constructor stub
	}



}
