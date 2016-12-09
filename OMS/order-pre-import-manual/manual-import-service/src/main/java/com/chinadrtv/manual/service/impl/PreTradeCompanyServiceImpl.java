package com.chinadrtv.manual.service.impl;

import com.chinadrtv.dal.oms.dao.PreTradeCompanyDao;
import com.chinadrtv.manual.service.PreTradeCompanyService;
import com.chinadrtv.model.oms.PreTradeCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-5
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
@Service("preTradeCompanyService")
public class PreTradeCompanyServiceImpl implements PreTradeCompanyService {
    @Autowired
    private PreTradeCompanyDao preTradeCompanyDao;
    @Override
    public long getPreTradeSourceId(String tradeType) {
        PreTradeCompany preTradeCompany = preTradeCompanyDao.findByTradeType(tradeType);
        if (preTradeCompany == null){
            return -1 ;
        }
        else{
            return preTradeCompany.getSourceId();
        }
    }
}
