package com.chinadrtv.erp.uc.util;

import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.uc.dao.PluSplitDao;
import com.chinadrtv.erp.uc.dto.ProductSuiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-8-16
 * Time: 上午10:34
 * 购物车自动组套逻辑
 */
@Service("ShoppingCartProductCombination")
public class ShoppingCartProductCombination {
    @Autowired
    private PluSplitDao pluSplitDao;

    /**
     * 获取指定productCode对应购物车的所有产品的组合
     *
     * @param shoppingCart
     * @param shoppingCartProductCode
     * @return
     */
    public List<ProductSuiteDto> getProductSuiteDtoList(ShoppingCart shoppingCart, String shoppingCartProductCode) {
        List<ProductSuiteDto> productSuiteDtoList = new ArrayList<ProductSuiteDto>();
        Map<String,List<String>> singleCodeMap= new HashMap<String,List<String>>();
        Set<String> allCombinationList = this.getProductCodesCombination(shoppingCart, shoppingCartProductCode);

        for (String productCodesStr : allCombinationList) {
            Long total = 0L;
            Map<String, Integer> productCodeMap = new HashMap<String, Integer>();
            Map<String, Integer> singleProductCodeMap = new HashMap<String, Integer>();
            String[] productCodeArray = productCodesStr.split(",");
            for (String productCode : productCodeArray) {
                if (!productCodeMap.keySet().contains(productCode)) {
                    productCodeMap.put(productCode, 1);
                } else {
                    productCodeMap.put(productCode, productCodeMap.get(productCode) + 1);
                }
                //通过购物车判断是否套装，如果是套装需要拆套
                List<String> singleCodeList =null;
                if(singleCodeMap.containsKey(productCode)){
                    singleCodeList = singleCodeMap.get(productCode);
                }else {
                    singleCodeList = this.getSingleProductCodeList(shoppingCart.getShoppingCartProducts(), productCode);
                    singleCodeMap.put(productCode, singleCodeList);
                }

                for (String singleProductCode : singleCodeList) {
                    total += Long.parseLong(singleProductCode);
                    if (!singleProductCodeMap.keySet().contains(singleProductCode)) {
                        singleProductCodeMap.put(singleProductCode, 1);
                    } else {
                        singleProductCodeMap.put(singleProductCode, singleProductCodeMap.get(singleProductCode) + 1);
                    }
                }
            }

            ProductSuiteDto productSuiteDto = new ProductSuiteDto();
            productSuiteDto.setTotal(String.valueOf(total));
            productSuiteDto.setProductCodeMap(productCodeMap);
            productSuiteDto.setSingleProductCodeMap(singleProductCodeMap);

            productSuiteDtoList.add(productSuiteDto);
        }
        return productSuiteDtoList;
    }

    //套装拆套，考虑套装单品数量
    private List<String> getSingleProductCodeList(Set<ShoppingCartProduct> shoppingCartProducts, String productCode) {
        List<String> singleProductCodeList = new ArrayList<String>();
        for (ShoppingCartProduct shoppingCartProduct : shoppingCartProducts) {
            if (productCode.equals(shoppingCartProduct.getProductCode())) {
                if (shoppingCartProduct.getParentId().equals(1L)) {
                    List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(productCode);
                    for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                        for (int i = 0; i < productSuiteType.getProdNum(); i++) {
                            singleProductCodeList.add(productSuiteType.getProductSuiteTypeId().getProdScmId());
                        }
                    }
                } else {
                    singleProductCodeList.add(productCode);
                }
                break;
            }
        }

        return singleProductCodeList;
    }

    //获取购物车中产品的组合种类
    private Set<String> getProductCodesCombination(ShoppingCart shoppingCart, String mainValue) {
        List<String> productCodeList = new ArrayList<String>();
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (scp.getIsGift() == 3) {
                continue;
            }
            for (int i = 0; i < scp.getProductQuantity(); i++) {
                if(mainValue.equals(scp.getProductCode())) {
                    productCodeList.add(0,scp.getProductCode());
                }else {
                    productCodeList.add(scp.getProductCode());
                }
            }
        }

        Set<String> combinationsSet = new HashSet<String>();
        for (String  productCode :productCodeList) {
            combination( productCode, combinationsSet);
        }

        return combinationsSet;
    }

    private   void combination( String  productCode , Set<String> combinationsSet) {
        List<String> newList = new ArrayList<String>();
        for (String s : combinationsSet) {
            newList.add(s + "," + productCode);
        }

        combinationsSet.addAll(newList);

        if(combinationsSet.isEmpty()){
            combinationsSet.add(productCode);
        }
    }
}
