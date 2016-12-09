package com.chinadrtv.taobao.model;

import java.io.Serializable;

import com.chinadrtv.taobao.common.dal.model.TradeFeedback;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-7
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
public class TradeStatusModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private TaobaoOrderConfig taobaoOrderConfig;
    private TradeFeedback tradefeedback;
    

    public TaobaoOrderConfig getTaobaoOrderConfig() {
        return taobaoOrderConfig;
    }

    public void setTaobaoOrderConfig(TaobaoOrderConfig taobaoOrderConfig) {
        this.taobaoOrderConfig = taobaoOrderConfig;
    }

    public TradeFeedback getTradefeedback() {
        return tradefeedback;
    }

    public void setTradefeedback(TradeFeedback tradefeedback) {
        this.tradefeedback = tradefeedback;
    }
}
