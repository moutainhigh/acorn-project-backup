package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.uc.dao.CardtypeDao;
import com.chinadrtv.erp.uc.service.CardtypeService;

/**
 * Created with IntelliJ IDEA.
 * Title: CardtypeServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service("cardtypeService")
public class CardtypeServiceImpl implements CardtypeService {

    @Autowired
    @Qualifier("cardtypeDao")
    private CardtypeDao cardtypeDao;

    @Override
    public List<Cardtype> queryUsefulCardtypes() {
        return cardtypeDao.queryUsefulCardtypes();
    }

    public List<String> getUsefulBanks() {
        List<String> banks = new ArrayList<String>();
        List<Cardtype> cardtypes = cardtypeDao.queryUsefulCardtypes();
        if(cardtypes != null)
        {
           for(Cardtype type : cardtypes){
              if(StringUtils.isNotBlank(type.getBankName()))
              {
                  if(!banks.contains(type.getBankName())){
                      banks.add(type.getBankName());
                  }
              }
           }

        }
        return banks;
    }

    public Cardtype getCardType(String cardTypeId)
    {
        try
        {
            return cardtypeDao.get(cardTypeId);
        }
        catch (Exception ex){
            //不存在返回空
            return null;
        }
    }

	@Override
	public List<Map<String, String>> getBanktypes() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> banks = cardtypeDao.getByCategory("2");
		for (int idx = 0; idx < banks.size(); idx++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", banks.get(idx));
			map.put("value", String.valueOf(idx));
			result.add(map);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "其他");
		map.put("value", String.valueOf(banks.size()));
		result.add(map);
		return result;
	}

	@Override
	public List<Cardtype> getAllBankCards() {
		return cardtypeDao.getAllBankCards();
	}
	
	public List<Cardtype> getCardtypes(List<String> cardtypeList)
    {
        return cardtypeDao.getCardtypes(cardtypeList);
    }
}
