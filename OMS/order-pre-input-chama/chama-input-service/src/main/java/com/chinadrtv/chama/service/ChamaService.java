package com.chinadrtv.chama.service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-4
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public interface ChamaService {
    int getPreTradeByTradeId(String tradeId, String opsTradeId);
    boolean checkCompanyCode(String tmsCode);
    boolean checkSkuCode(String skuCode);
}
