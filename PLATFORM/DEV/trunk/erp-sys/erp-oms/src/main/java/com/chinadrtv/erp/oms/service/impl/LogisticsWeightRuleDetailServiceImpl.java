package com.chinadrtv.erp.oms.service.impl;

/**
 * 承运商费用明细配置服务(按重量)
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */

import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;
import com.chinadrtv.erp.oms.dao.LogisticsWeightRuleDetailDao;
import com.chinadrtv.erp.oms.service.LogisticsWeightRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logisticsWeightRuleDetailService")
public class LogisticsWeightRuleDetailServiceImpl implements LogisticsWeightRuleDetailService {
    @Autowired
    private LogisticsWeightRuleDetailDao logisticsWeightRuleDetailDao;

    public LogisticsWeightRuleDetail getWeightRuleDetailById(Integer detailId){
        return logisticsWeightRuleDetailDao.get(detailId);
    }
    public void save(LogisticsWeightRuleDetail detail){
        logisticsWeightRuleDetailDao.saveOrUpdate(detail);
    }
    public void add(LogisticsWeightRuleDetail detail){
        logisticsWeightRuleDetailDao.save(detail);
    }
    public void remove(Integer detailId){
        logisticsWeightRuleDetailDao.remove(detailId);
    }

    public List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId){
        return logisticsWeightRuleDetailDao.getWeightRuleDetailsByRuleId(ruleId);
    }

    public Long getWeightRuleDetailCount(Integer ruleId){
        return logisticsWeightRuleDetailDao.getWeightRuleDetailCount(ruleId);
    }

    public List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId, int index, int size){
        return logisticsWeightRuleDetailDao.getWeightRuleDetailsByRuleId(ruleId, index, size);
    }
}
