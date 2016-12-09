package com.chinadrtv.service.oms;

import java.io.IOException;
import java.util.List;

import com.chinadrtv.model.oms.dto.PromotionDto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-5-7
 * Time: 下午12:54
 * To change this template use File | Settings | File Templates.
 */
public interface PreTradePromotionService {
    List<PromotionDto> getPromotionResult(String url, String tradeId) throws IOException;
}
