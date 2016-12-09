package com.chinadrtv.erp.admin.service;
import com.chinadrtv.erp.model.PreTradeDetail;
import java.util.*;
/**
 * 前置订单详细服务
 * @author haoleitao
 * @date 2012-12-28 下午4:37:33
 *
 */
public interface PreTradeDetailService{
    PreTradeDetail getCompanyById(String preTradeDetailId);
    List<PreTradeDetail> getAllPreTradeDetail();
    List<PreTradeDetail> getAllPreTradeDetail(int index, int size);
    int getPreTradeDetailCount();
    void savePreTradeDetail(PreTradeDetail preTradeDetail);
    void addPreTradeDetail(PreTradeDetail preTradeDetail);
    void removePreTradeDetail(PreTradeDetail preTradeDetail);
    List<PreTradeDetail> getAllPreTradeDetailByPerTradeID(String preTradeID);
}
