package com.chinadrtv.erp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * <dl>
 * <dt><b>Title:购物车</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-10 下午3:20:37
 * 
 */
@Table(name = "SHOPPING_CART", schema = "IAGENT")
@Entity
public class ShoppingCart implements Serializable{
	public  static  String CART="CART";
	public  static  String ORDER="ORDER";
	private Long id;
	
	private Long contactId;
	
	private String createUser;
	
	private Date createDate;
	
	private String updateUser;
	
	private Date upserDate;

    private String cartType ="CART";//购物车类型 默认值为CART

    private String orderid;//订单保存到购物车。
	
	private Set<ShoppingCartProduct> shoppingCartProducts = new HashSet<ShoppingCartProduct>();

	@Id
	@Column(name ="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_SHOPPING_CART")
	@SequenceGenerator(name="SEQ_SHOPPING_CART",sequenceName="IAGENT.SEQ_SHOPPING_CART",allocationSize=1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="CONTACT_ID",length=19)
	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	@Column(name="CREATE_USER",length=20)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	@Column(name="CREATE_DATE")
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
	@Column(name="UPDATE_DATE")
	public Date getUpserDate() {
		return upserDate;
	}

	public void setUpserDate(Date upserDate) {
		this.upserDate = upserDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart",orphanRemoval=true)
	@Cascade(value= {CascadeType.SAVE_UPDATE,CascadeType.REMOVE ,CascadeType.ALL})
	@OrderBy("id")
	public Set<ShoppingCartProduct> getShoppingCartProducts() {
		return shoppingCartProducts;
	}

	public void setShoppingCartProducts(Set<ShoppingCartProduct> shoppingCartProducts) {
		this.shoppingCartProducts = shoppingCartProducts;
	}

    @Column(name="CART_TYPE",length = 20)
    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    @Column(name="ORDERID",length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
