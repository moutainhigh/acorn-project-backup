package com.chinadrtv.erp.uc.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * Created with IntelliJ IDEA.
 * User: xuzkOrderSkuSplitServiceImpl
 * Date: 13-2-5
 */
public interface OrderSkuSplitService{

    /**
     * 拆分商品为12位商品编码，获取订单详细列表
     *
     * @param orderhist
     * @return List<Map<String,Object>>
     * @throws Exception
     * @Description: 返回　plucode　产品编码, pluname　产品名称, qty　数量, unitprice　单价
     */
    List<Map<String, Object>> orderSkuSplit(Order orderhist) throws Exception;

    /**
     * 根据12位SCM编码返回IAGENT订单商品
     * @param scmId
     * @return Map<String, Object>
     * @throws Exception
     * @Description: 返回　prodid　产品编码, prodtype　产品规格编号
     */
    Map<String, Object> findIagentProduct(String scmId) throws Exception;

    /**
     * 商品拆分Orderdet
     * @param orderdet
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> orderSkuSplit(OrderDetail orderdet) throws Exception;

}
