package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.core.service.ProductService;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;
import com.chinadrtv.erp.uc.dao.PluSplitDao;
import com.chinadrtv.erp.uc.dao.ShoppingCartDao;
import com.chinadrtv.erp.uc.dto.ProductSuiteDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.SCMpromotionService;
import com.chinadrtv.erp.uc.service.ShoppingCartService;
import com.chinadrtv.erp.uc.util.ShoppingCartProductCombination;
import com.chinadrtv.erp.uc.util.ShoppingCartProductValidate;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.*;

/**
 * 购物车ServiceImpl
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-13 下午12:32:19
 */
@Service("ShoppingCartServiceImpl")
public class ShoppingCartServiceImpl extends
        GenericServiceImpl<ShoppingCart, Long> implements ShoppingCartService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private PluSplitDao pluSplitDao;
    @Autowired
    private SCMpromotionService sCMpromotionService;
    @Autowired
    private PlubasInfoService plubasInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartProductValidate shoppingCartProductValidate;
    @Autowired
    private ShoppingCartProductCombination shoppingCartProductCombination;
    @Autowired
    private OrderDelDetailService orderDelDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    private static final Integer GIFT = 3;

    protected GenericDao<ShoppingCart, Long> getGenericDao() {
        return shoppingCartDao;
    }

    public ShoppingCart createShoppingCart(Long contactId, String userId, String cartType) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setContactId(contactId);
        shoppingCart.setCartType(cartType);
        shoppingCart.setCreateUser(userId);
        shoppingCart.setCreateDate(new Date());
        return shoppingCartDao.save(shoppingCart);
    }

    public ShoppingCart findShoppingCartByContactId(Long contactId, String cartType) {
        if (StringUtils.isBlank(cartType)) {
            cartType = ShoppingCart.CART;
        }
        return shoppingCartDao.findShoppingCartByContactId(contactId, cartType);
    }

    public ShoppingCart findShoppingCartById(Long shoppingCartId) {
        return shoppingCartDao.findShoppingCartById(shoppingCartId);
    }

    public ShoppingCartProduct findShoppingCartProductById(Long shoppingCartProductId) {
        return shoppingCartDao.findShoppingCartProductById(shoppingCartProductId);
    }

    public void deleteShoppingCartByContactId(Long contactId, String cartType) {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartByContactId(contactId, cartType);
        if (shoppingCart != null) {
            shoppingCartDao.deleteShoppingCartById(shoppingCart.getId());
        }
    }

    /**
     * 清空购物车选中商品
     *
     * @param shoppingCartId
     */
    public void deleteShoppingCartById(Long shoppingCartId) {
        logger.info("deleteShoppingCartById:  id=" + shoppingCartId);
        Integer count = shoppingCartDao.deleteShoppingCartSelectProducts(shoppingCartId);
        logger.info("deleteShoppingCartSelectProductsCount:  count=" + count);
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        logger.info("findShoppingCartById:  find");
        if (shoppingCart != null) {
            logger.info("deleteShoppingCartById:  shoppingCart is not null");
            if (shoppingCart.getShoppingCartProducts().isEmpty()) {
                shoppingCartDao.deleteShoppingCartById(shoppingCartId);
            } else if (count == shoppingCart.getShoppingCartProducts().size()) {
                shoppingCartDao.deleteShoppingCartById(shoppingCartId);
            }
        }
    }

    /**
     * 添加购物车产品
     *
     * @param shoppingCartId
     * @param userId
     * @param shoppingCartProduct
     * @return
     * @throws ErpException
     */
    public Map<String, Object> addShoppingCartProduct(Long shoppingCartId, String userId, ShoppingCartProduct shoppingCartProduct) throws ErpException {
        Map<String, Object> result = this.addShoppingCartProduct(shoppingCartId, userId, shoppingCartProduct, true);
        return result;
    }

    //添加购物车产品
    private Map<String, Object> addShoppingCartProduct(Long shoppingCartId, String userId, ShoppingCartProduct shoppingCartProduct, boolean combination) throws ErpException {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        //默认直接销售，只要包含直接销售 其他全搭销
        if (shoppingCartProduct.getIsGift() != GIFT) {
            for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
                if (scp.getIsGift() == 1) {
                    shoppingCartProduct.setIsGift(2);
                    break;
                }
            }
        }

        PlubasInfo plubasInfo = shoppingCartProductValidate.realTimeStockCheckShoppingCartProduct(shoppingCart, shoppingCartProduct);
        if (shoppingCartProduct.getIsGift() == GIFT) {
            List<ScmPromotion> scmPromotionList = this.getScmPromotions(shoppingCart, userId);
            boolean validateResult = this.shoppingCartProductValidate(scmPromotionList, shoppingCartProduct);
            if (!validateResult) {
                throw new ServiceTransactionException("当前商品不可作为赠品,产品号为:" + shoppingCartProduct.getSkuCode());
            }
        }

        shoppingCartProduct.setSkuCode(plubasInfo.getNccode());
        shoppingCartProduct.setProductType(plubasInfo.getNcfreename());
        shoppingCartProduct.setProductCode(plubasInfo.getPlucode());
        shoppingCartProduct.setProductName(getProductName(plubasInfo));
        shoppingCartProduct.setParentId(Long.parseLong(plubasInfo.getIssuiteplu()));
        shoppingCartDao.addShoppingCartProduct(shoppingCartId, shoppingCartProduct);

        if (combination) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            boolean reload = combinationShoppingCart(shoppingCartId, userId, shoppingCartProduct, new ArrayList<String>());
            resultMap.put("reload", reload);
            return resultMap;
        }

        return null;
    }

    //购物车自动组套
    private boolean combinationShoppingCart(Long shoppingCartId, String userId, ShoppingCartProduct shoppingCartProduct, List<String> unAddProductCodeList) throws ErpException {
        if (shoppingCartId != null || shoppingCartProduct != null) {
            return false;
        }
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        boolean validateCombination = validateCombination(shoppingCart);
        if (!validateCombination) {
            return false;
        }
        //根据组合种类解析
        List<ProductSuiteDto> combinationList = shoppingCartProductCombination.getProductSuiteDtoList(shoppingCart, shoppingCartProduct.getProductCode());
        List<String> totalList = this.getTotalList(combinationList);
        List<ProductSuiteDto> productSuiteDtoResultList = pluSplitDao.searchProductSuiteIdList(totalList);
        if (productSuiteDtoResultList == null || productSuiteDtoResultList.isEmpty()) {
            return false;
        }

        boolean combination = false;
        for (ProductSuiteDto productSuiteDto : productSuiteDtoResultList) {
            if (unAddProductCodeList.contains(productSuiteDto.getProdsuitescmid())) {
                continue;
            }

            ProductSuiteDto shoppingCartProductSuiteDto = this.getProductSuiteDto(combinationList, productSuiteDto.getTotal());
            if (shoppingCartProductSuiteDto == null) {
                continue;
            }
            //使用单品判断
            Map<String, Integer> singleProductCodeMap = shoppingCartProductSuiteDto.getSingleProductCodeMap();

            //查询套装组成项
            List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(productSuiteDto.getProdsuitescmid());
            if (productSuiteTypeList == null || productSuiteTypeList.isEmpty()) {
                continue;
            }
            boolean isMatching = getMatchingCount(singleProductCodeMap, productSuiteTypeList);
            if (isMatching) {
                if (productSuiteTypeList.size() == 1 && productSuiteDto.getProdsuitescmid().equals(productSuiteTypeList.get(0).getProductSuiteTypeId().getProdScmId())) {
                    continue;
                }
                int combinationCount = getMaxCombinationCount(shoppingCart, shoppingCartProductSuiteDto);
                PlubasInfo plubasInfo = plubasInfoService.getPlubasInfo(productSuiteDto.getProdsuitescmid());
                ShoppingCartProduct newShoppingCartProduct = new ShoppingCartProduct();
                newShoppingCartProduct.setSkuCode(plubasInfo.getNccode());
                newShoppingCartProduct.setProductType(plubasInfo.getNcfreename());
                newShoppingCartProduct.setIsGift(1);
                newShoppingCartProduct.setProductQuantity(combinationCount);
                newShoppingCartProduct.setCreateUser(userId);
                newShoppingCartProduct.setCreateDate(new Date());
                newShoppingCartProduct.setIsSelected(true);
                newShoppingCartProduct.setSalePrice(new BigDecimal(plubasInfo.getSlprc()));
                try {
                    //验证  删除 添加
                    shoppingCartProductValidate.realTimeStockCheckShoppingCartProduct(shoppingCart, newShoppingCartProduct);
                    this.removeShoppingCartProduct(shoppingCart, shoppingCartProductSuiteDto.getProductCodeMap(), combinationCount);
                    this.addShoppingCartProduct(shoppingCartId, userId, newShoppingCartProduct, false);
                    combination = true;
                } catch (Exception e) {
                    //成套后无法添加的商品记录记录
                    unAddProductCodeList.add(plubasInfo.getPlucode());
                }
            }
        }
        if (combination) {
            combinationShoppingCart(shoppingCartId, userId, shoppingCartProduct, unAddProductCodeList);
            return true;
        }

        return false;
    }

    //判断是否匹配
    private boolean getMatchingCount(Map<String, Integer> singleProductCodeMap, List<ProductSuiteType> productSuiteTypeList) {
        int matchingCount = 0;
        for (ProductSuiteType productSuiteType : productSuiteTypeList) {
            String prodScmId = productSuiteType.getProductSuiteTypeId().getProdScmId();
            Integer prodNum = singleProductCodeMap.get(prodScmId);
            if (singleProductCodeMap.containsKey(prodScmId) && prodNum >= productSuiteType.getProdNum()) {
                matchingCount++;
            } else {
                matchingCount = 0;
                break;
            }
        }
        return matchingCount == productSuiteTypeList.size();
    }

    //获取可组合录入的最大数量
    private int getMaxCombinationCount(ShoppingCart shoppingCart, ProductSuiteDto shoppingCartProductSuiteDto) {
        int combinationCount = 0;
        Map<String, Integer> productCodeMap = shoppingCartProductSuiteDto.getProductCodeMap();
        for (String pluCode : shoppingCartProductSuiteDto.getProductCodeMap().keySet()) {
            int tempProductQuantity = 0;
            for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
                if (pluCode.equals(scp.getProductCode())) {
                    tempProductQuantity += scp.getProductQuantity();
                }
            }
            int temp = tempProductQuantity / productCodeMap.get(pluCode);
            if (combinationCount == 0 || temp < combinationCount) {
                combinationCount = temp;
            }
        }
        return combinationCount;
    }

    //判断是否可组套
    private boolean validateCombination(ShoppingCart shoppingCart) {
        if (shoppingCart.getShoppingCartProducts().size() == 1) {
            return false;
        }
        int count = 0;
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            count += scp.getProductQuantity();
        }
        if (count > 100) {
            return false;
        }
        if (count > 50 && shoppingCart.getShoppingCartProducts().size() > 4) {
            return false;
        }
        return true;
    }

    private List<String> getTotalList(List<ProductSuiteDto> productSuiteDtoList) {
        List<String> totalList = new ArrayList<String>();
        for (ProductSuiteDto productSuiteDto : productSuiteDtoList) {
            if (!totalList.contains(productSuiteDto.getTotal())) {
                totalList.add(productSuiteDto.getTotal());
            }
        }
        return totalList;
    }

    //删除固定数量的购物车产品
    private void removeShoppingCartProduct(ShoppingCart shoppingCart, Map<String, Integer> productCodeMap, Integer combinationCount) {
        Set<ShoppingCartProduct> remainSet = new HashSet<ShoppingCartProduct>();
        for (String key : productCodeMap.keySet()) {
            int deleteCount = productCodeMap.get(key) * combinationCount;
            for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
                if (key.equals(scp.getProductCode()) && scp.getIsGift() != GIFT) {
                    if (deleteCount < scp.getProductQuantity()) {
                        scp.setProductQuantity(scp.getProductQuantity() - deleteCount);
                        remainSet.add(scp);
                    } else {
                        deleteCount = deleteCount - scp.getProductQuantity();
                    }
                } else {
                    remainSet.add(scp);
                }
            }
        }

        shoppingCart.getShoppingCartProducts().clear();
        shoppingCart.getShoppingCartProducts().addAll(remainSet);
        shoppingCartDao.saveOrUpdate(shoppingCart);
    }

    private String getProductName(PlubasInfo plubasInfo) {
        Product product = productService.query(plubasInfo.getNccode());
        if (product != null && StringUtils.isNotBlank(product.getProdname())) {
            return product.getProdname();
        } else {
            if ("0".equalsIgnoreCase(plubasInfo.getIssuiteplu())) {
                return plubasInfo.getSpellname();
            } else {
                return plubasInfo.getPluname();
            }
        }
    }

    private ProductSuiteDto getProductSuiteDto(List<ProductSuiteDto> productSuiteDtoList, String total) {
        for (ProductSuiteDto productSuiteDto : productSuiteDtoList) {
            if (total.equals(productSuiteDto.getTotal())) {
                return productSuiteDto;
            }
        }

        return null;
    }


    //促销产品检查
    private boolean shoppingCartProductValidate(List<ScmPromotion> scpPromotinos, ShoppingCartProduct scp) {
        boolean isExistInScm = checkGiftProductByScmPromotions(scpPromotinos, scp.getSkuCode());
        boolean isExistInPro = checkGiftProductByPlubasInfoDao(scp.getSkuCode(), scp.getProductType());

        return isExistInScm || isExistInPro;
    }

    //促销产品根据产品规则检查
    private boolean checkGiftProductByPlubasInfoDao(String skuCode, String productType) {
        List<PlubasInfo> pluBasInfos = plubasInfoService.getPlubasInfos(skuCode, null, null, productType);
        if (pluBasInfos == null || pluBasInfos.isEmpty()) {
            return false;
        }
        double pluBaseInfoPresent = 1d;
        if (pluBasInfos.get(0).getPresent().equals(pluBaseInfoPresent)) {
            return true;
        }
        return false;
    }

    //促销产品根据促销规则检查
    private boolean checkGiftProductByScmPromotions(List<ScmPromotion> scpPromotinos, String skuCode) {
        if (scpPromotinos == null || scpPromotinos.isEmpty()) {
            return false;
        }
        for (ScmPromotion scp : scpPromotinos) {
            if (skuCode.equals(scp.getPluid())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新购物车产品
     *
     * @param shoppingCartProduct
     * @param userId
     * @return 不满足规则的产品
     * @throws Exception
     */
    public Map<String, Object> updateShoppingCartProduct(
            ShoppingCartProduct shoppingCartProduct, String userId) throws ErpException {
        shoppingCartProductValidate.shoppingCartProductBasicInfoValidate(shoppingCartProduct);

        ShoppingCartProduct persistentScp = shoppingCartDao.findShoppingCartProductById(shoppingCartProduct.getId());
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(persistentScp.getShoppingCart().getId());
        ShoppingCartProduct copyProduct = new ShoppingCartProduct();
        BeanUtils.copyProperties(persistentScp, copyProduct);
        this.updateShoppingCartProductForValidate(copyProduct, shoppingCartProduct);
        shoppingCartProductValidate.realTimeStockCheckShoppingCartProduct(shoppingCart, copyProduct);
        //返回重复的购物车产品
        ShoppingCartProduct product = this.margeShoppingCartProduct(persistentScp, shoppingCartProduct, shoppingCart);
        ShoppingCartProduct cartProduct = null;
        List<ShoppingCartProduct> list = new ArrayList<ShoppingCartProduct>();
        if (product == null) {
            cartProduct = persistentScp;
            persistentScp.setUpdateDate(new Date());
            persistentScp.setUpdateUser(userId);
            shoppingCartDao.updateShoppingCartProduct(persistentScp);
        } else {
            cartProduct = product;
            list.add(persistentScp);
            shoppingCartDao.deleteShoppingCartProductId(persistentScp.getId());
            product.setUpdateDate(new Date());
            product.setUpdateUser(userId);
            shoppingCartDao.updateShoppingCartProduct(product);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        //删除不满足促销规则的促销品
        List<ShoppingCartProduct> allDeleteProducts = this.deleteScmPromotions(userId, shoppingCart);
        allDeleteProducts.addAll(list);
        resultMap.put("list", allDeleteProducts);

        //购物车自动组套
        List<String> unAddProductCodeList = new ArrayList<String>();
        boolean reload = combinationShoppingCart(shoppingCart.getId(), userId, cartProduct, unAddProductCodeList);
        resultMap.put("reload", reload);
        return resultMap;
    }

    private void updateShoppingCartProductForValidate(ShoppingCartProduct copyProduct, ShoppingCartProduct shoppingCartProduct) {
        if (shoppingCartProduct.getIsSelected() != null) {
            copyProduct.setIsSelected(shoppingCartProduct.getIsSelected());
        }
        if (StringUtils.isNotBlank(shoppingCartProduct.getProductType())) {
            copyProduct.setProductType(shoppingCartProduct.getProductType());
        }
        if (shoppingCartProduct.getSalePrice() != null) {
            copyProduct.setSalePrice(shoppingCartProduct.getSalePrice());
        }
        if (shoppingCartProduct.getProductQuantity() != null) {
            copyProduct.setProductQuantity(shoppingCartProduct.getProductQuantity());
        }
        if (shoppingCartProduct.getIsGift() != null) {
            copyProduct.setIsGift(shoppingCartProduct.getIsGift());
        }
        if (shoppingCartProduct.getPoint() != null) {
            copyProduct.setPoint(shoppingCartProduct.getPoint());
        }
    }

    private List<ShoppingCartProduct> deleteScmPromotions(String userId, ShoppingCart shoppingCart) {
        List<ShoppingCartProduct> allDeleteProducts = new ArrayList<ShoppingCartProduct>();
        List<ScmPromotion> scmPromotionList = this.getScmPromotions(shoppingCart, userId);
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (scp.getIsGift() == GIFT) {
                boolean validateGiftSucc = shoppingCartProductValidate(scmPromotionList, scp);
                if (!validateGiftSucc) {
                    allDeleteProducts.add(scp);
                    shoppingCartDao.deleteShoppingCartProductId(scp.getId());
                }
            }
        }
        return allDeleteProducts;
    }

    //更新购物车合并
    private ShoppingCartProduct margeShoppingCartProduct(ShoppingCartProduct persistentScp,
                                                         ShoppingCartProduct shoppingCartProduct, ShoppingCart shoppingCart) {
        if (shoppingCartProduct.getIsSelected() != null) {
            persistentScp.setIsSelected(shoppingCartProduct.getIsSelected());
        }
        if (StringUtils.isNotBlank(shoppingCartProduct.getProductType())) {
            persistentScp.setProductType(shoppingCartProduct.getProductType());
            //级联更新productCode
            List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(persistentScp.getSkuCode(), null, null, persistentScp.getProductType());
            PlubasInfo pluBasInfo = plubasInfoList.get(0);
            persistentScp.setProductCode(pluBasInfo.getPlucode());
        }
        if (shoppingCartProduct.getSalePrice() != null) {
            persistentScp.setSalePrice(shoppingCartProduct.getSalePrice());
        }
        if (shoppingCartProduct.getProductQuantity() != null) {
            persistentScp.setProductQuantity(shoppingCartProduct.getProductQuantity());
        }
        if (shoppingCartProduct.getIsGift() != null) {
            persistentScp.setIsGift(shoppingCartProduct.getIsGift());
        }
        if (shoppingCartProduct.getPoint() != null) {
            persistentScp.setPoint(shoppingCartProduct.getPoint());
        }
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (!scp.getId().equals(persistentScp.getId()) && scp.getProductCode().equals(persistentScp.getProductCode()) && scp.getIsGift().equals(persistentScp.getIsGift())) {
                scp.setProductQuantity(persistentScp.getProductQuantity() + scp.getProductQuantity());
                return scp;
            }
        }
        return null;
    }

    /**
     * 删除购物车产品
     *
     * @param shoppingCartId
     * @param shoppingCartProductId
     * @param userId
     * @return 级联删除产品列表
     * @throws ErpException
     */
    public List<ShoppingCartProduct> deleteShoppingCartProductById(Long shoppingCartId, Long shoppingCartProductId, String userId) throws ErpException {
        shoppingCartDao.deleteShoppingCartProductId(shoppingCartProductId);

        // 根据  购物车产品id获取获取购物车
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        List<ShoppingCartProduct> allDeleteProducts = this.deleteScmPromotions(userId, shoppingCart);
        return allDeleteProducts;
    }

    /**
     * 计算购物车商品数量
     *
     * @param shoppingCartId
     * @return
     */
    public Long getShoppingCartProductQuantity(Long shoppingCartId) {
        return shoppingCartDao.getShoppingCartProductQuantity(shoppingCartId);
    }

    /**
     * 计算购物车商品金额
     *
     * @param shoppingCartId
     * @return
     */
    public BigDecimal getShoppingCartSalePrice(Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        return calculateShoppingCartPrice(shoppingCart);
    }

    private BigDecimal calculateShoppingCartPrice(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = new BigDecimal(0);
        for (ShoppingCartProduct shoppingCartProduct : shoppingCart.getShoppingCartProducts()) {
            if (shoppingCartProduct.getIsSelected()) {
                if (shoppingCartProduct.getPoint() != null && shoppingCartProduct.getPoint() != BigDecimal.ZERO) {
                    BigDecimal singlePoint = shoppingCartProduct.getPoint().divide(new BigDecimal(shoppingCartProduct.getProductQuantity()), 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal result = shoppingCartProduct.getSalePrice().subtract(singlePoint).multiply(new BigDecimal(shoppingCartProduct.getProductQuantity()));
                    bigDecimal = bigDecimal.add(result);
                } else {
                    BigDecimal result = shoppingCartProduct.getSalePrice().multiply(new BigDecimal(shoppingCartProduct.getProductQuantity()));
                    bigDecimal = bigDecimal.add(result);
                }
            }
        }
        return bigDecimal;
    }

    /**
     * 查询购物车促销结果
     *
     * @param shoppingCart
     * @return
     */
    public List<ScmPromotion> getScmPromotions(ShoppingCart shoppingCart, String userId) {
        if (shoppingCart == null || shoppingCart.getShoppingCartProducts().isEmpty()) {
            return new ArrayList<ScmPromotion>();
        }

        StringBuffer prodidBuffer = new StringBuffer();
        StringBuffer moneyBuffer = new StringBuffer();
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (scp.getIsSelected() && scp.getIsGift() != GIFT) {
                prodidBuffer.append(scp.getSkuCode()).append(",");
                moneyBuffer.append(scp.getSalePrice().multiply(new BigDecimal(scp.getProductQuantity()))).append(",");
            }
        }
        String prodids = prodidBuffer.toString();
        String moneys = moneyBuffer.toString();
        if (StringUtil.isNullOrBank(prodids) || StringUtil.isNullOrBank(moneys)) {
            return new ArrayList<ScmPromotion>();
        }

        prodids = prodids.substring(0, prodidBuffer.length() - 1);
        moneys = moneys.substring(0, moneyBuffer.length() - 1);
        try {
            return sCMpromotionService.getCmsPromotion(prodids, moneys, userId, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e); //
            e.printStackTrace();
        }
        return new ArrayList<ScmPromotion>();
    }

    public Order createOrderbyShoppingCart(Long shoppingCartId) {
        AgentUser user = SecurityHelper.getLoginUser();
        return this.createOrderbyShoppingCart(shoppingCartId, user.getUserId(), user.getDefGrp());

    }

    /**
     * 购物车生成订单
     *
     * @param shoppingCartId
     * @return
     */
    public Order createOrderbyShoppingCart(Long shoppingCartId, String userId, String grpId) {
        return this.createOrder(shoppingCartId, false, userId, grpId);
    }

    /**
     * 购物车创建运费计算订单
     *
     * @param shoppingCartId
     * @return
     */
    public Order createMailOrderbyShoppingCart(Long shoppingCartId, String userId, String grpId) {
        return this.createOrder(shoppingCartId, true, userId, grpId);
    }

    private Order createOrder(Long shoppingCartId, boolean mailPriceOrder, String userId, String grpId) {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        if (shoppingCart == null) {
            return null;
        }
        List<OrderDetail> oldOrderDetailList = new ArrayList<OrderDetail>();
        if (ShoppingCart.ORDER.equals(shoppingCart.getCartType())) {
            try {
                oldOrderDetailList = orderDelDetailService.queryOrderdetbyorid(shoppingCart.getOrderid());
            } catch (SQLException e) {
            }
        }

        Order orderhist = new Order();
        orderhist.setContactid(String.valueOf(shoppingCart.getContactId()));
        orderhist.setCrusr(userId);
        orderhist.setCrdt(new Date());
        orderhist.setTicket(0);
        orderhist.setTicketcount(0);
        orderhist.setBill("1");
        orderhist.setResult("1");
        orderhist.setProdprice(this.calculateShoppingCartPrice(shoppingCart));
        orderhist.setGrpid(grpId);

        try {
            String areaCode = userService.getGroupAreaCode(userId);
            orderhist.setAreacode(areaCode);
        } catch (Exception e) {
            logger.error("查询groupAreaCode出错",e);
        }

        List<ScmPromotion> scmPromotions = null;
        Set<OrderDetail> orderdets = new HashSet<OrderDetail>();
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (!scp.getIsSelected()) {
                continue;
            }
            List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(scp.getSkuCode(), null, null, scp.getProductType());
            if (plubasInfoList == null || plubasInfoList.isEmpty()) {
                continue;
            }
            PlubasInfo plubasInfo = plubasInfoList.get(0);
            String productTypeId = pluSplitDao.getProductTypeId(plubasInfo.getNccode(), plubasInfo.getNcfreename());
            Product product = productService.query(plubasInfo.getNccode());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setAccountingcost(BigDecimal.ZERO);
            if (product != null && product.getDiscount() != null) {
                orderDetail.setAccountingcost(product.getDiscount());
            }
            orderDetail.setContactid(String.valueOf(shoppingCart.getContactId()));
            orderDetail.setProdscode(plubasInfo.getSpellcode());
            orderDetail.setOrderhist(orderhist);
            orderDetail.setProdid(scp.getSkuCode());
            if (mailPriceOrder && product != null) {
                orderDetail.setProdname(product.getProdname());
            } else {
                orderDetail.setProdname(plubasInfo.getPluname());
            }
            if (StringUtils.isNotBlank(productTypeId)) {
                orderDetail.setProducttype(productTypeId);
            }
            orderDetail.setSoldwith(String.valueOf(scp.getIsGift()));

            this.orderDetailPriceField(scp, plubasInfo, orderDetail);
            orderDetail.setOrderdt(new Date());
            orderDetail.setTicket(0);

            if (!oldOrderDetailList.isEmpty()) {
                orderDetail.setProvinceid(oldOrderDetailList.get(0).getProvinceid());
                orderDetail.setState(oldOrderDetailList.get(0).getState());
                orderDetail.setCity(oldOrderDetailList.get(0).getCity());
            }

            if (scp.getIsGift() == GIFT) {
                if (scmPromotions == null) {
                    scmPromotions = this.getScmPromotions(shoppingCart, userId);
                }
                ScmPromotion scmPromotion = getScmPromotion(scmPromotions, scp.getSkuCode());
                if (scmPromotion != null) {
                    orderDetail.setSpid(String.valueOf(scmPromotion.getRuid()));
                    orderDetail.setPromotionsdocno(scmPromotion.getDocno());
                    orderDetail.setPromotionsdetruid(Integer.valueOf(scmPromotion.getRuiddet()));
                    orderhist.setSpid(String.valueOf(scmPromotion.getRuid()));
                }
            }

            orderdets.add(orderDetail);
        }
        orderhist.setOrderdets(orderdets);
        return orderhist;
    }

    private ScmPromotion getScmPromotion(List<ScmPromotion> scmPromotions, String skuCode) {
        for (ScmPromotion scmPromotion : scmPromotions) {
            if (skuCode.equals(scmPromotion.getPluid())) {
                return scmPromotion;
            }
        }
        return null;
    }

    private void orderDetailPriceField(ShoppingCartProduct scp, PlubasInfo plubasInfo, OrderDetail orderDetail) {
        BigDecimal tempPrice = new BigDecimal(plubasInfo.getSlprc());
        if (scp.getPoint() != null && BigDecimal.ZERO.compareTo(scp.getPoint()) != 0) {
            orderDetail.setJifen(scp.getPoint().toString());
            BigDecimal averagePoint = scp.getPoint().divide(new BigDecimal(scp.getProductQuantity()), 2, BigDecimal.ROUND_HALF_UP);
            orderDetail.setSprice(scp.getSalePrice().subtract(averagePoint).setScale(2, RoundingMode.HALF_UP));
            orderDetail.setSpnum(scp.getProductQuantity());
            orderDetail.setUprice(tempPrice.setScale(2, RoundingMode.HALF_UP));
            orderDetail.setUpnum(0);
        } else {
            orderDetail.setJifen("0");
            if (plubasInfo.getSlprc().equals(scp.getSalePrice().doubleValue())) {
                orderDetail.setUprice(scp.getSalePrice().setScale(2, RoundingMode.HALF_UP)); //产品原价
                orderDetail.setUpnum(scp.getProductQuantity());//原价订购个数
                orderDetail.setSprice(scp.getSalePrice().setScale(2, RoundingMode.HALF_UP));
                orderDetail.setSpnum(0);
            } else {
                orderDetail.setSprice(scp.getSalePrice().setScale(2, RoundingMode.HALF_UP));
                orderDetail.setSpnum(scp.getProductQuantity());
                orderDetail.setUprice(tempPrice.setScale(2, RoundingMode.HALF_UP));
                orderDetail.setUpnum(0);
            }
        }
        BigDecimal uPrice = orderDetail.getUprice().multiply(new BigDecimal(orderDetail.getUpnum()));
        BigDecimal sPrice = orderDetail.getSprice().multiply(new BigDecimal(orderDetail.getSpnum()));
        orderDetail.setPayment(uPrice.add(sPrice).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 订单生成购物车
     *
     * @param order
     * @return
     */
    public ShoppingCart createShoppingCartbyOrder(Order order) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setContactId(Long.parseLong(order.getContactid()));
        shoppingCart.setCreateUser(order.getCrusr());
        shoppingCart.setCreateDate(order.getCrdt());
        shoppingCart.setUpdateUser(order.getMdusr());
        shoppingCart.setUpserDate(order.getMddt());
        shoppingCart.setCartType(ShoppingCart.ORDER);
        shoppingCart.setOrderid(order.getOrderid());
        Set<ShoppingCartProduct> scpSet = new HashSet<ShoppingCartProduct>();
        if (order.getOrderdets() != null) {
            for (OrderDetail orderDetail : order.getOrderdets()) {
                String productType = "";
                if (StringUtils.isNotBlank(orderDetail.getProducttype())) {
                    productType = pluSplitDao.searchProductType(orderDetail.getProducttype());
                }
                List<PlubasInfo> pluBasInfos = plubasInfoService.getPlubasInfos(orderDetail.getProdid(), null, null, productType);
                if (pluBasInfos == null || pluBasInfos.isEmpty()) {
                    continue;
                }
                PlubasInfo pluBasInfo = pluBasInfos.get(0);
                Product product = productService.query(orderDetail.getProdid());

                ShoppingCartProduct scp = new ShoppingCartProduct();
                if (product != null && StringUtils.isNotBlank(product.getProdname())) {
                    scp.setProductName(product.getProdname());
                } else {
                    if ("0".equalsIgnoreCase(pluBasInfo.getIssuiteplu())) {
                        scp.setProductName(pluBasInfo.getSpellname());
                    } else {
                        scp.setProductName(pluBasInfo.getPluname());
                    }
                }
                scp.setShoppingCart(shoppingCart);
                scp.setCreateUser(order.getCrusr());
                scp.setCreateDate(new Date());
                scp.setSkuCode(orderDetail.getProdid());
                scp.setProductType(productType);
                scp.setIsGift(Integer.parseInt(orderDetail.getSoldwith()));
                scp.setIsSelected(true);
                scp.setProductCode(pluBasInfo.getPlucode());
                scp.setParentId(Long.parseLong(pluBasInfo.getIssuiteplu()));
                if (orderDetail.getSpnum() != 0) {
                    scp.setSalePrice(orderDetail.getSprice());
                    scp.setProductQuantity(orderDetail.getSpnum());
                } else {
                    scp.setSalePrice(orderDetail.getUprice());
                    scp.setProductQuantity(orderDetail.getUpnum());
                }
                if (StringUtils.isNotBlank(orderDetail.getJifen())) {
                    BigDecimal point = new BigDecimal(orderDetail.getJifen());
                    BigDecimal average = point.divide(new BigDecimal(scp.getProductQuantity()), 2, BigDecimal.ROUND_HALF_UP);
                    scp.setSalePrice(scp.getSalePrice().add(average));
                }

                if (StringUtils.isNotEmpty(orderDetail.getJifen())) {
                    scp.setPoint(new BigDecimal(orderDetail.getJifen()));
                }
                scpSet.add(scp);
            }
        }
        shoppingCart.setShoppingCartProducts(scpSet);
        this.deleteShoppingCartByContactId(Long.parseLong(order.getContactid()), ShoppingCart.ORDER);
        this.save(shoppingCart);
        return this.findShoppingCartByContactId(Long.parseLong(order.getContactid()), ShoppingCart.ORDER);

    }

    /**
     * 购物车升级
     *
     * @param potentialContactCode 潜客
     * @param contactCode
     */
    public void updateShoppingCartContact(Long potentialContactCode, Long contactCode) {
        shoppingCartDao.updateShoppingCartContact(potentialContactCode, contactCode);
    }

    /**
     * 购物车验证
     *
     * @param shoppingCartId
     * @throws ErpException
     */
    public void validateShoppingCart(Long shoppingCartId, String userId, String grpId, Boolean approveManager) throws ErpException {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        shoppingCartProductValidate.realTimeStockCheckShoppingCart(shoppingCart);
        int directCount = 0;  //直销产品
        int tyingRestrictionsCount = 0; //搭销产品
        int insureCount = 0;   //保险产品检测 保险产品不能作为主销品。数量不能大于2
        //   1主销2搭销3赠品
        List<ScmPromotion> scpPromotinos = this.getScmPromotions(shoppingCart, userId);
        List<Product> insureList = shoppingCartDao.getInsureList(grpId);

        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (!scp.getIsSelected()) {
                continue;
            }
            if (1 == scp.getIsGift()) {
                directCount++;
            } else if (2 == scp.getIsGift()) {
                tyingRestrictionsCount++;

            } else {
                boolean validateGiftSucc = this.shoppingCartProductValidate(scpPromotinos, scp);
                if (!validateGiftSucc) {
                    throw new ServiceTransactionException("当前商品不可作为赠品,商品名称为:" + scp.getProductName());
                }
            }

            if (insureList != null && !insureList.isEmpty()) {
                for (Product product : insureList) {
                    if (scp.getSkuCode().equals(product.getProdid())) {
                        if (shoppingCart.getShoppingCartProducts().size() == 1) {
                            throw new ServiceTransactionException("保险产品不能单独下单");
                        }
                        if (scp.getProductQuantity() > 1) {
                            throw new ServiceTransactionException("保险产品数量大于1");
                        }
                        insureCount++;
                    }
                }
            }
        }
        if (directCount > 1) {
            throw new ServiceTransactionException("只能存在一种主销商品，无法保存订单");
        }
        if (directCount == 0 && tyingRestrictionsCount > 0) {
            throw new ServiceTransactionException("无主销商品，无法保存订单");
        }
        if (insureCount > 1) {
            throw new ServiceTransactionException("保险产品数量大于1");
        }

        //加入赠险产品使用控制
        if (insureCount == 1) {
            Parameters parameters = shoppingCartDao.getInsureDays();
            Integer insureDay = 90;
            if (parameters != null && StringUtils.isNotBlank(parameters.getParam())) {
                insureDay = Integer.valueOf(parameters.getParam());
            }
            List<String> productIdlist = shoppingCartDao.getUsedInsure(String.valueOf(shoppingCart.getContactId()), insureDay, shoppingCart.getOrderid());
            if (productIdlist != null && !productIdlist.isEmpty()) {
                throw new ServiceTransactionException(insureDay + "天内已经赠送过保险");
            }

            //判断坐席是否有权限
            AgentUser user = SecurityHelper.getLoginUser();
            boolean roleValidate = user.hasRole(SecurityConstants.ROLE_INSURE_PROMPT);
            if (!roleValidate) {
                throw new ServiceTransactionException("保险产品不能销售");
            }
        }

        //用户检测
        String groupType = userService.getGroupType(userId);
        if ("out".equalsIgnoreCase(groupType) && !approveManager) {
            try {
                Contact contact = contactService.get(String.valueOf(shoppingCart.getContactId()));
                //客户审批状态( 0:待审批,1:审批中,2:通过审批,3:未通过审批)
                if (contact.getState() != null && contact.getState() != 2) {
                    throw new ServiceTransactionException("客户无效，需要审批后才可下单");
                }
            } catch (ServiceTransactionException e) {
                throw new ServiceTransactionException("客户无效，需要审批后才可下单");
            } catch (Exception e) {
                throw new ServiceTransactionException("客户无效，需要录入基本信息审批后才可下单");
            }
        }

        if ("in".equalsIgnoreCase(groupType)) {
            try {
                Contact contact = contactService.get(String.valueOf(shoppingCart.getContactId()));
                if (contact != null && contact.getState() != null && contact.getState() != 2) {
                    throw new ServiceTransactionException("客户无效，需要审批后才可下单");
                }
            } catch (ServiceTransactionException e) {
                throw new ServiceTransactionException("客户无效，需要审批后才可下单");
            } catch (Exception e) {
            }
        }

    }

    public void shoppingCartProductSelectChange(Long shoppingCartId, boolean selected) {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        if (shoppingCart.getShoppingCartProducts().isEmpty()) {
            return;
        }
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            scp.setIsSelected(selected);
        }
        shoppingCartDao.saveOrUpdate(shoppingCart);
    }

    public List<Product> getInsureList(String contactId, String grpId, String orderId) {

        Parameters parameters = shoppingCartDao.getInsureDays();
        Integer insureDay = 90;
        if (parameters != null && StringUtils.isNotBlank(parameters.getParam())) {
            insureDay = Integer.valueOf(parameters.getParam());
        }

        List<String> usedInsureList = shoppingCartDao.getUsedInsure(contactId, insureDay, orderId);

        List<Product> productlist = new ArrayList<Product>();
        if (usedInsureList == null || usedInsureList.isEmpty()) {
            productlist = shoppingCartDao.getInsureList(grpId);
            if (productlist != null && !productlist.isEmpty()) {
                for (Product product : productlist) {
                    product.setScript(insureDay + "天只赠送一次");
                }
            }
        }
        return productlist;

    }

    public String insureProductValidate(Long shoppingCartId, String grpId) {
        ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartById(shoppingCartId);
        if (shoppingCart == null || shoppingCart.getShoppingCartProducts().isEmpty()) {
            return "";
        }

        List<Product> insureList = shoppingCartDao.getInsureList(grpId);
        if (insureList == null || insureList.isEmpty()) {
            return "";
        }

        Parameters parameters = shoppingCartDao.getInsureDays();
        Integer insureDay = 90;
        if (parameters != null && StringUtils.isNotBlank(parameters.getParam())) {
            insureDay = Integer.valueOf(parameters.getParam());
        }

        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            for (Product product : insureList) {
                if (scp.getSkuCode().equals(product.getProdid())) {
                    Date date = new Date();
                    long cha = date.getTime() - scp.getCreateDate().getTime();
                    if (cha >= 1000 * 60 * 60 * 24L && cha <= 1000 * 60 * 60 * 24 * insureDay * 1L) {
                        return "(" + insureDay + "天内已经赠送过保险)";
                    }

                }
            }
        }
        return "";
    }

    public List<Product> getUserdInsureProduct(String contactId) {
        Parameters parameters = shoppingCartDao.getInsureDays();
        Integer insureDay = 90;
        if (parameters != null && StringUtils.isNotBlank(parameters.getParam())) {
            insureDay = Integer.valueOf(parameters.getParam());
        }

        List<Product> usedInsureList = shoppingCartDao.getUsedInsure(contactId, insureDay);

        return usedInsureList == null ? new ArrayList<Product>() : usedInsureList;
    }
}
