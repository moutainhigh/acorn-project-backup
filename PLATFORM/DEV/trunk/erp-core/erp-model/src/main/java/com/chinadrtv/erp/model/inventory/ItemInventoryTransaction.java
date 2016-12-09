package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * User: gaodejian Date: 13-1-28 Time: 下午2:51 To
 * change this template use File | Settings | File Templates.
 */
@Table(name = "ITEM_INVENTORY_TRANSACTION", schema = "ACOAPP_OMS")
@Entity
public class ItemInventoryTransaction implements java.io.Serializable {
	private Long id;
	private String channel;
	private String warehouse;
	private String locationType;
    private String channelEx;
    private String warehouseEx;
    private String locationTypeEx;
	private String businessType;
	private String businessChildType;
	private String businessNo;
	private Date businessDate;
	private Long productId;
	private String productCode;
	private String lotNo;
	private Double estimatedQty;
	private Double qty;
    private Double onHandQty;
    private Double frozenQty;
    private Double allocatedQty;
    private Double allocatedDistributionQty;
    private Double allocatedOtherQty;
    private Double inTransitPurchaseQty;
    private Double inTransitMovementQty;
    private Double inTransitOtherQty;
	private String referenceNo;
	private Long sourceId;
	private String sourceDesc;
	private String createdBy;
	private String modifiedBy;
	private Date created;
	private Date modified;
	private String batchId;
	private Date batchDate;
	private String batchBy;

    public ItemInventoryTransaction(){
        super();
        setEstimatedQty(0.0);
        setQty(0.0);
        setOnHandQty(0.0);
        setFrozenQty(0.0);
        setAllocatedQty(0.0);
        setAllocatedDistributionQty(0.0);
        setAllocatedOtherQty(0.0);
        setInTransitPurchaseQty(0.0);
        setInTransitMovementQty(0.0);
        setInTransitOtherQty(0.0);
    }

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INVENTORY_TRANSACTION_SEQ")
	@SequenceGenerator(name = "ITEM_INVENTORY_TRANSACTION_SEQ", sequenceName = "ACOAPP_OMS.ITEM_INVENTORY_TRANSACTION_SEQ", allocationSize = 1)
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

    @Column(name = "CHANNEL_EX")
    public String getChannelEx() {
        return channelEx;
    }

    public void setChannelEx(String channelEx) {
        this.channelEx = channelEx;
    }

    @Column(name = "WAREHOUSE_EX")
    public String getWarehouseEx() {
        return warehouseEx;
    }

    public void setWarehouseEx(String warehouseEx) {
        this.warehouseEx = warehouseEx;
    }

    @Column(name = "LOCATION_TYPE_EX")
    public String getLocationTypeEx() {
        return locationTypeEx;
    }

    public void setLocationTypeEx(String locationTypeEx) {
        this.locationTypeEx = locationTypeEx;
    }

	@Column(name = "BUSINESS_TYPE")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "BUSINESS_CHILD_TYPE")
	public String getBusinessChildType() {
		return businessChildType;
	}

	public void setBusinessChildType(String businessChildType) {
		this.businessChildType = businessChildType;
	}

	@Column(name = "BUSINESS_NO")
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name = "BUSINESS_DATE")
	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
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

	@Column(name = "LOT_NO")
	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	@Column(name = "QTY")
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

    @Column(name = "ESTIMATED_QTY")
    public Double getEstimatedQty() {
        return estimatedQty;
    }

    public void setEstimatedQty(Double estimatedQty) {
        this.estimatedQty = estimatedQty;
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

	@Column(name = "REFERENCE_NO")
	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	@Column(name = "SOURCE_ID")
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "SOURCE_DESC")
	public String getSourceDesc() {
		return sourceDesc;
	}

	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
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

	@Column(name = "BATCH_ID")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name = "BATCH_DATE")
	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	@Column(name = "BATCH_BY")
	public String getBatchBy() {
		return batchBy;
	}

	public void setBatchBy(String batchBy) {
		this.batchBy = batchBy;
    }


}
