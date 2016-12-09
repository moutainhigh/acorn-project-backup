package com.chinadrtv.chama.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.chama.service.ChamaService;
import com.chinadrtv.dal.iagent.dao.CompanyDao;
import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;
import com.chinadrtv.dal.oms.dao.PreTradeDao;
import com.chinadrtv.model.iagent.Company;
import com.chinadrtv.model.oms.PreTrade;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-4
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Service("chamaService")
public class ChamaServiceImpl implements ChamaService {

    @Autowired
    private PreTradeDao preTradeDao;
    @Autowired
    private PlubasInfoDao plubasInfoDao;
    @Autowired
    private CompanyDao companyDao;

    /**
     * 检查前置订单是否已存在
     * @param tradeId
     * @param opsTradeId
     * @return
     */
    public int getPreTradeByTradeId(String tradeId, String opsTradeId) {
        PreTrade preTrade = new PreTrade();
        preTrade.setTradeId(tradeId);
        preTrade.setOpsTradeId(opsTradeId);

        PreTrade p = preTradeDao.findByTradeIdOrOpsId(preTrade);
        if(p != null){
            return 1;
        }
        return 0;
    }

    /**
     * 检查Company是否存在
     * @param tmsCode
     * @return
     */
    public boolean checkCompanyCode(String tmsCode) {
        if (null == tmsCode || "".equals(tmsCode))
            return true ;
        else {
            Company c = companyDao.findByCompanyId(tmsCode);
            if (c == null)
                return false;
        }
        return true;
    }

    /**
     * 检查是否存在PLUBASINFO
     * @param skuCode
     * @return
     */
    public boolean checkSkuCode(String skuCode) {
        boolean b = false;
        b = plubasInfoDao.checkSkuCode(skuCode);
        return b;
    }
}
