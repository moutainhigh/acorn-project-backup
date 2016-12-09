package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.model.AmortisationPK;
import com.chinadrtv.erp.sales.core.dao.AmortisationDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class AmortisationDaoImpl extends GenericDaoHibernateBase<Amortisation,AmortisationPK> implements AmortisationDao {
    public AmortisationDaoImpl()
    {
        super(Amortisation.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<Amortisation> queryCardTypeAmortisations(String cardTypeId)
    {
        List<Amortisation> amortisationList = this.findList("from Amortisation where cardtypeid=:cardtypeid and valid='-1'", new ParameterString("cardtypeid",cardTypeId));
        return amortisationList;
    }
}
