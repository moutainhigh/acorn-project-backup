package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.oms.service.CardtypeService;
import com.chinadrtv.erp.oms.dao.CardtypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("cardtypeService")
public class CardtypeServiceImpl implements CardtypeService{

    @Autowired
    private CardtypeDao cardtypeDao;


    public void addCardtype(Cardtype cardtype) {
        cardtypeDao.save(cardtype);
    }



    public void saveCardtype(Cardtype cardtype) {
        cardtypeDao.saveOrUpdate(cardtype);
    }

    public void delCardtype(Long id) {
        //cardtypeDao.remove(id);
    }
	
	public Cardtype getCardtypeById(String cardtypeId){
		return cardtypeDao.get(cardtypeId);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.CardtypeService#getAllCardtype()
	 */
	public List getAllCardtype() {
		// TODO Auto-generated method stub
		return cardtypeDao.getAllCardtype();
	}

    /**
     * 获取信用卡类型-gdj 2013-03-12
     * @return
     */
    public List<String> getCreditCardNames(){
        return cardtypeDao.getCreditCardNames();
    }

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.CardtypeService#getAllCardtype(int, int)
	 */
	public List<Cardtype> getAllCardtype(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.CardtypeService#getCardtypeCount()
	 */
	public int getCardtypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.CardtypeService#removeCardtype(com.chinadrtv.erp.model.Cardtype)
	 */
	public void removeCardtype(Cardtype cardtype) {
		// TODO Auto-generated method stub
		
	}
}
