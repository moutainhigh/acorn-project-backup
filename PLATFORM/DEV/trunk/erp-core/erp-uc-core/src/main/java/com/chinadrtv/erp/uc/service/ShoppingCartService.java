package com.chinadrtv.erp.uc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * 购物车Service
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-13 下午12:32:19
 */
public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {
    /**
     * 创建购物车
     *
     * @param contactId
     * @param userId
     * @param cartType
     * @return
     */
    public ShoppingCart createShoppingCart(Long contactId, String userId, String cartType);

    /**
     * 查询用户购物车
     *
     * @param contactId
     * @param cartType
     * @return
     */
    public ShoppingCart findShoppingCartByContactId(Long contactId, String cartType);

    /**
     * 查询用户购物车
     *
     * @param shoppingCartId
     * @return
     */
    public ShoppingCart findShoppingCartById(Long shoppingCartId);

    /**
     * 查询购物车产品
     *
     * @param shoppingCartProductId
     * @return
     */
    public ShoppingCartProduct findShoppingCartProductById(Long shoppingCartProductId);

    /**
     * 清空用户购物车选中产品
     *
     * @param shoppingCartId
     */
    public void deleteShoppingCartById(Long shoppingCartId);

    /**
     * 删除购物车
     *
     * @param contactId
     * @param cartType
     */
    public void deleteShoppingCartByContactId(Long contactId, String cartType);

    /**
     * 新增购物车产品 购物车产品中 skucode(required)  productType(required=false)  salesPrice(required=false)  giftSource(required=false)
     *
     * @param shoppingCartId
     * @param userId
     * @param shoppingCartProduct
     * @return map :key为reload值true时表示有套装，需要刷新购物车
     * @throws ErpException
     */
    public Map<String, Object> addShoppingCartProduct(Long shoppingCartId, String userId, ShoppingCartProduct shoppingCartProduct) throws ErpException;

    /**
     * 更新购物车产品
     *
     * @param shoppingCartProduct
     * @param userId
     * @return map:key为reload值true时表示有套装，需要刷新购物车；key为list时，值表示需要删除的购物车产品
     * @throws ErpException
     */
    public Map<String, Object> updateShoppingCartProduct(ShoppingCartProduct shoppingCartProduct, String userId) throws ErpException;

    /**
     * <p>删除购物车产品</p>
     *
     * @param shoppingCartId
     * @param shoppingCartProductId
     * @param userId
     * @return 级联删除产品列表
     */
    public List<ShoppingCartProduct> deleteShoppingCartProductById(Long shoppingCartId, Long shoppingCartProductId, String userId) throws ErpException;

    /**
     * 计算购物车商品数量
     *
     * @param shoppingCartId
     * @return
     */
    public Long getShoppingCartProductQuantity(Long shoppingCartId);

    /**
     * <p>计算购物车商品金额</p>
     *
     * @param shoppingCartId
     * @return
     */
    public BigDecimal getShoppingCartSalePrice(Long shoppingCartId);

    /**
     * 查询购物车促销结果
     *
     * @param shoppingCart
     * @param userId
     * @return
     */
    public List<ScmPromotion> getScmPromotions(ShoppingCart shoppingCart, String userId);

    /**
     * 购物车生成订单(使用获取当前登陆坐席)
     *
     * @param shoppingCartId
     * @return
     */
    public Order createOrderbyShoppingCart(Long shoppingCartId);

    /**
     * 购物车生成订单(使用指定坐席)
     *
     * @param shoppingCartId
     * @return
     */
    public Order createOrderbyShoppingCart(Long shoppingCartId, String userId, String grpId);

    /**
     * 购物车创建运费计算订单
     *
     * @param shoppingCartId
     * @return
     */
    public Order createMailOrderbyShoppingCart(Long shoppingCartId, String userId, String grpId);

    /**
     * 订单生成购物车
     *
     * @param order
     * @return
     */
    public ShoppingCart createShoppingCartbyOrder(Order order);

    /**
     * 更新购物车联系人
     *
     * @param potentialContactCode 潜客
     * @param contactCode
     */
    void updateShoppingCartContact(Long potentialContactCode, Long contactCode);

    /**
     * 验证购物车产品合理性
     *
     * @param shoppingCartId
     */
    void validateShoppingCart(Long shoppingCartId, String userId, String grpId, Boolean approveManager) throws ErpException;

    /**
     * 购物车产品选中改变
     *
     * @param shoppingCartId
     * @param selected
     */
    public void shoppingCartProductSelectChange(Long shoppingCartId, boolean selected);

    /**
     * 可用保险获取
     *
     * @param contactId
     * @param grpId
     * @param orderId   该订单中保险不考虑
     * @return
     */
    public List<Product> getInsureList(String contactId, String grpId, String orderId);

    /**
     * 判断购物车中是否有90天内
     *
     * @param shoppingCartId
     * @return
     */
    public String insureProductValidate(Long shoppingCartId, String grpId);

    /**
     * 获取用户规定天数内用过的赠险
     *
     * @param contactId
     * @return
     */
    public List<Product> getUserdInsureProduct(String contactId);
}
