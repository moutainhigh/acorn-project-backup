package com.chinadrtv.model.oms.dto;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-5-7
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 * JSON数据转换
 */
public class PromotionDto  {
    private String isVaid;
    private String tradeId;
    private String outSkuId;
    private String skuId;
    private String promotionResultId;
    private String qty;
    private String pgroup;

    public String getVaid() {
        return isVaid;
    }

    public void setVaid(String vaid) {
        isVaid = vaid;
    }

    public String getPgroup() {
        return pgroup;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPromotionResultId() {
        return promotionResultId;
    }

    public void setPromotionResultId(String promotionResultId) {
        this.promotionResultId = promotionResultId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
