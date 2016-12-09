package com.chinadrtv.erp.uc.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-8-2
 * Time: 下午4:38
 * 套装处理，购物车自动组套中间模型用于保存购物车中每样产品及套装拆套后的产品
 */
public class ProductSuiteDto {
    private String total;//套装内组成产品和
    private String prodsuitescmid;//套装12ID
    private Map<String, Integer> productCodeMap = new HashMap<String, Integer>(); //购物车产品key:12位id，value：数量
    private Map<String, Integer> singleProductCodeMap = new HashMap<String, Integer>();  //购物车拆套  单品key:12位id，value：数量

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProdsuitescmid() {
        return prodsuitescmid;
    }

    public void setProdsuitescmid(String prodsuitescmid) {
        this.prodsuitescmid = prodsuitescmid;
    }

    public Map<String, Integer> getProductCodeMap() {
        return productCodeMap;
    }

    public void setProductCodeMap(Map<String, Integer> productCodeMap) {
        this.productCodeMap = productCodeMap;
    }

    public Map<String, Integer> getSingleProductCodeMap() {
        return singleProductCodeMap;
    }

    public void setSingleProductCodeMap(Map<String, Integer> singleProductCodeMap) {
        this.singleProductCodeMap = singleProductCodeMap;
    }
}


