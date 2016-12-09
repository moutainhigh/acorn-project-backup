package com.chinadrtv.erp.sales.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;

import javax.persistence.Column;

/**
 * 
 * <dl>
 *    <dt><b>Title:购物车产品Form</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-28 下午6:27:27 
 *
 */
public class ShoppingCartProductDto{
	private ShoppingCartProduct shoppingCartProduct;
	public ShoppingCartProductDto (ShoppingCartProduct shoppingCartProduct){
		this.shoppingCartProduct = shoppingCartProduct;
	}
	//购物车产品规格
	private List<NcPlubasInfoAttribute> productTypes;
	
	//是否促销赠品(默认为false
	public Boolean isScmGift = false;

    //结算金额
    public BigDecimal  detailPrice;

    //结算金额等于产品总额减去积分
    public BigDecimal getDetailPrice() {
        if(this.getPoint()==null || this.getProductQuantity()==null  || this.getProductQuantity() ==0 ){
             return  this.getSalePrice();
        }
        BigDecimal singlePoint =this.getPoint().divide(new BigDecimal(this.getProductQuantity()),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal price = this.getSalePrice().subtract(singlePoint).multiply(new BigDecimal(this.getProductQuantity())).setScale(2, RoundingMode.HALF_UP);
        return price;
    }

    public void setDetailPrice(BigDecimal detailPrice) {
        this.detailPrice = detailPrice;
    }

    public Boolean getIsScmGift() {
		return isScmGift;
	}
	public void setIsScmGift(Boolean isScmGift) {
		this.isScmGift = isScmGift;
	}
	public List<NcPlubasInfoAttribute> getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(List<NcPlubasInfoAttribute> productTypes) {
		this.productTypes = productTypes;
	}
	
	public String getProductTypesStr(){
		if(productTypes==null){
			return "";
		}
		return JSONArray.fromObject(productTypes).toString();
	}
	
	public boolean equals(Object obj) {
		return shoppingCartProduct.equals(obj);
	}
	public Date getCreateDate() {
		return shoppingCartProduct.getCreateDate();
	}
	public String getCreateUser() {
		return shoppingCartProduct.getCreateUser();
	}
	public String getGiftSource() {
		return shoppingCartProduct.getGiftSource();
	}
	public String getGiftSourceDetail() {
		return shoppingCartProduct.getGiftSourceDetail();
	}
	public Long getId() {
		return shoppingCartProduct.getId();
	}
	public Integer getIsGift() {
		return shoppingCartProduct.getIsGift();
	}
	public Boolean getIsSelected() {
		return shoppingCartProduct.getIsSelected();
	}
	public String getOptionValue() {
		return shoppingCartProduct.getOptionValue();
	}
	public Long getParentId() {
		return shoppingCartProduct.getParentId();
	}
	public String getProductImage() {
		return shoppingCartProduct.getProductImage();
	}
	public String getProductName() {
		return shoppingCartProduct.getProductName();
	}
	public Integer getProductQuantity() {
		return shoppingCartProduct.getProductQuantity();
	}
	public String getProductType() {
		return shoppingCartProduct.getProductType();
	}
	public BigDecimal getSalePrice() {
		return shoppingCartProduct.getSalePrice();
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCartProduct.getShoppingCart();
	}
	public String getSkuCode() {
		return shoppingCartProduct.getSkuCode();
	}
	public Date getUpdateDate() {
		return shoppingCartProduct.getUpdateDate();
	}
	public String getUpdateUser() {
		return shoppingCartProduct.getUpdateUser();
	}
	public int hashCode() {
		return shoppingCartProduct.hashCode();
	}
	public void setCreateDate(Date createDate) {
		shoppingCartProduct.setCreateDate(createDate);
	}
	public void setCreateUser(String createUser) {
		shoppingCartProduct.setCreateUser(createUser);
	}
	public void setGiftSource(String giftSource) {
		shoppingCartProduct.setGiftSource(giftSource);
	}
	public void setGiftSourceDetail(String giftSourceDetail) {
		shoppingCartProduct.setGiftSourceDetail(giftSourceDetail);
	}
	public void setId(Long id) {
		shoppingCartProduct.setId(id);
	}
	public void setIsGift(Integer isGift) {
		shoppingCartProduct.setIsGift(isGift);
	}
	public void setIsSelected(Boolean isSelected) {
		shoppingCartProduct.setIsSelected(isSelected);
	}
	public void setOptionValue(String optionValue) {
		shoppingCartProduct.setOptionValue(optionValue);
	}
	public void setParentId(Long parentId) {
		shoppingCartProduct.setParentId(parentId);
	}
	public void setProductImage(String productImage) {
		shoppingCartProduct.setProductImage(productImage);
	}
	public void setProductName(String productName) {
		shoppingCartProduct.setProductName(productName);
	}
	public void setProductQuantity(Integer productQuantity) {
		shoppingCartProduct.setProductQuantity(productQuantity);
	}
	public void setProductType(String productType) {
		shoppingCartProduct.setProductType(productType);
	}
	public void setSalePrice(BigDecimal salePrice) {
		shoppingCartProduct.setSalePrice(salePrice);
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		shoppingCartProduct.setShoppingCart(shoppingCart);
	}
	public void setSkuCode(String skuCode) {
		shoppingCartProduct.setSkuCode(skuCode);
	}
	public void setUpdateDate(Date updateDate) {
		shoppingCartProduct.setUpdateDate(updateDate);
	}
	public void setUpdateUser(String updateUser) {
		shoppingCartProduct.setUpdateUser(updateUser);
	}
	public String toString() {
		return shoppingCartProduct.toString();
	}

    public void setPoint(BigDecimal point) {
        shoppingCartProduct.setPoint(point);
    }

    public BigDecimal getPoint() {
        return shoppingCartProduct.getPoint();
    }
}
