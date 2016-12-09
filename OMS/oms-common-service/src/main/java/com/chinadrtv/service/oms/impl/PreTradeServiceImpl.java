package com.chinadrtv.service.oms.impl;

import java.util.List;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.iagent.dao.AreaAllDao;
import com.chinadrtv.dal.iagent.dao.CityAllDao;
import com.chinadrtv.dal.iagent.dao.CountyAllDao;
import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;
import com.chinadrtv.dal.iagent.dao.ProvinceDao;
import com.chinadrtv.dal.oms.dao.AreaMappingDao;
import com.chinadrtv.dal.oms.dao.CityMappingDao;
import com.chinadrtv.dal.oms.dao.CountyMappingDao;
import com.chinadrtv.dal.oms.dao.PreTradeCompanyDao;
import com.chinadrtv.dal.oms.dao.PreTradeDao;
import com.chinadrtv.dal.oms.dao.PreTradeDetailDao;
import com.chinadrtv.dal.oms.dao.PreTradeInsuranceDao;
import com.chinadrtv.dal.oms.dao.PreTradeLotDao;
import com.chinadrtv.dal.oms.dao.ProvinceMappingDao;
import com.chinadrtv.model.constant.TradeSourceConstants;
import com.chinadrtv.model.iagent.AreaAll;
import com.chinadrtv.model.iagent.CityAll;
import com.chinadrtv.model.iagent.CountyAll;
import com.chinadrtv.model.iagent.Province;
import com.chinadrtv.model.oms.AreaMapping;
import com.chinadrtv.model.oms.CityMapping;
import com.chinadrtv.model.oms.CountyMapping;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeCompany;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.ProvinceMapping;
import com.chinadrtv.service.oms.PreTradeService;

/**
 * User: liuhaidong
 * Date: 12-12-10
 */
@Service("preTradeService")
public class PreTradeServiceImpl implements PreTradeService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PreTradeServiceImpl.class);

    @Autowired
    private PreTradeLotDao preTradeLotDao;
    @Autowired
    private PreTradeDao preTradeDao;
    @Autowired
    private PreTradeDetailDao preTradeDetailDao;
    @Autowired
    private PreTradeCompanyDao preTradeCompany ;
    @Autowired
    private PlubasInfoDao plubasInfoDao;
    @Autowired
    private ProvinceMappingDao provinceMappingDao;
    @Autowired
    private CountyMappingDao countyMappingDao;
    @Autowired
    private CityMappingDao cityMappingDao;
    @Autowired
    private AreaMappingDao areaMappingDao;
    @Autowired
    private PreTradeInsuranceDao preTradeInsuranceDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CountyAllDao countyAllDao;
    @Autowired
    private CityAllDao cityAllDao;
    @Autowired
    private AreaAllDao areaAllDao;


    public PreTrade findByOpsId(String opsTradeId,Long sourceId){
    	PreTrade pt = new PreTrade();
    	pt.setOpsTradeId(opsTradeId);
    	pt.setSourceId(sourceId);
        return preTradeDao.findByOpsId(pt);
    }

    public void updateTmsCodeAndPayType(PreTrade preTrade) {
        String tradeType = preTrade.getTradeType() ;
        PreTradeCompany preTradeCompany1 = preTradeCompany.findByTradeType(tradeType) ;
        if (preTradeCompany1 != null){
            if ((preTrade.getTmsCode() == null) || "".equals(preTrade.getTmsCode()) || (preTradeCompany1.getIstranslated() == 1))
                preTrade.setTmsCode(String.valueOf(preTradeCompany1.getCompanyId())) ;
            //增加默认付款方式处理
            preTrade.setPaytype(preTradeCompany1.getPaytype());
        }
    }

    public List<String> getInsuranceSkus() {
        return preTradeInsuranceDao.getInsuranceSkus() ;
    }

    public void updateInsuranceSku(PreTrade preTrade, List<String> skus) {
        if (skus == null) return ;
        List<PreTradeDetail> preTradeDetailList = preTradeDetailDao.queryDetailByTradeId(preTrade.getTradeId());
        if (preTrade!=null && preTradeDetailList!=null){
            for(PreTradeDetail preTradeDetail : preTradeDetailList){
                if (preTradeDetail.getOutSkuId() != null){
                    if (skus.contains(preTradeDetail.getOutSkuId())){
                        preTradeDetail.setPrice(0d);
                        preTradeDetail.setUpPrice(0d);
                        preTradeDetail.setDiscountFee(0d);
                        preTradeDetail.setPayment(0d) ;
                        preTradeDetail.setShippingFee(0d);
                    }
                }
            }
        }
    }

    @Override
    public PreTrade queryPreTradeByTradeId(String tradeId) {
        PreTrade preTrade = preTradeDao.findByTradeId(tradeId);

        return preTrade;
    }

    @Override
    public void insert(PreTrade preTrade) {
        preTradeDao.insert(preTrade);
    }

    @Override
    public void updatePreTrade(PreTrade preTrade) {
        preTradeDao.updatePreTrade(preTrade);
    }
/*
    public String validatePreTrade(PreTrade preTrade) {
        String val = ModelValidator.validate(preTrade);
        return val;
    }*/

    public boolean checkSkuCode(String skuCode) {
        Boolean b = plubasInfoDao.checkSkuCode(skuCode);
        if(b!=null&&b.booleanValue()==true)
            return true;
        return false;
    }

    public void updateSkuTitle(PreTrade preTrade,List<PreTradeDetail> preTradeDetailList) {
        String skuTitle;
        //List<PreTradeDetail> preTradeDetailList = preTradeDetailDao.queryDetailByTradeId(preTrade.getTradeId());
        if (preTrade!=null && preTradeDetailList!=null){
            for(PreTradeDetail preTradeDetail : preTradeDetailList){
                if (preTradeDetail.getSkuName() == null){
                    skuTitle = plubasInfoDao.getSkuTitle(preTradeDetail.getOutSkuId());
                    preTradeDetail.setSkuName(skuTitle);
                }
            }
        }
    }

    /**
     * 把前3级地址根据映射关系对应到橡果地址 
     * 并做相应替换
     */
	public String checkReceiverAddress(PreTrade preTrade) {
		StringBuffer errorMsg = new StringBuffer();
		String receiverAddress = preTrade.getReceiverAddress()==null?"":preTrade.getReceiverAddress();
        preTrade.setReceiverAddress(receiverAddress);
        String province = preTrade.getReceiverProvince() == null?"":preTrade.getReceiverProvince();
		preTrade.setReceiverAddress(preTrade.getReceiverAddress().replace(province, ""));
		//check province
        logger.debug("check provice:"+province);
		ProvinceMapping provinceMapping = provinceMappingDao.findByName(province);
		if(provinceMapping!=null){
			
			//判断是否直辖市,如果是,则变换area的位置
            logger.debug("check proviceMapping:"+provinceMapping.getMappingProvinceid());
			Province provinceObj = provinceDao.queryById(provinceMapping.getMappingProvinceid());
			if(provinceObj.getChinese().equals(provinceObj.getCapital()) 
					&& preTrade.getReceiverArea()==null
					&& preTrade.getSourceId()==TradeSourceConstants.BUY360_SOURCE_ID){
				if(preTrade.getReceiverProvince().equals("北京") || preTrade.getReceiverProvince().equals("上海")){
					preTrade.setReceiverAddress(preTrade.getReceiverCounty()+":"+preTrade.getReceiverAddress());
					preTrade.setReceiverCounty(preTrade.getReceiverCity());
					preTrade.setReceiverCity(preTrade.getReceiverProvince());
				}
			}
			
			preTrade.setReceiverProvince(provinceObj.getChinese());
			
		}else{
			errorMsg.append("省份:[").append(province).append("]不匹配;");
		}
		
        String country = preTrade.getReceiverCounty()==null?"":preTrade.getReceiverCounty();
		preTrade.setReceiverAddress(preTrade.getReceiverAddress().replace(country, ""));
		//检查country
        logger.debug("check country:"+country);
		CountyMapping countryMapping = countyMappingDao.findByName(country);
		if(countryMapping!=null){
            logger.debug("check countryMapping:"+countryMapping.getMappingCountyid());
			CountyAll countyAll = countyAllDao.queryById(countryMapping.getMappingCountyid());
			preTrade.setReceiverCounty(countyAll.getCountyname());
		}else{
			errorMsg.append("区县:[").append(country).append("]不存在映射;");
		}
		
		//检查city
        String city = preTrade.getReceiverCity()==null?"":preTrade.getReceiverCity();
        logger.debug("check city:"+city);
		CityMapping cityMapping = cityMappingDao.findByName(city);
		if(cityMapping!=null){
            logger.debug("check cityMapping:"+cityMapping.getMappingCityid());
			preTrade.setReceiverAddress(preTrade.getReceiverAddress().replace(city, ""));
			CityAll cityAll = cityAllDao.queryById(cityMapping.getMappingCityid());
			preTrade.setReceiverCity(cityAll.getCityname());
		}else{
			errorMsg.append("城市:[").append(city).append("]不存在映射;");
		}
		
		
		//检查area
        String area = preTrade.getReceiverArea()==null?"":preTrade.getReceiverArea();
		if(preTrade.getReceiverArea()!=null){
			preTrade.setReceiverAddress(preTrade.getReceiverAddress().replace(area, ""));
            logger.debug("check area:"+area);
			AreaMapping areaMapping = areaMappingDao.findByName(area);
			if(areaMapping!=null){
                logger.debug("check areaMapping:"+areaMapping.getMappingAreaid());
				AreaAll areaAll = areaAllDao.queryById(areaMapping.getMappingAreaid());
				preTrade.setReceiverArea(areaAll.getAreaname());
			}else{
				errorMsg.append("街道:[").append(area).append("]不存在映射;");
			}
		}

				
		return errorMsg.toString();
	}
	
}
