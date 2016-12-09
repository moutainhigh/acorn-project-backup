package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.model.UserProduct;
import com.chinadrtv.erp.sales.core.model.OrderSoldDto;
import com.chinadrtv.erp.sales.core.service.OrderSoldService;
import com.chinadrtv.erp.tc.core.dto.OrderTopItem;
import com.chinadrtv.erp.tc.core.service.OrderdetService;
import com.chinadrtv.erp.tc.core.service.UserProductService;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-7-5
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Service("orderSoldService")
public class OrderSoldServiceImpl implements OrderSoldService {

    @Autowired
    private ItemInventoryService itemInventoryService;

    @Autowired
    private PlubasInfoService plubasInfoService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private OrderdetService orderdetService;


    @ReadThroughSingleCache(namespace = TOP20_FAVORITES_CACHE_KEY, expiration = 86400)
    public List<OrderSoldDto> getTop20Favorites(@ParameterValueKeyProvider String userId) {
        List<OrderSoldDto> list = new ArrayList<OrderSoldDto>();
        List<UserProduct> favorites = userProductService.getUserProducts(userId);
        for(UserProduct favorite : favorites){
            String ncfreeName = favorite.getProductType();
            if(ncfreeName == null)  ncfreeName = "";
            List<NcRealTimeStockItem> items = itemInventoryService.getNcRealTimeStock(favorite.getProductId(), "", ncfreeName);
            if(items.size() > 0){
                for(NcRealTimeStockItem item : items){
                    Double week = orderdetService.getOrderSoldQty(item.getNcCode(), ncfreeName, 7) / 7;
                           week = new BigDecimal(week).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    Double yestoday = orderdetService.getOrderSoldQty(item.getNcCode(), ncfreeName, 1);
                    list.add(new OrderSoldDto(item, favorite.getProductType(), week, yestoday, favorite.getId(), 0L));
                }
            } else {
                NcPlubasInfo item = plubasInfoService.getNcPlubasInfo(favorite.getProductId());
                list.add(new OrderSoldDto(item, ncfreeName, favorite.getId()));
            }
        }
        return list;
    }

    @InvalidateSingleCache(namespace = TOP20_FAVORITES_CACHE_KEY)
    public void updateTop20Favorites(@ParameterValueKeyProvider String userId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @ReadThroughSingleCache(namespace = TOP20_SALES_CACHE_KEY, expiration = 86400)
    public List<OrderSoldDto> getTop20Sales(@ParameterValueKeyProvider String userId) {
        List<OrderSoldDto> list = new ArrayList<OrderSoldDto>();
        List<OrderTopItem> tops = orderdetService.getCacheOrderTopItems(7);
        for(OrderTopItem top : tops){
            Double week = top.getSoldQty() / 7.0;
            week = new BigDecimal(week).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

            List<NcRealTimeStockItem> items = itemInventoryService.getNcRealTimeStock(top.getProdId(), "", top.getNcfreeName());
            if(items.size() > 0){
                for(NcRealTimeStockItem item : items){
                    Double yestoday = orderdetService.getOrderSoldQty(item.getNcCode(), top.getNcfreeName(), 1);
                    list.add(new OrderSoldDto(item, top.getNcfreeName(), week, yestoday, -1, 0L));
                }
            } else {
                NcPlubasInfo item = plubasInfoService.getNcPlubasInfo(top.getProdId());
                list.add(new OrderSoldDto(item, top.getNcfreeName(), -1));
            }
        }
        return list;
    }

    @InvalidateSingleCache(namespace = TOP20_SALES_CACHE_KEY)
    public void updateTop20Sales(@ParameterValueKeyProvider String userId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final String TOP20_SALES_CACHE_KEY=  "com.chinadrtv.erp.sales.core.service.impl.top20sales";
    private static final String TOP20_FAVORITES_CACHE_KEY=  "com.chinadrtv.erp.sales.core.service.impl.top20favorities";
}
