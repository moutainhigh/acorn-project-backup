package com.chinadrtv.erp.uc.util;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.ic.service.ProductGrpLimitService;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;
import com.chinadrtv.erp.uc.dao.PluSplitDao;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-12
 * Time: 上午10:51
 * 购物车验证端独抽出，功能分离
 */
@Service("ShoppingCartProductValidate")
public class ShoppingCartProductValidate {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShoppingCartProductValidate.class);
    @Autowired
    private PluSplitDao pluSplitDao;
    @Autowired
    private ItemInventoryService itemInventoryService;
    @Autowired
    private PlubasInfoService plubasInfoService;
    @Autowired
    private OrderDelDetailService orderDelDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductGrpLimitService productGrpLimitService;

    /**
     * 新增或修改购物车商品判断，修改时shoppingCartProduct.id不能为空
     *
     * @param shoppingCart
     * @param shoppingCartProduct
     * @return
     * @throws ServiceTransactionException
     */
    public PlubasInfo realTimeStockCheckShoppingCartProduct(ShoppingCart shoppingCart, ShoppingCartProduct shoppingCartProduct) throws ServiceTransactionException {
        List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(shoppingCartProduct.getSkuCode(), null, null, shoppingCartProduct.getProductType());
        //商品基本信息判断
        if (plubasInfoList == null || plubasInfoList.isEmpty()) {
            throw new ServiceTransactionException("该商品暂时没有库存,商品编号为:" + shoppingCartProduct.getSkuCode());
        }
        PlubasInfo plubasInfo = plubasInfoList.get(0);

        this.ncFreeStatusValidate(shoppingCartProduct, plubasInfo);

        //商品进行价格判断
        this.validateShoppingCartProductPrice(shoppingCart, shoppingCartProduct, plubasInfo);

        //不受控商品不进行库存判断
        if ("1".equalsIgnoreCase(plubasInfo.getStatus()) || "3".equalsIgnoreCase(plubasInfo.getStatus()) || "9".equalsIgnoreCase(plubasInfo.getStatus())) {
            return plubasInfo;
        }
        //购物车多件商品判断
        Map<String, Integer> cartProductMap = getCartSingleProductMap(shoppingCart, shoppingCartProduct);
        if ("0".equals(plubasInfo.getIssuiteplu())) {
            Integer productQuantity = shoppingCartProduct.getProductQuantity();
            if (cartProductMap.containsKey(plubasInfo.getPlucode())) {
                productQuantity = productQuantity + cartProductMap.get(plubasInfo.getPlucode());
            }
            this.validateShoppingCartProduct(plubasInfo.getPlucode(), productQuantity, shoppingCartProduct.getProductName());
        } else {
            List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubasInfo.getPlucode());
            for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                Integer productQuantity = shoppingCartProduct.getProductQuantity() * productSuiteType.getProdNum();
                if (cartProductMap.containsKey(productSuiteType.getProdScmId())) {
                    productQuantity = productQuantity + cartProductMap.get(productSuiteType.getProdScmId());
                }
                //单品不受控判断   //不受控商品
                PlubasInfo suitePlubasInfo = plubasInfoService.getPlubasInfo(productSuiteType.getProductSuiteTypeId().getProdScmId());
                if ("1".equalsIgnoreCase(suitePlubasInfo.getStatus()) || "3".equalsIgnoreCase(suitePlubasInfo.getStatus()) || "9".equalsIgnoreCase(suitePlubasInfo.getStatus())) {
                    continue;
                }

                this.validateShoppingCartProduct(productSuiteType.getProdScmId(), productQuantity, shoppingCartProduct.getProductName());
            }
        }

        return plubasInfo;
    }

    public boolean specialPriceValidate(OrderDetail orderDetail, String userId) throws Exception {
        if (!"2".equals(orderDetail.getSoldwith()) || BigDecimal.ZERO.compareTo(getProdPrice(orderDetail))!=0) {
            return false;
        }
        String userGroup = userService.getUserGroup(userId);
        String department = userService.getDepartment(userId);
        List<AcornRole> roleList = userService.getRoleList(userId);
        boolean managerRole = false;
        for (AcornRole acornRole : roleList) {
            if (acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_OUTBOUND_MANAGER)
                    || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_INBOUND_MANAGER)
                    || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER)
                    || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER)) {
                managerRole = true;
                break;
            }
        }

        return validateCode(orderDetail.getProdid(), getProdPrice(orderDetail), userGroup, department, managerRole);
    }

    private BigDecimal getProdPrice(OrderDetail orderDetail) {
        if (orderDetail.getSpnum() != null && orderDetail.getSpnum().intValue() > 0
                && orderDetail.getSprice() != null) {
            return orderDetail.getSprice();
        }
        if (orderDetail.getUpnum() != null && orderDetail.getUpnum().intValue() > 0
                && orderDetail.getUprice() != null)
            return orderDetail.getUprice();

        return BigDecimal.ZERO;
    }

    /**
     * @param skuCode
     * @param salePrice
     * @return 返回true表示需要走审批
     * @throws ServiceTransactionException 抛异常表示价格出错
     */
    public boolean specialPriceValidate(String skuCode, BigDecimal salePrice) throws ServiceTransactionException {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user==null){
            //TODO 此处依赖AgentUser
            return true;
        }
        boolean managerRole = checkUserRole(user);

        return this.validateCode(skuCode, salePrice, user.getWorkGrp(), user.getDepartment(), managerRole);
    }

    private boolean validateCode(String skuCode, BigDecimal salePrice, String userGroup, String department, boolean managerRole) throws ServiceTransactionException {
        if (managerRole) {
            Boolean isValidPrice = productGrpLimitService.isValidPrice(userGroup, skuCode, salePrice.doubleValue());
            if (!isValidPrice) {
                throw new ServiceTransactionException("该价格超过允许销售的价格范围，商品编号为:" + skuCode);
            }
        } else {
            try {
                Boolean isValidPrice = productGrpLimitService.isValidPrice(userGroup, skuCode, salePrice.doubleValue());
                if (isValidPrice) {
                    return false;
                }
                List<String> managerGroupList = userService.getManageGroupList(department);

                for (String managerGrp : managerGroupList) {
                    isValidPrice = productGrpLimitService.isValidPrice(managerGrp, skuCode, salePrice.doubleValue());
                    if (isValidPrice) {
                        return true;
                    }
                }
                throw new ServiceTransactionException("该价格超过允许销售的价格范围，商品编号为:" + skuCode);
            } catch (ServiceException e) {
                throw new ServiceTransactionException("该价格超过允许销售的价格范围，商品编号为:" + skuCode);
            }
        }
        return false;
    }

    private boolean checkUserRole(AgentUser user) {
        boolean isOutboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
        boolean isInboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
        boolean isOutboundGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
        boolean isInboundGroupManager = user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);

        return isOutboundDepartmentManager || isInboundDepartmentManager || isOutboundGroupManager || isInboundGroupManager;
    }

    /**
     * 购物车校验
     *
     * @param shoppingCart
     * @throws com.chinadrtv.erp.exception.service.ServiceTransactionException
     *
     */
    public void realTimeStockCheckShoppingCart(ShoppingCart shoppingCart) throws ServiceTransactionException {
        if (shoppingCart == null) {
            throw new ServiceTransactionException("购物车为空，无法保存订单");
        }
        Set<ShoppingCartProduct> shoppingCartProducts = shoppingCart.getShoppingCartProducts();
        if (shoppingCartProducts.isEmpty()) {
            throw new ServiceTransactionException("购物车为空，无法保存订单");
        }

        Map<String, Integer> cartProductMap = new HashMap<String, Integer>();
        for (ShoppingCartProduct shoppingCartProduct : shoppingCartProducts) {
            List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(shoppingCartProduct.getSkuCode(), null, null, shoppingCartProduct.getProductType());
            //商品基本信息判断
            if (plubasInfoList == null || plubasInfoList.isEmpty()) {
                throw new ServiceTransactionException("该商品暂时没有库存,商品编号为:" + shoppingCartProduct.getSkuCode());
            }

            PlubasInfo plubasInfo = plubasInfoList.get(0);

            //可售不可售判断
            this.ncFreeStatusValidate(shoppingCartProduct, plubasInfo);

            //非赠品进行价格判断
            this.validateShoppingCartProductPrice(shoppingCart, shoppingCartProduct, plubasInfo);


            //不受控商品
            if ("1".equalsIgnoreCase(plubasInfo.getStatus()) || "3".equalsIgnoreCase(plubasInfo.getStatus()) || "9".equalsIgnoreCase(plubasInfo.getStatus())) {
                return;
            }

            if ("0".equals(plubasInfo.getIssuiteplu())) {
                if (cartProductMap.containsKey(plubasInfo.getPlucode())) {
                    Integer value = cartProductMap.get(plubasInfo.getPlucode()) + shoppingCartProduct.getProductQuantity();
                    cartProductMap.put(plubasInfo.getPlucode(), value);
                } else {
                    cartProductMap.put(plubasInfo.getPlucode(), shoppingCartProduct.getProductQuantity());
                }
            } else {
                List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubasInfo.getPlucode());
                for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                    //单品不受控判断   //不受控商品
                    PlubasInfo suitePlubasInfo = plubasInfoService.getPlubasInfo(productSuiteType.getProductSuiteTypeId().getProdScmId());
                    if ("1".equalsIgnoreCase(suitePlubasInfo.getStatus()) || "3".equalsIgnoreCase(suitePlubasInfo.getStatus()) || "9".equalsIgnoreCase(suitePlubasInfo.getStatus())) {
                        continue;
                    }
                    if (cartProductMap.containsKey(productSuiteType.getProdScmId())) {
                        Integer value = cartProductMap.get(productSuiteType.getProdScmId()) + shoppingCartProduct.getProductQuantity() * productSuiteType.getProdNum();
                        cartProductMap.put(productSuiteType.getProdScmId(), value);
                    } else {
                        cartProductMap.put(productSuiteType.getProdScmId(), shoppingCartProduct.getProductQuantity() * productSuiteType.getProdNum());
                    }
                }
            }
        }

        Map<String, Integer> orderProductMap = getOrderSingleProductMap(shoppingCart);
        for (String key : cartProductMap.keySet()) {
            if (orderProductMap.containsKey(key)) {
                Integer value = cartProductMap.get(key) - orderProductMap.get(key);
                cartProductMap.put(key, value);
            }
        }

        for (String key : cartProductMap.keySet()) {
            this.validateShoppingCartProduct(key, cartProductMap.get(key), null);
        }
    }

    //可售不可售判断
    private void ncFreeStatusValidate(ShoppingCartProduct shoppingCartProduct, PlubasInfo plubasInfo) throws ServiceTransactionException {
        if (shoppingCartProduct.getIsGift() != 3 && StringUtils.isBlank(shoppingCartProduct.getGiftSource())) {

            double ncFreeStatus = 0d;
            if (plubasInfo.getNcfreestatus().equals(ncFreeStatus)) {
                throw new ServiceTransactionException("该商品不可售,商品编号为:" + shoppingCartProduct.getSkuCode());
            }
        }
    }

    private void validateShoppingCartProductPrice(ShoppingCart shoppingCart, ShoppingCartProduct shoppingCartProduct, PlubasInfo plubasInfo) throws ServiceTransactionException {
        if (shoppingCartProduct.getIsGift() != 3) {
            //价格判断(价格范围及渠道价格)
            Double minPrice = plubasInfo.getLslprc();
            Double maxPrice = plubasInfo.getHslprc();
            AgentUser user = SecurityHelper.getLoginUser();
            if(user !=null){
                Double grpPrice = productGrpLimitService.getLpPrice(user.getWorkGrp(), plubasInfo.getNccode());
                if (grpPrice != null) {
                    minPrice = minPrice > grpPrice ? grpPrice : minPrice;
                    maxPrice = maxPrice > grpPrice ? maxPrice : grpPrice;
                }
            }

            boolean baseInfoPriceValidate = shoppingCartProduct.getSalePrice().doubleValue() < minPrice || shoppingCartProduct.getSalePrice().doubleValue() > maxPrice;
            if (baseInfoPriceValidate) {
                //0元搭销会进行额外的判断
                if (BigDecimal.ZERO.compareTo(shoppingCartProduct.getSalePrice()) ==0 && shoppingCartProduct.getIsGift() == 2) {
                    this.specialPriceValidate(shoppingCartProduct.getSkuCode(), shoppingCartProduct.getSalePrice());
                } else {
                    throw new ServiceTransactionException("该价格超过允许销售的价格范围，商品编号为:" + shoppingCartProduct.getSkuCode());
                }
            }
        } else { //赠品判断零元商品数量
            if (BigDecimal.ZERO.compareTo(shoppingCartProduct.getSalePrice()) ==0) {
                if(shoppingCartProduct.getProductQuantity()>1){
                    throw new ServiceTransactionException("购物车只能包含一件价格为零的赠品");
                }
                for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
                    if (BigDecimal.ZERO.compareTo(scp.getSalePrice())==0 && !scp.getId().equals(shoppingCartProduct.getId()) && scp.getIsGift() == 3) {
                        throw new ServiceTransactionException("购物车只能包含一件价格为零的赠品");
                    }
                }
            }
        }
    }

    //单品库存判断
    private void validateShoppingCartProduct(String pluCode, Integer productQuantity, String name) throws ServiceTransactionException {
        List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeAllStock(pluCode);
        if (stockItems == null || stockItems.isEmpty()) {
            if (StringUtils.isNotBlank(name)) {
                throw new ServiceTransactionException("该商品暂时没有库存,商品名称为:" + name);
            } else {
                throw new ServiceTransactionException("该商品暂时没有库存,商品编号为:" + pluCode);
            }
        }
        if (stockItems.get(0).getAvailableQty() < productQuantity || stockItems.get(0).getAvailableQty() < 1d) {
            if (StringUtils.isNotBlank(name)) {
                throw new ServiceTransactionException("该商品库存不足,商品名称为:" + name);
            } else {
                throw new ServiceTransactionException("该商品库存不足,商品编号为:" + pluCode);
            }
        }
    }

    //获取购物车中所有单品的数量 ，如果是订单修改。则去除订单中的
    private Map<String, Integer> getCartSingleProductMap(ShoppingCart shoppingCart, ShoppingCartProduct shoppingCartProduct) throws ServiceTransactionException {
        Map<String, Integer> cartProductMap = new HashMap<String, Integer>();
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (scp.getId().equals(shoppingCartProduct.getId())) {
                continue;
            }
            List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(scp.getSkuCode(), null, null, scp.getProductType());
            PlubasInfo plubasInfo = plubasInfoList.get(0);
            if ("0".equals(plubasInfo.getIssuiteplu())) {
                if (cartProductMap.containsKey(plubasInfo.getPlucode())) {
                    Integer value = cartProductMap.get(plubasInfo.getPlucode()) + scp.getProductQuantity();
                    cartProductMap.put(plubasInfo.getPlucode(), value);
                } else {
                    cartProductMap.put(plubasInfo.getPlucode(), scp.getProductQuantity());
                }
            } else {
                List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubasInfo.getPlucode());
                for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                    if (cartProductMap.containsKey(productSuiteType.getProdScmId())) {
                        Integer value = cartProductMap.get(productSuiteType.getProdScmId()) + scp.getProductQuantity() * productSuiteType.getProdNum();
                        cartProductMap.put(productSuiteType.getProdScmId(), value);
                    } else {
                        cartProductMap.put(productSuiteType.getProdScmId(), scp.getProductQuantity() * productSuiteType.getProdNum());
                    }
                }
            }

        }
        Map<String, Integer> orderProductMap = getOrderSingleProductMap(shoppingCart);
        for (String key : orderProductMap.keySet()) {
            if (cartProductMap.containsKey(key)) {
                Integer value = cartProductMap.get(key) - orderProductMap.get(key);
                cartProductMap.put(key, value);
            } else {
                cartProductMap.put(key, -orderProductMap.get(key));
            }
        }

        return cartProductMap;
    }

    //获取订单中所有单品的数量
    private Map<String, Integer> getOrderSingleProductMap(ShoppingCart shoppingCart) throws ServiceTransactionException {
        Map<String, Integer> orderProductMap = new HashMap<String, Integer>();//key:12位码  value:数量
        if (StringUtils.isBlank(shoppingCart.getOrderid())) {
            return orderProductMap;
        }

        List<OrderDetail> orderdets = new ArrayList<OrderDetail>();
        try {
            orderdets = orderDelDetailService.queryOrderdetbyorid(shoppingCart.getOrderid());
        } catch (SQLException e) {
            logger.error(e.getMessage(),e); //
            e.printStackTrace();
        }
        for (OrderDetail orderDetail : orderdets) {
            String productType = pluSplitDao.searchProductType(orderDetail.getProducttype());
            List<PlubasInfo> orderPlubasInfoList = plubasInfoService.getPlubasInfos(orderDetail.getProdid(), null, null, productType);
            if (orderPlubasInfoList == null || orderPlubasInfoList.isEmpty()) {
                throw new ServiceTransactionException("该商品暂时没有库存,商品名称为:" + orderDetail.getProdname());
            }
            PlubasInfo orderPlubasInfo = orderPlubasInfoList.get(0);
            if ("0".equals(orderPlubasInfo.getIssuiteplu())) {
                if (orderProductMap.containsKey(orderPlubasInfo.getPlucode())) {
                    Integer value = orderProductMap.get(orderPlubasInfo.getPlucode()) + orderDetail.getUpnum() + orderDetail.getSpnum();
                    orderProductMap.put(orderPlubasInfo.getPlucode(), value);
                } else {
                    orderProductMap.put(orderPlubasInfo.getPlucode(), (orderDetail.getUpnum() + orderDetail.getSpnum()));
                }
            } else {
                List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(orderPlubasInfo.getPlucode());
                for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                    if (orderProductMap.containsKey(productSuiteType.getProdScmId())) {
                        Integer value = orderProductMap.get(productSuiteType.getProdScmId()) + (orderDetail.getUpnum() + orderDetail.getSpnum()) * productSuiteType.getProdNum();
                        orderProductMap.put(productSuiteType.getProdScmId(), value);
                    } else {
                        orderProductMap.put(productSuiteType.getProdScmId(), (orderDetail.getUpnum() + orderDetail.getSpnum()) * productSuiteType.getProdNum());
                    }
                }
            }
        }
        return orderProductMap;
    }

    //获取订单中相同商品的数量
    private Integer getOrderContainCount(ShoppingCartProduct shoppingCartProduct, String orderId) throws ServiceTransactionException {
        Order order = orderDelDetailService.queryOrderhistbyorid(orderId);
        Set<OrderDetail> orderdets = order.getOrderdets();
        String productTypeId = pluSplitDao.getProductTypeId(shoppingCartProduct.getSkuCode(), shoppingCartProduct.getProductType());
        OrderDetail orderDetail = null;
        for (OrderDetail od : orderdets) {
            if (od.getProdid().equalsIgnoreCase(shoppingCartProduct.getSkuCode())) {
                if (StringUtils.isNotBlank(od.getProducttype()) && od.getProducttype().equalsIgnoreCase(productTypeId)) {
                    orderDetail = od;
                    break;
                } else if (StringUtils.isBlank(od.getProducttype()) && StringUtils.isBlank(productTypeId)) {
                    orderDetail = od;
                    break;
                }

            }
        }
        Integer lockProductNum = 0;
        if (orderDetail != null) {
            lockProductNum = orderDetail.getUpnum() + orderDetail.getSpnum();
        }
        return lockProductNum;
    }


    public void shoppingCartProductBasicInfoValidate(ShoppingCartProduct shoppingCartProduct) throws ServiceTransactionException {
        if (shoppingCartProduct.getSalePrice() != null) {
            if (shoppingCartProduct.getSalePrice().compareTo(BigDecimal.ZERO) == -1) {
                throw new ServiceTransactionException("价格录入格式有误");
            }
        }
        if (shoppingCartProduct.getProductQuantity() != null) {
            if (shoppingCartProduct.getProductQuantity() < 1) {
                throw new ServiceTransactionException("数量不能等于小于零");
            }
        }
        if (shoppingCartProduct.getPoint() != null) {
            if (shoppingCartProduct.getPoint().compareTo(BigDecimal.ZERO) < 0) {
                throw new ServiceTransactionException("积分不能小于零");
            }
            try {
                Integer.parseInt(shoppingCartProduct.getPoint().toString());
            } catch (Exception e) {
                throw new ServiceTransactionException("积分不能为小数");
            }
        }

    }
}
