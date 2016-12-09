package com.chinadrtv.erp.oms.service.impl;


import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.oms.dao.LogisticsFeeRuleDao;
import com.chinadrtv.erp.oms.service.LogisticsFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 承运商费用配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@Service("logisticsFeeRuleService")
public class LogisticsFeeRuleServiceImpl implements LogisticsFeeRuleService {
    @Autowired
    private LogisticsFeeRuleDao ruleDao;

    public List<LogisticsFeeRule> getActiveLogisticsFeeRules(Integer contractId){
        return ruleDao.getActiveLogisticsFeeRules(contractId);
    }

    public LogisticsFeeRule getLogisticsFeeRuleById(Integer ruleId){
        return ruleDao.get(ruleId);
    }
    public List<LogisticsFeeRule> getAllLogisticsFeeRules(Map<String, Object> params, int index, int size){
        return ruleDao.getAllLogisticsFeeRules(params, index, size);
    }
    public Long getLogisticsFeeRuleCount(Map<String, Object> params){
        return ruleDao.getLogisticsFeeRuleCount(params);
    }
    public void saveLogisticsFeeRule(LogisticsFeeRule rule){
        ruleDao.saveOrUpdate(rule);
    }
    public void addLogisticsFeeRule(LogisticsFeeRule rule){
        ruleDao.save(rule);
    }
    public void removeLogisticsFeeRule(Integer ruleId){
        ruleDao.remove(ruleId);
    }
}
