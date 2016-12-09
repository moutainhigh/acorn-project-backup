package com.chinadrtv.erp.oms.service.impl;


import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsPriceRuleDetail;
import com.chinadrtv.erp.oms.dao.LogisticsFeeRuleDao;
import com.chinadrtv.erp.oms.dao.LogisticsPriceRuleDetailDao;
import com.chinadrtv.erp.oms.service.LogisticsPriceRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 承运商费用明细配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@Service("logisticsPriceRuleDetailService")
public class LogisticsPriceRuleDetailServiceImpl implements LogisticsPriceRuleDetailService {
    @Autowired
    private LogisticsPriceRuleDetailDao logisticsPriceRuleDetailDao;

    public LogisticsPriceRuleDetail getPriceRuleDetailById(Integer detailId){
        return logisticsPriceRuleDetailDao.get(detailId);
    }
    public void save(LogisticsPriceRuleDetail detail){
        logisticsPriceRuleDetailDao.saveOrUpdate(detail);
    }
    public void add(LogisticsPriceRuleDetail detail){
        logisticsPriceRuleDetailDao.save(detail);
    }
    public void remove(Integer detailId){
        logisticsPriceRuleDetailDao.remove(detailId);
    }

    public List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId){
        return logisticsPriceRuleDetailDao.getPriceRuleDetailsByRuleId(ruleId);
    }

    public Long getPriceRuleDetailCount(Integer ruleId){
        return logisticsPriceRuleDetailDao.getPriceRuleDetailCount(ruleId);
    }

    public List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId, int index, int size){
        return logisticsPriceRuleDetailDao.getPriceRuleDetailsByRuleId(ruleId, index, size);
    }
}
