package com.chinadrtv.erp.sales.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;

/**
 * 
 * <dl>
 *    <dt><b>Title:购物车Form</b></dt>
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
 * @since 2013-5-28 下午6:24:19 
 *
 */
public class ShoppingCartDto{
	private ShoppingCart shoppingCart;
	public ShoppingCartDto(ShoppingCart shoppingCart){
		this.shoppingCart = shoppingCart;
	}
	//购物车产品Form
	public Set<ShoppingCartProductDto> shoppingCartProductDtos;

	public Set<ShoppingCartProductDto> getShoppingCartProductDtos() {
		return shoppingCartProductDtos;
	}
	public void setShoppingCartProductDtos(
			Set<ShoppingCartProductDto> shoppingCartProductDtos) {
		this.shoppingCartProductDtos = shoppingCartProductDtos;
	}
	public Long getContactId() {
		return shoppingCart.getContactId();
	}
	public Date getCreateDate() {
		return shoppingCart.getCreateDate();
	}
	public String getCreateUser() {
		return shoppingCart.getCreateUser();
	}
	public Long getId() {
		return shoppingCart.getId();
	}
	public Set<ShoppingCartProduct> getShoppingCartProducts() {
		return shoppingCart.getShoppingCartProducts();
	}
	public String getUpdateUser() {
		return shoppingCart.getUpdateUser();
	}
	public Date getUpserDate() {
		return shoppingCart.getUpserDate();
	}
	public void setContactId(Long contactId) {
		shoppingCart.setContactId(contactId);
	}
	public void setCreateDate(Date createDate) {
		shoppingCart.setCreateDate(createDate);
	}
	public void setCreateUser(String createUser) {
		shoppingCart.setCreateUser(createUser);
	}
	public void setId(Long id) {
		shoppingCart.setId(id);
	}
	public void setShoppingCartProducts(
			Set<ShoppingCartProduct> shoppingCartProducts) {
		shoppingCart.setShoppingCartProducts(shoppingCartProducts);
	}
	public void setUpdateUser(String updateUser) {
		shoppingCart.setUpdateUser(updateUser);
	}
	public void setUpserDate(Date upserDate) {
		shoppingCart.setUpserDate(upserDate);
	}
	
}
