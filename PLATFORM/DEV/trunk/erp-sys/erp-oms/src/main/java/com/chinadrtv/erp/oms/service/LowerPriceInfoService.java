package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.oms.dto.LowerPriceInfoDto;
import com.chinadrtv.erp.oms.dto.OrderDetailsDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 下午1:09
 * To change this template use File | Settings | File Templates.
 */
public interface LowerPriceInfoService {
    //获取分页数据
    List<LowerPriceInfoDto> getLowerPriceInfoDto(String orderId,String mailId,int index,int size);
    //统计
    Long getCountLowerPriceDto(String orderId,String mailId);
    //折扣商品订单明细
    List<OrderDetailsDto> getOrderDetailsDto(String orderId);

    //折扣编辑数据保存
    void saveLowerPrice(String orderId,String editPrice);

}
