package com.chinadrtv.amazon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-1-21
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public class TradeResultList {
    private String processStatus ;
    private List<TradeResultInfo> tradeResultInfos = new ArrayList<TradeResultInfo>() ;

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public List<TradeResultInfo> getTradeResultInfos() {
        return tradeResultInfos;
    }

    public void setTradeResultInfos(List<TradeResultInfo> tradeResultInfos) {
        this.tradeResultInfos = tradeResultInfos;
    }
}
