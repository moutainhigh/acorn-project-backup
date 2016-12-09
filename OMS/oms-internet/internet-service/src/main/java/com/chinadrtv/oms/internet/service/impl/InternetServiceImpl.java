package com.chinadrtv.oms.internet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.iagent.dao.CompanyDao;
import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;
import com.chinadrtv.dal.oms.dao.PreTradeDao;
import com.chinadrtv.model.iagent.Company;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.oms.internet.dto.OpsTradeRequestDto;
import com.chinadrtv.oms.internet.dto.OpsTradeResponseDto;
import com.chinadrtv.oms.internet.service.InternetCheckService;
import com.chinadrtv.oms.internet.service.InternetService;
import com.chinadrtv.service.oms.PreTradeImportService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-4
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class InternetServiceImpl implements InternetService {
	
	private static final Logger logger = LoggerFactory.getLogger(InternetServiceImpl.class);

    @Autowired
    private PreTradeDao preTradeDao;
    @Autowired
    private PlubasInfoDao plubasInfoDao;
    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    private InternetCheckService internetCheckService;
    @Autowired
    private PreTradeImportService preTradeImportService;

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

	/** 
	 * <p>Title: importOrderList</p>
	 * <p>Description: </p>
	 * @param xml
	 * @return
	 * @throws Exception 
	 * @see com.chinadrtv.oms.internet.service.InternetService#importOrderList(java.lang.String)
	 */ 
	@Override
	public OpsTradeResponseDto importOrderList(OpsTradeRequestDto opsTradeRequestDto) throws Exception {

		// 检查数据的合法性
		OpsTradeResponseDto  responseDto = internetCheckService.checkError(opsTradeRequestDto.getOps_trade());
		
		logger.info("check valid: " + responseDto);

		if (!responseDto.getMessage_code().equals("") && responseDto.getMessage_code().equals("013")) {
			String customerId = "官网子网站订单";
			PreTradeLotDto p = internetCheckService.routeTradeInfo(customerId, opsTradeRequestDto.getOps_trade());
			// 数据保存
			preTradeImportService.importPretrades(p);
			logger.info("save success !");
		}

		return responseDto;
	}
}
