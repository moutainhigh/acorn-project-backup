package com.chinadrtv.erp.uc.dao;

import java.math.BigDecimal;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.model.agent.Product;

/**
 * 
 * <dl>
 *    <dt><b>Title:购物车dao</b></dt>
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
 * @since 2013-5-13 下午12:33:26 
 *
 */
public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
	/**
	 * 
	 * <p>查询用户购物车</p>
	 * @param Id
	 * @return
	 */
	public ShoppingCart findShoppingCartById(Long Id);
	/**
	 * 
	 * <p>查询用户购物车</p>
	 * @param contactId
	 * @param cartType
	 * @return
	 */
	public ShoppingCart findShoppingCartByContactId(Long contactId,String cartType);
	/**
	 * 
	 * <p>删除用户购物车</p>
	 * @param shoppingCartId
	 */
	public void deleteShoppingCartById(Long shoppingCartId);

    /**
     * 删除购物车选择商品
     * @param shoppingCartId
     */
    public Integer deleteShoppingCartSelectProducts(Long shoppingCartId);

    /**
     * 新增购物车产品
     * @param shoppingCartId
     * @param shoppingCartProduct
     */
	public void addShoppingCartProduct(Long shoppingCartId,ShoppingCartProduct shoppingCartProduct);
	/**
	 * 
	 * <p>>查询购物车产品</p>
	 * @param id
	 * @return
	 * @throws ErpException
	 */
	public ShoppingCartProduct findShoppingCartProductById(Long id);
	
	/**
	 * 
	 * <p>更新购物车产品</p>
	 * @param shoppingCartProduct
	 */
	public ShoppingCartProduct updateShoppingCartProduct(ShoppingCartProduct shoppingCartProduct);
	/**
	 * 
	 * <p>删除购物车产品</p>
	 * @param shoppingCartProductId
	 */
	public void deleteShoppingCartProductId(Long shoppingCartProductId);
	/**
	 * 
	 * <p>计算购物车产品数量</p>
	 * @param shoppingCartId
	 * @return
	 */
	public Long getShoppingCartProductQuantity(Long shoppingCartId);


    /**
     * 更新购物车联系人
     * @param potentialContactCode
     * @param contactCode
     */
    void updateShoppingCartContact(Long potentialContactCode, Long contactCode);

    /**
     * 获取 赠险控制时间
     * @return
     */
    Parameters getInsureDays();

    /**
     * 获取客户使用的赠险产品
     * @param contactId
     * @param insureDay
     * @param orderId
     * @return
     */
    List<String> getUsedInsure(String contactId, Integer insureDay,String orderId);

    /**
     * 获取用户可用的赠险产品
     * @param grpId
     * @return
     */
    List<Product>  getInsureList(String grpId);

    /**
     * 获取客户使用的赠险产品
     * @param contactId
     * @param insureDay
     * @return
     */
    List<Product> getUsedInsure(String contactId, Integer insureDay);
}
