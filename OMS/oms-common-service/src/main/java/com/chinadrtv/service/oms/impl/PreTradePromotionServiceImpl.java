package com.chinadrtv.service.oms.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinadrtv.model.oms.dto.PromotionDto;
import com.chinadrtv.service.oms.PreTradePromotionService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-5-7
 * Time: 下午12:56
 * To change this template use File | Settings | File Templates.
 * 调用促销接口转换json数据对象
 */
@Service("preTradePromotionService")
public class PreTradePromotionServiceImpl implements PreTradePromotionService {
//    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    /**
     * 返回结果集
     *
     * @return
     */
    public List<PromotionDto> getPromotionResult(String url, String tradeId) throws IOException {
        //促销服务返回的促销信息
        List<PromotionDto> promotionDtoList = new ArrayList<PromotionDto>();

        /*
        //调用促销服务
        String jsons = getUrl(url + tradeId);
        if (!"".equals(jsons) && !"[]".equals(jsons)) {
            //获取json数据
            List<Map> list = jsonBinder.fromJson(jsons, List.class);
            if ((list != null) && !list.isEmpty()) {
                Map map = null;
                PromotionDto promotionDto = null;
                //从map取值
                for (int i = 0; i < list.size(); i++) {
                    map = list.get(i);
                    promotionDto = new PromotionDto();
                    promotionDto.setVaid(map.get("isVaid").toString());
                    promotionDto.setTradeId(map.get("tradeId").toString());
                    promotionDto.setOutSkuId(map.get("outSkuId").toString());
                    promotionDto.setSkuId(map.get("skuId").toString());
                    promotionDto.setPromotionResultId(map.get("promotionResultId").toString());
                    promotionDto.setQty(map.get("qty").toString());
                    promotionDto.setPgroup(map.get("pgroup").toString());

                    promotionDtoList.add(promotionDto);
                }
            }

        }
        */

        return promotionDtoList;
    }

}
