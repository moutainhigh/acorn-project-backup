package com.chinadrtv.erp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "SHOPPING_CART_PRODUCT", schema = "IAGENT")
public class ShoppingCartProduct implements Serializable {

	private Long id; 
	private String giftSource; //赠品来源（如果是赠品）
	private String optionValue; //自由项
	private String productImage;//产品图片URL
	private String productName; //产品名称
	private Integer productQuantity; //产品数量
	private BigDecimal salePrice; //价格
	private String skuCode;  //SKU CODE  10位编码
    private String productCode;//产品12位编码
	private String productType; //产品类型
	private Boolean isSelected; //是否选择
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private Long parentId;//套装关系    1为套装 0为单品
    private Integer isGift; //是否赠品   生成订单时，对应SOLDWITH字段：1主销2搭销3赠品
    private String giftSourceDetail;
    private BigDecimal point =BigDecimal.ZERO;//使用积分 (默认制0)
    private ShoppingCart shoppingCart;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_SHOPPING_CART_PRODUCT")
	@SequenceGenerator(name="SEQ_SHOPPING_CART_PRODUCT",sequenceName="IAGENT.SEQ_SHOPPING_CART_PRODUCT",allocationSize=1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="GIFT_SOURCE",length=100)
	public String getGiftSource() {
		return giftSource;
	}
	public void setGiftSource(String giftSource) {
		this.giftSource = giftSource;
	}
	@Column(name="OPTION_VALUE",length=100)
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	@Column(name="PRODUCT_IMAGE",length=20)
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	@Column(name="PRODUCT_NAME",length=20)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(name="PRODUCT_QUANTITY",length=10)
	public Integer getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}
	@Column(name="SALE_PRICE",length=15)
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	@Column(name="SKU_CODE",length=100)
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
    @Column(name="PRODUCT_CODE",length=20)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name="PRODUCT_TYPE",length=100)
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	@Column(name="IS_SELECTED",length=1)
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	@Column(name="CREATE_USER",length=20)
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	@Column(name="CREATE_DATE",length=7)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="UPDATE_USER",length=20)
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	@Column(name="UPDATE_DATE",length=7)
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@ManyToOne
	@JoinColumn(name = "SHOPPING_CART_ID")
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	@Column(name="PARENT_ID",length=1)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(name="IS_GIFT",length=1)
	public Integer getIsGift() {
		return isGift;
	}
	public void setIsGift(Integer isGift) {
		this.isGift = isGift;
	}
	@Column(name="GIFT_SOURCE_DETAIL",length=20)
	public String getGiftSourceDetail() {
		return giftSourceDetail;
	}
	public void setGiftSourceDetail(String giftSourceDetail) {
		this.giftSourceDetail = giftSourceDetail;
	}
    @Column(name="point",length=10)
    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    @Override
	public int hashCode() {
		final int prime=31;
		int result=1;
		result =prime*result +((id==null)?0:id.hashCode());
		result =prime*result +((giftSource==null)?0:giftSource.hashCode());
		result =prime*result +((optionValue==null)?0:optionValue.hashCode());
		result =prime*result +((productImage==null)?0:productImage.hashCode());
		result =prime*result +((productQuantity==null)?0:productQuantity.hashCode());
		result =prime*result +((salePrice==null)?0:salePrice.hashCode());
		result =prime*result +((skuCode==null)?0:skuCode.hashCode());
		result =prime*result +((productType==null)?0:productType.hashCode());
		result =prime*result +((isSelected==null)?0:isSelected.hashCode());
		result =prime*result +((createUser==null)?0:createUser.hashCode());
		result =prime*result +((createDate==null)?0:createDate.hashCode());
		result =prime*result +((updateUser==null)?0:updateUser.hashCode());
		result =prime*result +((updateDate==null)?0:updateDate.hashCode());
		result =prime*result +((parentId==null)?0:parentId.hashCode());
		result =prime*result +((isGift==null)?0:isGift.hashCode());
		
		
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(getClass() !=obj.getClass())
			return false;
		ShoppingCartProduct other = (ShoppingCartProduct) obj;
		if(id ==null ){
			if(other.id !=null)
				return false;
		}else if(!id.equals(other.id))
				return false;
		if(giftSource ==null ){
			if(other.giftSource !=null)
				return false;
		}else if(!giftSource.equals(other.giftSource))
			return false;
		if(optionValue ==null ){
			if(other.optionValue !=null)
				return false;
		}else if(!optionValue.equals(other.optionValue))
			return false;
		if(productImage ==null ){
			if(other.productImage !=null)
				return false;
		}else if(!productImage.equals(other.productImage))
			return false;
		if(productName ==null ){
			if(other.productName !=null)
				return false;
		}else if(!productName.equals(other.productName))
			return false;
		if(productQuantity ==null ){
			if(other.productQuantity !=null)
				return false;
		}else if(!productQuantity.equals(other.productQuantity))
			return false;
		if(salePrice ==null ){
			if(other.salePrice !=null)
				return false;
		}else if(!salePrice.equals(other.salePrice))
			return false;
		if(skuCode ==null ){
			if(other.skuCode !=null)
				return false;
		}else if(!skuCode.equals(other.skuCode))
			return false;
		if(productType ==null ){
			if(other.productType !=null)
				return false;
		}else if(!productType.equals(other.productType))
			return false;
		if(isSelected ==null ){
			if(other.isSelected !=null)
				return false;
		}else if(!isSelected.equals(other.isSelected))
			return false;
		if(createUser ==null ){
			if(other.createUser !=null)
				return false;
		}else if(!createUser.equals(other.createUser))
			return false;
		if(createDate ==null ){
			if(other.createDate !=null)
				return false;
		}else if(!createDate.equals(other.createDate))
			return false;
		if(updateUser ==null ){
			if(other.updateUser !=null)
				return false;
		}else if(!updateUser.equals(other.updateUser))
			return false;
		if(updateDate ==null ){
			if(other.updateDate !=null)
				return false;
		}else if(!updateDate.equals(other.updateDate))
			return false;
		if(parentId ==null ){
			if(other.parentId !=null)
				return false;
		}else if(!parentId.equals(other.parentId))
			return false;
		if(isGift ==null ){
			if(other.isGift !=null)
				return false;
		}else if(!isGift.equals(other.isGift))
			return false;
		
		return true;
	}
	
	
}
