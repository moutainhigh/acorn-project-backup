package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Date;
import java.util.List;

/**
 * PreTradeDetailDaoImpl
 * @author haoleitao
 *
 */
@Repository
public class PreTradeDetailDaoImpl extends GenericDaoHibernate<PreTradeDetail, Long> implements PreTradeDetailDao{

	public PreTradeDetailDaoImpl() {
	    super(PreTradeDetail.class);
	}
	
	
	public PreTradeDetail getPreTradeDetailById(String preTradeDetailId) {
        Session session = getSession();
        return (PreTradeDetail)session.get(PreTradeDetail.class, preTradeDetailId);
    }
	
	public List<PreTradeDetail> getPreTradeDetailByPreTradeId(String preTradeId) {
		   Session session = getSession();
	        Query query = session.createQuery("from PreTradeDetail where tradeId = :preTradeId and isActive = :isActive and isVaid = :isVaid  ");
	        query.setString("preTradeId", preTradeId);
	        query.setInteger("isActive", 1);
            query.setInteger("isVaid", 1);
        return query.list();
    }
	
	    public List<PreTradeDetail> getAllPreTradeDetail()
    {
        Session session = getSession();
        Query query = session.createQuery("from PreTradeDetail");
        return query.list();
    }
    public List<PreTradeDetail> getAllPreTradeDetail(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from PreTradeDetail");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(PreTradeDetail preTradeDetail){
        try
        {
    	Object id = getSession().save(preTradeDetail);
        getSession().flush();
        System.out.println("id:"+id);
        }
        catch(Exception ex){
        	System.out.println(ex.getMessage());
        }
        
    }
	

}
